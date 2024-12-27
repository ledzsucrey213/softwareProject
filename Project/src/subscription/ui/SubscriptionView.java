package subscription.ui;

import javafx.application.Application;
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
import subscription.bl.*;

public class SubscriptionView extends Application {

    private TableView<Subscription> subscriptionTable;
    private ObservableList<Subscription> subscriptions;

    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Subscription Manager");

        // Initialize subscriptions (dummy data)
        subscriptions = FXCollections.observableArrayList();

        // Setup TableView
        subscriptionTable = new TableView<>();
        TableColumn<Subscription, String> labelColumn = new TableColumn<>("Label");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        TableColumn<Subscription, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Subscription, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<Subscription, Boolean> isActiveColumn = new TableColumn<>("Active");
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        subscriptionTable.getColumns().addAll(labelColumn, typeColumn, amountColumn, isActiveColumn);
        subscriptionTable.setItems(subscriptions);

        // Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showSubscriptionForm("Add Subscription"));
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> showSubscriptionForm("Edit Subscription"));
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeSelectedSubscription());

        HBox buttonBox = new HBox(10, addButton, editButton, removeButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(subscriptionTable);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSubscriptionForm(String title) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        // Label field
        TextField labelField = new TextField();
        labelField.setPromptText("Label");

        // Type combo box
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Monthly", "Yearly"));
        typeComboBox.setPromptText("Type");

        // Amount field
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        // Is active checkbox
        CheckBox isActiveCheckBox = new CheckBox("Active");

        // Description field (larger)
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        descriptionField.setPrefRowCount(4); // Adjust rows to make it larger
        descriptionField.setWrapText(true); // Enable text wrapping

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Collect form data into variables
            String label = labelField.getText();
            String type = typeComboBox.getValue();
            Double amount = Double.valueOf(amountField.getText());
            boolean isActive = isActiveCheckBox.isSelected();
            String description = descriptionField.getText();

            // Placeholder for future logic (e.g., adding to list or updating)
            popup.close();
        });

        // Add elements to the form
        form.getChildren().addAll(
            new Label("Label:"), labelField,
            new Label("Type:"), typeComboBox,
            new Label("Amount:"), amountField,
            isActiveCheckBox,
            new Label("Description:"), descriptionField,
            submitButton
        );

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }


    private void removeSelectedSubscription() {
        Subscription selected = subscriptionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            subscriptions.remove(selected);
        }
    }
}
