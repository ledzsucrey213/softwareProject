package com.autocare.servicetype.service;

import com.autocare.servicetype.dao.ServiceTypeDAO;
import com.autocare.servicetype.factory.ServiceTypeDAOMySQLFactory;
import com.autocare.servicetype.ServiceType;

public class ServiceTypeService {
    private final ServiceTypeDAO serviceTypeDAO;

    public ServiceTypeService() {
        this.serviceTypeDAO = ServiceTypeDAOMySQLFactory.getInstance().createServiceTypeDAO();
    }

    public boolean addServiceType(String name, String description) {
        ServiceType serviceType = new ServiceType(name, description);
        serviceTypeDAO.saveServiceType(serviceType);
        return true;
    }

    public boolean updateServiceType(int id, String name, String description) {
        ServiceType serviceType = serviceTypeDAO.loadServiceType(id);
        if (serviceType == null) {
            return false;
        }
        serviceType.setServiceTypeDetails(name, description);
        serviceTypeDAO.saveServiceType(serviceType);
        return true;
    }
    
    public List<ServiceType> getAllServiceTypes() throws SQLException {
        return serviceTypeDAO.loadAllServiceTypes();
    }
}
