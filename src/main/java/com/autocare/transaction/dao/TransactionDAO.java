package com.autocare.transaction.dao;

import com.autocare.transaction.Cart;
import com.autocare.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {
    long insertTransaction(Transaction transaction) throws SQLException;
    void updateTransaction(Transaction transaction) throws SQLException;
    boolean deleteTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getTransactionFromCart(long cartId) throws SQLException;
}
