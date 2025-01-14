package com.autocare.clientsubscription.dao;

import com.autocare.clientsubscription.ClientSubscription;
import com.autocare.sql.SqlConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientSubscriptionDAOMySQL implements ClientSubscriptionDAO {

    @Override
    public void addClientSubscription(ClientSubscription clientSubscription) throws SQLException {
        String query = "INSERT INTO client_subscription (client_id, subscription_id) VALUES (?, ?)";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, clientSubscription.getClientId());
            statement.setLong(2, clientSubscription.getSubscriptionId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<ClientSubscription> getSubscriptionsByClientId(long clientId) throws SQLException {
        String query = "SELECT * FROM client_subscription WHERE client_id = ?";
        List<ClientSubscription> clientSubscriptions = new ArrayList<>();
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                clientSubscriptions.add(new ClientSubscription(
                        resultSet.getLong("id"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("subscription_id")
                ));
            }
        }
        return clientSubscriptions;
    }

    @Override
    public List<ClientSubscription> getClientsBySubscriptionId(long subscriptionId) throws SQLException {
        String query = "SELECT * FROM client_subscription WHERE subscription_id = ?";
        List<ClientSubscription> clientSubscriptions = new ArrayList<>();
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, subscriptionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                clientSubscriptions.add(new ClientSubscription(
                        resultSet.getLong("id"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("subscription_id")
                ));
            }
        }
        return clientSubscriptions;
    }

    @Override
    public void deleteClientSubscription(long clientId, long subscriptionId) throws SQLException {
        String query = "DELETE FROM client_subscription WHERE client_id = ? AND subscription_id = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, clientId);
            statement.setLong(2, subscriptionId);
            statement.executeUpdate();
        }
    }
    public List<ClientSubscription> getAllClientSubscriptions() throws SQLException {
        String query = "SELECT * FROM client_subscription";
        List<ClientSubscription> clientSubscriptions = new ArrayList<>();

        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clientSubscriptions.add(new ClientSubscription(
                        resultSet.getLong("id"),                // ID de la liaison
                        resultSet.getLong("client_id"),         // ID du client
                        resultSet.getLong("subscription_id")    // ID de l'abonnement
                ));
            }
        }

        return clientSubscriptions;
    }

}

