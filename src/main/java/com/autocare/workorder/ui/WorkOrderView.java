package com.autocare.workorder.ui;

import com.autocare.workorder.Order;
import com.autocare.workorder.service.WorkOrderService;
import com.autocare.workorder.factory.OrderAbstractFactory;

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
import java.util.Optional;

public class WorkOrderView {

    private final WorkOrderService workOrderService;
    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    public WorkOrderView(OrderAbstractFactory orderFactory) {
        workOrderService = new WorkOrderService(orderFactory);
        loadOrders();
    }

    private void loadOrders() {
        try {
            orders.setAll(workOrderService.getAllOrders());
        } catch (SQLException e) {
            showAlert("Error", "Failed to load orders: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public Scene createWorkOrderScene(Stage primaryStage) {
        // TableView
        TableView<Order> orderTable = new TableView<>();
        orderTable.setItems(orders);
        orderTable.setPrefHeight(300);

        TableColumn<Order, String> partNameCol = new TableColumn<>("Part Name");
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));

        TableColumn<Order, Integer> partQtyCol = new TableColumn<>("Quantity");
        partQtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Order, String> dateCol = new TableColumn<>("Arrival Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));

        TableColumn<Order, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable.getColumns().addAll(partNameCol, partQtyCol, statusCol, dateCol, priceCol);

        // Buttons
        Button addOrderButton = new Button("Add Order");
        Button editOrderButton = new Button("Edit Order");
        Button deleteOrderButton = new Button("Remove Order");

        addOrderButton.setOnAction(e -> openAddOrderDialog());
        editOrderButton.setOnAction(e -> openEditOrderDialog(orderTable.getSelectionModel().getSelectedItem()));
        deleteOrderButton.setOnAction(e -> deleteOrder(orderTable.getSelectionModel().getSelectedItem()));

        HBox buttonBox = new HBox(10, addOrderButton, editOrderButton, deleteOrderButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, orderTable, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 600, 400);
    }

    private void openAddOrderDialog() {
        Dialog<Order> dialog = createOrderDialog("Add Order", null);
        Optional<Order> result = dialog.showAndWait();

        result.ifPresent(order -> {
            try {
                workOrderService.addOrder(order);
                orders.add(order);
            } catch (SQLException e) {
                showAlert("Error", "Failed to add order: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void openEditOrderDialog(Order selectedOrder) {
        if (selectedOrder == null) {
            showAlert("Warning", "Please select an order to edit.", Alert.AlertType.WARNING);
            return;
        }

        Dialog<Order> dialog = createOrderDialog("Edit Order", selectedOrder);
        Optional<Order> result = dialog.showAndWait();

        result.ifPresent(order -> {
            try {
                workOrderService.updateOrder(order);
                orders.set(orders.indexOf(selectedOrder), order);
            } catch (SQLException e) {
                showAlert("Error", "Failed to update order: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    private void deleteOrder(Order selectedOrder) {
        if (selectedOrder == null) {
            showAlert("Warning", "Please select an order to delete.", Alert.AlertType.WARNING);
            return;
        }

        try {
            workOrderService.deleteOrder(selectedOrder);
            orders.remove(selectedOrder);
        } catch (SQLException e) {
            showAlert("Error", "Failed to delete order: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Dialog<Order> createOrderDialog(String title, Order existingOrder) {
        Dialog<Order> dialog = new Dialog<>();
        dialog.setTitle(title);

        // Form fields
        TextField partNameField = new TextField();
        partNameField.setPromptText("Part Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField statusField = new TextField();
        statusField.setPromptText("Status");

        TextField arrivalDateField = new TextField();
        arrivalDateField.setPromptText("Arrival Date");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        if (existingOrder != null) {
            partNameField.setText(existingOrder.getPartName());
            quantityField.setText(String.valueOf(existingOrder.getQuantity()));
            statusField.setText(existingOrder.getStatus());
            arrivalDateField.setText(existingOrder.getArrivalDate());
            priceField.setText(String.valueOf(existingOrder.getPrice()));
        }

        VBox form = new VBox(10, partNameField, quantityField, statusField, arrivalDateField, priceField);
        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new Order(
                    existingOrder != null ? existingOrder.getId() : 0,
                    partNameField.getText(),
                    Integer.parseInt(quantityField.getText()),
                    statusField.getText(),
                    arrivalDateField.getText(),
                    Double.parseDouble(priceField.getText())
                );
            }
            return null;
        });

        return dialog;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
