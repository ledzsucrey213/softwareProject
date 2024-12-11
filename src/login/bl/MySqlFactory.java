package login.bl;
import login.dao.*;

/**
 * 
 */
public class MySqlFactory extends AbstractFactory {
	private static MySqlFactory mySqlFactory;

    /**
     * Default constructor
     */
    private MySqlFactory() {
        System.out.println("MySqlFactory created");
    }

    /**
     * @return
     */
    public UserDaoMySql createUserDaoMySql() {
        return new UserDaoMySql();
    }

    /**
     * 
     */
    public static MySqlFactory getInstance() {
        if (mySqlFactory == null) {
            mySqlFactory = new MySqlFactory();
        }
        return mySqlFactory;
    }

}
