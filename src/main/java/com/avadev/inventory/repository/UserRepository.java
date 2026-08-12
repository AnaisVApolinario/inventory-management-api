package com.avadev.inventory.repository;

import com.avadev.inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
