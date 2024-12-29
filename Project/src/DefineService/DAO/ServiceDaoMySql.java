import java.sql.*;

import login.bl.service;
import login.bl.Role;

public class ServiceDaoMySql implements ServiceDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CarService";
    private static final String DB_service = "root";
    private static final String DB_PASSWORD = "Omaramerbaf.30052003";

    public ServiceDaoMySql() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_service, DB_PASSWORD)) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveService(String name, double price, String categorie, double duration, boolean availability) {
        String serviceQuery = "INSERT INTO service (name, price, categorie, duration, availability) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_service, DB_PASSWORD);
             PreparedStatement serviceStmt = connection.prepareStatement(serviceQuery)) {

            // Insert service into the service table
            serviceStmt.setString(1, name);
            serviceStmt.setDouble(2, price);
            serviceStmt.setString(3, categorie);
            serviceStmt.setDouble(4, duration);
            serviceStmt.setBoolean(4, availability);
            serviceStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   public service loadService(String IDServ) {
    String query = "SELECT s.IDServ, s.name, s.price, s.categorie, s.duration, s.availability";

    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_service, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setInt(1, IDServ);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
               

                // Create Service object
                Service service = new Service();
                service.setId(resultSet.getInt("ID_service"));
                service.setName(resultSet.getString("name"));
                service.setPrice(resultSet.getDouble("price"));
                service.setCategorie(resultSet.getString("categorie"));
                service.setDuration(resultSet.getDouble("duration"));
                service.setAvailability(resultSet.getBoolean("availability"));

                return service;
            } else {
                // No service found
                return null;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}



}
