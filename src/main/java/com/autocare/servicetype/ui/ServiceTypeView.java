package com.autocare.servicetype.ui;

import com.autocare.servicetype.ServiceType;
import com.autocare.servicetype.service.ServiceTypeService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class ServiceTypeView {

    private final ServiceTypeService serviceTypeService;
    private final ObservableList<ServiceType> serviceTypes = FXCollections.observableArrayList();

    public ServiceTypeView() {
        this.serviceTypeService = new ServiceTypeService();
        loadServiceTypes();
    }

    private void loadServiceTypes() {
        // Mock implementation for loading data into ObservableList (replace with actual DAO retrieval logic if required)
        // serviceTypes.setAll(serviceTypeService.getAllServiceTypes());
    }

    public Scene createServiceTypeScene(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Service Types");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // TableView for Service Types
        TableView<ServiceType> serviceTypeTable = new TableView<>();
        serviceTypeTable.setItems(serviceTypes);
        serviceTypeTable.setPrefHeight(300);

        TableColumn<ServiceType, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ServiceType, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        serviceTypeTable.getColumns().addAll(nameCol, descriptionCol);

        // Buttons
        Button addServiceTypeButton = new Button("Add New");
        Button updateServiceTypeButton = new Button("Edit");
        Button deleteServiceTypeButton = new Button("Delete");

        addServiceTypeButton.setOnAction(e -> openAddServiceTypeDialog());
        updateServiceTypeButton.setOnAction(e -> openUpdateServiceTypeDialog(serviceTypeTable.getSelectionModel().getSelectedItem()));
        deleteServiceTypeButton.setOnAction(e -> deleteServiceType(serviceTypeTable.getSelectionModel().getSelectedItem()));

        HBox buttonBox = new HBox(10, addServiceTypeButton, updateServiceTypeButton, deleteServiceTypeButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, titleLabel, serviceTypeTable, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 600, 400);
    }

    private void openAddServiceTypeDialog() {
        Dialog<ServiceType> dialog = createServiceTypeDialog("Add New Service Type", null);
        dialog.setTitle("New Service Type");

        Optional<ServiceType> result = dialog.showAndWait();
        result.ifPresent(serviceType -> {
            try {
                serviceTypeService.addServiceType(serviceType.getName(), serviceType.getDescription());
                serviceTypes.add(serviceType);
            } catch (Exception e) {
                showAlert("Error", "Failed to add service type: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void openUpdateServiceTypeDialog(ServiceType selectedServiceType) {
        if (selectedServiceType == null) {
            showAlert("Warning", "Please select a service type to edit.", Alert.AlertType.WARNING);
            return;
        }

        Dialog<ServiceType> dialog = createServiceTypeDialog("Update Service Type", selectedServiceType);
        dialog.setTitle("Edit Service Type");

        Optional<ServiceType> result = dialog.showAndWait();
        result.ifPresent(serviceType -> {
            try {
                serviceTypeService.updateServiceType(selectedServiceType.getId(), serviceType.getName(), serviceType.getDescription());
                serviceTypes.set(serviceTypes.indexOf(selectedServiceType), serviceType);
            } catch (Exception e) {
                showAlert("Error", "Failed to update service type: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void deleteServiceType(ServiceType selectedServiceType) {
        if (selectedServiceType == null) {
            showAlert("Warning", "Please select a service type to delete.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service type?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Replace with actual deletion logic
                serviceTypes.remove(selectedServiceType);
                showAlert("Success", "Service type deleted successfully.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", "Failed to delete service type: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private Dialog<ServiceType> createServiceTypeDialog(String title, ServiceType existingServiceType) {
        Dialog<ServiceType> dialog = new Dialog<>();
        dialog.setTitle(title);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        if (existingServiceType != null) {
            nameField.setText(existingServiceType.getName());
            descriptionField.setText(existingServiceType.getDescription());
        }

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Description:"), 0, 1);
        gridPane.add(descriptionField, 1, 1);

        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new ServiceType(
                        existingServiceType != null ? existingServiceType.getId() : 0,
                        nameField.getText(),
                        descriptionField.getText()
                );
            }
            return null;
        });

        return dialog;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
