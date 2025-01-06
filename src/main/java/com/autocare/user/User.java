package com.autocare.user;

/**
 *
 */
public class User {
    private long    id;
    private String username;
    private String surname;
    private String name;
    private Role   role;

    // Constructor
    public User() {}

    // Constructor with username and password
    public User(long userId, String username, Role r, String surname,
                String name) {
        this.setId(userId);
        this.setName(name);
        this.setRole(r);
        this.setUsername(username);
        this.setSurname(surname);

    }

    public long getId() {
        return this.id;
    }

    public void setId(long userId) {
        this.id = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the role assigned to this entity.
     * <p>
     * The role can have one of the following values:
     * - 0: Admin
     * - 1: Operator
     * - 2: User
     *
     * @return the role of the entity.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Assigns a role to this entity.
     * <p>
     * The role can be set to one of the following values:
     * - 0: Admin
     * - 1: Operator
     * - 2: User
     *
     * @param role the role to assign to this entity.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
