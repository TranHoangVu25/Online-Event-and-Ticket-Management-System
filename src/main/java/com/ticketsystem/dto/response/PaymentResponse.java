package com.ticketsystem.dto.response;

import com.ticketsystem.entity.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
     Integer id;
     Order order;
     BigDecimal amount;
     String method;
     int status;
     String transactionCode;
     LocalDateTime createdAt = LocalDateTime.now();
}
