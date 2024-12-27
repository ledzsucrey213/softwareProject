package login.bl;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class User {
    private int id;
    private String password;
    private String surname;
    private String name;
    private Role role;

    // Constructor
    public User() {}

    // Constructor with username and password
    public User(int userId, String password , Role r, String surname, String name) {
        this.setId(userId);
        this.setName(name);
        this.setRole(r);
        this.setPassword(password);
        this.setSurname(surname);
        
    }

    public int getId() {
        return this.id;
    }

    public void setId(int userId) {
        this.id = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the role assigned to this entity.
     * 
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
     * 
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
