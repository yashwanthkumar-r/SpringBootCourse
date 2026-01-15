package com.codingshuttle.yash.modules.module1.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {PasswordValidator.class}
)
public @interface PasswordValidation {
    String message() default "Pass should be contain at-least one upperCase, " +
            "one lowerCase, one special character and min length of 10";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
