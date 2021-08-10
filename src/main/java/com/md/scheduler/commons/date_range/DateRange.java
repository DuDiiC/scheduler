package com.md.scheduler.commons.date_range;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "An object with a selected date range.")
public class DateRange implements Serializable {

    @Serial
    private static final long serialVersionUID = -7255235043854373096L;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Start date of schedule range.", example = "2021-02-27")
    private LocalDate startOfRange;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "End date of schedule range.", example = "2021-03-30")
    private LocalDate endOfRange;
}
