package com.ticketsystem.dto.response;

import com.ticketsystem.entity.Role;
import com.ticketsystem.validator.EndDateConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    Role role;
    String username;
    String email;
//    String passwordHash;
    String fullName;
    String phoneNumber;
    Boolean isActive = true;
    LocalDateTime createdAt = LocalDateTime.now();
    LocalDateTime lastLogin;
}
