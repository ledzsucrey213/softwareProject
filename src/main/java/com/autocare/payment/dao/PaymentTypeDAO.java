package com.autocare.payment.dao;

import com.autocare.payment.PaymentType;

import java.sql.SQLException;
import java.util.List;

public interface PaymentTypeDAO {
    void insertPaymentType(PaymentType paymentType) throws SQLException;

    List<PaymentType> loadPaymentTypes() throws SQLException;

    boolean deletePaymentType(long paymentTypeID) throws SQLException;

    void updatePaymentType(PaymentType paymentType) throws SQLException;
}