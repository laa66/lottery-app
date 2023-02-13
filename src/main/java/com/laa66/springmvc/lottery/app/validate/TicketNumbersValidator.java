package com.laa66.springmvc.lottery.app.validate;

import com.laa66.springmvc.lottery.app.dto.TicketDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Ticket numbers validator checks:
 *  if numbers are in range 0-100
 *  if there are exactly 6 numbers
 */

public class TicketNumbersValidator implements ConstraintValidator<ValidTicketNumbers, TicketDTO> {

    @Override
    public void initialize(ValidTicketNumbers constraintAnnotation) {

    }

    @Override
    public boolean isValid(TicketDTO object, ConstraintValidatorContext context) {
        System.out.println(object);
        HashSet<Integer> numbers = new HashSet<>(List.of(
                object.getField1(),
                object.getField2(),
                object.getField3(),
                object.getField4(),
                object.getField5(),
                object.getField6()
        ));
        if (numbers.size() != 6) {
            System.out.println("Numbers.size() = false");
            return false;
        }

        boolean b =  checkIfNumbersInRange(numbers);
        System.out.println(b);

        return b;
    }

    public boolean checkIfNumbersInRange(Set<Integer> numbers) {
        for (Integer num: numbers) if (num < 0 || num > 100) return false;
        return true;
    }
}
