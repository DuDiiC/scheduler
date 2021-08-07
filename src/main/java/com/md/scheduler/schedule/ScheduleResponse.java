package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import lombok.Getter;

@Getter
class ScheduleResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String status;
    private final DateRange dateRange;
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
