package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoMySQL {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CarService";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Omaramerbaf.30052003";

    // Establish a connection to the database
    private Connection connect() throws Exception {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    // Verify user credentials
    public boolean verifyLogin(String username, String password) {
        String query = "SELECT * FROM user u JOIN login_info l ON u.ID_user = l.ID_user WHERE u.name = ? AND l.password = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a matching record is found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Save user and return a User instance
    public User saveUser(int userId, String userName, String password) {
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement loginStatement = null;
        User savedUser = null;

        try {
            // Establish the database connection
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");

            // Prepare SQL queries
            String userQuery = "INSERT INTO user (ID_user, name) VALUES (?, ?)";
            String loginQuery = "INSERT INTO login_info (ID_user, password) VALUES (?, ?)";

            // Insert into `user` table
            userStatement = connection.prepareStatement(userQuery);
            userStatement.setInt(1, userId);
            userStatement.setString(2, userName);
            userStatement.executeUpdate();

            // Insert into `login_info` table
            loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setInt(1, userId);
            loginStatement.setString(2, password);
            loginStatement.executeUpdate();

            // Create and return a User instance with the saved information
            savedUser = new User();
            savedUser.setId(userId);
            savedUser.setUsername(userName);
            savedUser.setPassword(password);

            System.out.println("User saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (userStatement != null) userStatement.close();
                if (loginStatement != null) loginStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedUser;
    }

    // Load a user by username and password and return a User instance
    public User loadUser(String userName, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User loadedUser = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected");

            // SQL query to fetch user data based on username and password
            String query = """
                SELECT user.ID_user, user.name, login_info.password
                FROM user
                JOIN login_info ON user.ID_user = login_info.ID_user
                WHERE user.name = ? AND login_info.password = ?
            """;

            // Use PreparedStatement to avoid SQL injection
            statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);

            // Execute query
            resultSet = statement.executeQuery();

            // Check if a matching user is found
            if (resultSet.next()) {
                int id = resultSet.getInt("ID_user");
                String name = resultSet.getString("name");
                String pwd = resultSet.getString("password");

                // Create and return a User instance with the loaded information
                loadedUser = new User();
                loadedUser.setId(id);
                loadedUser.setUsername(name);
                loadedUser.setPassword(pwd);

                System.out.printf("Loaded User - ID: %d, Name: %s, Password: %s%n", id, name, pwd);
            } else {
                System.out.println("No user found with the given username and password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loadedUser;
    }

   }