package com.md.scheduler.schedule;

import com.md.scheduler.commons.BaseEntity;
import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.configuration.api.errors.EnumValueNotFoundException;
import com.md.scheduler.users.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "schedules")
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
class Schedule extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    private String name;

    private String description;

    @Embedded
    private DateRange dateRange;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Embedded
    private ScheduleImage image;

    public Schedule(NewSchedule newSchedule, User owner) {
        this.name = newSchedule.getName();
        this.description = newSchedule.getDescription();
        this.dateRange = newSchedule.getDateRange();
        this.status = ScheduleStatus.fromName(newSchedule.getStatus()).orElseThrow(() -> new EnumValueNotFoundException(newSchedule.getStatus()));
        this.image = new ScheduleImage("default");
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        var schedule = (Schedule) o;
        return owner.equals(schedule.getOwner()) && name.equals(schedule.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name);
    }
}
