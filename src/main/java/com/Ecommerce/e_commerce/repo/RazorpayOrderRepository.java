package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.RazorpayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RazorpayOrderRepository extends JpaRepository<RazorpayOrder, String> {
}
