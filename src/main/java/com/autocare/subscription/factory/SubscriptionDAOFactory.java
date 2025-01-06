package com.autocare.subscription.factory;

import com.autocare.subscription.dao.SubscriptionDAO;

public interface SubscriptionDAOFactory {
    SubscriptionDAO createSubscriptionDAO();
}
