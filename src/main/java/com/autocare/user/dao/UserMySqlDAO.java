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
}





