package com.laa66.springmvc.lottery.app.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 *  Password validator checks:
 *  if password is correct (match PASSWORD_PATTERN)
 *  if two given passwords are the same
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, UserValid> {
    private final static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

    @Override
    public boolean isValid(UserValid object, ConstraintValidatorContext context) {
        if (object == null) return false;
        UserValid userValid = (UserValid) object;
        String password = userValid.getPassword();
        String confirmPassword = userValid.getConfirmPassword();
        if (PASSWORD_PATTERN.matcher(password).matches()) {
            return password.equals(confirmPassword);
        }
        return false;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }
}
