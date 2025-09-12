package com.ticketsystem.repository;

import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);

}
