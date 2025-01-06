package com.autocare.transaction.dao;

import com.autocare.transaction.Item;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ItemDAO {
    void insertItem(Item item) throws SQLException;

    void updateItem(Item item) throws SQLException;

    boolean deleteItem(Item item) throws SQLException;

    Optional<Item> getItem(long id) throws SQLException;

    List<Item> getAllItems() throws SQLException;
}
