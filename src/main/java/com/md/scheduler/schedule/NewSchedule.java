package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.commons.date_range.ValidDateRange;
import lombok.Getter;

@Getter
class NewSchedule {

    private String name;
    private String description;
    @ValidDateRange
    private DateRange dateRange;
    private String status;
}
