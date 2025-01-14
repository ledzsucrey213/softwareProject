package com.autocare.clientsubscription.service;

import com.autocare.clientsubscription.ClientSubscription;
import com.autocare.clientsubscription.dao.ClientSubscriptionDAO;
import com.autocare.clientsubscription.factory.ClientSubscriptionDAOFactory;

import java.sql.SQLException;
import java.util.List;

public class ClientSubscriptionService {
    private final ClientSubscriptionDAO clientSubscriptionDAO;

    public ClientSubscriptionService(ClientSubscriptionDAOFactory factory) {
        this.clientSubscriptionDAO = factory.createClientSubscriptionDAO();
    }

    public  void addClientSubscription(ClientSubscription clientSubscription) throws SQLException {
        clientSubscriptionDAO.addClientSubscription(clientSubscription);
    }

    public List<ClientSubscription> getSubscriptionsByClientId(long clientId) throws SQLException {
        return clientSubscriptionDAO.getSubscriptionsByClientId(clientId);
    }

    public void deleteClientSubscription(long clientId, long subscriptionId) throws SQLException {
        clientSubscriptionDAO.deleteClientSubscription(clientId, subscriptionId);
    }
}

