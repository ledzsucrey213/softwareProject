package com.autocare.billing.ui;

import com.autocare.billing.Bill;
import com.autocare.billing.service.BillingService;
import com.autocare.billing.factory.BillingAbstractFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ManageBillingView {

    private final BillingService billingService;
    private final ObservableList<Bill> bills = FXCollections.observableArrayList();

    public ManageBillingView(BillingAbstractFactory billingFactory) {
        this.billingService = new BillingService(billingFactory);
        loadBills();
    }

    private void loadBills() {
        try {
            bills.setAll(billingService.getAllBills());
        } catch (SQLException e) {
            showAlert("Error", "Failed to load bills: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public Scene createBillingScene(Stage primaryStage) {
        // TableView configuration
        TableView<Bill> billTable = new TableView<>();
        billTable.setItems(bills);
        billTable.setPrefHeight(300);

        TableColumn<Bill, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Bill, Integer> clientIdCol = new TableColumn<>("Client ID");
        clientIdCol.setCellValueFactory(new PropertyValueFactory<>("clientId"));

        TableColumn<Bill, String> serviceCol = new TableColumn<>("Service");
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        TableColumn<Bill, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("billDate"));

        TableColumn<Bill, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("billStatus"));

        TableColumn<Bill, Double> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

        billTable.getColumns().addAll(idCol, clientIdCol, serviceCol, dateCol, statusCol, costCol);

        // Buttons
        Button addBillButton = new Button("Add New");
        Button updateBillButton = new Button("Edit");
        Button deleteBillButton = new Button("Delete");

        addBillButton.setOnAction(e -> openAddBillDialog());
        updateBillButton.setOnAction(e -> openUpdateBillDialog(billTable.getSelectionModel().getSelectedItem()));
        deleteBillButton.setOnAction(e -> deleteBill(billTable.getSelectionModel().getSelectedItem()));

        HBox buttonBox = new HBox(10, addBillButton, updateBillButton, deleteBillButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, billTable, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 800, 600);
    }

    private void openAddBillDialog() {
        Dialog<Bill> dialog = createBillDialog("Add New Bill", null);
        dialog.setTitle("New Bill");

        Optional<Bill> result = dialog.showAndWait();
        result.ifPresent(bill -> {
            try {
                billingService.generateBill(bill);
                bills.add(bill);
            } catch (SQLException e) {
                showAlert("Error", "Failed to add bill: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void openUpdateBillDialog(Bill selectedBill) {
        if (selectedBill == null) {
            showAlert("Warning", "Please select a bill to edit.", Alert.AlertType.WARNING);
            return;
        }

        Dialog<Bill> dialog = createBillDialog("Update Bill", selectedBill);
        dialog.setTitle("Edit Bill");

        Optional<Bill> result = dialog.showAndWait();
        result.ifPresent(bill -> {
            try {
                billingService.updateBill(bill);
                bills.set(bills.indexOf(selectedBill), bill);
            } catch (SQLException e) {
                showAlert("Error", "Failed to update bill: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void deleteBill(Bill selectedBill) {
        if (selectedBill == null) {
            showAlert("Warning", "Please select a bill to delete.", Alert.AlertType.WARNING);
            return;
        }

        try {
            billingService.deleteBill(selectedBill);
            bills.remove(selectedBill);
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete bill: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Dialog<Bill> createBillDialog(String title, Bill existingBill) {
        Dialog<Bill> dialog = new Dialog<>();
        dialog.setTitle(title);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Client ID");

        TextField dateField = new TextField();
        dateField.setPromptText("Date (YYYY-MM-DD)");

        TextField serviceField = new TextField();
        serviceField.setPromptText("Service Type");

        TextField statusField = new TextField();
        statusField.setPromptText("Status");

        TextField costField = new TextField();
        costField.setPromptText("Cost");

        if (existingBill != null) {
            clientIdField.setText(String.valueOf(existingBill.getClientId()));
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(existingBill.getBillDate()));
            serviceField.setText(existingBill.getServiceType());
            statusField.setText(existingBill.getBillStatus());
            costField.setText(String.valueOf(existingBill.getCost()));
        }

        gridPane.add(new Label("Client ID:"), 0, 0);
        gridPane.add(clientIdField, 1, 0);
        gridPane.add(new Label("Date:"), 0, 1);
        gridPane.add(dateField, 1, 1);
        gridPane.add(new Label("Service Type:"), 0, 2);
        gridPane.add(serviceField, 1, 2);
        gridPane.add(new Label("Status:"), 0, 3);
        gridPane.add(statusField, 1, 3);
        gridPane.add(new Label("Cost:"), 0, 4);
        gridPane.add(costField, 1, 4);

        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    return new Bill(
                            existingBill != null ? existingBill.getId() : 0,
                            Integer.parseInt(clientIdField.getText()),
                            serviceField.getText(),
                            new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText()),
                            statusField.getText(),
                            Double.parseDouble(costField.getText())
                    );
                } catch (Exception e) {
                    showAlert("Error", "Invalid input: " + e.getMessage(), Alert.AlertType.ERROR);
                }
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
