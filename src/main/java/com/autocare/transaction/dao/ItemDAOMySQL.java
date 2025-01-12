package com.autocare.transaction.dao;

import com.autocare.sql.SqlConnectionManager;
import com.autocare.transaction.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOMySQL implements ItemDAO {
    /**
     * Inserts a new item into the database.
     *
     * @param item The {@link Item} object to be inserted into the database.
     * @throws SQLException If a database error occurs during the insertion.
     */

    @Override public void insertItem(Item item) throws SQLException {
        String query = "INSERT INTO item (label, description, price) VALUES "
                       + "(?, ?, ?)";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setString(1, item.getLabel());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setDouble(3, item.getPrice());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates an existing item in the database.
     *
     * @param item The {@link Item} object with updated information to be saved.
     * @throws SQLException If a database error occurs during the update.
     */

    @Override public void updateItem(Item item) throws SQLException {
        String query = "UPDATE item SET label = ?, description = ?, price = ? "
                       + "WHERE id = ?";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setString(1, item.getLabel());
        preparedStatement.setString(2, item.getDescription());
        preparedStatement.setDouble(3, item.getPrice());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Deletes an item from the database.
     * The item must have a valid ID before it can be deleted.
     *
     * @param item The {@link Item} object to be deleted.
     * @return true if the item was successfully deleted, false otherwise.
     * @throws SQLException If a database error occurs during the deletion.
     * @throws IllegalArgumentException If the item does not have a valid ID.
     */

    @Override public boolean deleteItem(Item item) throws SQLException {
        if (item.getId().isEmpty()) {
            throw new IllegalArgumentException("Item id cannot " + "be empty");
        }

        String query = "DELETE FROM item WHERE id = ?";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setLong(1, item.getId().get());

        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        return rowsAffected > 0;
    }

    /**
     * Retrieves a specific item from the database by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return An {@link Optional} containing the {@link Item} object if found, or an empty {@link Optional} if no item exists with the given ID.
     * @throws SQLException If a database error occurs during the retrieval.
     */

    @Override public Optional<Item> getItem(long id) throws SQLException {
        String
                query
                = "SELECT id, label, description, price FROM item WHERE id = ?";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Optional<Item> item = Optional.empty();

        if (resultSet.next()) {
            item = Optional.of(new Item(resultSet.getLong("id"),
                                        resultSet.getString("label"),
                                        resultSet.getString("description"),
                                        resultSet.getDouble("price")
            ));
        }

        return item;
    }

    /**
     * Retrieves a list of all items in the database.
     *
     * @return A {@link List} containing all {@link Item} objects in the database.
     * @throws SQLException If a database error occurs during the retrieval.
     */

    @Override public List<Item> getAllItems() throws SQLException {
        List<Item> list = new ArrayList<>();

        String query = "SELECT id, label, description, price FROM item";

        Connection conn = SqlConnectionManager.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            list.add(new Item(resultSet.getLong("id"),
                              resultSet.getString("label"),
                              resultSet.getString("description"),
                              resultSet.getDouble("price")
            ));
        }
        return list;
    }
}
