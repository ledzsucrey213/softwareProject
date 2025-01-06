package com.autocare.autocare.Pages;

import com.autocare.payment.ui.ManagePaymentTypeView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


import static javafx.application.Platform.exit;

public class AdminPage {

    public Scene createAdminPage() {
        // Create buttons
        Button definePaymentTypesButton = new Button("Define Payment Types");
        Button addEditUserListButton = new Button("Add/Edit User List");
        Button defineVehiclesButton = new Button("Define Vehicles");
        Button defineSubscriptionsButton = new Button("Define Subscriptions");
        Button logoutButton = new Button("Logout"); // Optional Logout button

        // Style buttons (optional)
        definePaymentTypesButton.setStyle("-fx-font-size: 14px;");
        addEditUserListButton.setStyle("-fx-font-size: 14px;");
        defineVehiclesButton.setStyle("-fx-font-size: 14px;");
        defineSubscriptionsButton.setStyle("-fx-font-size: 14px;");
        logoutButton.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");

        // Layout
        VBox layout = new VBox(15, definePaymentTypesButton, addEditUserListButton, defineVehiclesButton, defineSubscriptionsButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #34495e;");

        ManagePaymentTypeView PaymentInterface = new ManagePaymentTypeView();

        // Button actions (to be implemented)
        definePaymentTypesButton.setOnAction(e -> PaymentInterface.createPaymentTypeScene()  );
        addEditUserListButton.setOnAction(e -> System.out.println("Redirect to Add/Edit User List Page"));
        defineVehiclesButton.setOnAction(e -> System.out.println("Redirect to Define Vehicles Page"));
        defineSubscriptionsButton.setOnAction(e -> exit());


        // Return the scene
        return new Scene(layout, 600, 400);
    }
}
