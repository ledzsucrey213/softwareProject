package com.autocare.loyalty.ui;

import com.autocare.loyalty.LoyaltyProgram;
import com.autocare.loyalty.service.LoyaltyProgramService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoyaltyProgramView {

    private final LoyaltyProgramService loyaltyProgramService;
    private final ObservableList<String> rewardsList = FXCollections.observableArrayList();

    public LoyaltyProgramView() {
        loyaltyProgramService = new LoyaltyProgramService();
    }

    public Scene createLoyaltyProgramScene(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Loyalty Program");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Client ID Input
        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Enter Client ID");

        Button fetchButton = new Button("Fetch");
        fetchButton.setOnAction(e -> loadLoyaltyProgram(clientIdField.getText()));

        HBox clientInputBox = new HBox(10, clientIdField, fetchButton);
        clientInputBox.setAlignment(Pos.CENTER);

        // Loyalty Program Details
        Label pointsLabel = new Label("Points: ");
        TextField pointsField = new TextField();
        pointsField.setEditable(false);

        TableView<String> rewardsTable = new TableView<>();
        rewardsTable.setPlaceholder(new Label("No rewards available"));

        TableColumn<String, String> rewardColumn = new TableColumn<>("Rewards");
        rewardColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        rewardsTable.getColumns().add(rewardColumn);
        rewardsTable.setItems(rewardsList);

        // Action Buttons
        Button updatePointsButton = new Button("Update Points");
        updatePointsButton.setOnAction(e -> updatePoints(clientIdField.getText(), pointsField.getText()));

        Button redeemRewardButton = new Button("Redeem Reward");
        redeemRewardButton.setOnAction(e -> redeemReward(clientIdField.getText(), rewardsTable.getSelectionModel().getSelectedItem()));

        Button reverseRewardButton = new Button("Reverse Reward");
        reverseRewardButton.setOnAction(e -> reverseReward(clientIdField.getText(), rewardsTable.getSelectionModel().getSelectedItem()));

        HBox actionButtons = new HBox(10, updatePointsButton, redeemRewardButton, reverseRewardButton);
        actionButtons.setAlignment(Pos.CENTER);

        VBox detailsBox = new VBox(10, pointsLabel, pointsField, rewardsTable, actionButtons);
        detailsBox.setPadding(new Insets(20));

        VBox layout = new VBox(20, titleLabel, clientInputBox, detailsBox);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 600, 400);
    }

    private void loadLoyaltyProgram(String clientIdText) {
        try {
            Long clientId = Long.parseLong(clientIdText);
            LoyaltyProgram loyaltyProgram = loyaltyProgramService.getLoyaltyProgram(clientId);

            rewardsList.setAll(loyaltyProgram.getRewards());
            showAlert("Success", "Loyalty program loaded successfully.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Client ID must be a valid number.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updatePoints(String clientIdText, String pointsText) {
        try {
            Long clientId = Long.parseLong(clientIdText);
            int points = Integer.parseInt(pointsText);

            loyaltyProgramService.updatePoints(clientId, points);
            showAlert("Success", "Points updated successfully.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input for Client ID or Points.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void redeemReward(String clientIdText, String rewardId) {
        if (rewardId == null) {
            showAlert("Warning", "Please select a reward to redeem.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Long clientId = Long.parseLong(clientIdText);

            loyaltyProgramService.redeemReward(clientId, rewardId);
            rewardsList.remove(rewardId);
            showAlert("Success", "Reward redeemed successfully.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Client ID must be a valid number.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void reverseReward(String clientIdText, String rewardId) {
        if (rewardId == null) {
            showAlert("Warning", "Please select a reward to reverse.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Long clientId = Long.parseLong(clientIdText);

            loyaltyProgramService.reverseReward(clientId, rewardId);
            rewardsList.add(rewardId);
            showAlert("Success", "Reward reversed successfully.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Error", "Client ID must be a valid number.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
