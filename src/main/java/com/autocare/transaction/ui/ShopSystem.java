package com.autocare.transaction.ui;

import com.autocare.transaction.Cart;
import com.autocare.transaction.Item;
import com.autocare.transaction.Transaction;
import com.autocare.transaction.factory.CartDAOMySQLFactory;
import com.autocare.transaction.factory.ItemDAOMySQLFactory;
import com.autocare.transaction.factory.TransactionDAOMySQLFactory;
import com.autocare.transaction.service.CartService;
import com.autocare.transaction.service.ItemService;
import com.autocare.user.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class ShopSystem {

    private final ItemService itemService;
    private final CartService cartService;
    private final User user;
    private Optional<Cart> cart;
    private TableView<Item> itemTable;
    private ObservableList<Item> availableItems;

    public ShopSystem(User user) throws SQLException {
        itemService = new ItemService(new ItemDAOMySQLFactory());
        cartService = new CartService(new CartDAOMySQLFactory(), new TransactionDAOMySQLFactory());
        this.user = user;
        this.cart = cartService.getCart(user);
    }

    public Scene createItemScene() {
        availableItems = FXCollections.observableArrayList();
        itemTable = new TableView<>();

        // Define columns for item table
        TableColumn<Item, String> labelColumn = new TableColumn<>("Label");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));

        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Define the "Add" button column
        TableColumn<Item, Void> buttonColumn = new TableColumn<>("Actions");
        buttonColumn.setCellFactory(param -> new TableCell<>() {
            private final Button addButton = new Button("Add");

            {
                addButton.setOnAction(event -> {
                    if (cart.isEmpty()) {
                        try {
                            cart = Optional.of(cartService.createCart(user));
                        } catch (SQLException sqlException) {
                            showAlert("ERROR", "Could not create a cart!");
                            sqlException.printStackTrace();
                            throw new RuntimeException("RGOUWROUGHOGNR");
                        }
                    }

                    Transaction transaction = new Transaction(cart.get(), getTableRow().getItem(), 1);
                    try {
                        cartService.addTransaction(cart.get(), transaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("ERROR", "Item could not be added to cart!");
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addButton);
                }
            }
        });

        // Add columns to the table
        itemTable.getColumns().addAll(labelColumn, descriptionColumn, priceColumn, buttonColumn);
        itemTable.setItems(availableItems);

        // Buttons: Refresh and Checkout
        Button refreshButton = new Button("Refresh Items");
        refreshButton.setOnAction(e -> loadAvailableItems());

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(e -> {
            if (cart.isPresent()) {
                // Create a new Stage (popup window)
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
                popupStage.setTitle("Checkout");

                // Set the checkout scene
                Scene checkoutScene = createCheckoutPage();
                popupStage.setScene(checkoutScene);

                // Set the dimensions of the popup
                popupStage.setWidth(700);
                popupStage.setHeight(400);

                // Show the popup
                popupStage.show();
            } else {
                showAlert("ERROR", "Your cart is empty! Add items before proceeding to checkout.");
            }
        });



        // Layout for buttons
        HBox buttonBox = new HBox(10, refreshButton, checkoutButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(itemTable);
        layout.setBottom(buttonBox);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #34495e);");

        loadAvailableItems();

        // Return the scene
        return new Scene(layout, 700, 400);
    }

    private void loadAvailableItems() {
        try {
            // This should be loading items from a service or database
            availableItems.clear();
            availableItems.addAll(itemService.getAllItems());
        } catch (Exception ex) {
            showAlert("Error", "Could not load available items.");
        }
    }

    private void openCheckoutPage() {
        // This will be where you open the checkout page
        System.out.println("Checkout button clicked. Proceed to checkout page.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene createCheckoutPage() {
        // Observable list for transactions in the cart
        ObservableList<Transaction> cartTransactions = FXCollections.observableArrayList(cart.get().getCartContent());

        // TableView for displaying cart items
        TableView<Transaction> cartTable = new TableView<>();

        // Define columns for cart table
        TableColumn<Transaction, String> itemLabelColumn = new TableColumn<>("Item");
        itemLabelColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getItem().getLabel()));

        TableColumn<Transaction, String> itemDescriptionColumn = new TableColumn<>("Description");
        itemDescriptionColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getItem().getDescription()));

        TableColumn<Transaction, Double> itemPriceColumn = new TableColumn<>("Price");
        itemPriceColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getItem().getPrice()).asObject());

        TableColumn<Transaction, Long> itemQuantityColumn = new TableColumn<>("Quantity");
        itemQuantityColumn.setCellValueFactory(param -> new SimpleLongProperty(param.getValue().getAmount()).asObject());

        // Actions column for Add/Remove buttons
        TableColumn<Transaction, Void> actionsColumn = new TableColumn<>("Actions");

        // Label for total
        Label totalLabel = new Label("Total: $0.00");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        updateTotal(cartTransactions, totalLabel);

        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button addButton = new Button("+");
            private final Button removeButton = new Button("-");

            {
                addButton.setOnAction(event -> {
                    if (cart.isEmpty()) {
                        try {
                            cart = Optional.of(cartService.createCart(user));
                        } catch (SQLException sqlException) {
                            showAlert("ERROR", "Could not create a cart!");
                            sqlException.printStackTrace();
                            throw new RuntimeException("RGOUWROUGHOGNR");
                        }
                    }

                    Transaction transaction = new Transaction(cart.get(), getTableRow().getItem().getItem(), 1);
                    try {
                        cartService.addTransaction(cart.get(), transaction);
                    } catch (SQLException e) {
                        showAlert("ERROR", "Item could not be added to cart checkout!");
                    }

                    // Refresh the table after adding the item
                    refreshCartTable(cartTable, cartTransactions, cart.get());
                    updateTotal(cartTransactions, totalLabel);
                });

                removeButton.setOnAction(event -> {
                    if (cart.isEmpty()) {
                        try {
                            cart = Optional.of(cartService.createCart(user));
                        } catch (SQLException sqlException) {
                            showAlert("ERROR", "Could not create a cart!");
                            sqlException.printStackTrace();
                            throw new RuntimeException("RGOUWROUGHOGNR");
                        }
                    }

                    try {
                        cartService.decreaseTransactionAmountUntilDeletion(cart.get(), getTableRow().getItem());
                    } catch (SQLException e) {
                        showAlert("ERROR", "Item could not be removed from cart!");
                    }

                    // Refresh the table after adding the item
                    refreshCartTable(cartTable, cartTransactions, cart.get());
                    updateTotal(cartTransactions, totalLabel);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5, addButton, removeButton);
                    setGraphic(buttonBox);
                }
            }
        });

        // Add columns to the table
        cartTable.getColumns().addAll(itemLabelColumn, itemDescriptionColumn, itemPriceColumn, itemQuantityColumn, actionsColumn);
        cartTable.setItems(cartTransactions);

        // Label for subtotal
        Label subtotalLabel = new Label();
        subtotalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");


        Button payNowButton = new Button("Pay Now");
        payNowButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;");

        payNowButton.setOnAction(event -> {
            if (cart.isEmpty()) {
                showAlert("ERROR", "No cart available to checkout!");
                return;
            }

            try {
                cartService.deleteCart(cart.get());
                cart = Optional.empty();
                ((Stage) payNowButton.getScene().getWindow()).close();
            } catch (SQLException sqlException) {
                showAlert("ERROR", "Could not checkout!");
            }
        });





        // Add the total label to the footer layout
        HBox footerBox = new HBox(10, totalLabel, payNowButton);
        footerBox.setAlignment(Pos.CENTER_LEFT); // Align to left
        footerBox.setPadding(new Insets(10));
        footerBox.setStyle("-fx-background-color: #2c3e50;");


        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(cartTable);
        layout.setBottom(footerBox);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #34495e, #2c3e50);");



        // Return the scene
        return new Scene(layout, 700, 400);
    }

    private void refreshCartTable(TableView<Transaction> cartTable, ObservableList<Transaction> cartTransactions, Cart cart) {
        // Clear the current data in the table
        cartTransactions.clear();

        // Refill the observable list with updated cart content
        cartTransactions.addAll(cart.getCartContent());

        // Update the TableView
        cartTable.setItems(cartTransactions);
    }

    private void updateTotal(ObservableList<Transaction> cartTransactions, Label totalLabel) {
        double total = cartTransactions.stream()
                .mapToDouble(transaction -> transaction.getItem().getPrice() * transaction.getAmount())
                .sum();
        totalLabel.setText(String.format("Total: $%.2f", total));
    }




}
