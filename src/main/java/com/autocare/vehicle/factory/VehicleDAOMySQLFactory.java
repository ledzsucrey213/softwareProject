package com.autocare.vehicle.factory;

import com.autocare.vehicle.dao.VehicleDAO;
import com.autocare.vehicle.dao.VehicleDAOMySQL;

public class VehicleDAOMySQLFactory implements VehicleDAOFactory {
    @Override public VehicleDAO createVehicleDAO() {
        return new VehicleDAOMySQL();
    }
}
