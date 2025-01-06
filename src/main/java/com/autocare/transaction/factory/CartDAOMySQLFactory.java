package com.autocare.transaction.factory;

import com.autocare.transaction.dao.CartDAO;
import com.autocare.transaction.dao.CartDAOMySQL;

public class CartDAOMySQLFactory implements CartDAOFactory {
    @Override public CartDAO createCartDAO() {
        return new CartDAOMySQL();
    }
}
