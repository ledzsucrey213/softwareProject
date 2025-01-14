package com.autocare.billing.factory;


import com.autocare.billing.dao.BillingDAO;
import com.autocare.billing.dao.BillDAOMySQL;

public class BillingMySQLFactory implements BillingAbstractFactory {
    @Override public BillingDAO createBillingDAO() {
        return new BillDAOMySQL();
    }
}
