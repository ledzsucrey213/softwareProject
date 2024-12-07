package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label usernameLabel = new Label("Nom d'utilisateur:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Se connecter");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticate(username, password)) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            }
        });

        // Mise en page
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        Scene scene = new Scene(grid, 300, 200);

        primaryStage.setTitle("Connexion");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean authenticate(String username, String password) {
        // TODO: Appeler votre méthode MongoDB ici pour vérifier les identifiants
        return "admin".equals(username) && "password".equals(password); // Exemple
    }

    public static void main(String[] args) {
        launch(args);
    }
}
