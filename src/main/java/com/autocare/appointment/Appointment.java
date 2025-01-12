package com.autocare.appointment;

import java.util.Date;

public class Appointment {
    private Date date;
    private String time; // Added the time attribute
    private long userId; // Changed from String to long
    private boolean done;
    private String description;

    public Appointment(Date date, String time, long userId, boolean done, String description) {
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.done = done;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppointmentDetails() {
        return "Date: " + date + ", Time: " + time + ", User ID: " + userId + ", Done: " + done + ", Description: " + description;
    }

    public void setAppointmentDetails(Date date, String time, long userId, boolean done, String description) {
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.done = done;
        this.description = description;
    }
}
