package com.autocare.subscription.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.subscription.Subscription;
import com.autocare.subscription.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOMySQL implements SubscriptionDAO {

    public SubscriptionDAOMySQL() {

    }

    /**
     * Inserts a new subscription into the database.
     * This method will store the subscription details such as type, label, status, amount, and description.
     *
     * @param subscription The {@link Subscription} object containing the subscription data to be inserted.
     * @throws SQLException If there is an issue with the database during the insertion.
     */

    public void insertSubscription(Subscription subscription)
    throws SQLException {
        String insertQuery =
                "INSERT INTO Subscription (type, label, is_active, amount, "
                + "description) VALUES (?, ?, ?, ?, ?)";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement insertstatement = connection.prepareStatement(
                insertQuery);

        // Set parameters for the query
        insertstatement.setString(1,
                                  subscription.getType().toString()
        ); // Assuming `type` is an enum
        insertstatement.setString(2, subscription.getLabel());
        insertstatement.setBoolean(3, subscription.getIsActive());
        insertstatement.setDouble(4, subscription.getAmount());
        insertstatement.setString(5, subscription.getDescription());

        // Execute the query
        insertstatement.executeUpdate();
        System.out.println("Subscription saved successfully!");

    }

    /**
     * Loads all subscriptions from the database.
     * This method queries the database and returns a list of {@link Subscription} objects
     * containing details such as id, type, label, status, amount, and description.
     *
     * @return A list of {@link Subscription} objects representing all subscriptions in the system.
     * @throws SQLException If there is an issue with the database during the query.
     */

    public List<Subscription> loadSubscription() throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT id, type, label, is_active, amount, description "
                       + "FROM subscription";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            SubscriptionType
                    type
                    = SubscriptionType.valueOf(resultSet.getString("type").toUpperCase());

            String label = resultSet.getString("label");
            boolean isActive = resultSet.getBoolean("is_active");
            double amount = resultSet.getDouble("amount");
            String description = resultSet.getString("description");

            Subscription subscription = new Subscription(id,
                                                         type,
                                                         label,
                                                         isActive,
                                                         amount,
                                                         description
            );
            subscriptions.add(subscription);
        }

        return subscriptions;
    }


    /**
     * Deletes a subscription from the database based on the provided subscription ID.
     * This method will remove the subscription record corresponding to the specified ID.
     *
     * @param subscriptionId The ID of the {@link Subscription} to be deleted.
     * @throws SQLException If there is an issue with the database during the deletion.
     */

    public void deleteSubscription(long subscriptionId) throws SQLException {
        String deleteSQL = "DELETE FROM subscription WHERE id = ?";

        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        deleteSQL);

            // Set the parameter in the SQL query
            preparedStatement.setLong(1, subscriptionId);

            // Execute the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Subscription with ID "
                                   + subscriptionId
                                   + " deleted successfully.");
            }
            else {
                System.out.println("No subscription found with ID "
                                   + subscriptionId
                                   + ".");
            }


    }

    /**
     * Updates the details of an existing subscription in the database.
     * This method updates the subscription’s label, type, amount, status, and description
     * based on the subscription’s ID.
     *
     * @param subscription The {@link Subscription} object containing the updated details to be stored.
     * @throws SQLException If there is an issue with the database during the update.
     * @throws IllegalArgumentException If the subscription ID is empty.
     */

    public void update(Subscription subscription) throws SQLException {
        if (subscription.getId().isEmpty()) {
            throw new IllegalArgumentException("Subscription ID cannot be empty.");
        }

        String query = "UPDATE subscription SET label = ?, type = ?, amount = ?, is_active = ?, description = ? WHERE id = ?";
        Connection connection = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        query);
            preparedStatement.setString(1, subscription.getLabel());
            preparedStatement.setString(2, subscription.getType().toString());
            preparedStatement.setDouble(3, subscription.getAmount());
            preparedStatement.setBoolean(4, subscription.getIsActive());
            preparedStatement.setString(5, subscription.getDescription());
            preparedStatement.setLong(6, subscription.getId().get());
            preparedStatement.executeUpdate();
    }
    @Override
    public Subscription loadSubscriptionById(long id) throws SQLException {
        String query = "SELECT id, type, label, is_active, amount, description FROM subscription WHERE id = ?";
        Subscription subscription = null;

        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                subscription = new Subscription(
                    resultSet.getLong("id"),
                    SubscriptionType.valueOf(resultSet.getString("type").toUpperCase()), // Conversion
                    resultSet.getString("label"),
                    resultSet.getBoolean("is_active"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("description")
                );
            }
        }

        return subscription;
    }



}
