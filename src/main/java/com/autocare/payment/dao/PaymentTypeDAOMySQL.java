package com.autocare.payment.dao;

import com.autocare.payment.PaymentType;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of the {@link PaymentTypeDAO} interface for interacting with a MySQL database.
 * It provides methods for CRUD (Create, Read, Update, Delete) operations on the {@link PaymentType} entity.
 * All database connections are managed via the {@link SqlConnectionManager}.
 */

public class PaymentTypeDAOMySQL implements PaymentTypeDAO {

    /**
     * Inserts a new {@link PaymentType} into the database.
     *
     * @param paymentType The {@link PaymentType} object to be inserted.
     * @throws SQLException If there is an issue with the SQL execution or database connection.
     */

    @Override public void insertPaymentType(PaymentType paymentType)
    throws SQLException {
        String query = "INSERT INTO payment_type (label, fees, isAvailable) "
                       + "VALUES (?, ?, ?)";

        Connection con = SqlConnectionManager.getConnection();
        PreparedStatement statement = con.prepareStatement(query);

        statement.setString(1, paymentType.getLabel());
        statement.setDouble(2, paymentType.getFees());
        statement.setBoolean(3, paymentType.isAvailable());

        statement.executeUpdate();

        statement.close();
    }

    /**
     * Loads all {@link PaymentType} records from the database.
     *
     * @return A list of {@link PaymentType} objects representing all the payment types in the database.
     * @throws SQLException If there is an issue with the SQL execution or database connection.
     */

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

    /**
     * Deletes a {@link PaymentType} from the database based on its ID.
     *
     * @param paymentTypeID The ID of the {@link PaymentType} to be deleted.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     * @throws SQLException If there is an issue with the SQL execution or database connection.
     */

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


    /**
     * Updates an existing {@link PaymentType} in the database.
     *
     * @param paymentType The {@link PaymentType} object with updated data.
     * @throws SQLException If there is an issue with the SQL execution or database connection.
     */

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
