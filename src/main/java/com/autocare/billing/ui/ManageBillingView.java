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
        // TableView
        TableView<Bill> billTable = new TableView<>();
        billTable.setItems(bills);
        billTable.setPrefHeight(300);

        TableColumn<Bill, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Bill, String> clientCol = new TableColumn<>("Client");
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));

        TableColumn<Bill, String> serviceCol = new TableColumn<>("Service");
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceType"));

        TableColumn<Bill, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        billTable.getColumns().addAll(dateCol, clientCol, serviceCol, totalCol);

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

        return new Scene(layout, 600, 400);
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

        TextField clientField = new TextField();
        clientField.setPromptText("Client Name");

        TextField dateField = new TextField();
        dateField.setPromptText("Date (YYYY-MM-DD)");

        TextField serviceField = new TextField();
        serviceField.setPromptText("Service Type");

        TextField totalField = new TextField();
        totalField.setPromptText("Total Amount");

        if (existingBill != null) {
            clientField.setText(existingBill.getClientName());
            dateField.setText(existingBill.getDate());
            serviceField.setText(existingBill.getServiceType());
            totalField.setText(String.valueOf(existingBill.getTotalAmount()));
        }

        gridPane.add(new Label("Client Name:"), 0, 0);
        gridPane.add(clientField, 1, 0);
        gridPane.add(new Label("Date:"), 0, 1);
        gridPane.add(dateField, 1, 1);
        gridPane.add(new Label("Service Type:"), 0, 2);
        gridPane.add(serviceField, 1, 2);
        gridPane.add(new Label("Total Amount:"), 0, 3);
        gridPane.add(totalField, 1, 3);

        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    return new Bill(
                            existingBill != null ? existingBill.getId() : 0,
                            clientField.getText(),
                            dateField.getText(),
                            serviceField.getText(),
                            Double.parseDouble(totalField.getText())
                    );
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid input for total amount.", Alert.AlertType.ERROR);
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
