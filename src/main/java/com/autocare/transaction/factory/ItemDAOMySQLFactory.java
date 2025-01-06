package com.autocare.transaction.factory;

import com.autocare.transaction.dao.ItemDAO;
import com.autocare.transaction.dao.ItemDAOMySQL;

public class ItemDAOMySQLFactory implements ItemDAOFactory {
    @Override public ItemDAO createItemDAO() {
        return new ItemDAOMySQL();
    }
}
