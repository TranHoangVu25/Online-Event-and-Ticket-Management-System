package com.ticketsystem.validator;

import com.ticketsystem.dto.request.EventUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class EndDateValidatorUpdate implements ConstraintValidator<EndDateConstraint, EventUpdateRequest> {
    @Override
    public void initialize(EndDateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EventUpdateRequest request, ConstraintValidatorContext context) {
        if (Objects.isNull(request)
        || request.getStartTime() == null
        || request.getEndTime() == null
        )
            return true;


        return request.getEndTime().isAfter(request.getStartTime());
    }
}
