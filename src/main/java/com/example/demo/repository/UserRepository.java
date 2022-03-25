package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<Boolean> existsByemail(String email);

    Optional<Boolean> existsByphone(String phone);

    Optional<Boolean> existsByusername(String username);

    Optional<Boolean> existsByUsernameOrEmailOrPhone(String username, String email, String phone);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String username, String email);
}
