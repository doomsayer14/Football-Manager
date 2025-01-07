package org.example.footballmanager.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.footballmanager.validation.PositionValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositionValidator.class)
@Documented
public @interface ValidPosition {
    String message() default "Provided string is not a football position";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
