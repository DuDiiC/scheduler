package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
class ScheduleResponse {

    @Schema(description = "It is exactly what you expect.", example = "1")
    private final Long id;

    @Schema(description = "It is exactly what you expect.", example = "name")
    private final String name;

    @Schema(description = "It is exactly what you expect.", example = "description")
    private final String description;

    @Schema(description = "Schedule status (active or inactive).", example = "active")
    private final String status;

    @Schema(description = "Date range object with start and end schedule dates.")
    private final DateRange dateRange;

    @Schema(description = "Schedule owner.")
    private final ScheduleOwner owner;

    public ScheduleResponse(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.description = schedule.getDescription();
        this.status = schedule.getStatus().getName();
        this.dateRange = schedule.getDateRange();
        this.owner = new ScheduleOwner(schedule.getOwner());
    }
}
