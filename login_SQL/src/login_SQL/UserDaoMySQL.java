package login_SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDaoMySQL {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/CarService"; 
        String dbUser = "root";
        String dbPassword = "Omaramerbaf.30052003";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            System.out.println("Connected");

            statement = connection.createStatement();

            String query = """
                SELECT user.ID_user, user.name, login_info.password
                FROM user
                JOIN login_info ON user.ID_user = login_info.ID_user
            """;

            resultSet = statement.executeQuery(query);
            System.out.println("Employee Details:");
            
            
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_user");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                System.out.printf("ID: %d, Name: %s, Password: %s%n", id, name, password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
