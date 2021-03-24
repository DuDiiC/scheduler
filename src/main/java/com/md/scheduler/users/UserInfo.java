package com.md.scheduler.users;

import com.md.scheduler.configuration.security.AppUserRole;
import lombok.Getter;

@Getter
public class UserInfo {

    private final Long id;
    private final String email;
    private final String username;
    private final AppUserRole role;
    private final boolean enabled;

    public UserInfo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }
}
