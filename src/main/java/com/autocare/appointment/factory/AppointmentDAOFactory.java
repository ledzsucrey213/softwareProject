package com.autocare.appointment.factory;

import com.autocare.appointment.dao.AppointmentDAO;

public interface AppointmentDAOFactory {
    AppointmentDAO createAppointmentDAO();
    
}
