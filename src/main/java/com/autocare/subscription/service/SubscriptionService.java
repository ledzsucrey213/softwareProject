package com.autocare.subscription.service;

import com.autocare.subscription.Subscription;
import com.autocare.subscription.dao.SubscriptionDAO;
import com.autocare.subscription.factory.SubscriptionDAOFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Service class for managing subscriptions.
 * This class provides business logic for interacting with the data access layer
 * (DAO) to perform CRUD (Create, Read, Update, Delete) operations on {@link Subscription} objects.
 * It acts as a bridge between the controller layer and the data access layer.
 */

public class SubscriptionService {
    private final SubscriptionDAO subscriptionDAO;

    /**
     * Constructor for creating a SubscriptionService instance.
     * It initializes the {@link SubscriptionDAO} using the provided factory.
     *
     * @param subscriptionFactory The {@link SubscriptionDAOFactory} used to create the DAO object.
     */

    public SubscriptionService(SubscriptionDAOFactory subscriptionFactory) {
        this.subscriptionDAO = subscriptionFactory.createSubscriptionDAO();
    }

    /**
     * Retrieves all subscriptions from the database.
     *
     * @return A list of {@link Subscription} objects representing all subscriptions in the system.
     * @throws SQLException If there is an issue with accessing the database.
     */

    public List<Subscription> getAllSubscriptions() throws SQLException {
        return subscriptionDAO.loadSubscription();
    }

    /**
     * Adds a new subscription to the database.
     *
     * @param subscription The {@link Subscription} object to be added to the system.
     * @throws SQLException If there is an issue with the database or while inserting the subscription.
     */

    public void addSubscription(Subscription subscription) throws SQLException {
        subscriptionDAO.insertSubscription(subscription);
    }

    /**
     * Deletes an existing subscription from the database based on its ID.
     *
     * @param subscription The {@link Subscription} object to be deleted.
     * @throws SQLException If there is an issue with the database or while deleting the subscription.
     * @throws IllegalArgumentException If the subscription ID is empty.
     */

    public void deleteSubscription(Subscription subscription)
    throws SQLException {
        if (subscription.getId().isEmpty()) {
            throw new IllegalArgumentException("Subscription id cannot be "
                                               + "empty");
        }

        subscriptionDAO.deleteSubscription(subscription.getId().get());
    }

    /**
     * Updates the details of an existing subscription in the database.
     *
     * @param subscription The {@link Subscription} object containing the updated details.
     * @throws SQLException If there is an issue with the database or while updating the subscription.
     */

    public void updateSubscription(Subscription subscription)
    throws SQLException {
        subscriptionDAO.update(subscription);
    }

}
