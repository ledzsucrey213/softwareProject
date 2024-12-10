package login.dao;

import java.io.*;
import java.util.*;

/**
 *
 */
public interface UserDAO {

    /**
     *
     */
    public void saveUser(int id, String name, String surname, int roleId, String password);

    /**
     *
     */
    public void loadUser(int userId);

}