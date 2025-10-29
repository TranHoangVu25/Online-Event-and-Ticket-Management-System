package com.ticketsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponUpdateRequest {
    Integer condition;
    LocalDateTime expire;
    String code;
    BigDecimal discount;
    Integer type;
}
