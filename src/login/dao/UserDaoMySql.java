import java.sql.*;

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

    public void saveUser(String name, String surname, int roleId, String password) {
        String userQuery = "INSERT INTO user (name, surname, roleID, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement userStmt = connection.prepareStatement(userQuery)) {

            // Insert user into the user table
            userStmt.setString(1, name);
            userStmt.setString(2, surname);
            userStmt.setInt(3, roleId);
            userStmt.setString(4, password);
            userStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* public void loadUser(int userId, String password) {
        String query = "SELECT u.ID_user, u.name, u.surname, r.label_role FROM user u " +
                       "JOIN role r ON u.roleID = r.ID_role " +
                       "WHERE u.ID_user = ? AND u.password = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("ID_user"));
                    System.out.println("Name: " + resultSet.getString("name"));
                    System.out.println("Surname: " + resultSet.getString("surname"));
                    System.out.println("Role: " + resultSet.getString("label_role"));
                } else {
                    System.out.println("User not found or incorrect password.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    } */ 


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
                Role role = new Role();
                role.setLabel(resultSet.getString("label_role"));

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
