package login.bl;

import login.dao.*;

public abstract class AbstractFactory {
	public abstract UserDaoMySql createUserDaoMySql();
	
}
