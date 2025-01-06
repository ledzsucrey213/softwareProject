package com.autocare.transaction.factory;

import com.autocare.transaction.dao.TransactionDAO;

public interface TransactionDAOFactory {
    TransactionDAO createTransactionDAO();
}
