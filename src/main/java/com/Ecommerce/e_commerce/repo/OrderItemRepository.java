package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.DTO.OrderItemDTO;
import com.Ecommerce.e_commerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Query("""
            SELECT new com.Ecommerce.e_commerce.DTO.OrderItemDTO(oi.id, oi.product, oi.quantity) FROM OrderItem oi WHERE oi.order.id = :orderId
            """)
    public List<OrderItemDTO> getAllOrderItems(@Param("orderId") UUID orderId);
}

