package com.autocare.transaction.factory;

import com.autocare.transaction.dao.TransactionDAO;
import com.autocare.transaction.dao.TransactionDAOMySQL;

public class TransactionDAOMySQLFactory implements TransactionDAOFactory {
    @Override public TransactionDAO createTransactionDAO() {
        return new TransactionDAOMySQL();
    }
}
