package com.autocare.clientsubscription;

public class ClientSubscription {
    private long id; 
    private long clientId; 
    private long subscriptionId; 

    public ClientSubscription(long id, long clientId, long subscriptionId) {
        this.id = id;
        this.clientId = clientId;
        this.subscriptionId = subscriptionId;
    }

    public ClientSubscription(long clientId, long subscriptionId) {
        this.clientId = clientId;
        this.subscriptionId = subscriptionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}

