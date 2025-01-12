package com.autocare.autocare.Pages;

import com.autocare.SessionManager.SessionManager;
import com.autocare.transaction.ui.ShopSystem;
import com.autocare.user.User;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.sql.SQLException;

import static javafx.application.Platform.exit;

public class CustomerPage {

    public Scene createCustomerPage() {
        // Create buttons
        Button accessShopButton = new Button("Access Shop");
        Button scheduleRepairAppointmentButton = new Button("Schedule Repair Appointment");
        Button logoutButton = new Button("Logout");

        // Style buttons (optional)
        accessShopButton.setStyle("-fx-font-size: 14px;");
        scheduleRepairAppointmentButton.setStyle("-fx-font-size: 14px;");
        logoutButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");

        // Layout
        VBox layout = new VBox(15, accessShopButton, scheduleRepairAppointmentButton , logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #34495e;");

        // Button actions (open as popups or navigate to new views as needed)
        accessShopButton.setOnAction(e -> {
            try {
                // Ensure the user is logged in and retrieve the current user from the session
                User currentUser = SessionManager.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user logged in"));

                // Create the ShopSystem instance using the logged-in user
                ShopSystem shopSystem = new ShopSystem(currentUser);

                // Retrieve the Scene from ShopSystem
                Scene shopScene = shopSystem.createItemScene();

                // Open the Shop scene in a new stage
                Stage shopStage = new Stage();
                shopStage.setTitle("Shop");
                shopStage.setScene(shopScene);
                shopStage.show();

            } catch (SQLException | IllegalArgumentException ex) {
                ex.printStackTrace();  // Handle any potential exceptions (e.g., no user logged in or database issues)
            }
        });

        scheduleRepairAppointmentButton.setOnAction(e -> {
            // Action to schedule repair appointment
            // Example: Open a new Schedule Repair Appointment Scene
            Stage repairAppointmentPopup = new Stage();
            repairAppointmentPopup.setTitle("Schedule Repair Appointment");
            repairAppointmentPopup.setScene(new Scene(new VBox(), 300, 200));  // Replace with actual content
            repairAppointmentPopup.show();
        });

        logoutButton.setOnAction(e -> exit());



        // Return the scene
        return new Scene(layout, 600, 400);
    }
}
