package com.autocare.subscription.service;

import com.autocare.subscription.Subscription;
import com.autocare.subscription.dao.SubscriptionDAO;
import com.autocare.subscription.factory.SubscriptionDAOFactory;

import java.sql.SQLException;
import java.util.List;

public class SubscriptionService {
    private final SubscriptionDAO subscriptionDAO;

    public SubscriptionService(SubscriptionDAOFactory subscriptionFactory) {
        this.subscriptionDAO = subscriptionFactory.createSubscriptionDAO();
    }

    public List<Subscription> getAllSubscriptions() throws SQLException {
        return subscriptionDAO.loadSubscription();
    }

    public void addSubscription(Subscription subscription) throws SQLException {
        subscriptionDAO.insertSubscription(subscription);
    }

    public void deleteSubscription(Subscription subscription)
    throws SQLException {
        if (subscription.getId().isEmpty()) {
            throw new IllegalArgumentException("Subscription id cannot be "
                                               + "empty");
        }

        subscriptionDAO.deleteSubscription(subscription.getId().get());
    }

    public void updateSubscription(Subscription subscription)
    throws SQLException {
        subscriptionDAO.update(subscription);
    }

}
