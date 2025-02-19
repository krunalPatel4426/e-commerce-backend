package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.Product;
import com.Ecommerce.e_commerce.model.ProductRating;
import com.Ecommerce.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, UUID> {
    List<ProductRating> findByProduct(Product product);
    List<ProductRating> findByUser(User user);
}
