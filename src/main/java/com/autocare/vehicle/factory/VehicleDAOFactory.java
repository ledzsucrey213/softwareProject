package com.autocare.vehicle.factory;

import com.autocare.vehicle.dao.VehicleDAO;

public interface VehicleDAOFactory {
    VehicleDAO createVehicleDAO();
}
