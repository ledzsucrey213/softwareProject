package com.autocare.clientsubscription.factory;

import com.autocare.clientsubscription.dao.ClientSubscriptionDAO;

public abstract class ClientSubscriptionDAOFactory {
    public abstract ClientSubscriptionDAO createClientSubscriptionDAO();
}
