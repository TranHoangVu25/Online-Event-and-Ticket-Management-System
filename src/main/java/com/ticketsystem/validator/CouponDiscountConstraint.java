package com.ticketsystem.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CouponDiscountCreateValidator.class})
public @interface CouponDiscountConstraint {
    String message() default "Voucher (%) must not > 100";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
