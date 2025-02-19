package com.Ecommerce.e_commerce.controller;

import com.Ecommerce.e_commerce.DTO.ErrorDTO;
import com.Ecommerce.e_commerce.DTO.PaymentDTO;
import com.Ecommerce.e_commerce.DTO.PaymentVerificationDTO;
import com.Ecommerce.e_commerce.DTO.ResponseDTO;
import com.Ecommerce.e_commerce.service.RazorpayService;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    RazorpayService razorpayService;

    @PostMapping("/razorpay")
    public ResponseEntity<Object> payment(@RequestBody PaymentDTO paymentDTO){
        try{
            Order order = razorpayService.payment(paymentDTO);
            Map<String, String> response = new HashMap<>();
            response.put("id", order.get("id"));
            response.put("currency", order.get("currency"));
            response.put("amount", order.get("amount").toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error while making payment", 500));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Object> verify(@RequestBody PaymentVerificationDTO paymentVerificationDTO){
        try{
            boolean isVerified = razorpayService.verifyRazorpayPayment(paymentVerificationDTO);
            if(isVerified){
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("payment successfull.", 200));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Payment verification failed", 400));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error while verification.", 500));
        }
    }
}
