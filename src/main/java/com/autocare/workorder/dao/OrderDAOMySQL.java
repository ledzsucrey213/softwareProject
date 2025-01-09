package com.autocare.workorder.dao;

import com.autocare.workorder.Order;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOMySQL implements OrderDAO {

    @Override public void insertOrder(Order o)
    throws SQLException {
        String query = "INSERT INTO order (partName, partQuantity, orderStatus, price, estimatedArrival) "
                       + "VALUES (?, ?, ?, ?, ?)";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setString(1, o.getLabel());
        statement.setDouble(2, o.getFees());
        statement.setBoolean(3, o.isAvailable());

        statement.executeUpdate();

        statement.close();
    }

    @Override public List<PaymentType> loadPaymentTypes() throws SQLException {
        List<PaymentType> paymentTypes = new ArrayList<>();
        String query = "SELECT id, label, fees, isAvailable FROM payment_type";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            paymentTypes.add(new PaymentType(resultSet.getLong("id"),
                                             resultSet.getString("label"),
                                             resultSet.getDouble("fees"),
                                             resultSet.getBoolean("isAvailable")
            ));
        }

        statement.close();

        return paymentTypes;
    }

    @Override public boolean deletePaymentType(long paymentTypeID)
    throws SQLException {
        String query = "DELETE FROM payment_type WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setLong(1, paymentTypeID);

        int rowsUpdated = statement.executeUpdate();
        statement.close();

        return rowsUpdated > 0;
    }

    @Override public void updatePaymentType(PaymentType paymentType)
    throws SQLException {
        String query = "UPDATE payment_type SET label = ?, fees = ?, "
                       + "isAvailable = ? WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setString(1, paymentType.getLabel());
        statement.setDouble(2, paymentType.getFees());
        statement.setBoolean(3, paymentType.isAvailable());
        statement.setLong(4, paymentType.getId());

        statement.executeUpdate();
        statement.close();
    }
}
