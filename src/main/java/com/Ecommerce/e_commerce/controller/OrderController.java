package com.Ecommerce.e_commerce.controller;

import com.Ecommerce.e_commerce.DTO.ErrorDTO;
import com.Ecommerce.e_commerce.DTO.OrderRequestDTO;
import com.Ecommerce.e_commerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> Order(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.makeOrder(orderRequestDTO);
    }

    @GetMapping("/getOrder")
    ResponseEntity<Object> getAllOrder(@RequestParam("userId")UUID userId){
        try{
            return orderService.getAllOrderDetails(userId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDTO("Error from our side.", 500));
        }
    }
}
