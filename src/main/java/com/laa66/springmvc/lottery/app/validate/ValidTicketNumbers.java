package com.laa66.springmvc.lottery.app.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TicketNumbersValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTicketNumbers {
    String message() default "Incorrect numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
