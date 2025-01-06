package com.autocare.subscription.ui;

import com.autocare.subscription.Subscription;
import com.autocare.subscription.SubscriptionType;
import com.autocare.subscription.factory.SubscriptionDAOMySQLFactory;
import com.autocare.subscription.service.SubscriptionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SubscriptionView {

    private final SubscriptionService          subscriptionService;
    private       TableView<Subscription>      subscriptionTable;
    private       ObservableList<Subscription> subscriptions;

    public SubscriptionView() {
        subscriptionService
                = new SubscriptionService(new SubscriptionDAOMySQLFactory());
    }

    public Scene createSubscriptionScene() {
        // Initialize the subscriptions list and table
        subscriptions = FXCollections.observableArrayList();
        subscriptionTable = new TableView<>();

        // Define columns
        TableColumn<Subscription, String> labelColumn = new TableColumn<>(
                "Label");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));

        TableColumn<Subscription, String>
                typeColumn
                = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Subscription, Double> amountColumn = new TableColumn<>(
                "Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Subscription, String> descriptionColumn = new TableColumn<>(
                "Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>(
                "description"));

        // Create the is_active column for Subscription
        TableColumn<Subscription, Boolean> isActiveColumn = new TableColumn<>(
                "Active");

        // Set the cell value factory to bind to the 'is_active' property
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>(
                "isActive"));

        // Set a custom cell factory to color the cells based on the boolean
        // value
        isActiveColumn.setCellFactory(column -> new TableCell<Subscription,
                Boolean>() {
            @Override
            protected void updateItem(Boolean isActive, boolean empty) {
                super.updateItem(isActive, empty);

                if (empty || isActive == null) {
                    setText(null);
                    setStyle(""); // Reset the style if the cell is empty
                }
                else {
                    setText(isActive
                            ? "Yes"
                            : "No"); // Display Yes or No based on the
                    // boolean value
                    // Set the background color based on the value
                    setStyle(isActive
                             ? "-fx-background-color: green; -fx-text-fill: "
                               + "white;"
                             : "-fx-background-color: red; -fx-text-fill: "
                               + "white;");
                }
            }
        });


        // Add columns to the table
        subscriptionTable.getColumns().addAll(labelColumn,
                                              typeColumn,
                                              amountColumn,
                                              descriptionColumn,
                                              isActiveColumn

        );

        subscriptionTable.setItems(subscriptions);

        // Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showSubscriptionForm("Add Subscription"));

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            Subscription
                    selectedSubscription
                    = subscriptionTable.getSelectionModel().getSelectedItem();
            showEditingForm(selectedSubscription);
        });


        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeSelectedSubscription();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, removeButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(subscriptionTable);
        layout.setBottom(buttonBox);
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, "
                + "#2c3e50, #34495e);");

        loadSubscriptions();

        // Return the scene
        return new Scene(layout, 800, 400);
    }

    private void showSubscriptionForm(String title) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        TextField labelField = new TextField();
        labelField.setPromptText("Label");

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Monthly",
                                                                "Yearly"
        ));
        typeComboBox.setPromptText("Type");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        CheckBox isActiveCheckBox = new CheckBox("Active");

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        descriptionField.setPrefRowCount(4); // Adjust rows to make it larger
        descriptionField.setWrapText(true); // Enable text wrapping

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                // Collect form data
                String label = labelField.getText();
                String type = typeComboBox.getValue();
                Double amount = Double.valueOf(amountField.getText());
                boolean isActive = isActiveCheckBox.isSelected();
                String description = descriptionField.getText();

                // Add to the list or update logic here
                System.out.printf("Submitted: %s, %s, %.2f, %b, %s%n",
                                  label,
                                  type,
                                  amount,
                                  isActive,
                                  description
                );

                subscriptionService.addSubscription(new Subscription(
                        SubscriptionType.valueOf(type.toUpperCase()),
                        label,
                        isActive,
                        amount,
                        description
                ));

                loadSubscriptions();
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Amount",
                          "Please enter a valid number for the amount."
                );
            } catch (SQLException sqlException) {
                showAlert("ERROR", "Could not insert subscriptions");
            }
        });

        // Add elements to the form
        form.getChildren().addAll(new Label("Label:"),
                                  labelField,
                                  new Label("Type:"),
                                  typeComboBox,
                                  new Label("Amount:"),
                                  amountField,
                                  isActiveCheckBox,
                                  new Label("Description:"),
                                  descriptionField,
                                  submitButton
        );

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private void removeSelectedSubscription() throws SQLException {
        Subscription selected = subscriptionTable.getSelectionModel()
                                                 .getSelectedItem();
        if (selected != null) {
            subscriptionService.deleteSubscription(selected);
            subscriptions.remove(selected);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadSubscriptions() {
        try {
            subscriptionTable.getItems().clear();
            var subscriptions = subscriptionService.getAllSubscriptions();
            subscriptionTable.getItems().addAll(subscriptions);

        } catch (SQLException ex) {
            showAlert("ERROR", "Could not load subscriptions.");
        }
    }


    private void showEditingForm(Subscription subscriptionToEdit) {
        if (subscriptionToEdit == null) {
            showAlert("No Selection", "Please select a subscription to edit.");
            return;
        }

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Edit Subscription");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        // Input fields
        TextField labelField = new TextField(subscriptionToEdit.getLabel());
        labelField.setPromptText("Label");

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Monthly",
                                                                "Yearly"
        ));
        typeComboBox.setValue(subscriptionToEdit.getType().toString());

        TextField
                amountField
                = new TextField(String.valueOf(subscriptionToEdit.getAmount()));
        amountField.setPromptText("Amount");

        CheckBox isActiveCheckBox = new CheckBox("Active");
        isActiveCheckBox.setSelected(subscriptionToEdit.getIsActive());

        TextArea
                descriptionField
                = new TextArea(subscriptionToEdit.getDescription());
        descriptionField.setPromptText("Description");
        descriptionField.setPrefRowCount(4); // Adjust rows to make it larger
        descriptionField.setWrapText(true); // Enable text wrapping

        // Submit button
        Button submitButton = new Button("Save Changes");
        submitButton.setOnAction(e -> {
            try {
                // Update the subscription with the new data
                subscriptionToEdit.setLabel(labelField.getText());
                subscriptionToEdit.setType(SubscriptionType.valueOf(
                        typeComboBox.getValue().toUpperCase()));
                subscriptionToEdit.setAmount(Double.parseDouble(amountField.getText()));
                subscriptionToEdit.setIsActive(isActiveCheckBox.isSelected());
                subscriptionToEdit.setDescription(descriptionField.getText());

                subscriptionService.updateSubscription(subscriptionToEdit);

                loadSubscriptions();
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Amount",
                          "Please enter a valid number for the amount."
                );
            } catch (SQLException sqlException) {
                showAlert("Error", "Could not update subscription.");
            }
        });

        // Add elements to the form
        form.getChildren().addAll(new Label("Label:"),
                                  labelField,
                                  new Label("Type:"),
                                  typeComboBox,
                                  new Label("Amount:"),
                                  amountField,
                                  isActiveCheckBox,
                                  new Label("Description:"),
                                  descriptionField,
                                  submitButton
        );

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }


}

