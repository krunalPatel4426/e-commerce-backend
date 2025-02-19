package com.Ecommerce.e_commerce.DTO;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.util.Date;

public class RazorpayOrderDTO {
    private String orderId;
    private BigDecimal totalAmount;

    public RazorpayOrderDTO(String orderId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
