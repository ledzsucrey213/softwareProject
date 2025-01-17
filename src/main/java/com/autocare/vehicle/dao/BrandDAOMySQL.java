package com.autocare.vehicle.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.vehicle.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDAOMySQL implements BrandDAO {
    @Override public List<Brand> getAllBrands() throws SQLException {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT id, name FROM brand";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement
                preparedStatement
                = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            Brand brand = new Brand(id, name);
            brands.add(brand);
        }

        preparedStatement.close();

        return brands;
    }
}
