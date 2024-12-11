package login.dao;



/**
 *
 */
public interface UserDao {

    /**
     *
     */
    public void saveUser(String name, String surname, int roleId, String password);

    /**
     *
     */
    public void loadUser(int userId, String password);

}