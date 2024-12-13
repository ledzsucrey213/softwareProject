package login.bl;
import login.dao.*;
import login.ui.*;
/**
 * 
 */
public class LoginService {
	
    private UserDaoMySql userDao;

    /**
     * Default constructor
     */
    public LoginService() {
        // Get the singleton instance of MySqlFactory
        MySqlFactory mySqlFactory = MySqlFactory.getInstance();
        
        // Create UserDaoMySql instance through the factory
        this.userDao = mySqlFactory.createUserDaoMySql();
    }

    /**
     * @param id 
     * @param password
     * @return boolean
     */
    public boolean authenticate(int id, String password) {
    // Load the user from the database
    User user = userDao.loadUser(id, password);

    // Check if the user exists and the password matches
    if (user != null && user.getPassword().equals(password)) {
        System.out.println("Authentication successful for user ID: " + id);
        return true; // Authentication successful
    } else {
        System.out.println("Authentication failed for user ID: " + id);
        return false; // Authentication failed
    }
}

}
