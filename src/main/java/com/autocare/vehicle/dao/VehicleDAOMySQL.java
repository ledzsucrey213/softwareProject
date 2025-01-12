package com.autocare.vehicle.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.vehicle.Brand;
import com.autocare.vehicle.FuelType;
import com.autocare.vehicle.Vehicle;
import com.autocare.vehicle.VehicleType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOMySQL implements VehicleDAO {

    public VehicleDAOMySQL() {
    }

    /**
     * Inserts a new vehicle into the database.
     *
     * @param car The {@link Vehicle} object containing the details of the vehicle to be inserted.
     * @throws SQLException If a database error occurs during the insertion.
     */

    // Save a car to the database
    public void insertVehicle(Vehicle car) throws SQLException {
        String insertQuery = "INSERT INTO vehicle"
                             + " (year, color, type, fuel_type, "
                             + "engine_size, brand_id, model) VALUES (?, ?, "
                             + "?, ?, ?, ?, ?)";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement insertStatement = connection.prepareStatement(
                insertQuery);

        // Set parameters for the query
        insertStatement.setInt(1, car.getYear().getValue());
        insertStatement.setString(2, car.getColor());
        insertStatement.setString(3, car.getType().toString());
        insertStatement.setString(4, car.getFuelType().toString());
        insertStatement.setLong(5, car.getEngineSize());
        insertStatement.setLong(6, car.getBrand().getId());
        insertStatement.setString(7, car.getModel());

        // Execute the query
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    /**
     * Loads all vehicles from the database.
     *
     * @return A list of {@link Vehicle} objects representing all vehicles in the database.
     * @throws SQLException If a database error occurs during the retrieval of vehicles.
     */

    // Load all cars from the database
    public List<Vehicle> loadVehicles() throws SQLException {
        List<Vehicle> cars = new ArrayList<>();
        String query = "SELECT veh"
                       + "icle.id, year, color, type, fuel_type, "
                       + "engine_size, "
                       + "brand_id, brand.name, model FROM vehicle JOIN brand"
                       + " ON "
                       + "vehicle.brand_id = brand.id";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong("vehicle.id");
            Year year = Year.of(resultSet.getInt("year"));
            String color = resultSet.getString("color");
            VehicleType type = VehicleType.valueOf(resultSet.getString("type").toUpperCase());
            FuelType
                    fuelType
                    = FuelType.valueOf(resultSet.getString("fuel_type"));
            long engineSize = resultSet.getLong("engine_size");
            long brandId = resultSet.getLong("brand_id");
            String brandName = resultSet.getString("brand.name");
            Brand brand = new Brand(brandId, brandName);
            String model = resultSet.getString("model");

            Vehicle car = new Vehicle(id,
                                      year,
                                      color,
                                      type,
                                      fuelType,
                                      engineSize,
                                      brand,
                                      model
            );
            cars.add(car);
        }

        statement.close();
        return cars;
    }

    /**
     * Deletes a vehicle from the database.
     *
     * @param carId The ID of the vehicle to be deleted.
     * @return A boolean indicating whether the deletion was successful.
     * @throws SQLException If a database error occurs during the deletion.
     */

    // Delete a car from the database
    public boolean deleteVehicles(long carId) throws SQLException {
        String deleteSQL = "DELETE FROM vehicle WHERE id = ?";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                deleteSQL);

        // Set the parameter in the SQL query
        preparedStatement.setLong(1, carId);

        // Execute the deletion
        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected > 0;
    }

    /**
     * Updates the details of an existing vehicle in the database.
     *
     * @param vehicle The {@link Vehicle} object containing the updated details of the vehicle.
     * @throws SQLException If a database error occurs during the update.
     */

    // Update a car in the database
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        if (vehicle.getId().isEmpty()) {
            throw new IllegalArgumentException("Vehicle cannot be empty");
        }

        String query =
                "UPDATE vehicle SET year = ?, color = ?, type = ?, fuel_type = "
                + "?, engine_size = ?, brand_id = ?, model = ? WHERE id = ?";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement
                preparedStatement
                = connection.prepareStatement(query);

        preparedStatement.setInt(1, vehicle.getYear().getValue());
        preparedStatement.setString(2, vehicle.getColor());
        preparedStatement.setString(3, vehicle.getType().toString());
        preparedStatement.setString(4, vehicle.getFuelType().toString());
        preparedStatement.setLong(5, vehicle.getEngineSize());
        preparedStatement.setLong(6, vehicle.getBrand().getId());
        preparedStatement.setString(7, vehicle.getModel());
        preparedStatement.setLong(8, vehicle.getId().get());

        // Execute the update
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
