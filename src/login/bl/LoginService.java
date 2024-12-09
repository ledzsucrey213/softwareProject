package login.bl;

import login.dao.*;
import login.ui.*;

public class LoginService {

    private UserDaoMySql userDao;

    // Constructor initializes the UserDaoMySql via MySqlFactory
    public LoginService() {
        // Get the singleton instance of MySqlFactory
        MySqlFactory mySqlFactory = MySqlFactory.getInstance();
        
        // Create UserDaoMySql instance through the factory
        this.userDao = mySqlFactory.createUserDaoMySql();
    }

    // Authenticate a user by username and password
    public boolean authenticate(String userName, String password) {
        try {
            // Attempt to load the user with the given username and password
            userDao.loadUser(userName, password);
            return true; // Authentication successful
        } catch (Exception e) {
            // If loadUser fails (i.e., user does not exist or credentials don't match)
            System.out.println("Authentication failed: " + e.getMessage());
            return false; // Authentication failed
        }
    }

    // Example usage of the authenticate method
    public static void main(String[] args) {
        LoginService loginService = new LoginService();

        // Authenticate a user (this is just an example, adjust credentials as needed)
        boolean isAuthenticated = loginService.authenticate("Alice", "password456");

        if (isAuthenticated) {
            System.out.println("User authenticated successfully!");
        } else {
            System.out.println("Authentication failed.");
        }
    }
}
