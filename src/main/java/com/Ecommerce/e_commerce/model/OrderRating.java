package com.Ecommerce.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_ratings")
public class OrderRating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonManagedReference
    private User user;

    @ManyToOne
//    @JsonManagedReference("order_rating")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    @Override
    public String toString() {
        return "OrderRating{" +
                "id=" + id +
                ", user=" + user +
                ", order=" + order +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
