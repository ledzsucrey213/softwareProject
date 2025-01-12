package com.autocare.billing.dao;

import com.autocare.billing.Bill;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAOMySQL implements BillingDAO {

    @Override
    public void insertBill(Bill bill) throws SQLException {
        String query = "INSERT INTO bill (client_id, service_type, bill_date, bill_status, cost) "
                       + "VALUES (?, ?, ?, ?, ?)";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setInt(1, bill.getClientId());
        statement.setString(2, bill.getServiceType());
        statement.setDate(3, new java.sql.Date(bill.getBillDate().getTime()));
        statement.setString(4, bill.getBillStatus());
        statement.setDouble(5, bill.getCost());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public Bill loadBill(long billId) throws SQLException {
        String query = "SELECT id, client_id, service_type, bill_date, bill_status, cost "
                       + "FROM bill WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setLong(1, billId);

        ResultSet resultSet = statement.executeQuery();
        Bill bill = null;

        if (resultSet.next()) {
            bill = new Bill(
                resultSet.getLong("id"),
                resultSet.getInt("client_id"),
                resultSet.getString("service_type"),
                resultSet.getDate("bill_date"),
                resultSet.getString("bill_status"),
                resultSet.getDouble("cost")
            );
        }

        statement.close();
        return bill;
    }

    @Override
    public boolean deleteBill(long billId) throws SQLException {
        String query = "DELETE FROM bill WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setLong(1, billId);

        int rowsUpdated = statement.executeUpdate();
        statement.close();

        return rowsUpdated > 0;
    }

    @Override
    public void updateBill(Bill bill) throws SQLException {
        String query = "UPDATE bill SET client_id = ?, service_type = ?, bill_date = ?, "
                       + "bill_status = ?, cost = ? WHERE id = ?";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setInt(1, bill.getClientId());
        statement.setString(2, bill.getServiceType());
        statement.setDate(3, new java.sql.Date(bill.getBillDate().getTime()));
        statement.setString(4, bill.getBillStatus());
        statement.setDouble(5, bill.getCost());
        statement.setLong(6, bill.getId());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public List<Bill> loadAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT id, client_id, service_type, bill_date, bill_status, cost FROM bill";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            bills.add(new Bill(
                resultSet.getLong("id"),
                resultSet.getInt("client_id"),
                resultSet.getString("service_type"),
                resultSet.getDate("bill_date"),
                resultSet.getString("bill_status"),
                resultSet.getDouble("cost")
            ));
        }

        statement.close();
        return bills;
    }
}
