package com.laa66.springmvc.lottery.app.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


/**
 * DateValidator checks if user is at least 18 years old
 */
public class DateValidator implements ConstraintValidator<ValidDate, String> {

    @Override
    public void initialize(ValidDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate birthdate = LocalDate.parse(value);
            LocalDate now = LocalDate.now();
            long diff = birthdate.until(now, ChronoUnit.YEARS);
            return diff > 17;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
