package application;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label usernameLabel = new Label("Nom d'utilisateur:");
        usernameLabel.setFont(Font.font("Helvetica Neue", 18));
        usernameLabel.setTextFill(Color.web("#BDBDBD"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Entrez votre nom d'utilisateur");
        usernameField.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-border-radius: 25; -fx-border-color: #757575;");
        usernameField.setPrefHeight(40);
        usernameField.setEffect(new DropShadow(8, Color.BLACK));

        Label passwordLabel = new Label("Mot de passe:");
        passwordLabel.setFont(Font.font("Helvetica Neue", 18));
        passwordLabel.setTextFill(Color.web("#BDBDBD"));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez votre mot de passe");
        passwordField.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-border-radius: 25; -fx-border-color: #757575;");
        passwordField.setPrefHeight(40);
        passwordField.setEffect(new DropShadow(8, Color.BLACK));

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 25;");
        loginButton.setPrefHeight(45);
        loginButton.setMaxWidth(Double.MAX_VALUE);  
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #FFCC00; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25;"));

        
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticate(username, password)) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            }
        });

        
        VBox vbox = new VBox(20);  
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: linear-gradient(to top, #212121, #616161);");
        vbox.setPadding(new javafx.geometry.Insets(50, 30, 50, 30));

       
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        grid.setStyle("-fx-background-color: transparent;");

        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40); 
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(60); 
        grid.getColumnConstraints().addAll(column1, column2);

       
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        
        vbox.getChildren().add(grid);

        
        Scene scene = new Scene(vbox, 600, 450);  
        scene.setFill(Color.WHITE);

        
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(e -> primaryStage.setOpacity(1));
        primaryStage.setOpacity(0);
        pause.play();

        primaryStage.setTitle("Connexion");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);  
        primaryStage.show();

        
        usernameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                usernameField.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-border-radius: 25; -fx-border-color: #FFD700;");
            } else {
                usernameField.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-border-radius: 25; -fx-border-color: #757575;");
            }
        });

        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                passwordField.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-border-radius: 25; -fx-border-color: #FFD700;");
            } else {
                passwordField.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-border-radius: 25; -fx-border-color: #757575;");
            }
        });
    }

    private boolean authenticate(String username, String password) {
        
        UserDaoMySQL userDao = new UserDaoMySQL();
        return userDao.verifyLogin(username, password);
    }

    public static void main(String[] args) {
        launch(args);
    }
}



