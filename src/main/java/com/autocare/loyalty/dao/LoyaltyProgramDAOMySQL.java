package com.autocare.loyalty.dao;

import com.autocare.loyalty.LoyaltyProgram;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoyaltyProgramDAOMySQL implements LoyaltyProgramDAO {

    @Override
    public void saveLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        String query = "INSERT INTO LoyaltyProgram (clientId, points, rewards) VALUES (?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE points = ?, rewards = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (loyaltyProgram.getClientId() != null) {
                statement.setLong(1, loyaltyProgram.getClientId());
            } else {
                throw new IllegalArgumentException("Client ID cannot be null when saving a LoyaltyProgram.");
            }

            statement.setInt(2, loyaltyProgram.getPoints());
            statement.setString(3, String.join(",", loyaltyProgram.getRewards()));
            statement.setInt(4, loyaltyProgram.getPoints());
            statement.setString(5, String.join(",", loyaltyProgram.getRewards()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LoyaltyProgram loadLoyaltyProgram(Long clientId) {
        String query = "SELECT clientId, points, rewards FROM LoyaltyProgram WHERE clientId = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long clientIdFromDB = resultSet.getLong("clientId");
                int points = resultSet.getInt("points");
                String rewardsStr = resultSet.getString("rewards");

                LoyaltyProgram loyaltyProgram = new LoyaltyProgram(clientIdFromDB, points);

                if (rewardsStr != null && !rewardsStr.isEmpty()) {
                    for (String reward : rewardsStr.split(",")) {
                        loyaltyProgram.addReward(reward);
                    }
                }

                return loyaltyProgram;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteLoyaltyProgram(Long clientId) {
        String query = "DELETE FROM LoyaltyProgram WHERE clientId = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, clientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        if (loyaltyProgram.getClientId() == null) {
            throw new IllegalArgumentException("Client ID cannot be null for updating a LoyaltyProgram.");
        }

        String query = "UPDATE LoyaltyProgram SET points = ?, rewards = ? WHERE clientId = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loyaltyProgram.getPoints());
            statement.setString(2, String.join(",", loyaltyProgram.getRewards()));
            statement.setLong(3, loyaltyProgram.getClientId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("LoyaltyProgram updated successfully for clientId: " + loyaltyProgram.getClientId().toString());
            } else {
                System.out.println("No LoyaltyProgram found for clientId: " + loyaltyProgram.getClientId().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
