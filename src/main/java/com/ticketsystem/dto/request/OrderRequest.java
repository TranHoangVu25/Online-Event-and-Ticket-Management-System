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
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    private Map<Integer, Integer> tickets;
    private String method;
}
