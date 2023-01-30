package com.laa66.springmvc.lottery.app.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid birthdate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
