package com.autocare.vehicle.factory;

import com.autocare.vehicle.dao.BrandDAO;
import com.autocare.vehicle.dao.BrandDAOMySQL;

public class BrandDAOMySQLFactory implements BrandDAOFactory {
    @Override public BrandDAO createBrandDAO() {
        return new BrandDAOMySQL();
    }
}
