package com.Ecommerce.e_commerce.DTO;

import com.Ecommerce.e_commerce.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderItemDTO {
    private UUID id;
    private Product product;
    private int quantity;

    public OrderItemDTO(UUID id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
