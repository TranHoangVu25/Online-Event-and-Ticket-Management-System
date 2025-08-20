package com.ticketsystem.validator;

import com.ticketsystem.dto.request.EventCreationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class EndDateValidatorCreate implements ConstraintValidator<EndDateConstraint, EventCreationRequest> {
    @Override
    public void initialize(EndDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EventCreationRequest request, ConstraintValidatorContext context) {
        if (Objects.isNull(request)
        || request.getStartTime() == null
        || request.getEndTime() == null
        )
            return true;


        return request.getEndTime().isAfter(request.getStartTime());
    }
}
