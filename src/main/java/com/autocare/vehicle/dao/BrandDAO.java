package com.autocare.vehicle.dao;

import com.autocare.vehicle.Brand;

import java.sql.SQLException;
import java.util.List;

public interface BrandDAO {
    public List<Brand> getAllBrands() throws SQLException;
}
