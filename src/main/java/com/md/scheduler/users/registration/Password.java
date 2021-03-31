package com.md.scheduler.users.registration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { PasswordValidator.class })
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@interface Password {

    String message() default "Invalid password";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int minLength() default 0;

    int maxLength() default 0;

    boolean uppercaseCharRequired() default false;

    boolean lowercaseCharRequired() default false;

    boolean digitRequired() default false;

    boolean specialCharRequired() default false;
}
