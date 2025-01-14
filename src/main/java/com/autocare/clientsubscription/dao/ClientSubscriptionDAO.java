package com.autocare.clientsubscription.dao;

import com.autocare.clientsubscription.ClientSubscription;

import java.sql.SQLException;
import java.util.List;

public interface ClientSubscriptionDAO {
    void addClientSubscription(ClientSubscription clientSubscription) throws SQLException;
    List<ClientSubscription> getSubscriptionsByClientId(long clientId) throws SQLException;
    List<ClientSubscription> getClientsBySubscriptionId(long subscriptionId) throws SQLException;
    void deleteClientSubscription(long clientId, long subscriptionId) throws SQLException;
}
