package com.ticketsystem.dto.request;

import com.ticketsystem.entity.Order;
import com.ticketsystem.validator.EndDateConstraint;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
     Integer id;
     Order order;
     BigDecimal amount;
     String method;
     int status;
     String transactionCode;
     LocalDateTime createdAt = LocalDateTime.now();
}
