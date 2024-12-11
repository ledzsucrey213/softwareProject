package login.bl;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String name;
    private Role role;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Constructor
    public User() {}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
    // Constructor with username and password
    public User(String username, String password , Role r, String firstname, String name) {
        this.username = username;
        this.password = password;
        this.setRole(r);
    }
}
