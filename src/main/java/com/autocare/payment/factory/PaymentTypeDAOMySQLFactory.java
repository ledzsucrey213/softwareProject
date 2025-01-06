package com.autocare.payment.factory;


import com.autocare.payment.dao.PaymentTypeDAO;
import com.autocare.payment.dao.PaymentTypeDAOMySQL;

public class PaymentTypeDAOMySQLFactory implements PaymentTypeDAOFactory {
    @Override public PaymentTypeDAO createPaymentTypeDAO() {
        return new PaymentTypeDAOMySQL();
    }
}
