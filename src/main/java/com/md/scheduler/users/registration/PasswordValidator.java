package com.md.scheduler.users.registration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.function.IntPredicate;

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

        if (minLength != 0 && value.length() < minLength) {
            return false;
        }
        if (maxLength != 0 && value.length() > maxLength) {
            return false;
        }
        if (uppercaseCharRequired && !stringContainsUppercaseChar(value)) {
            return false;
        }
        if (lowercaseCharRequired && !stringContainsLowercaseChar(value)) {
            return false;
        }
        if (digitRequired && !stringContainsDigit(value)) {
            return false;
        }
        if (specialCharRequired && !stringContainsSpecialChar(value)) {
            return false;
        }

        return true;
    }

    private boolean stringContainsUppercaseChar(String value) {
        return value.matches(".*[A-Z].*");
    }

    private boolean stringContainsLowercaseChar(String value) {
        return value.matches(".*[a-z].*");
    }

    private boolean stringContainsDigit(String value) {
        return value.matches(".*\\d.*");
    }

    private boolean stringContainsSpecialChar(String value) {
        return value.matches(".*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-].*");
    }
}
