package com.autocare.payment.factory;

import com.autocare.payment.dao.PaymentTypeDAO;

public interface PaymentTypeDAOFactory {
    public PaymentTypeDAO createPaymentTypeDAO();
}
