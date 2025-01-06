package com.autocare.transaction.factory;

import com.autocare.transaction.dao.CartDAO;

public interface CartDAOFactory {
    CartDAO createCartDAO();
}
