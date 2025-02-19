package com.Ecommerce.e_commerce.service;

import com.Ecommerce.e_commerce.DTO.*;
import com.Ecommerce.e_commerce.model.Cart;
import com.Ecommerce.e_commerce.model.Product;
import com.Ecommerce.e_commerce.model.User;
import com.Ecommerce.e_commerce.repo.CartRepository;
import com.Ecommerce.e_commerce.repo.ProductRepository;
import com.Ecommerce.e_commerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepository productRepository;


    public List<Cart> getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public ResponseEntity<Object> addToCart(CartDTO cartDTO) {
        try {
            if (cartDTO == null) {
                return ResponseEntity.badRequest().body(new ErrorDTO("Invalid request data", 400));
            }

            boolean isProductInCart = cartRepository.isProductThere(cartDTO.getUserId(), cartDTO.getProductId());
            System.out.println("Is product there : " + isProductInCart);
            if(isProductInCart){
                System.out.println("Working");
                cartRepository.addQuantity(cartDTO.getUserId(), cartDTO.getProductId());
                List<Cart> cartList = cartRepository.findByUserId(cartDTO.getUserId());
                List<CartResponseDTO> cartResponse = new ArrayList<>();
                for(Cart c : cartList){
                    Optional<Product> product = productRepository.findById(c.getProduct().getId());
                    CartResponseDTO dto = new CartResponseDTO();
                    dto.setId(c.getId());
                    dto.setPrice(c.getPrice());
                    dto.setQuantity(c.getQuantity());
                    dto.setProduct(product.get());
                    cartResponse.add(dto);
                }
                return ResponseEntity.status(HttpStatus.OK).body(cartResponse);
            }

            Optional<User> userOpt = userRepo.findById(cartDTO.getUserId());
            Optional<Product> productOpt = productRepository.findById(cartDTO.getProductId());

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("User not found", 404));
            }

            if (productOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO("Product not found", 404));
            }

            Cart cart = new Cart();
            cart.setUser(userOpt.get());
            cart.setProduct(productOpt.get());
            cart.setQuantity(cartDTO.getQuantity());
            cart.setPrice(productOpt.get().getPrice().multiply(BigDecimal.valueOf(cartDTO.getQuantity())));

            cartRepository.save(cart);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Product added to cart", 201));
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error while adding to cart", 500));
        }
    }

    public ResponseEntity<Object> updateQuantity(CartDTO cartDTO) {
        try{

            if(cartDTO == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Body not found.", 400));
            }

            if(cartDTO.getQuantity() == 0){
                cartRepository.deleteCartQuery(cartDTO.getUserId(), cartDTO.getProductId());
            }

            cartRepository.updateCartQuantity(cartDTO.getUserId(), cartDTO.getProductId(), cartDTO.getQuantity());
            Integer quantity = cartRepository.findCartQuantity(cartDTO.getUserId(), cartDTO.getProductId());
            List<Cart> cartList = cartRepository.findByUserId(cartDTO.getUserId());
            List<CartResponseDTO> cartResponse = new ArrayList<>();
            for(Cart c : cartList){
                Optional<Product> product = productRepository.findById(c.getProduct().getId());
                CartResponseDTO dto = new CartResponseDTO();
                dto.setId(c.getId());
                dto.setPrice(c.getPrice());
                dto.setQuantity(c.getQuantity());
                dto.setProduct(product.get());
                cartResponse.add(dto);
            }

//            if(quantity <= 0){
//                cartRepository.deleteCartQuery(cartDTO.getUserId(), cartDTO.getProductId());
//            }

            return ResponseEntity.status(HttpStatus.OK).body(cartResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(
                    "Error while updating.",
                    501
            ));
        }
    }

    public ResponseEntity<Object> deleteAllCart(UUID userId) {
        try{

            if(userId == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Body not found.", 400));
            }

            cartRepository.deleteAllCartQuery(userId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Deleted Successfully", 200));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error while removing", 501));
        }
    }

    public ResponseEntity<Object> getCartItems(UUID userId) {
        System.out.println("working");
        try{
            List<Cart> cartList = cartRepository.findByUserId(userId);
            List<CartResponseDTO> cartResponse = new ArrayList<>();
            for(Cart c : cartList){
                Optional<Product> product = productRepository.findById(c.getProduct().getId());
                CartResponseDTO dto = new CartResponseDTO();
                dto.setId(c.getId());
                dto.setPrice(c.getPrice());
                dto.setQuantity(c.getQuantity());
                dto.setProduct(product.get());
                cartResponse.add(dto);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cartResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error while retriving data.", 500));
        }
    }
}
