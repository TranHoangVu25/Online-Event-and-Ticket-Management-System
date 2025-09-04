package com.ticketsystem.dto.request;

import com.ticketsystem.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    Role role;
    String username;
    String email;
    String passwordHash;
    String fullName;
    String phoneNumber;
    LocalDateTime lastLogin;

}
