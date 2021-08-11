package com.md.scheduler.commons.date_range;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {
    @Override
    public boolean isValid(DateRange value, ConstraintValidatorContext context) {
        return value.getStartOfRange().isBefore(value.getEndOfRange());
    }
}
