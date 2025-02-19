package com.Ecommerce.e_commerce.repo;

import com.Ecommerce.e_commerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    public User findByUsername(String username);
    public User findByUsernameOrEmail(String username, String email);
    public User findByEmail(String email);
    public Optional<User> findById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.address = :address WHERE u.id = :userId")
    public void updateAddress(@Param("userId") UUID userId, @Param("address") String address);

    @Modifying
    @Transactional
    @Query("UPDATE User u set u.password = :password WHERE u.id = :userId")
    public void updatePassword(@Param("userId") UUID userId, @Param("password") String password);
}
