package com.autocare.transaction.service;

import com.autocare.transaction.Cart;
import com.autocare.transaction.Transaction;
import com.autocare.transaction.dao.CartDAO;
import com.autocare.transaction.dao.TransactionDAO;
import com.autocare.transaction.factory.CartDAOFactory;
import com.autocare.transaction.factory.TransactionDAOFactory;
import com.autocare.user.User;

import java.sql.SQLException;
import java.util.Optional;

public class CartService {
    private final CartDAO cartDAO;
    private final TransactionDAO transactionDAO;

    // Constructor to initialize the CartDAO and TransactionDAO
    public CartService(CartDAOFactory cartDAOFactory, TransactionDAOFactory transactionDAOFactory) {
        this.cartDAO = cartDAOFactory.createCartDAO();
        this.transactionDAO = transactionDAOFactory.createTransactionDAO();
    }

    public Optional<Cart> getCart(User user) throws SQLException {
        return cartDAO.getCartOfUser(user.getId());
    }

    public Cart createCart(User user) throws SQLException {
        return cartDAO.insertCart(new Cart(user));
    }

    public void addTransaction(Cart cart, Transaction transaction) throws SQLException {
        for (Transaction trans : cart.getCartContent()) {
            if (
                    (trans.getItem().getId().isPresent() && transaction.getItem().getId().isPresent())
                            && trans.getItem().getId().get().equals(transaction.getItem().getId().get())) {
                trans.setAmount(trans.getAmount() + 1);
                try {
                    transactionDAO.updateTransaction(trans);
                } catch (SQLException sqlException) {
                    trans.setAmount(trans.getAmount() - 1);
                    throw sqlException;
                }
                return;
            }
        }



        long newId = transactionDAO.insertTransaction(transaction);
        transaction.setId(newId);
        cart.addTransaction(transaction);


    }

    public void decreaseTransactionAmountUntilDeletion(Cart cart, Transaction tmp) throws SQLException {
        if (tmp.getId().isEmpty()) {
            throw new IllegalArgumentException("Transaction id cannot be empty.");
        }

        Transaction transaction = cart.getCartContent()
                .stream()
                .filter(
                        trns -> trns.getId().isPresent() && trns.getId().get().equals(tmp.getId().get()))
                .findFirst().get();

        if (transaction.getAmount() == 1L) {
            transactionDAO.deleteTransaction(transaction);
            cart.getCartContent().remove(transaction);
            return;
        }

        transaction.setAmount(transaction.getAmount() - 1L);
        try {
            transactionDAO.updateTransaction(transaction);
        } catch (SQLException sqlException) {
            transaction.setAmount(transaction.getAmount() + 1L);
            throw sqlException;
        }
    }

    public void deleteCart(Cart cart) throws SQLException {
        cartDAO.removeCart(cart);
    }
}
