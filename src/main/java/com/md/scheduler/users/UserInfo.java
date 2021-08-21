package com.md.scheduler.users;

import com.md.scheduler.configuration.security.enums.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long id;
    private String email;
    private String username;
    private AppUserRole role;
    private boolean enabled;

    public UserInfo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }
}
