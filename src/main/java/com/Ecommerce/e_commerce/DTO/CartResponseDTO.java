package com.Ecommerce.e_commerce.DTO;

import com.Ecommerce.e_commerce.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class CartResponseDTO {
    private UUID id;
    private BigDecimal price;
    private int quantity;
    private Product product;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
