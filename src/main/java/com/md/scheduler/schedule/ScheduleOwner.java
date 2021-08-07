package com.md.scheduler.schedule;

import com.md.scheduler.users.User;
import lombok.Getter;

@Getter
class ScheduleOwner {

    private final Long id;
    private final String username;

    public ScheduleOwner(User owner) {
        this.id = owner.getId();
        this.username = owner.getUsername();
    }
}
