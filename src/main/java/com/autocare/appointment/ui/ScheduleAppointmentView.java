package com.autocare.appointment.ui;

import com.autocare.appointment.service.AppointmentService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleAppointmentView {

    private final AppointmentService appointmentService;

    public ScheduleAppointmentView() {
        this.appointmentService = new AppointmentService();
    }

    public Scene createScheduleAppointmentScene(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Schedule Appointment");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Calendar and Time Selection
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");

        ComboBox<String> timePicker = new ComboBox<>();
        timePicker.getItems().addAll("9:00 AM", "10:00 AM", "11:00 AM", "1:00 PM", "2:00 PM", "3:00 PM");
        timePicker.setPromptText("Select Time");

        Button checkAvailabilityButton = new Button("Check Availability");
        checkAvailabilityButton.setOnAction(e -> checkAvailability(datePicker.getValue(), timePicker.getValue(), primaryStage));

        VBox calendarBox = new VBox(10, datePicker, timePicker, checkAvailabilityButton);
        calendarBox.setAlignment(Pos.CENTER);

        // Navigation Buttons
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(previousScene()));

        calendarBox.getChildren().add(backButton);

        VBox layout = new VBox(20, titleLabel, calendarBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 600, 400);
    }

    private void checkAvailability(java.time.LocalDate localDate, String time, Stage primaryStage) {
        if (localDate == null || time == null) {
            showAlert("Error", "Please select both a date and time.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Date date = java.sql.Date.valueOf(localDate);
            boolean isAvailable = appointmentService.checkAvailability(date, time);

            if (isAvailable) {
                showAppointmentForm(primaryStage, date, time);
            } else {
                showAlert("Unavailable", "The selected date and time are not available. Please choose a different date or time.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while checking availability: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAppointmentForm(Stage primaryStage, Date date, String time) {
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(20));
        formLayout.setAlignment(Pos.CENTER);

        // Form Fields
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");

        ComboBox<String> serviceTypeComboBox = new ComboBox<>();
        serviceTypeComboBox.getItems().addAll("Oil Change", "Tire Replacement", "General Maintenance");
        serviceTypeComboBox.setPromptText("Select Service Type");

        TextField vehicleDetailsField = new TextField();
        vehicleDetailsField.setPromptText("Enter Vehicle Details");

        Button confirmButton = new Button("Confirm Appointment");
        confirmButton.setOnAction(e -> confirmAppointment(primaryStage, date, time, nameField.getText(), phoneField.getText(), serviceTypeComboBox.getValue(), vehicleDetailsField.getText()));

        formLayout.getChildren().addAll(
                new Label("Appointment Details"),
                nameField,
                phoneField,
                serviceTypeComboBox,
                vehicleDetailsField,
                confirmButton
        );

        primaryStage.setScene(new Scene(formLayout, 600, 400));
    }

    private void confirmAppointment(Stage primaryStage, Date date, String time, String name, String phone, String serviceType, String vehicleDetails) {
        if (name.isEmpty() || phone.isEmpty() || serviceType == null || vehicleDetails.isEmpty()) {
            showAlert("Error", "Please fill in all fields to proceed.", Alert.AlertType.ERROR);
            return;
        }

        try {
            long userId = 1; // Replace with actual user ID logic
            String description = "Service: " + serviceType + ", Vehicle: " + vehicleDetails;

            Appointment appointment = new Appointment(date, time, userId, false, description);
            appointmentService.scheduleAppointment(appointment);

            showConfirmationScreen(primaryStage, date, time, name, phone, serviceType, vehicleDetails);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while scheduling the appointment: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showConfirmationScreen(Stage primaryStage, Date date, String time, String name, String phone, String serviceType, String vehicleDetails) {
        VBox confirmationLayout = new VBox(10);
        confirmationLayout.setPadding(new Insets(20));
        confirmationLayout.setAlignment(Pos.CENTER);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");

        Label confirmationLabel = new Label("Appointment Confirmation");
        confirmationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label detailsLabel = new Label(
                "Name: " + name + "\n" +
                        "Contact: " + phone + "\n" +
                        "Appointment Date: " + dateFormat.format(date) + "\n" +
                        "Time: " + time + "\n" +
                        "Service: " + serviceType + "\n" +
                        "Vehicle Details: " + vehicleDetails
        );

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> primaryStage.setScene(createScheduleAppointmentScene(primaryStage)));

        confirmationLayout.getChildren().addAll(confirmationLabel, detailsLabel, okButton);

        primaryStage.setScene(new Scene(confirmationLayout, 600, 400));
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Scene previousScene() {
        // Placeholder logic for the previous scene
        return null;
    }
}
