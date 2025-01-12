package com.autocare.loyalty.factory;

import com.autocare.loyalty.dao.LoyaltyProgramDAO;
import com.autocare.loyalty.dao.LoyaltyProgramDAOMySQL;

public class LoyaltyProgramDAOMySqlFactory implements LoyaltyProgramDAOFactory {

    // Static instance of the factory
    private static LoyaltyProgramDAOMySqlFactory instance;

    // Private constructor to prevent external instantiation
    private LoyaltyProgramDAOMySqlFactory() {}

    // Public method to provide access to the singleton instance
    public static synchronized LoyaltyProgramDAOMySqlFactory getInstance() {
        if (instance == null) {
            instance = new LoyaltyProgramDAOMySqlFactory();
        }
        return instance;
    }

    @Override
    public LoyaltyProgramDAO createLoyaltyProgramDAO() {
        return new LoyaltyProgramDAOMySQL();
    }
}
