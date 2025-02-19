package com.Ecommerce.e_commerce.DTO;

import com.Ecommerce.e_commerce.model.Product;
import com.Ecommerce.e_commerce.model.User;

import java.util.UUID;

public class CartDTO {
    private UUID userId;
    private UUID productId;
    private int quantity;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
