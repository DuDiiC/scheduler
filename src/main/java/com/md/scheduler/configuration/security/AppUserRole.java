package com.md.scheduler.configuration.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.md.scheduler.configuration.security.AppUserPermission.USER_READ;
import static com.md.scheduler.configuration.security.AppUserPermission.USER_WRITE;

public enum AppUserRole {

    ROLE_ADMIN(Set.of(USER_READ, USER_WRITE)),

    ROLE_USER(Set.of(USER_READ)),

    ;

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority(this.name()));
        return permissions;
    }
}
