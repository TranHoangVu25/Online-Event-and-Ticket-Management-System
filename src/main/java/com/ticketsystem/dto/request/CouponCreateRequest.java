package com.ticketsystem.dto.request;

import com.ticketsystem.exception.Error;
import com.ticketsystem.validator.CouponExpireConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@CouponExpireConstraint
public class CouponCreateRequest {
    @Min(message = "Condition must be > 0", value = 0)
    Integer condition;
    LocalDateTime expire;
    @Size(message = "Code length must be >= 5",min = 5)
    String code;
    @Min(message = "discount must be > 0", value = 0)
    BigDecimal discount;
    Integer type;
}
