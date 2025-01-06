package com.autocare.login.service;

import com.autocare.user.Role;
import com.autocare.user.dao.UserDAO;
import com.autocare.user.factory.UserDAOFactory;

import java.sql.SQLException;

/**
 *
 */
public class LoginService {

    private final UserDAO userDao;

    /**
     * Default constructor
     */
    public LoginService(UserDAOFactory userFactory) {
        this.userDao = userFactory.createUserDAO();
    }

    /**
     * @param username
     * @param password
     * @return boolean
     */
    public boolean authenticate(String username, String password)
    throws SQLException {
        // Load the user from the database
        var user = userDao.loadUser(username, password);

        // Check if the user exists and the password matches
        // Authentication failed
        return user.isPresent(); // Authentication successful
    }

    public Role UserRole(String username, String password)
            throws SQLException {
        // Load the user from the database
        var user = userDao.loadUser(username, password);

        // Check if the user exists and the password matches
        // Authentication failed
        return user.get().getRole(); // Authentication successful
    }

}
