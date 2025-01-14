package com.autocare.user.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.user.Role;
import com.autocare.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class UserMySqlDAO implements UserDAO {

    /**
     * Saves a new user to the database.
     *
     * @param name The user's first name.
     * @param surname The user's last name.
     * @param username The user's username.
     * @param role The {@link Role} of the user (e.g., ADMIN, USER).
     * @param password The user's password.
     * @throws SQLException If a database error occurs during the user insertion.
     */

    public void saveUser(String name, String surname, String username,
                         Role role, String password)
    throws SQLException {
        String userQuery =
                "INSERT INTO user (name, surname, username, role, password) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement userStmt = connection.prepareStatement(userQuery);

        // Insert user into the user table
        userStmt.setString(1, name);
        userStmt.setString(2, surname);
        userStmt.setString(3, username);
        userStmt.setString(4, role.toString());
        userStmt.setString(5, password);
        userStmt.executeUpdate();

        userStmt.close();
    }

    /**
     * Loads a user from the database based on their username and password.
     *
     * @param username The username of the user to be loaded.
     * @param password The password of the user to be loaded.
     * @return An {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if no user matches.
     * @throws SQLException If a database error occurs during the user retrieval.
     */


    public Optional<User> loadUser(String username, String password)
    throws SQLException {

        String query = "SELECT u.ID_user, u.name, u.surname, u.username, u"
                       + ".role FROM user u WHERE u.username = ? AND u"
                       + ".password = ?;";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement
                preparedStatement
                = connection.prepareStatement(query);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        Optional<User> user = Optional.empty();

        if (resultSet.next()) {
            // Create Role object
            String roleValue = resultSet.getString("role".trim());
            Role role = Role.fromValue(roleValue);

            // Create User object
            user = Optional.of(new User(resultSet.getInt("ID_user"),
                                        resultSet.getString("username"),
                                        role,
                                        resultSet.getString("name"),
                                        resultSet.getString("surname")
            ));
        }

        preparedStatement.close();
        return user;
    }

    /**
     * Loads all users from the database.
     *
     * @return A list of all {@link User} objects in the database.
     * @throws SQLException If a database error occurs during the retrieval of users.
     */

    public List<User> loadUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.ID_user, u.name, u.surname, u.username, u.role FROM user u";

        // Establish the database connection
        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        // Iterate through the result set and create User objects
        while (resultSet.next()) {
            // Retrieve data from the result set
            long id = resultSet.getLong("ID_user");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String username = resultSet.getString("username");
            String roleValue = resultSet.getString("role").trim();
            Role role = Role.fromValue(roleValue);

            // Create a User object and set its properties
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setSurname(surname);
            user.setUsername(username);
            user.setRole(role);

            // Add the User object to the list
            users.add(user);
        }

        // Clean up
        resultSet.close();
        preparedStatement.close();

        return users;
    }

    /**
     * Deletes a user from the database based on the user ID.
     *
     * @param userId The ID of the user to be deleted.
     * @throws SQLException If a database error occurs during the deletion of the user.
     */

    @Override
    public void deleteUser(long userId) throws SQLException {
        String query = "DELETE FROM user WHERE ID_user = ?";

        // Establish the database connection
        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set the user ID parameter
        preparedStatement.setLong(1, userId);

        // Execute the delete query
        preparedStatement.executeUpdate();

        // Clean up resources
        preparedStatement.close();
    }

    /**
     * Updates an existing user's information in the database.
     *
     * @param user The {@link User} object containing the updated information.
     * @throws SQLException If a database error occurs during the update.
     */


    @Override
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE user SET name = ?, surname = ?, username = ?, role = ?  WHERE ID_user = ?";

        // Establish the database connection
        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set the values in the prepared statement
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getRole().toString()); // Assuming Role is an enum
        preparedStatement.setLong(5, user.getId());

        // Execute the update query
        preparedStatement.executeUpdate();

        // Clean up resources
        preparedStatement.close();
    }
    /**
     * Loads all users with the role 'CLIENT' from the database.
     *
     * @return A list of all {@link User} objects with the role 'CLIENT'.
     * @throws SQLException If a database error occurs during the retrieval of users.
     */
    public List<User> loadClients() throws SQLException {
        List<User> clients = new ArrayList<>();
        String query = "SELECT u.ID_user, u.name, u.surname, u.username, u.role " +
                       "FROM user u " +
                       "WHERE u.role = 'CLIENT'"; // Filtrer uniquement les clients

        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("ID_user");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String username = resultSet.getString("username");
                String roleValue = resultSet.getString("role").trim();
                Role role = Role.fromValue(roleValue);

                // Créer un objet User et l'ajouter à la liste
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setSurname(surname);
                user.setUsername(username);
                user.setRole(role);

                clients.add(user);
            }
        }
        return clients;
    }

}





