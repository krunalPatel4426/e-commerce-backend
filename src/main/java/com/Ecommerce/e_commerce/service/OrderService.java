package com.Ecommerce.e_commerce.service;

import com.Ecommerce.e_commerce.DTO.*;
import com.Ecommerce.e_commerce.model.*;
import com.Ecommerce.e_commerce.repo.OrderItemRepository;
import com.Ecommerce.e_commerce.repo.OrderRatingRepository;
import com.Ecommerce.e_commerce.repo.OrderRepository;
import com.Ecommerce.e_commerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRatingRepository orderRatingRepository;

    @Autowired
    RazorpayService razorpayService;

    public ResponseEntity<Object> makeOrder(OrderRequestDTO orderRequestDTO) {
        try{
            Optional<User> user = userRepo.findById(orderRequestDTO.getUserId());
            Order order = new Order();
            order.setUser(user.get());
            order.setTotalPrice(orderRequestDTO.getTotalAmount());
            order.setStatus(OrderStatus.PENDING);
            Order savedOrder = orderRepository.save(order);
            List<CartResponseDTO> cartList = orderRequestDTO.getCartData();
            for(CartResponseDTO cartResponseDTO : cartList){
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setPrice(orderRequestDTO.getTotalAmount());
                orderItem.setProduct(cartResponseDTO.getProduct());
                orderItem.setQuantity(cartResponseDTO.getQuantity());
                orderItemRepository.save(orderItem);
            }

//            com.razorpay.Order razorpayOrder = razorpayService.order(orderRequestDTO, savedOrder, user.get());
//
//            return ResponseEntity.status(HttpStatus.OK).body(new RazorpayOrderDTO(razorpayOrder.get("id"), orderRequestDTO.getTotalAmount()));

            return ResponseEntity.status(HttpStatus.OK).body(new RazorpayOrderDTO(savedOrder.getId().toString(), order.getTotalPrice()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error while making order", 500));
        }

    }

    public ResponseEntity<Object> getAllOrderDetails(UUID userId) {
        try{
            List<Order> orderList = orderRepository.getAllOrder(userId);
            List<OrderResponseDTO> response = new ArrayList<>();
            for(Order order: orderList){
                List<OrderItemDTO> orderItems = orderItemRepository.getAllOrderItems(order.getId());
                List<OrderRating> orderRatings = orderRatingRepository.getAllRatings(order.getId());
                OrderResponseDTO o = new OrderResponseDTO();
                o.setOrderId(order.getId());
                o.setOrderStatus(order.getStatus());
                o.setUserId(userId);
                o.setOrderItems(orderItems);
//                o.setOrderRatings(orderRatings);
                o.setTotalPrice(order.getTotalPrice());
                o.setOrderDate(order.getOrderDate());

                response.add(o);
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error while fetching order details.", 500));
        }
    }
}