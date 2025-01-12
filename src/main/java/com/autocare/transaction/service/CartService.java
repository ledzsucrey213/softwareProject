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

/**
 * Service class for managing shopping carts and transactions.
 * This class provides business logic for interacting with the data access layer
 * (DAO) to manage operations related to the shopping cart and transactions, such as adding,
 * removing, and updating cart content, as well as creating and deleting carts.
 */

public class CartService {
    private final CartDAO cartDAO;
    private final TransactionDAO transactionDAO;

    /**
     * Constructor for creating a CartService instance.
     * Initializes the {@link CartDAO} and {@link TransactionDAO} using the provided factories.
     *
     * @param cartDAOFactory The {@link CartDAOFactory} used to create the CartDAO instance.
     * @param transactionDAOFactory The {@link TransactionDAOFactory} used to create the TransactionDAO instance.
     */

    // Constructor to initialize the CartDAO and TransactionDAO
    public CartService(CartDAOFactory cartDAOFactory, TransactionDAOFactory transactionDAOFactory) {
        this.cartDAO = cartDAOFactory.createCartDAO();
        this.transactionDAO = transactionDAOFactory.createTransactionDAO();
    }


    /**
     * Retrieves the cart associated with a given user.
     *
     * @param user The {@link User} object whose cart is to be fetched.
     * @return An {@link Optional} containing the {@link Cart} if found, or an empty Optional if the user has no cart.
     * @throws SQLException If there is an issue with accessing the database while fetching the cart.
     */

    public Optional<Cart> getCart(User user) throws SQLException {
        return cartDAO.getCartOfUser(user.getId());
    }

    /**
     * Creates a new cart for a given user.
     *
     * @param user The {@link User} object for whom the cart is to be created.
     * @return The created {@link Cart} object.
     * @throws SQLException If there is an issue with the database while creating the cart.
     */

    public Cart createCart(User user) throws SQLException {
        return cartDAO.insertCart(new Cart(user));
    }

    /**
     * Adds a transaction to a cart. If the item already exists in the cart, the amount is increased by 1.
     * If not, a new transaction is created and added to the cart.
     *
     * @param cart The {@link Cart} to which the transaction should be added.
     * @param transaction The {@link Transaction} to be added to the cart.
     * @throws SQLException If there is an issue with the database while adding or updating the transaction.
     */

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

    /**
     * Decreases the amount of a transaction in the cart. If the amount reaches 1, the transaction is deleted.
     *
     * @param cart The {@link Cart} that contains the transaction to be decreased.
     * @param tmp The {@link Transaction} to be modified.
     * @throws SQLException If there is an issue with the database while updating or deleting the transaction.
     * @throws IllegalArgumentException If the transaction ID is empty.
     */

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

    /**
     * Deletes the given cart from the system.
     *
     * @param cart The {@link Cart} to be deleted.
     * @throws SQLException If there is an issue with the database while deleting the cart.
     */

    public void deleteCart(Cart cart) throws SQLException {
        cartDAO.removeCart(cart);
    }
}
