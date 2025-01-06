package com.autocare.user.service;

import com.autocare.user.Role;
import com.autocare.user.User;
import com.autocare.user.dao.UserDAO;
import com.autocare.user.factory.UserDAOFactory;


import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    // Constructor that initializes the UserDAO using the factory
    public UserService(UserDAOFactory userDAOFactory) {
        this.userDAO = userDAOFactory.createUserDAO();
    }

    // Method to get all users from the database
    public List<User> getAllUsers() throws SQLException {
        return userDAO.loadUsers();
    }

    // Method to add a new user
    public void addUser(String name, String surname, String username, Role role, String password) throws SQLException {
        userDAO.saveUser(name, surname, username, role, password);
    }

    // Method to delete a user by ID (for example, during account deletion)
    public void deleteUser(long userId) throws SQLException {
        userDAO.deleteUser(userId);  // Assuming you implement the deleteUser method in DAO
    }

    // Method to update user details (if needed)
    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);  // Assuming you implement the updateUser method in DAO
    }
}
