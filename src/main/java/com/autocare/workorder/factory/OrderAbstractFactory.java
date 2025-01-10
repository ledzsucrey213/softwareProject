package com.autocare.workorder.factory;

import com.autocare.workorder.dao.OrderDAO;

public interface OrderAbstractFactory {
    public OrderDAO createOrderDAO();
}
