package com.autocare.user.service;

import com.autocare.user.Role;
import com.autocare.user.User;
import com.autocare.user.dao.UserDAO;
import com.autocare.user.factory.UserDAOFactory;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling user-related operations. It interacts with the UserDAO
 * to manage users in the system, including retrieving, adding, deleting, and updating users.
 */

public class UserService {
    private final UserDAO userDAO;

    /**
     * Constructs a new UserService using a UserDAOFactory to instantiate the UserDAO.
     *
     * @param userDAOFactory The factory used to create an instance of UserDAO.
     */

    // Constructor that initializes the UserDAO using the factory
    public UserService(UserDAOFactory userDAOFactory) {
        this.userDAO = userDAOFactory.createUserDAO();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all {@link User} objects from the database.
     * @throws SQLException If a database error occurs during the retrieval of users.
     */

    // Method to get all users from the database
    public List<User> getAllUsers() throws SQLException {
        return userDAO.loadUsers();
    }

    /**
     * Adds a new user to the database.
     *
     * @param name The user's first name.
     * @param surname The user's last name.
     * @param username The user's username.
     * @param role The {@link Role} of the user (e.g., ADMIN, USER).
     * @param password The user's password.
     * @throws SQLException If a database error occurs during the user insertion.
     */

    // Method to add a new user
    public void addUser(String name, String surname, String username, Role role, String password) throws SQLException {
        userDAO.saveUser(name, surname, username, role, password);
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws SQLException If a database error occurs during the deletion of the user.
     */

    // Method to delete a user by ID (for example, during account deletion)
    public void deleteUser(long userId) throws SQLException {
        userDAO.deleteUser(userId);  // Assuming you implement the deleteUser method in DAO
    }

    /**
     * Updates an existing user's details in the database.
     *
     * @param user The {@link User} object containing the updated user details.
     * @throws SQLException If a database error occurs during the user update.
     */

    // Method to update user details (if needed)
    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);  // Assuming you implement the updateUser method in DAO
    }

    public Optional<User> getUser(String username , String password) throws SQLException {
        return userDAO.loadUser(username,password);
    }
    public List<User> getClients() throws SQLException {
        return userDAO.loadClients();
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return An {@link Optional} containing the user if found, or an empty {@link Optional} otherwise.
     * @throws SQLException If a database error occurs during the retrieval of the user.
     */
    public Optional<User> getUserById(long id) throws SQLException {
        return userDAO.loadUserById(id);
    }


}
