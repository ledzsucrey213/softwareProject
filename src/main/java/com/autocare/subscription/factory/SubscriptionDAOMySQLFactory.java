package com.autocare.subscription.factory;

import com.autocare.subscription.dao.SubscriptionDAO;
import com.autocare.subscription.dao.SubscriptionDAOMySQL;

public class SubscriptionDAOMySQLFactory implements SubscriptionDAOFactory {
    @Override public SubscriptionDAO createSubscriptionDAO() {
        return new SubscriptionDAOMySQL();
    }
}
