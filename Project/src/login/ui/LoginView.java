package login.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.bl.LoginService;


public class LoginView {

    public Scene createLoginScene() {
        // Créer le titre
        Text title = new Text("AutoCare Login");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        title.setFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.7, 0, 3);");

        // Champs de texte stylisés
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #f1c40f; -fx-padding: 15; -fx-font-size: 16; -fx-text-fill: white; -fx-background-color: rgba(255, 255, 255, 0.2);");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #f1c40f; -fx-padding: 15; -fx-font-size: 16; -fx-text-fill: white; -fx-background-color: rgba(255, 255, 255, 0.2);");

        // Bouton stylisé avec effet de survol
        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: white; -fx-background-radius: 25; -fx-padding: 12 30; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.7, 0, 3);");
        
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: black; -fx-background-radius: 25; -fx-padding: 12 30; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 5, 0.7, 0, 5);"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: white; -fx-background-radius: 25; -fx-padding: 12 30; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0.7, 0, 3);"));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            checkAuthentication(username, password);
        });
        
        // Mise en page
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #34495e);");

        layout.getChildren().addAll(title, usernameField, passwordField, loginButton);

        return new Scene(layout, 450, 350);
    }
    private void checkAuthentication(String username, String password) {
        try {
        	LoginService login=new LoginService();
            int userId = Integer.parseInt(username); // Convertit l'ID utilisateur en entier
            boolean isAuthenticated = login.authenticate(userId, password);

            if (isAuthenticated) {
                System.out.println("correct");
            } else {
                System.out.println("incorrect");
            }
        } catch (NumberFormatException e) {
            System.out.println("incorrect: Username must be a valid number.");
        }
    }
}


