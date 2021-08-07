package com.md.scheduler.commons.date_range;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOfRange;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfRange;
}
