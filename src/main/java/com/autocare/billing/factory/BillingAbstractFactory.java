package com.autocare.billing.factory;

import com.autocare.billing.dao.BillingDAO;

public interface BillingAbstractFactory {
    public BillingDAO createBillingDAO();
}
