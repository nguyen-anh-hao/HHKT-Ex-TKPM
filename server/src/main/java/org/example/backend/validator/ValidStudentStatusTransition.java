package org.example.backend.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;

@Documented
@Constraint(validatedBy = {StudentStatusTransitionValidator.class})
public @interface ValidStudentStatusTransition {
    String message() default "Invalid student status transition";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
