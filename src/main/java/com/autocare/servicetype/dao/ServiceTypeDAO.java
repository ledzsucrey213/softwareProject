package com.autocare.servicetype.dao;

import com.autocare.servicetype.ServiceType;

public interface ServiceTypeDAO {
    void saveServiceType(ServiceType serviceType);

    ServiceType loadServiceType(int serviceTypeId);

    void deleteServiceType(int serviceTypeId);
}
