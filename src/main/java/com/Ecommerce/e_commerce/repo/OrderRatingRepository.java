package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.Order;
import com.Ecommerce.e_commerce.model.OrderRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRatingRepository extends JpaRepository<OrderRating, UUID> {
    List<OrderRating> findByOrder(Order order);

    @Query("""
            SELECT or FROM OrderRating or WHERE or.order.id = :orderId
            """)
    public List<OrderRating> getAllRatings(@Param("orderId") UUID orderId);
}
