package com.ticketsystem.validator;

import com.ticketsystem.dto.request.CouponUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Objects;

public class CouponDiscountUpdateValidator implements ConstraintValidator<CouponDiscountConstraint, CouponUpdateRequest> {
    @Override
    public void initialize(CouponDiscountConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CouponUpdateRequest request, ConstraintValidatorContext context) { // Đổi tên tham số
        if (Objects.isNull(request) || request.getDiscount() == null) {
            return true;
        }
        return request.getDiscount() > 100 && request.getType() == 2;
    }
}