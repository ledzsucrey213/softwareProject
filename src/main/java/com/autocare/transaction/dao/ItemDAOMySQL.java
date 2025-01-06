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
