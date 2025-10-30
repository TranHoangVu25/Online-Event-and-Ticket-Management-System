package com.ticketsystem.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {CouponExpireValidator.class,CouponExpireUpdateValidator.class})
public @interface CouponExpireConstraint {
    String message() default "Invalid expire date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
