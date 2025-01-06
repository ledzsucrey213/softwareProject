package com.autocare.transaction.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.transaction.Cart;
import com.autocare.transaction.Item;
import com.autocare.transaction.Transaction;
import com.autocare.user.Role;
import com.autocare.user.User;

import java.sql.*;
import java.util.Optional;

public class CartDAOMySQL implements CartDAO {
    @Override public Cart insertCart(Cart cart) throws SQLException {
        String query = "INSERT INTO cart (owner_id) VALUES (?)";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setLong(1, cart.getUser().getId());

        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            cart.setId(generatedKeys.getLong(1));
        } else {
            throw new SQLException("Could not get generated key / insert cart");
        }

        statement.close();

        return cart;
    }

    @Override public boolean removeCart(Cart cart) throws SQLException {
        if (cart.getId().isEmpty()) {
            throw new IllegalArgumentException("Cart id cannot be empty");
        }

        String query = "DELETE FROM cart WHERE id = ?";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setLong(1, cart.getId().get());

        int rowsAffected = statement.executeUpdate();

        statement.close();
        return rowsAffected > 0;
    }

    @Override public Optional<Cart> getCartOfUser(long userId) throws SQLException {
        String query = """
                       SELECT
                           t.id AS transaction_id,
                           t.item_id AS item_id,
                           t.amount AS transaction_amount,
                           c.id AS cart_id,
                           c.owner_id AS user_id,
                           u.username AS user_username,
                           u.name AS user_name,
                           u.surname AS user_surname,
                           u.role AS user_role,
                           i.label AS item_label,
                           i.description AS item_description,
                           i.price AS item_price
                       FROM
                           cart c
                       JOIN
                           user u ON c.owner_id = u.ID_user
                       JOIN
                           transaction t ON c.id = t.cart_id
                       JOIN
                           item i ON t.item_id = i.id
                       WHERE
                           u.ID_user = ?
                       """;

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setLong(1, userId);

        ResultSet rs = statement.executeQuery();

        Optional<Cart> cart = Optional.empty();
        if (rs.next()) {
            User user = new User(rs.getLong("user_id"),
                                 rs.getString("user_username"),
                                 Role.valueOf(rs.getString("user_role")
                                                .toUpperCase()),
                                 rs.getString("user_surname"),
                                 rs.getString("user_name")
            );

            cart = Optional.of(new Cart(rs.getLong("cart_id"), user));

            Item item = new Item(rs.getLong("item_id"),
                    rs.getString("item_label"),
                    rs.getString("item_description"),
                    rs.getDouble("item_price")
            );

            Transaction transaction = new Transaction(rs.getLong(
                    "transaction_id"),
                    cart.get(),
                    item,
                    rs.getLong(
                            "transaction_amount")
            );

            cart.get().addTransaction(transaction);
        } else {
            return cart;
        }

        while (rs.next()) {
            Item item = new Item(rs.getLong("item_id"),
                                 rs.getString("item_label"),
                                 rs.getString("item_description"),
                                 rs.getDouble("item_price")
            );

            Transaction transaction = new Transaction(rs.getLong(
                    "transaction_id"),
                                                      cart.get(),
                                                      item,
                                                      rs.getLong(
                                                              "transaction_amount")
            );

            cart.get().addTransaction(transaction);
        }

        return cart;
    }



}
