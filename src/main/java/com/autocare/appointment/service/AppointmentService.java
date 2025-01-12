package com.autocare.appointment.service;

import java.util.Date;
import java.util.List;

import com.autocare.appointment.Appointment;
import com.autocare.appointment.dao.AppointmentDAO;
import com.autocare.appointment.factory.AppointmentDAOMySQLFactory;

public class AppointmentService {
    private final AppointmentDAO appointmentDAO;

    public AppointmentService() {
        this.appointmentDAO = AppointmentDAOMySQLFactory.getInstance().createAppointmentDAO();
    }

    public boolean scheduleAppointment(Date date, String time, long userId, String description) {
        Appointment appointment = new Appointment(date, time, userId, false, description);
        appointmentDAO.saveAppointment(appointment);
        return true;
    }

 public boolean checkAvailability(Date date, String time) {
        // Fetch all appointments from the database
        List<Appointment> appointments = appointmentDAO.loadAllAppointments();

        // Check if any appointment exists with the same date and time
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(date) && appointment.getTime().equals(time)) {
                return false; // Slot is already taken
            }
        }
        return true; // Slot is available
    }

    public Appointment getAppointment(int id) {
        return appointmentDAO.loadAppointment(id);
    }
}
