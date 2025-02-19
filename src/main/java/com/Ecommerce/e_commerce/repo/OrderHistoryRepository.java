package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.OrderHistory;
import com.Ecommerce.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, UUID> {
    List<OrderHistory> findByUser(User user);
}
