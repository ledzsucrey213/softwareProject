package com.autocare.servicetype.factory;

import com.autocare.servicetype.dao.ServiceTypeDAO;

public interface ServiceTypeDAOFactory {
    ServiceTypeDAO createServiceTypeDAO();
}
