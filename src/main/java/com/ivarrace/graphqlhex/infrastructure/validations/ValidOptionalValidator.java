package com.ivarrace.graphqlhex.infrastructure.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ValidOptionalValidator implements ConstraintValidator<ValidOptional, Optional<String>> {

    @Override
    public void initialize(ValidOptional constraintAnnotation) {
    }

    @Override
    public boolean isValid(Optional<String> optional, ConstraintValidatorContext constraintValidatorContext) {
        return !optional.isPresent() || !optional.get().isEmpty();
    }
}