package com.autocare.workorder.service;

import com.autocare.workorder.Order;
import com.autocare.workorder.dao.OrderDAO;
import com.autocare.workorder.factory.OrderAbstractFactory;

import java.sql.SQLException;
import java.util.List;

public class WorkOrderService {

    final private OrderDAO orderDAO;

    public WorkOrderService(OrderAbstractFactory orderFactory) {
        orderDAO = orderFactory.createOrderDAO();
    }

    public List<Order> getAllOrders() throws SQLException {
        //
    }

    public void addOrder(Order o) throws SQLException {
        orderDAO.insertOrder(o);
    }

    public void deleteOrder(Order o) throws SQLException {
        orderDAO.deleteOrder(o.getId());
    }

    public void updateOrder(Order o) throws SQLException {
        orderDAO.updateOrder(o);
    }
}