package com.autocare.clientsubscription.factory;

import com.autocare.clientsubscription.dao.ClientSubscriptionDAO;
import com.autocare.clientsubscription.dao.ClientSubscriptionDAOMySQL;

public class ClientSubscriptionDAOFactoryMySQL extends ClientSubscriptionDAOFactory {
    @Override
    public ClientSubscriptionDAO createClientSubscriptionDAO() {
        return new ClientSubscriptionDAOMySQL();
    }
}

