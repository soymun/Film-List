package com.example.films.entity;

public enum Permission {
    USER("USER"), ADMIN("ADMIN");

    final String p;

    Permission(String p) {
        this.p = p;
    }

    public String getP() {
        return p;
    }
}
