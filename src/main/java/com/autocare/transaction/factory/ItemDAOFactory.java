package com.autocare.transaction.factory;

import com.autocare.transaction.dao.ItemDAO;

public interface ItemDAOFactory {
    ItemDAO createItemDAO();
}
