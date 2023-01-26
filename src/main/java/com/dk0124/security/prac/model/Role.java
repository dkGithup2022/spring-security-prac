package com.dk0124.security.prac.model;

import lombok.Getter;

import java.util.Locale;

@Getter
public enum Role {
    USER("user"), ADMIN("admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public static Role of(String role) {
        for (Role r : Role.values())
            if (r.getRole().equals(role))
                return r;

        throw new IllegalArgumentException("no valid role");
    }
}
