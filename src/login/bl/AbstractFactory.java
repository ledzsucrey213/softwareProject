
<<<<<<< HEAD
import login.dao.*;

public abstract class AbstractFactory {
	public abstract UserDaoMySql createUserDaoMySql();
	
}
=======
import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class AbstractFactory {

    /**
     * Default constructor
     */
    public AbstractFactory() {
    }

    /**
     * @return
     */
    public UserDAO createUserDAO() {
        // TODO implement here
        return null;
    }

}
>>>>>>> 10effe60dafa487a1b0a800717ff73114fd2c7aa
