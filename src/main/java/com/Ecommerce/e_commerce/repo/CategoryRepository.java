package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    public Category findByName(String name);
}
