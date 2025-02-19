package com.Ecommerce.e_commerce.service;

import com.Ecommerce.e_commerce.DTO.PaymentDTO;
import com.Ecommerce.e_commerce.DTO.PaymentVerificationDTO;
import com.Ecommerce.e_commerce.model.Order;
import com.Ecommerce.e_commerce.model.RazorpayOrder;
import com.Ecommerce.e_commerce.model.User;
import com.Ecommerce.e_commerce.repo.OrderRepository;
import com.Ecommerce.e_commerce.repo.RazorpayOrderRepository;
import com.Ecommerce.e_commerce.repo.UserRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class RazorpayService {
    @Value("${RAZORPAY_KEY_ID}")
    private String RAZORPAY_KEY_ID;

    @Value("${RAZORPAY_SERCET}")
    private String RAZORPAY_SERCET;

    @Autowired
    RazorpayOrderRepository razorpayOrderRepository;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderRepository orderRepository;

    public com.razorpay.Order payment(PaymentDTO paymentDTO) {
        try {

            Optional<Order> DBOrder = orderRepository.findById(paymentDTO.getOrderId());
            Optional<User> user = userRepo.findById(paymentDTO.getUserId());

            RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_SERCET);
            JSONObject orderRequestJson = new JSONObject();
            orderRequestJson.put("amount", DBOrder.get().getTotalPrice().intValue() * 100);
            orderRequestJson.put("currency", "INR");
            orderRequestJson.put("receipt", "order_receipt_"+ user.get().getName());
            com.razorpay.Order razorpayOrderDTO = razorpayClient.orders.create(orderRequestJson);
            RazorpayOrder razorpayOrder = new RazorpayOrder();

            razorpayOrder.setAmount(DBOrder.get().getTotalPrice());
            razorpayOrder.setCurrency("INR");
            razorpayOrder.setUser(user.orElse(null));
            razorpayOrder.setPayment_id(razorpayOrderDTO.get("id"));
            razorpayOrder.setReciept_id(razorpayOrderDTO.get("receipt"));
            razorpayOrder.setStatus("CREATED");
            razorpayOrderRepository.save(razorpayOrder);

            return razorpayOrderDTO;
        } catch (RazorpayException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean verifyRazorpayPayment(PaymentVerificationDTO paymentVerificationDTO) {
        try{
            String razorpayPaymentId = paymentVerificationDTO.getRazorpayPaymentId();
            String razorpayOrderId = paymentVerificationDTO.getRazorpayOrderId();
            String razorpaySignature = paymentVerificationDTO.getRazorpaySignature();
            System.out.println(razorpayPaymentId);
            System.out.println(razorpayOrderId);
            System.out.println(razorpaySignature);
            String generateSignature = generateSignature(razorpayPaymentId, razorpayOrderId, RAZORPAY_SERCET);
            System.out.println("sign" + generateSignature);
            return generateSignature.equals(razorpaySignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateSignature(String razorpayPaymentId, String razorpayOrderId, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        try{
            String hmacString = razorpayOrderId + "|" + razorpayPaymentId;
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKeySpec);

            byte[] hash = sha256Hmac.doFinal(hmacString.getBytes());
            return Hex.encodeHexString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
