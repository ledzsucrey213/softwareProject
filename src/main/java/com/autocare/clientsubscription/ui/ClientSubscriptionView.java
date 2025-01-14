package com.autocare.clientsubscription.ui;

import com.autocare.clientsubscription.ClientSubscription;
import com.autocare.clientsubscription.service.ClientSubscriptionService;
import com.autocare.user.User;
import com.autocare.user.service.UserService;
import com.autocare.subscription.service.SubscriptionService;
import com.autocare.subscription.Subscription;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ClientSubscriptionView extends Application {
    private ComboBox<User> clientComboBox;
    private ComboBox<Subscription> subscriptionComboBox;
    private ClientSubscriptionService clientSubscriptionService;
    private UserService userService;
    private SubscriptionService subscriptionService;

    @Override
    public void start(Stage primaryStage) {
        clientSubscriptionService = new ClientSubscriptionService(new com.autocare.clientsubscription.factory.ClientSubscriptionDAOFactoryMySQL());
        userService = new UserService(new com.autocare.user.factory.UserDAOMySQLFactory());
        subscriptionService = new SubscriptionService(new com.autocare.subscription.factory.SubscriptionDAOMySQLFactory());

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        // Load clients and subscriptions
        clientComboBox = new ComboBox<>();
        subscriptionComboBox = new ComboBox<>();
        try {
            clientComboBox.setItems(FXCollections.observableArrayList(userService.getClients()));
            subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getAllSubscriptions()));
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }

        Button linkButton = new Button("Link Subscription");
        linkButton.setOnAction(e -> linkSubscription());

        layout.getChildren().addAll(
                new Label("Select Client:"), clientComboBox,
                new Label("Select Subscription:"), subscriptionComboBox,
                linkButton
        );

        primaryStage.setScene(new Scene(layout, 400, 300));
        primaryStage.show();
    }

    private void linkSubscription() {
        User client = clientComboBox.getSelectionModel().getSelectedItem();
        Subscription subscription = subscriptionComboBox.getSelectionModel().getSelectedItem();
        if (client != null && subscription != null) {
            try {
            	clientSubscriptionService.addClientSubscription(
            		    new ClientSubscription(client.getId(), subscription.getId().orElseThrow(() -> new IllegalArgumentException("Subscription ID is required")))
            		);                showInfo("Subscription linked successfully!");
            } catch (SQLException e) {
                showError("Error linking subscription: " + e.getMessage());
            }
        } else {
            showError("Please select both a client and a subscription.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

	
	public Scene createClientSubscriptionScene() {
        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Manage Client Subscriptions"));
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #ecf0f1;");

        return new Scene(layout, 400, 300);
    }
    
}

