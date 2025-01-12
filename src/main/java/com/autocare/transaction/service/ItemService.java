package com.autocare.transaction.service;

import com.autocare.transaction.Item;
import com.autocare.transaction.dao.ItemDAO;
import com.autocare.transaction.factory.ItemDAOFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing items in the system.
 * This class provides business logic for interacting with the data access layer
 * (DAO) to manage operations related to items, such as adding, updating, deleting,
 * and retrieving items.
 */

public class ItemService {
    private ItemDAO itemDAO;

    /**
     * Constructor for creating an ItemService instance.
     * Initializes the {@link ItemDAO} using the provided factory.
     *
     * @param itemDAOFactory The {@link ItemDAOFactory} used to create the ItemDAO instance.
     */

    public ItemService(ItemDAOFactory itemDAOFactory) {
        itemDAO = itemDAOFactory.createItemDAO();
    }

    /**
     * Adds a new item to the system.
     *
     * @param item The {@link Item} object to be added.
     */

    // Add a new item
    public void addItem(Item item) {
        try {
            itemDAO.insertItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing item in the system.
     *
     * @param item The {@link Item} object with updated information.
     */

    // Update an existing item
    public void updateItem(Item item) {
        try {
            itemDAO.updateItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an item from the system.
     *
     * @param item The {@link Item} object to be deleted.
     * @return true if the item was successfully deleted, false otherwise.
     */

    // Delete an item
    public boolean deleteItem(Item item) {
        try {
            return itemDAO.deleteItem(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Retrieves a specific item by its ID.
     *
     * @param id The ID of the {@link Item} to be retrieved.
     * @return An {@link Optional} containing the {@link Item} if found, or an empty Optional if not.
     */

    // Get a specific item by its ID
    public Optional<Item> getItemById(long id) {
        try {
            return itemDAO.getItem(id); // ItemDAO expects an int for ID, but here we pass a long
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Retrieves a list of all items in the system.
     *
     * @return A list of all {@link Item} objects.
     */

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
