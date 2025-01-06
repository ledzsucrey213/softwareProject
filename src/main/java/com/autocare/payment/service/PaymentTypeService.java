package com.autocare.payment.service;

import com.autocare.payment.PaymentType;
import com.autocare.payment.dao.PaymentTypeDAO;
import com.autocare.payment.factory.PaymentTypeDAOFactory;

import java.sql.SQLException;
import java.util.List;

public class PaymentTypeService {

    final private PaymentTypeDAO paymentTypeDAO;

    public PaymentTypeService(PaymentTypeDAOFactory paymentTypeFactory) {
        paymentTypeDAO = paymentTypeFactory.createPaymentTypeDAO();
    }

    public List<PaymentType> getAllPaymentType() throws SQLException {
        return paymentTypeDAO.loadPaymentTypes();
    }

    public void addPaymentType(PaymentType paymentType) throws SQLException {
        paymentTypeDAO.insertPaymentType(paymentType);
    }

    public void deleteSubscription(PaymentType paymentType)
    throws SQLException {
        paymentTypeDAO.deletePaymentType(paymentType.getId());
    }

    public void updatePaymentType(PaymentType paymentType) throws SQLException {
        paymentTypeDAO.updatePaymentType(paymentType);
    }
}