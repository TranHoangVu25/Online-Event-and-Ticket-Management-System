package com.ticketsystem.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {EndDateValidatorCreate.class,EndDateValidatorUpdate.class})
public @interface EndDateConstraint {
    String message() default "Invalid End Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
