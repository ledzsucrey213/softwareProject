package com.autocare.loyalty.dao;

import com.autocare.loyalty.LoyaltyProgram;

public interface LoyaltyProgramDAO {
    void saveLoyaltyProgram(LoyaltyProgram loyaltyProgram);

    LoyaltyProgram loadLoyaltyProgram(Long clientId);

    void deleteLoyaltyProgram(Long clientId);
    
    void updateLoyaltyProgram(LoyaltyProgram loyaltyProgram); // Updated with Optional<Long>
}
