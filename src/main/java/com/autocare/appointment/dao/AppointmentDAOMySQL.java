package com.autocare.appointment.dao;

import com.autocare.appointment.Appointment;
import com.autocare.sql.SqlConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAOMySQL implements AppointmentDAO {

    @Override
    public void saveAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointment (date, time, userId, done, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, new java.sql.Date(appointment.getDate().getTime()));
            statement.setString(2, appointment.getTime());
            statement.setLong(3, appointment.getUserId());
            statement.setBoolean(4, appointment.isDone());
            statement.setString(5, appointment.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Appointment loadAppointment(int id) {
        String query = "SELECT * FROM Appointment WHERE id = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date date = resultSet.getDate("date");
                String time = resultSet.getString("time");
                long userId = resultSet.getLong("userId");
                boolean done = resultSet.getBoolean("done");
                String description = resultSet.getString("description");

                return new Appointment(date, time, userId, done, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteAppointment(int id) {
        String query = "DELETE FROM Appointment WHERE id = ?";
        try (Connection connection = SqlConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public List<Appointment> loadAllAppointments() {
    List<Appointment> appointments = new ArrayList<>();
    String query = "SELECT * FROM Appointment";

    try (Connection connection = SqlConnectionManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Date date = resultSet.getDate("date");
            String time = resultSet.getString("time");
            long userId = resultSet.getLong("userId");
            boolean done = resultSet.getBoolean("done");
            String description = resultSet.getString("description");

            Appointment appointment = new Appointment(date, time, userId, done, description);
            appointments.add(appointment);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return appointments;
}

}
