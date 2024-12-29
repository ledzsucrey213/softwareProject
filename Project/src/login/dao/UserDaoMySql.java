import java.sql.*;

import login.bl.User;
import login.bl.Role;

public class UserDaoMySql implements UserDao {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CarService";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Omaramerbaf.30052003";

    public UserDaoMySql() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveUser(String name, String surname, Role role, String password) {
        String userQuery = "INSERT INTO user (name, surname, role, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement userStmt = connection.prepareStatement(userQuery)) {

            // Insert user into the user table
            userStmt.setString(1, name);
            userStmt.setString(2, surname);
            userStmt.setInt(3, role.getValue());
            userStmt.setString(4, password);
            userStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   public User loadUser(int userId, String password) {
    String query = "SELECT u.ID_user, u.name, u.surname, r.label_role, u.password FROM user u " +
                   "JOIN role r ON u.roleID = r.ID_role " +
                   "WHERE u.ID_user = ? AND u.password = ?";

    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, password);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                // Create Role object
                int roleValue = Integer.parseInt(resultSet.getString("label_role").trim());
                Role role = Role.fromValue(roleValue);

                // Create User object
                User user = new User();
                user.setId(resultSet.getInt("ID_user"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(role);

                return user;
            } else {
                // No user found
                return null;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}



}
