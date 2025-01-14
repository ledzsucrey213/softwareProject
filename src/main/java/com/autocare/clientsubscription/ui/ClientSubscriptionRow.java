package com.autocare.clientsubscription.ui;

import com.autocare.clientsubscription.ClientSubscription;
import com.autocare.clientsubscription.service.ClientSubscriptionService;
import com.autocare.subscription.Subscription;
import com.autocare.subscription.service.SubscriptionService;
import com.autocare.user.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class ClientSubscriptionRow {

    private final SimpleStringProperty clientName;
    private final SimpleStringProperty subscriptionLabel;
    private final SimpleObjectProperty<Button> changeButton;
    private final SimpleObjectProperty<Button> deleteButton;

    public ClientSubscriptionRow(User client, Subscription subscription, ClientSubscription clientSubscription,
                                  ClientSubscriptionService clientSubscriptionService, SubscriptionService subscriptionService) {
        this.clientName = new SimpleStringProperty(client.getName() + " " + client.getSurname());
        this.subscriptionLabel = new SimpleStringProperty(subscription.getLabel());

        this.changeButton = new SimpleObjectProperty<>(new Button("Change"));
        this.changeButton.get().setOnAction(e -> {
            // Logic for changing the subscription
            try {
                subscriptionService.updateSubscription(subscription); // Assumes an update method exists
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        this.deleteButton = new SimpleObjectProperty<>(new Button("Delete"));
        this.deleteButton.get().setStyle("-fx-background-color: red; -fx-text-fill: white;");
        this.deleteButton.get().setOnAction(e -> {
            try {
                clientSubscriptionService.deleteClientSubscription(client.getId(), subscription.getId().orElseThrow());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public SimpleStringProperty clientNameProperty() {
        return clientName;
    }

    public SimpleStringProperty subscriptionLabelProperty() {
        return subscriptionLabel;
    }

    public SimpleObjectProperty<Button> changeButtonProperty() {
        return changeButton;
    }

    public SimpleObjectProperty<Button> deleteButtonProperty() {
        return deleteButton;
    }
}

