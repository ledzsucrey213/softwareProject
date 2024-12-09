package login.bl;

public class User {
    private int id;
    private String username;
    private String password;

    // Getters and setters
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

    // Constructor with username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
