package com.ticketsystem.repository;

import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);
//    User findByUserId(int id);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Optional<Integer> findIdByUsername(@Param("username") String username);

    @Query("SELECT u.fullName FROM User u WHERE u.id = :id")
    Optional<String> findFullName(@Param("id") Integer id);

    Optional<User> findByEmail(String email);
}
