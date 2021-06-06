package com.md.scheduler.schedule;

import java.util.Arrays;
import java.util.Optional;

enum ScheduleStatus {

    INACTIVE(0L, "inactive"),
    ACTIVE(1L, "active"),
    ;

    private final Long value;
    private final String name;

    ScheduleStatus(Long value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Optional<ScheduleStatus> fromName(String name) {
        return Arrays.stream(ScheduleStatus.values())
                .filter(status -> status.name.equals(name))
                .findAny();
    }

    public Long getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
