package login.bl;
import java.sql.*;



public class UserDaoMySql implements UserDAO {



    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CarService";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "Omaramerbaf.30052003";

    public UserDAOMySQL() {

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

            System.out.println("Connected to the database.");

        } catch (Exception e) {

            System.err.println("Connection failed: " + e.getMessage());

        }

    }



    public void saveUser(int id, String name, String surname, int roleId, String password) {

        String userQuery = "INSERT INTO user (ID_user, name, surname, roleID) VALUES (?, ?, ?, ?)";

        String loginQuery = "INSERT INTO login_info (ID_user, password) VALUES (?, ?)";



        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

             PreparedStatement userStmt = connection.prepareStatement(userQuery);

             PreparedStatement loginStmt = connection.prepareStatement(loginQuery)) {



            userStmt.setInt(1, id);

            userStmt.setString(2, name);

            userStmt.setString(3, surname);

            userStmt.setInt(4, roleId);

            userStmt.executeUpdate();



            loginStmt.setInt(1, id);

            loginStmt.setString(2, password);

            loginStmt.executeUpdate();



            System.out.println("User saved successfully.");



        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    public void loadUser(int userId) {

        String query = "SELECT u.ID_user, u.name, u.surname, r.label_role " +

                       "FROM user u " +

                       "JOIN role r ON u.roleID = r.ID_role " +

                       "WHERE u.ID_user = ?";



        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {



            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    System.out.println("ID: " + resultSet.getInt("ID_user"));

                    System.out.println("Name: " + resultSet.getString("name"));

                    System.out.println("Surname: " + resultSet.getString("surname"));

                    System.out.println("Role: " + resultSet.getString("label_role"));

                } else {

                    System.out.println("User not found.");

                }

            }



        } catch (Exception e) {

            e.printStackTrace();

        }

    }

