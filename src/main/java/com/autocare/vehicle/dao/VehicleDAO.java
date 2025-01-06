package com.autocare.vehicle.dao;

import com.autocare.vehicle.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleDAO {
    void insertVehicle(Vehicle car) throws SQLException;

    List<Vehicle> loadVehicles() throws SQLException;

    boolean deleteVehicles(long vehicleId) throws SQLException;

    void updateVehicle(Vehicle car) throws SQLException;
}