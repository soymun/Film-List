package com.example.films.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER");

    final String name;
    Role(String user) {
        name = user;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
