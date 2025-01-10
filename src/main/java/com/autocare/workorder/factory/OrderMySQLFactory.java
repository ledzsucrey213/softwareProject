package com.autocare.workorder.factory;


import com.autocare.workorder.dao.OrderDAO;
import com.autocare.workorder.dao.OrderDAOMySQL;

public class OrderMySQLFactory implements OrderAbstractFactory {
    @Override public OrderDAO createOrderDAO() {
        return new OrderDAOMySQL();
    }
}
