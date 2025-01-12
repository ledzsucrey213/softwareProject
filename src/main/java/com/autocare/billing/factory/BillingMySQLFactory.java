package com.autocare.billing.factory;


import com.autocare.billing.dao.BillingDAO;
import com.autocare.billing.dao.BillingDAOMySQL;

public class BillingMySQLFactory implements BillingAbstractFactory {
    @Override public BillingDAO createBillingDAO() {
        return new BillingDAOMySQL();
    }
}
