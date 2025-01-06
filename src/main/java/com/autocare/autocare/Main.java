package com.autocare.autocare;

import com.autocare.login.ui.LoginView;
import com.autocare.sql.SqlConnectionManager;
import com.autocare.subscription.ui.SubscriptionView;
import com.autocare.transaction.ui.ShopSystem;
import com.autocare.user.User;
import com.autocare.user.ui.ManageUsersView;
import com.autocare.vehicle.ui.VehicleView;
import javafx.application.Application;
import javafx.stage.Stage;
import com.autocare.user.Role;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException {
        User user;
        user = new User(4 , "omaritto", Role.ADMIN, "EL BAF", "Omar");
        // Crée l'instance de LoginView
        ShopSystem loginView = new ShopSystem(user);

        // Crée la scène de connexion
        primaryStage.setScene(loginView.createItemScene());

        // Définit le titre de la fenêtre et la montre
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        SqlConnectionManager.loadConnection();
        launch(args);
        SqlConnectionManager.closeConnection();
    }
}
