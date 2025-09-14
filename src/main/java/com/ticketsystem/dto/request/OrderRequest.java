package com.ticketsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticketsystem.entity.OrderDetail;
import com.ticketsystem.entity.Payment;
import com.ticketsystem.entity.User;
import com.ticketsystem.validator.EndDateConstraint;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EndDateConstraint
public class OrderRequest {
    Integer id;
    User user;
    BigDecimal totalAmount;
    int status = 0;
    LocalDateTime createdAt = LocalDateTime.now();
    private Set<OrderDetail> orderDetails;
    Set<Payment> payments;
}
