package com.md.scheduler.users.registration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength;
    private int maxLength;
    private boolean uppercaseCharRequired;
    private boolean lowercaseCharRequired;
    private boolean digitRequired;
    private boolean specialCharRequired;

    @Override
    public void initialize(Password constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
        this.uppercaseCharRequired = constraintAnnotation.uppercaseCharRequired();
        this.lowercaseCharRequired = constraintAnnotation.lowercaseCharRequired();
        this.digitRequired = constraintAnnotation.digitRequired();
        this.specialCharRequired = constraintAnnotation.specialCharRequired();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return false;
    }
}
