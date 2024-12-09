package login.bl;

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
