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

    public VehicleService(VehicleDAOFactory vehicleFactory,
                          BrandDAOFactory brandFactory) {
        vehicleDAO = vehicleFactory.createVehicleDAO();
        brandDAO = brandFactory.createBrandDAO();
    }

    public void addVehicle(Vehicle vehicle) throws SQLException {
        vehicleDAO.insertVehicle(vehicle);
    }

    public boolean deleteVehicle(long vehicleId) throws SQLException {
        return vehicleDAO.deleteVehicles(vehicleId);
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleDAO.loadVehicles();
    }

    public void updateVehicle(Vehicle vehicle) throws SQLException {
        vehicleDAO.updateVehicle(vehicle);
    }

    public List<Brand> getAllBrands() throws SQLException {
        return brandDAO.getAllBrands();
    }

}