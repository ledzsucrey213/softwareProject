package com.autocare.user;

public enum Role {
    ADMIN,
    MANAGER,
    CLIENT;


    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.toString().equals(value)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid Role value: " + value);
    }
}
