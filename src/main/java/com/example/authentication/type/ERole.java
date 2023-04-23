package com.example.authentication.type;

public enum ERole {
    ADMIN ("ADMIN", "ROLE_ADMIN"),
    USER("USER", "ROLE_USER");

    private final String role;
    private final String fullRole;

    private ERole(String role, String fullRole) {
        this.role = role;
        this.fullRole = fullRole;
    }

    public String getRole() {
        return role;
    }

    public String getFullRole() {
        return fullRole;
    }
}
