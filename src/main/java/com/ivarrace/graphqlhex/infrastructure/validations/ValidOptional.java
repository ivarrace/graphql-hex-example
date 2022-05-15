package com.ivarrace.graphqlhex.infrastructure.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidOptionalValidator.class})
public @interface ValidOptional {
    String message() default "Valid optional";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}