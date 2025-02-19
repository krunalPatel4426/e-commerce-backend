package com.Ecommerce.e_commerce.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDTO {
    private UUID orderId;
    private BigDecimal amount;
    private UUID userId;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
