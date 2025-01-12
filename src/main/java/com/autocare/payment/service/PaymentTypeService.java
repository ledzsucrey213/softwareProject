package com.autocare.payment.service;

import com.autocare.payment.PaymentType;
import com.autocare.payment.dao.PaymentTypeDAO;
import com.autocare.payment.factory.PaymentTypeDAOFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for managing payment types.
 * This class provides business logic for interacting with the data access layer
 * (DAO) to perform CRUD (Create, Read, Update, Delete) operations on {@link PaymentType} objects.
 * It acts as a bridge between the controller layer and the data access layer.
 */
public class PaymentTypeService {

    // DAO instance for interacting with the payment_type table in the database
    final private PaymentTypeDAO paymentTypeDAO;

    /**
     * Constructor for creating a PaymentTypeService instance.
     * It initializes the {@link PaymentTypeDAO} using the provided factory.
     *
     * @param paymentTypeFactory The {@link PaymentTypeDAOFactory} used to create the DAO object.
     */
    public PaymentTypeService(PaymentTypeDAOFactory paymentTypeFactory) {
        paymentTypeDAO = paymentTypeFactory.createPaymentTypeDAO();
    }

    /**
     * Retrieves all payment types from the database.
     *
     * @return A list of {@link PaymentType} objects representing all payment types in the system.
     * @throws SQLException If there is an issue with accessing the database.
     */
    public List<PaymentType> getAllPaymentType() throws SQLException {
        return paymentTypeDAO.loadPaymentTypes();
    }

    /**
     * Adds a new payment type to the database.
     *
     * @param paymentType The {@link PaymentType} object to be added to the system.
     * @throws SQLException If there is an issue with the database or while inserting the payment type.
     */
    public void addPaymentType(PaymentType paymentType) throws SQLException {
        paymentTypeDAO.insertPaymentType(paymentType);
    }

    /**
     * Deletes an existing payment type from the database based on its ID.
     *
     * @param paymentType The {@link PaymentType} object to be deleted.
     * @throws SQLException If there is an issue with the database or while deleting the payment type.
     */
    public void deleteSubscription(PaymentType paymentType) throws SQLException {
        paymentTypeDAO.deletePaymentType(paymentType.getId());
    }

    /**
     * Updates the details of an existing payment type in the database.
     *
     * @param paymentType The {@link PaymentType} object containing the updated details.
     * @throws SQLException If there is an issue with the database or while updating the payment type.
     */
    public void updatePaymentType(PaymentType paymentType) throws SQLException {
        paymentTypeDAO.updatePaymentType(paymentType);
    }
}
