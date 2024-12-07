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

   }