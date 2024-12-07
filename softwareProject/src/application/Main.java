package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Labels et champs de texte
        Label usernameLabel = new Label("Nom d'utilisateur:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Entrez votre nom d'utilisateur");

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez votre mot de passe");

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // Action sur le bouton
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticate(username, password)) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            }
        });

        // Mise en page avec GridPane
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new javafx.geometry.Insets(20));  // Padding pour donner de l'espace autour
        grid.setStyle("-fx-background-color: #f4f4f4;");  // Fond de la fenêtre

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Centrer les éléments et ajuster la taille de la scène
        VBox vbox = new VBox(15);  // Espacement entre les éléments
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.getChildren().add(grid);

        Scene scene = new Scene(vbox, 350, 250);
        scene.setFill(Color.WHITE);  // Définir la couleur de fond

        primaryStage.setTitle("Connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean authenticate(String username, String password) {
        // Exemple de méthode d'authentification, remplacer par votre logique réelle
        UserDaoMySQL userDao = new UserDaoMySQL();
        return userDao.verifyLogin(username, password);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

