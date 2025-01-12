package com.autocare.servicetype;

public class ServiceType {
    private int serviceTypeId;
    private String name;
    private String description;

    public ServiceType(int serviceTypeId, String name, String description) {
        this.serviceTypeId = serviceTypeId;
        this.name = name;
        this.description = description;
    }

    public ServiceType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceTypeDetails() {
        return "ServiceTypeID: " + serviceTypeId + ", Name: " + name + ", Description: " + description;
    }

    public void setServiceTypeDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
