package com.Ecommerce.e_commerce.controller;

import com.Ecommerce.e_commerce.DTO.ChangePasswordReqDTO;
import com.Ecommerce.e_commerce.DTO.LoginRequest;
import com.Ecommerce.e_commerce.DTO.UpdateAddressReqDTO;
import com.Ecommerce.e_commerce.model.User;
import com.Ecommerce.e_commerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> verify(@RequestParam String token){
        return userService.verifyToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest, HttpServletResponse res){
        return userService.login(loginRequest, res);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getUserDetials(@PathVariable UUID userId){
        return userService.getUserDetials(userId);
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<Object> changePassword(@RequestParam("userId") UUID userId, @RequestBody ChangePasswordReqDTO changePasswordReqDTO){
        return userService.changePassword(userId, changePasswordReqDTO);
    }

    @PostMapping("user/update-address")
    public ResponseEntity<Object> updateAddress(@RequestParam("userId") UUID userId, @RequestBody UpdateAddressReqDTO updateAddressReqDTO){
        return userService.updateAddress(userId, updateAddressReqDTO);
    }

    @GetMapping("/user/order")
    public ResponseEntity<Object> getOrderDetails(@RequestParam("userId") UUID userId){
        return userService.getOrderDetails(userId);
    }
}
