package com.ticketsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.util.Objects;

public class StartDateValidator implements ConstraintValidator<StartDateConstraint, LocalDateTime> {

    @Override
    public void initialize(StartDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true;
        return value.isAfter(LocalDateTime.now());
    }
}
