package com.md.scheduler.schedule;

import com.md.scheduler.users.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
class ScheduleOwner {

    @Schema(description = "It is exactly what you expect.", example = "1")
    private final Long id;

    @Schema(description = "It is exactly what you expect.", example = "username")
    private final String username;

    public ScheduleOwner(User owner) {
        this.id = owner.getId();
        this.username = owner.getUsername();
    }
}
