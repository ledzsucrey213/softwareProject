
<<<<<<< HEAD
import login.dao.*;

public class MySqlFactory extends AbstractFactory {
    private static MySqlFactory mySqlFactory;

    // Private constructor for Singleton pattern
    private MySqlFactory() {
        System.out.println("MySqlFactory created");
    }

    // Singleton pattern: Return the single instance of the factory
    public static MySqlFactory getInstance() {
        if (mySqlFactory == null) {
            mySqlFactory = new MySqlFactory();
        }
        return mySqlFactory;
    }

    // Create a new instance of UserDaoMySql
    public UserDaoMySql createUserDaoMySql() {
        return new UserDaoMySql();
    }
}
=======
import java.io.*;
import java.util.*;

/**
 * 
 */
public class MySQLFactory extends AbstractFactory {

    /**
     * Default constructor
     */
    public MySQLFactory() {
    }

    /**
     * @return
     */
    public UserDAOMySQL createUserDAO() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void getInstance() {
        // TODO implement here
    }

}
>>>>>>> 10effe60dafa487a1b0a800717ff73114fd2c7aa
