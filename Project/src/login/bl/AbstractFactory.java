package login.bl;

import login.dao.*;
public abstract class AbstractFactory {

    /**
     * Default constructor
     */
    public AbstractFactory() {
    	
    }

    /**
     * abstract func
     */
    public abstract UserDao createUserDaoMySql();
}
