package com.md.scheduler.commons.date_range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {
    @Override
    public boolean isValid(DateRange value, ConstraintValidatorContext context) {
        if (value.getStartOfRange() == null || value.getEndOfRange() == null) return true;
        return value.getStartOfRange().isBefore(value.getEndOfRange());
    }
}
