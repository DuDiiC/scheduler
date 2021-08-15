package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.commons.date_range.ValidDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Object sent to create a new schedule.")
class NewSchedule {

    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "It is exactly what you expect.", example = "name")
    private String name;

    @Schema(description = "Schedule description with more details", example = "description with details")
    @Size(max = 5000)
    private String description;

    @ValidDateRange
    private DateRange dateRange;

    @Schema(description = "Schedule status. Only active schedules are editable. By default, schedule has active status.", example = "active")
    private String status;
}
