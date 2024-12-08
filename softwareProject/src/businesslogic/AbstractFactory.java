package softwareProject;

import login_SQL.UserDaoMySQL;

public abstract class AbstractFactory {	
	public abstract UserDaoMySql createUserDaoMySql();

}
