package com.autocare.appointment.factory;

import com.autocare.appointment.dao.AppointmentDAO;
import com.autocare.appointment.dao.AppointmentDAOMySQL;

public class AppointmentDAOMySQLFactory implements AppointmentDAOFactory {

    // Static instance of the factory (Singleton)
    private static AppointmentDAOMySQLFactory instance;

    // Private constructor to prevent external instantiation
    private AppointmentDAOMySQLFactory() {}

    // Public method to provide access to the singleton instance
    public static synchronized AppointmentDAOMySQLFactory getInstance() {
        if (instance == null) {
            instance = new AppointmentDAOMySQLFactory();
        }
        return instance;
    }

    @Override
    public AppointmentDAO createAppointmentDAO() {
        return new AppointmentDAOMySQL();
    }
}
