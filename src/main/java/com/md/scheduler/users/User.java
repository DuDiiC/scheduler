package com.md.scheduler.users;

import com.md.scheduler.commons.BaseEntity;
import com.md.scheduler.configuration.security.AppUserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter(value = AccessLevel.PACKAGE)
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private AppUserRole role;
    private boolean enabled;

    public User() {
    }

    public User(String username, String password, String email, AppUserRole role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return username.equals(user.getUsername()) && email.equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
