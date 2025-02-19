package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.Order;
import com.Ecommerce.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);

    @Query("""
            SELECT o FROM Order o WHERE o.user.id = :userId
            """)
    public List<Order> getAllOrder(@Param("userId") UUID userId);
}
