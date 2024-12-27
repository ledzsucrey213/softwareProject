
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import subscription.bl.Subscription;
import subscription.bl.SubscriptionType;
/**
 * 
 */
public class SubscriptionDAOMySQL implements SubscriptionDAO {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Omaramerbaf.30052003";
    public SubscriptionDAOMySQL() {
    	
    }

    /**
     * 
     */
    public void saveSubscription(Subscription subscription) {
        String insertQuery = "INSERT INTO Subscription (type, label, is_active, amount, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            // Set parameters for the query
            preparedStatement.setString(1, subscription.getType().toString()); // Assuming `type` is an enum
            preparedStatement.setString(2, subscription.getLabel());
            preparedStatement.setBoolean(3, subscription.isActive());
            preparedStatement.setDouble(4, subscription.getAmount());
            preparedStatement.setString(5, subscription.getDescription());

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("Subscription saved successfully!");

        } catch (Exception e) {
            System.err.println("Error saving subscription: " + e.getMessage());
        }
    }

    /**
     * 
     */
    public List<Subscription> loadSubscription() {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT id, type, label, is_active, amount, description FROM subscriptions";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                // Directly use SubscriptionType.valueOf to parse the type
                String typeString = resultSet.getString("type");
                SubscriptionType type = null;
                if (typeString != null && !typeString.trim().isEmpty()) {
                    try {
                        type = SubscriptionType.valueOf(typeString.trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Unknown subscription type: " + typeString);
                        type = SubscriptionType.MONTHLY; // default value in case of invalid type
                    }
                } else {
                    type = SubscriptionType.MONTHLY; // default if typeString is null or empty
                }

                String label = resultSet.getString("label");
                boolean isActive = resultSet.getBoolean("is_active");
                double amount = resultSet.getDouble("amount");
                String description = resultSet.getString("description");

                Subscription subscription = new Subscription(id, type, label, isActive, amount, description);
                subscriptions.add(subscription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subscriptions;
    }

    /**
     * 
     */
    public void deleteSubscription(int subscriptionId) {
        String deleteSQL = "DELETE FROM subscriptions WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            // Set the parameter in the SQL query
            preparedStatement.setInt(1, subscriptionId);

            // Execute the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Subscription with ID " + subscriptionId + " deleted successfully.");
            } else {
                System.out.println("No subscription found with ID " + subscriptionId + ".");
            }

        } catch (SQLException e) {
            System.err.println("Error while deleting subscription: " + e.getMessage());
        }
    }

    /**
     * 
     */
}