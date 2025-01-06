package com.autocare.transaction.service;

import com.autocare.transaction.Item;
import com.autocare.transaction.dao.ItemDAO;
import com.autocare.transaction.factory.ItemDAOFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService(ItemDAOFactory itemDAOFactory) {
        itemDAO = itemDAOFactory.createItemDAO();
    }

    // Add a new item
    public void addItem(Item item) {
        try {
            itemDAO.insertItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing item
    public void updateItem(Item item) {
        try {
            itemDAO.updateItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an item
    public boolean deleteItem(Item item) {
        try {
            return itemDAO.deleteItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get a specific item by its ID
    public Optional<Item> getItemById(long id) {
        try {
            return itemDAO.getItem(id); // ItemDAO expects an int for ID, but here we pass a long
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Get a list of all items
    public List<Item> getAllItems() {
        try {
            return itemDAO.getAllItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
