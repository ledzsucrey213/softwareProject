package com.autocare.loyalty.service;

import com.autocare.loyalty.LoyaltyProgram;
import com.autocare.loyalty.dao.LoyaltyProgramDAO;
import com.autocare.loyalty.factory.LoyaltyProgramDAOMySqlFactory;

public class LoyaltyProgramService {
    private final LoyaltyProgramDAO loyaltyProgramDAO;

    // Constructor uses the Singleton Factory to create DAO
    public LoyaltyProgramService() {
        this.loyaltyProgramDAO = LoyaltyProgramDAOMySqlFactory.getInstance().createLoyaltyProgramDAO();
    }

    // Updates the points balance for a LoyaltyProgram
    public boolean updatePoints(Long clientId, int points) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramDAO.loadLoyaltyProgram(clientId);
        if (loyaltyProgram == null) {
            throw new IllegalArgumentException("Loyalty Program not found for clientId: " + clientId);
        }

        loyaltyProgram.setPoints(loyaltyProgram.getPoints() + points);
        loyaltyProgramDAO.updateLoyaltyProgram(loyaltyProgram);
        return true;
    }

    // Redeems a reward from a LoyaltyProgram
    public boolean redeemReward(Long clientId, String rewardId) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramDAO.loadLoyaltyProgram(clientId);
        if (loyaltyProgram == null) {
            throw new IllegalArgumentException("Loyalty Program not found for clientId: " + clientId);
        }

        if (!loyaltyProgram.getRewards().contains(rewardId)) {
            throw new IllegalArgumentException("Reward not found for clientId: " + clientId);
        }

        loyaltyProgram.getRewards().remove(rewardId);
        loyaltyProgramDAO.updateLoyaltyProgram(loyaltyProgram);
        return true;
    }

    // Reverses a redeemed reward
    public boolean reverseReward(Long clientId, String rewardId) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramDAO.loadLoyaltyProgram(clientId);
        if (loyaltyProgram == null) {
            throw new IllegalArgumentException("Loyalty Program not found for clientId: " + clientId);
        }

        loyaltyProgram.addReward(rewardId);
        loyaltyProgramDAO.updateLoyaltyProgram(loyaltyProgram);
        return true;
    }

    // Retrieves a LoyaltyProgram for a specific client
    public LoyaltyProgram getLoyaltyProgram(Long clientId) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramDAO.loadLoyaltyProgram(clientId);
        if (loyaltyProgram == null) {
            throw new IllegalArgumentException("Loyalty Program not found for clientId: " + clientId);
        }
        return loyaltyProgram;
    }
}
