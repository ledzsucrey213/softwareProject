package com.autocare.payment.ui;

import com.autocare.payment.PaymentType;
import com.autocare.payment.factory.PaymentTypeDAOFactory;
import com.autocare.payment.factory.PaymentTypeDAOMySQLFactory;
import com.autocare.payment.service.PaymentTypeService;
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

public class ManagePaymentTypeView {

    private final PaymentTypeService paymentTypeService;
    private TableView<PaymentType> paymentTypeTable;
    private ObservableList<PaymentType> paymentTypes;

    public ManagePaymentTypeView() {
        paymentTypeService = new PaymentTypeService(new PaymentTypeDAOMySQLFactory());
    }

    public Scene createPaymentTypeScene() {
        // Initialize the payment types list and table
        paymentTypes = FXCollections.observableArrayList();
        paymentTypeTable = new TableView<>();

        // Define columns
        TableColumn<PaymentType, String> labelColumn = new TableColumn<>("Label");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));

        TableColumn<PaymentType, Double> feesColumn = new TableColumn<>("Fees");
        feesColumn.setCellValueFactory(new PropertyValueFactory<>("fees"));


        TableColumn<PaymentType, Boolean> isAvailableColumn = new TableColumn<>("Available");
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

// Set a custom cell factory to color the cells based on the boolean value
        isAvailableColumn.setCellFactory(column -> new TableCell<PaymentType, Boolean>() {
            @Override
            protected void updateItem(Boolean isAvailable, boolean empty) {
                super.updateItem(isAvailable, empty);

                if (empty || isAvailable == null) {
                    setText(null);
                    setStyle(""); // Reset the style
                } else {
                    setText(isAvailable ? "Yes" : "No");
                    setStyle(isAvailable ? "-fx-background-color: green; -fx-text-fill: white;"
                            : "-fx-background-color: red; -fx-text-fill: white;");
                }
            }
        });


        // Add columns to the table
        paymentTypeTable.getColumns().addAll(labelColumn, feesColumn, isAvailableColumn);

        paymentTypeTable.setItems(paymentTypes);

        // Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showPaymentTypeForm("Add Payment Type"));

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            PaymentType selectedPaymentType = paymentTypeTable.getSelectionModel().getSelectedItem();
            showEditingForm(selectedPaymentType);
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeSelectedPaymentType();
            } catch (SQLException ex) {
                showAlert("Error", "Could not remove payment type.");
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, removeButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(paymentTypeTable);
        layout.setBottom(buttonBox);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #34495e);");

        loadPaymentTypes();

        // Return the scene
        return new Scene(layout, 600, 400);
    }

    private void showPaymentTypeForm(String title) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        TextField labelField = new TextField();
        labelField.setPromptText("Label");

        TextField feesField = new TextField();
        feesField.setPromptText("Fees");

        CheckBox isAvailableCheckBox = new CheckBox("Available");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                String label = labelField.getText();
                double fees = Double.parseDouble(feesField.getText());
                boolean isAvailable = isAvailableCheckBox.isSelected();

                paymentTypeService.addPaymentType(new PaymentType(0, label, fees, isAvailable));

                loadPaymentTypes();
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Fees", "Please enter a valid number for the fees.");
            } catch (SQLException sqlException) {
                showAlert("Error", "Could not add payment type.");
            }
        });

        // Add elements to the form
        form.getChildren().addAll(new Label("Label:"), labelField, new Label("Fees:"), feesField, isAvailableCheckBox, submitButton);

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 250);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private void removeSelectedPaymentType() throws SQLException {
        PaymentType selected = paymentTypeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            paymentTypeService.deleteSubscription(selected);
            paymentTypes.remove(selected);
        }
    }

    private void loadPaymentTypes() {
        try {
            paymentTypeTable.getItems().clear();
            var types = paymentTypeService.getAllPaymentType();
            paymentTypeTable.getItems().addAll(types);
        } catch (SQLException ex) {
            showAlert("Error", "Could not load payment types.");
        }
    }

    private void showEditingForm(PaymentType paymentTypeToEdit) {
        if (paymentTypeToEdit == null) {
            showAlert("No Selection", "Please select a payment type to edit.");
            return;
        }

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Edit Payment Type");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        TextField labelField = new TextField(paymentTypeToEdit.getLabel());
        labelField.setPromptText("Label");

        TextField feesField = new TextField(String.valueOf(paymentTypeToEdit.getFees()));
        feesField.setPromptText("Fees");

        CheckBox isAvailableCheckBox = new CheckBox("Available");
        isAvailableCheckBox.setSelected(paymentTypeToEdit.isAvailable());

        // Submit button
        Button submitButton = new Button("Save Changes");
        submitButton.setOnAction(e -> {
            try {
                paymentTypeToEdit.setLabel(labelField.getText());
                paymentTypeToEdit.setFees(Double.parseDouble(feesField.getText()));
                paymentTypeToEdit.setAvailable(isAvailableCheckBox.isSelected());

                paymentTypeService.updatePaymentType(paymentTypeToEdit);

                loadPaymentTypes();
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Fees", "Please enter a valid number for the fees.");
            } catch (SQLException sqlException) {
                showAlert("Error", "Could not update payment type.");
            }
        });

        // Add elements to the form
        form.getChildren().addAll(new Label("Label:"), labelField, new Label("Fees:"), feesField, isAvailableCheckBox, submitButton);

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 250);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
