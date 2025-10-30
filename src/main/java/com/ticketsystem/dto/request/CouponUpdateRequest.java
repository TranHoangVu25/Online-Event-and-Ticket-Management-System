package com.ticketsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.User;
import com.ticketsystem.validator.CouponExpireConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CouponExpireConstraint
public class CouponUpdateRequest {
    @Min(message = "Condition must be > 0", value = 0)
    Integer condition;
    LocalDateTime expire;
    @Size(message = "Code length must be >= 5",min = 5)
    String code;
    @Min(message = "discount must be > 0", value = 0)
    BigDecimal discount;
    Integer type;
}
