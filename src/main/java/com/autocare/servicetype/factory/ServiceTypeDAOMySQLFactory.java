package com.autocare.servicetype.factory;

import com.autocare.servicetype.dao.ServiceTypeDAO;
import com.autocare.servicetype.dao.ServiceTypeDAOMySQL;

public class ServiceTypeDAOMySQLFactory implements ServiceTypeDAOFactory {
    private static ServiceTypeDAOMySQLFactory instance;

    private ServiceTypeDAOMySQLFactory() {}

    public static synchronized ServiceTypeDAOMySQLFactory getInstance() {
        if (instance == null) {
            instance = new ServiceTypeDAOMySQLFactory();
        }
        return instance;
    }

    public ServiceTypeDAO createServiceTypeDAO() {
        return new ServiceTypeDAOMySQL();
    }
}
