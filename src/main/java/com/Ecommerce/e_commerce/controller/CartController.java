package com.Ecommerce.e_commerce.controller;

import com.Ecommerce.e_commerce.DTO.CartDTO;
import com.Ecommerce.e_commerce.DTO.RmCartDTO;
import com.Ecommerce.e_commerce.model.Cart;
import com.Ecommerce.e_commerce.model.User;
import com.Ecommerce.e_commerce.repo.UserRepo;
import com.Ecommerce.e_commerce.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    UserRepo userRepo;

//    @GetMapping("/{userId}")
//    public List<Cart> getCartByUser(HttpServletRequest request){
//        User user = userRepo.findByUsername(request.getAttribute("username").toString());
//        return cartService.getCartByUser(user);
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> addCart(@RequestBody CartDTO cart){
        return cartService.addToCart(cart);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateQuantity(@RequestBody CartDTO cart){
        return cartService.updateQuantity(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Object> deleteAllCart(@RequestParam("userId") UUID userId){
        return cartService.deleteAllCart(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getCartItem(@PathVariable UUID userId){
        return cartService.getCartItems(userId);
    }
}
