package com.example.films.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    USER(Set.of(Permission.USER)), ADMIN(Set.of(Permission.USER, Permission.ADMIN));

    final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> authorities(){
        return permissions.stream().map(n -> new SimpleGrantedAuthority(n.getP())).collect(Collectors.toList());
    }
}
