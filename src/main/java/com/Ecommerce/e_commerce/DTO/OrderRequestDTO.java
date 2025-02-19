package com.Ecommerce.e_commerce.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderRequestDTO {
    private UUID userId;
    private List<CartResponseDTO> cartData;
    private BigDecimal totalAmount;
    private String address;
    private String email;
    private String phone;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<CartResponseDTO> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartResponseDTO> cartData) {
        this.cartData = cartData;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "OrderRequestDTO{" +
                "userId=" + userId +
                ", cartData=" + cartData +
                ", totalAmount=" + totalAmount +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrderRequestDTO(UUID userId, List<CartResponseDTO> cartData, BigDecimal totalAmount, String address, String email, String phone) {
        this.userId = userId;
        this.cartData = cartData;
        this.totalAmount = totalAmount;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }
}
