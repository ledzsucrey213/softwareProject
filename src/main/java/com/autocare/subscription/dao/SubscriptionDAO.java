package com.autocare.subscription.dao;

import com.autocare.subscription.Subscription;
import com.autocare.user.User;

import java.sql.SQLException;
import java.util.*;

public interface SubscriptionDAO {

    void insertSubscription(Subscription subscription) throws SQLException;

    List<Subscription> loadSubscription() throws SQLException;

    void deleteSubscription(long subscriptionId) throws SQLException;

    void update(Subscription subscription) throws SQLException;

}