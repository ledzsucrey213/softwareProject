package com.autocare.loyalty;

import java.util.ArrayList;
import java.util.List;

public class LoyaltyProgram {

    private Long clientId;
    private int points;
    private List<String> rewards;

    // Constructor with clientId and initial points
    public LoyaltyProgram(Long clientId, int points) {
        this.clientId = clientId;
        this.points = points;
        this.rewards = new ArrayList<>();
    }

    // Constructor with clientId only, points default to 0
    public LoyaltyProgram(Long clientId) {
        this.clientId = clientId;
        this.points = 0;
        this.rewards = new ArrayList<>();
    }

    // Getter for clientId
    public Long getClientId() {
        return clientId;
    }

    // Getter for points
    public int getPoints() {
        return points;
    }

    // Setter for points
    public void setPoints(int points) {
        this.points = points;
    }

    // Getter for rewards
    public List<String> getRewards() {
        return rewards;
    }

    // Method to add a reward
    public void addReward(String reward) {
        this.rewards.add(reward);
    }

    // Method to remove a reward
    public void removeReward(String reward) {
        this.rewards.remove(reward);
    }

    // Utility method to display loyalty program details
    public String getLoyaltyProgramDetails() {
        return "Client ID: " + clientId + ", Points: " + points + ", Rewards: " + rewards;
    }
}
