package com.autocare.transaction.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.transaction.Cart;
import com.autocare.transaction.Item;
import com.autocare.transaction.Transaction;
import com.autocare.user.Role;
import com.autocare.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOMySQL implements TransactionDAO {

    /**
     * Inserts a new transaction into the database.
     *
     * @param transaction The {@link Transaction} object to be inserted into the database.
     * @return The ID of the newly inserted transaction.
     * @throws SQLException If a database error occurs during the insertion.
     * @throws IllegalArgumentException If the cart or item ID is not valid.
     */

    @Override public long insertTransaction(Transaction transaction)
    throws SQLException {
        if (transaction.getCart().getId().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cart id in transaction cannot be empty");
        }

        if (transaction.getItem().getId().isEmpty()) {
            throw new IllegalArgumentException("Item id in transaction cannot "
                                               + "be empty");
        }

        String query = "INSERT INTO transaction (cart_id, item_id, amount) "
                       + "VALUES (?, ?, ?)";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setLong(1, transaction.getCart().getId().get());
        preparedStatement.setLong(2, transaction.getItem().getId().get());
        preparedStatement.setLong(3, transaction.getAmount());

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Could not add transaction");
        }

        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (!resultSet.next()) {
            throw new SQLException("Could not add transaction");
        }

        long id = resultSet.getLong(1);
        preparedStatement.close();
        return id;
    }

    /**
     * Updates an existing transaction in the database.
     *
     * @param transaction The {@link Transaction} object with updated information to be saved.
     * @throws SQLException If a database error occurs during the update.
     * @throws IllegalArgumentException If the cart or item ID is not valid.
     */

    @Override public void updateTransaction(Transaction transaction)
    throws SQLException {
        if (transaction.getCart().getId().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cart id in transaction cannot be empty");
        }

        if (transaction.getItem().getId().isEmpty()) {
            throw new IllegalArgumentException("Item id in transaction cannot "
                                               + "be empty");
        }

        String query =
                "UPDATE transaction SET amount = ? WHERE cart_id = ? AND "
                + "item_id = ?";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setLong(1, transaction.getAmount());
        preparedStatement.setLong(2, transaction.getCart().getId().get());
        preparedStatement.setLong(3, transaction.getItem().getId().get());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Deletes a transaction from the database.
     *
     * @param transaction The {@link Transaction} object to be deleted.
     * @return true if the transaction was successfully deleted, false otherwise.
     * @throws SQLException If a database error occurs during the deletion.
     * @throws IllegalArgumentException If the cart or item ID is not valid.
     */

    @Override public boolean deleteTransaction(Transaction transaction)
    throws SQLException {
        if (transaction.getCart().getId().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cart id in transaction cannot be empty");
        }

        if (transaction.getItem().getId().isEmpty()) {
            throw new IllegalArgumentException("Item id in transaction cannot "
                                               + "be empty");
        }

        String
                query
                = "DELETE FROM transaction WHERE cart_id = ? AND item_id = ?";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setLong(1, transaction.getCart().getId().get());
        preparedStatement.setLong(2, transaction.getItem().getId().get());

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();

        return rowsAffected > 0;
    }

    /**
     * Retrieves all transactions associated with a given cart from the database.
     *
     * @param cartId The ID of the cart whose transactions are to be retrieved.
     * @return A list of {@link Transaction} objects associated with the specified cart.
     * @throws SQLException If a database error occurs during the retrieval.
     */

    @Override public List<Transaction> getTransactionFromCart(long cartId)
    throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String query = """
                       SELECT
                           t.id AS transaction_id,
                           t.cart_id AS cart_id,
                           t.item_id AS item_id,
                           t.amount AS transaction_amount,
                           c.owner_id AS user_id,
                           u.username AS user_username,
                           u.name AS user_name,
                           u.surname AS user_surname,
                           u.role AS user_role,
                           i.label AS item_label,
                           i.description AS item_description,
                           i.price AS item_price
                       FROM
                           transaction t
                       JOIN
                           cart c ON t.cart_id = c.id
                       JOIN
                           user u ON c.owner_id = u.ID_user
                       JOIN
                           item i ON t.item_id = i.id
                       WHERE c.id = ?;
                       """;


        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setLong(1, cartId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = new User(resultSet.getLong("user_id"),
                                 resultSet.getString("user_username"),
                                 Role.valueOf(resultSet.getString("user_role")
                                                       .toUpperCase()),
                                 resultSet.getString("user_surname"),
                                 resultSet.getString("user_name")

            );
            Cart cart = new Cart(resultSet.getLong("cart_id"), user);
            Item item = new Item(resultSet.getLong("item_id"),
                                 resultSet.getString("item_label"),
                                 resultSet.getString("item_description"),
                                 resultSet.getDouble("item_price")
            );

            transactions.add(new Transaction(resultSet.getLong("transaction_id"),
                                             cart,
                                             item,
                                             resultSet.getLong("transaction_amount")
            ));
        }

        preparedStatement.close();
        return transactions;
    }
}
