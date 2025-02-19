package com.Ecommerce.e_commerce.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment")
public class RazorpayOrder {
    @Id
    private String payment_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    private String currency;
    private String reciept_id;
    private String status;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReciept_id() {
        return reciept_id;
    }

    public void setReciept_id(String reciept_id) {
        this.reciept_id = reciept_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
