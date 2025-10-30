package com.ticketsystem.validator;

import com.ticketsystem.dto.request.CouponUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.util.Objects;

public class CouponExpireUpdateValidator implements ConstraintValidator<CouponExpireConstraint, CouponUpdateRequest> {
    @Override
    public void initialize(CouponExpireConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CouponUpdateRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(request)
        || request.getExpire() == null
        )
            return true;
        return request.getExpire().isAfter(LocalDateTime.now());
    }
}
