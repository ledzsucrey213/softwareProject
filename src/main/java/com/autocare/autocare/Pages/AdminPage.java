package com.autocare.autocare.Pages;

import com.autocare.payment.ui.ManagePaymentTypeView;
import com.autocare.subscription.ui.SubscriptionView;
import com.autocare.user.ui.ManageUsersView;
import com.autocare.vehicle.ui.VehicleView;
import com.autocare.clientsubscription.ui.ClientSubscriptionView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class AdminPage {

    public Scene createAdminPage() {
        // Create buttons
        Button definePaymentTypesButton = new Button("Define Payment Types");
        Button addEditUserListButton = new Button("Add/Edit User List");
        Button defineVehiclesButton = new Button("Define Vehicles");
        Button defineSubscriptionsButton = new Button("Define Subscriptions");
        Button manageClientSubscriptionsButton = new Button("Manage Client Subscriptions"); // New Button
        Button logoutButton = new Button("Logout"); // Optional Logout button

        // Style buttons (optional)
        definePaymentTypesButton.setStyle("-fx-font-size: 14px;");
        addEditUserListButton.setStyle("-fx-font-size: 14px;");
        defineVehiclesButton.setStyle("-fx-font-size: 14px;");
        defineSubscriptionsButton.setStyle("-fx-font-size: 14px;");
        manageClientSubscriptionsButton.setStyle("-fx-font-size: 14px;"); // Style for the new button
        logoutButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");

        // Layout
        VBox layout = new VBox(15, definePaymentTypesButton, addEditUserListButton, defineVehiclesButton, defineSubscriptionsButton, manageClientSubscriptionsButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #34495e;");

        // Interfaces
        ManagePaymentTypeView paymentInterface = new ManagePaymentTypeView();
        ManageUsersView userInterface = new ManageUsersView();
        VehicleView vehicleInterface = new VehicleView();
        SubscriptionView subscriptionInterface = new SubscriptionView();
        ClientSubscriptionView clientSubscriptionInterface = new ClientSubscriptionView(); // Interface for the new use case

        // Button actions (open as popups)
        definePaymentTypesButton.setOnAction(e -> {
            Stage paymentTypePopup = new Stage();
            paymentTypePopup.setTitle("Manage Payment Types");
            paymentTypePopup.setScene(paymentInterface.createPaymentTypeScene());
            paymentTypePopup.show();
        });

        addEditUserListButton.setOnAction(e -> {
            Stage userPopup = new Stage();
            userPopup.setTitle("Manage Users");
            userPopup.setScene(userInterface.createUserScene());
            userPopup.show();
        });

        defineVehiclesButton.setOnAction(e -> {
            Stage vehiclePopup = new Stage();
            vehiclePopup.setTitle("Manage Vehicles");
            vehiclePopup.setScene(vehicleInterface.createVehicleScene());
            vehiclePopup.show();
        });

        defineSubscriptionsButton.setOnAction(e -> {
            Stage subscriptionPopup = new Stage();
            subscriptionPopup.setTitle("Manage Subscriptions");
            subscriptionPopup.setScene(subscriptionInterface.createSubscriptionScene());
            subscriptionPopup.show();
        });

        manageClientSubscriptionsButton.setOnAction(e -> {
            Stage clientSubscriptionPopup = new Stage();
            clientSubscriptionPopup.setTitle("Manage Client Subscriptions");
            clientSubscriptionPopup.setScene(clientSubscriptionInterface.createClientSubscriptionScene()); // Method to create the UI for client subscriptions
            clientSubscriptionPopup.show();
        });

        logoutButton.setOnAction(e -> exit());

        // Return the scene
        return new Scene(layout, 600, 400);
    }
}

