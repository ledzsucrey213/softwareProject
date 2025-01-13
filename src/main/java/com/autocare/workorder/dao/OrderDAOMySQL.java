package com.autocare.workorder.dao;

import com.autocare.workorder.*;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderDAOMySQL implements OrderDAO {

    @Override
    public void insertOrder(Order o) throws SQLException {
        String query = "INSERT INTO `order` (partName, partQuantity, orderStatus, price, estimatedArrival) "
                       + "VALUES (?, ?, ?, ?, ?)";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setString(1, o.getPartName());
        statement.setInt(2, o.getPartQuantity());
        statement.setString(3, o.getOrderStatus().toString()); // Convert Status to String
        statement.setDouble(4, o.getPrice());
        statement.setDate(5, new Date(o.getEstimatedArrival().getTime())); // Convert java.util.Date to java.sql.Date

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Order loadOrder(long orderId) throws SQLException {
        String query = "SELECT id, partName, partQuantity, orderStatus, price, estimatedArrival "
                       + "FROM `order` WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setLong(1, orderId);

        ResultSet resultSet = statement.executeQuery();
        Order order = null;

        if (resultSet.next()) {
            order = new Order(
                resultSet.getLong("id"),
                resultSet.getString("partName"),
                resultSet.getInt("partQuantity"),
                Status.fromValue(resultSet.getString("orderStatus")), // Convert String to Status
                resultSet.getDouble("price"),
                resultSet.getDate("estimatedArrival") // Use java.sql.Date directly
            );
        }

        statement.close();
        return order;
    }

    @Override
    public boolean deleteOrder(long orderId) throws SQLException {
        String query = "DELETE FROM `order` WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setLong(1, orderId);

        int rowsUpdated = statement.executeUpdate();
        statement.close();

        return rowsUpdated > 0;
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE `order` SET partName = ?, partQuantity = ?, orderStatus = ?, "
                       + "price = ?, estimatedArrival = ? WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setString(1, order.getPartName());
        statement.setInt(2, order.getPartQuantity());
        statement.setString(3, order.getOrderStatus().toString()); // Convert Status to String
        statement.setDouble(4, order.getPrice());
        statement.setDate(5, new Date(order.getEstimatedArrival().getTime())); // Convert java.util.Date to java.sql.Date
        statement.setLong(6, order.getId());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public List<Order> loadAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, partName, partQuantity, orderStatus, price, estimatedArrival FROM `order`";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            orders.add(new Order(
                resultSet.getLong("id"),
                resultSet.getString("partName"),
                resultSet.getInt("partQuantity"),
                Status.fromValue(resultSet.getString("orderStatus")), // Convert String to Status
                resultSet.getDouble("price"),
                resultSet.getDate("estimatedArrival") // Use java.sql.Date directly
            ));
        }

        statement.close();
        return orders;
    }
}
