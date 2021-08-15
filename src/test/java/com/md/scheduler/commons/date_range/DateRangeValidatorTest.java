package com.md.scheduler.commons.date_range;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateRangeValidatorTest {

    public static final String EXAMPLE_DATE = "2020-01-01";
    private final DateRangeValidator dateRangeValidator = new DateRangeValidator();

    private boolean isValid(DateRange dateRange) {
        return dateRangeValidator.isValid(dateRange, null);
    }

    @Nested
    @DisplayName("Valid cases")
    class DateRangeIsValidTest {

        @ParameterizedTest
        @DisplayName("DateRange should be valid when start date is before end date")
        @CsvSource({
                "1999-12-31,2000-01-01",
                "2000-01-01,2000-01-02",
                "2000-01-01,2000-02-01",
                "2000-01-01,2001-01-01",
                "2021-10-13,2022-02-11"
        })
        void shouldBeValid_whenDateRangeIsCorrect(String startDate, String endDate) {
            DateRange validDateRange = new DateRange(
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );
            assertTrue(isValid(validDateRange));
        }

        @Test
        @DisplayName("DateRange should be valid when start and end dates are null")
        void shouldBeValid_whenBothDatesAreNull() {
            DateRange validDateRange = new DateRange(null, null);
            assertTrue(isValid(validDateRange));
        }

        @Test
        @DisplayName("DateRange should be valid when start date is null")
        void shouldBeValid_whenStartDateIsNull() {
            DateRange validDateRange = new DateRange(null, LocalDate.parse(EXAMPLE_DATE));
            assertTrue(isValid(validDateRange));
        }

        @Test
        @DisplayName("DateRange should be valid when end date is null")
        void shouldBeValid_whenEndDateIsNull() {
            DateRange validDateRange = new DateRange(LocalDate.parse(EXAMPLE_DATE), null);
            assertTrue(isValid(validDateRange));
        }
    }

    @Nested
    @DisplayName("Invalid cases")
    class DateRangeIsNotValidTest {

        @ParameterizedTest
        @DisplayName("DateRange should be invalid when end date is before start date")
        @CsvSource({
                "2000-01-01,1999-12-31",
                "2000-01-02,2000-01-01",
                "2000-02-01,2000-01-01",
                "2001-01-01,2000-01-01",
                "2022-02-11,2021-10-13"
        })
        void shouldNotBeValid_whenDateRangeIsNotCorrect(String startDate, String endDate) {
            DateRange invalidDateRange = new DateRange(
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );
            assertFalse(isValid(invalidDateRange));
        }

        @Test
        @DisplayName("DateRange should be invalid when start and end date are the same")
        void shouldNotBeValid_whenStartAndEndDateAreTheSame() {
            DateRange invalidDateRange = new DateRange(
                    LocalDate.parse(EXAMPLE_DATE),
                    LocalDate.parse(EXAMPLE_DATE)
            );
            assertFalse(isValid(invalidDateRange));
        }
    }
}
