package com.md.scheduler.commons.date_range;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
public class DateRange implements Serializable {

    @Serial
    private static final long serialVersionUID = -7255235043854373096L;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOfRange;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfRange;
}
