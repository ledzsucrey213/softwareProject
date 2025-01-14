package com.autocare.clientsubscription.ui;

import com.autocare.clientsubscription.ClientSubscription;
import com.autocare.clientsubscription.service.ClientSubscriptionService;
import com.autocare.subscription.Subscription;
import com.autocare.subscription.service.SubscriptionService;
import com.autocare.user.User;
import com.autocare.user.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ClientSubscriptionView {

    private final ClientSubscriptionService clientSubscriptionService;
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    public ClientSubscriptionView(ClientSubscriptionService clientSubscriptionService, SubscriptionService subscriptionService, UserService userService) {
        this.clientSubscriptionService = clientSubscriptionService;
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    public Scene createClientSubscriptionScene() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #ecf0f1;");

        TableView<ClientSubscriptionRow> table = new TableView<>();
        TableColumn<ClientSubscriptionRow, String> clientColumn = new TableColumn<>("Client");
        clientColumn.setCellValueFactory(data -> data.getValue().clientNameProperty());

        TableColumn<ClientSubscriptionRow, String> subscriptionColumn = new TableColumn<>("Subscription");
        subscriptionColumn.setCellValueFactory(data -> data.getValue().subscriptionLabelProperty());

        TableColumn<ClientSubscriptionRow, Button> changeColumn = new TableColumn<>("Change");
        changeColumn.setCellValueFactory(data -> data.getValue().changeButtonProperty());

        TableColumn<ClientSubscriptionRow, Button> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(data -> data.getValue().deleteButtonProperty());

        table.getColumns().addAll(clientColumn, subscriptionColumn, changeColumn, deleteColumn);
        table.setPlaceholder(new Label("No client subscriptions available."));

        ObservableList<ClientSubscriptionRow> rows = loadClientSubscriptions();
        table.setItems(rows);

        Button addSubscriptionButton = new Button("Add Subscription");
        addSubscriptionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #27ae60; -fx-text-fill: white;");
        addSubscriptionButton.setOnAction(e -> openAddSubscriptionPopup(table));

        layout.getChildren().addAll(table, addSubscriptionButton);
        return new Scene(layout, 800, 600);
    }

    private ObservableList<ClientSubscriptionRow> loadClientSubscriptions() {
        ObservableList<ClientSubscriptionRow> rows = FXCollections.observableArrayList();
        try {
            List<ClientSubscription> clientSubscriptions = clientSubscriptionService.getAllClientSubscriptions();
            for (ClientSubscription cs : clientSubscriptions) {
                User client = userService.getUserById(cs.getClientId())
                                         .orElseThrow(() -> new IllegalArgumentException("Client not found for ID: " + cs.getClientId()));
                Subscription subscription = subscriptionService.getSubscriptionById(cs.getSubscriptionId());
                rows.add(new ClientSubscriptionRow(client, subscription, cs, clientSubscriptionService, subscriptionService));
            }
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
        return rows;
    }

    private void openAddSubscriptionPopup(TableView<ClientSubscriptionRow> table) {
        Stage popup = new Stage();
        popup.setTitle("Add Client Subscription");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #ecf0f1;");

        ComboBox<User> clientComboBox = new ComboBox<>();
        ComboBox<Subscription> subscriptionComboBox = new ComboBox<>();

        try {
            clientComboBox.setItems(FXCollections.observableArrayList(userService.getClients()));
            subscriptionComboBox.setItems(FXCollections.observableArrayList(subscriptionService.getAllSubscriptions()));
        } catch (SQLException e) {
            showError("Error loading clients or subscriptions: " + e.getMessage());
        }

        Button linkButton = new Button("Link Subscription");
        linkButton.setStyle("-fx-font-size: 14px;");
        linkButton.setOnAction(e -> {
            User client = clientComboBox.getSelectionModel().getSelectedItem();
            Subscription subscription = subscriptionComboBox.getSelectionModel().getSelectedItem();
            if (client != null && subscription != null) {
                try {
                    clientSubscriptionService.addClientSubscription(new ClientSubscription(client.getId(), subscription.getId().orElseThrow()));
                    table.setItems(loadClientSubscriptions());
                    popup.close();
                } catch (SQLException ex) {
                    showError("Error linking subscription: " + ex.getMessage());
                }
            } else {
                showError("Please select both a client and a subscription.");
            }
        });

        layout.getChildren().addAll(new Label("Select Client:"), clientComboBox,
                                     new Label("Select Subscription:"), subscriptionComboBox, linkButton);

        popup.setScene(new Scene(layout, 400, 300));
        popup.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}


