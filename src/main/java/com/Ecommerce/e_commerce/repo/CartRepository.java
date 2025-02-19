package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.Cart;
import com.Ecommerce.e_commerce.model.Product;
import com.Ecommerce.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    List<Cart> findByUser(User user);

    Optional<Cart> findByUserAndProduct(User user, Product product);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Cart c SET c.quantity = :quantity,
            c.price = (SELECT p.price FROM Product p WHERE p.id = :productId)
            WHERE c.user.id = :userId AND c.product.id = :productId
            """)
    void updateCartQuantity(@Param("userId") UUID userId, @Param("productId") UUID productId, @Param("quantity") int quantity);

    @Query("""
            SELECT c.quantity from Cart c 
            WHERE c.user.id = :userId AND c.product.id = :productId
            """)
    Integer findCartQuantity(@Param("userId") UUID userId, @Param("productId") UUID productId);

    @Modifying
    @Transactional
    @Query("DELETE Cart c WHERE c.user.id = :userId AND c.product.id = :productId")
    void deleteCartQuery(@Param("userId") UUID userId, @Param("productId") UUID productId);

    @Modifying
    @Transactional
    @Query("DELETE Cart c WHERE c.user.id = :userId")
    void deleteAllCartQuery(@Param("userId") UUID userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT EXISTS (SELECT 1 FROM Cart c WHERE c.user.id = :userId AND c.product.id = :productId)")
    boolean isProductThere(@Param("userId") UUID userId, @Param("productId") UUID productId);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Cart c SET c.quantity = c.quantity + 1
            WHERE c.user.id = :userId AND c.product.id = :productId
            """)
    void addQuantity(@Param("userId") UUID userId, @Param("productId") UUID productId);
}