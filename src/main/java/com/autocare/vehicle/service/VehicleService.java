package com.autocare.vehicle.service;

import com.autocare.vehicle.Brand;
import com.autocare.vehicle.Vehicle;
import com.autocare.vehicle.dao.BrandDAO;
import com.autocare.vehicle.dao.VehicleDAO;
import com.autocare.vehicle.factory.BrandDAOFactory;
import com.autocare.vehicle.factory.VehicleDAOFactory;

import java.sql.SQLException;
import java.util.List;

public class VehicleService {

    private final VehicleDAO vehicleDAO;
    private final BrandDAO   brandDAO;

    /**
     * Constructor for initializing the VehicleService with DAO factories.
     * This method initializes both the vehicle and brand DAOs.
     *
     * @param vehicleFactory Factory to create the {@link VehicleDAO} object.
     * @param brandFactory Factory to create the {@link BrandDAO} object.
     */

    public VehicleService(VehicleDAOFactory vehicleFactory,
                          BrandDAOFactory brandFactory) {
        vehicleDAO = vehicleFactory.createVehicleDAO();
        brandDAO = brandFactory.createBrandDAO();
    }

    /**
     * Adds a new vehicle to the database.
     *
     * @param vehicle The {@link Vehicle} object to be added to the database.
     * @throws SQLException If a database error occurs during the vehicle insertion.
     */

    public void addVehicle(Vehicle vehicle) throws SQLException {
        vehicleDAO.insertVehicle(vehicle);
    }

    /**
     * Deletes a vehicle from the database by its ID.
     *
     * @param vehicleId The ID of the vehicle to be deleted.
     * @return {@code true} if the vehicle was successfully deleted, otherwise {@code false}.
     * @throws SQLException If a database error occurs during the vehicle deletion.
     */

    public boolean deleteVehicle(long vehicleId) throws SQLException {
        return vehicleDAO.deleteVehicles(vehicleId);
    }

    /**
     * Retrieves all vehicles from the database.
     *
     * @return A list of {@link Vehicle} objects representing all the vehicles in the database.
     * @throws SQLException If a database error occurs during the retrieval of the vehicles.
     */

    public List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleDAO.loadVehicles();
    }

    /**
     * Updates the details of an existing vehicle in the database.
     *
     * @param vehicle The {@link Vehicle} object with updated details.
     * @throws SQLException If a database error occurs during the vehicle update.
     */

    public void updateVehicle(Vehicle vehicle) throws SQLException {
        vehicleDAO.updateVehicle(vehicle);
    }

    /**
     * Retrieves all vehicle brands from the database.
     *
     * @return A list of {@link Brand} objects representing all the vehicle brands.
     * @throws SQLException If a database error occurs during the retrieval of the brands.
     */

    public List<Brand> getAllBrands() throws SQLException {
        return brandDAO.getAllBrands();
    }

}