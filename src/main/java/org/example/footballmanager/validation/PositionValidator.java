package org.example.footballmanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;
import org.example.footballmanager.annotation.ValidPosition;
import org.example.footballmanager.entity.enums.Position;

public class PositionValidator implements ConstraintValidator<ValidPosition, String> {
    @Override
    public void initialize(ValidPosition constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return EnumUtils.isValidEnum(Position.class, s);
    }
}
