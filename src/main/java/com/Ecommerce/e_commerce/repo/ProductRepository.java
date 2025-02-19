package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.Category;
import com.Ecommerce.e_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(Category category);
    Optional<Product> findById(UUID id);
}
