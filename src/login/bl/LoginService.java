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
     * @param username 
     * @param password
     * @return boolean
     */
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

}
