package login.dao;



/**
 *
 */
public interface UserDao {

    /**
     *
     */
    public void saveUser(String name, String surname, Role role, String password);

    /**
     *
     */
    public User loadUser(int userId, String password);

}