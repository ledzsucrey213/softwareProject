package application;

import javafx.application.Application;
import javafx.stage.Stage;
import login.ui.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crée l'instance de LoginView
        LoginView loginView = new LoginView();

        // Crée la scène de connexion
        primaryStage.setScene(loginView.createLoginScene());

        // Définit le titre de la fenêtre et la montre
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
