package com.laa66.springmvc.lottery.app.validate;

import com.laa66.springmvc.lottery.app.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 *  Password validator checks:
 *  if password is correct (match PASSWORD_PATTERN)
 *  if two given passwords are the same
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, UserDTO> {
    private final static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

    @Override
    public boolean isValid(UserDTO object, ConstraintValidatorContext context) {
        if (object == null) return true;
        UserDTO userDTO = (UserDTO) object;
        String password = userDTO.getPassword();
        String confirmPassword = userDTO.getConfirmPassword();
        if (PASSWORD_PATTERN.matcher(password).matches()) {
            return password.equals(confirmPassword);
        }
        return false;
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }
}
