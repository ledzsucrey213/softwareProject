package com.autocare.appointment.dao;

import java.util.List;

import com.autocare.appointment.Appointment;

public interface AppointmentDAO {
    void saveAppointment(Appointment appointment);

    Appointment loadAppointment(int id);

    void deleteAppointment(int id);

    List<Appointment> loadAllAppointments();
}
