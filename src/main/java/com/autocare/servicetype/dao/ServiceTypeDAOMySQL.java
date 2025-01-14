package com.autocare.servicetype.dao;

import com.autocare.servicetype.ServiceType;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceTypeDAOMySQL implements ServiceTypeDAO {

    @Override
    public void saveServiceType(ServiceType serviceType) {
        String query = "INSERT INTO ServiceType (name, description) VALUES (?, ?) " +
                       "ON DUPLICATE KEY UPDATE name = ?, description = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, serviceType.getName());
            statement.setString(2, serviceType.getDescription());
            statement.setString(3, serviceType.getName());
            statement.setString(4, serviceType.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceType loadServiceType(int serviceTypeId) {
        String query = "SELECT * FROM ServiceType WHERE serviceTypeId = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, serviceTypeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("serviceTypeId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                return new ServiceType(id, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteServiceType(int serviceTypeId) {
        String query = "DELETE FROM ServiceType WHERE serviceTypeId = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, serviceTypeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Override
    public List<ServiceType> loadAllServiceTypes() throws SQLException {
        List<ServiceType> serviceTypes = new ArrayList<>();
        String query = "SELECT serviceTypeId, name, description FROM ServiceType";

        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("serviceTypeId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                // Ajouter un nouvel objet ServiceType à la liste
                serviceTypes.add(new ServiceType(id, name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Propager l'exception pour que l'appelant puisse la gérer
        }

        return serviceTypes;
    }

}
