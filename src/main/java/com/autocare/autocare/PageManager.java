package com.autocare.autocare;

import com.autocare.autocare.Pages.AdminPage;
import com.autocare.autocare.Pages.CustomerPage;
import com.autocare.user.Role;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PageManager {
    private final Role role;
    private Stage stage;

    public PageManager(Role role , Stage primaryStage) {
        this.role = role;
        this.stage = primaryStage;
    }

    public Scene getSceneForRole() {
        StackPane root = new StackPane();
        Label label;


        switch (role) {
            case ADMIN:
                AdminPage adminPage = new AdminPage();
                stage.setTitle("Admin Page");
                return adminPage.createAdminPage();


            case MANAGER:
                label = new Label("Welcome, Manager! You are redirected to the Manager Dashboard.");
                stage.setTitle("Manager Page");
                // You can replace this with an actual Manager Scene or layout
                break;

            case CLIENT:
                CustomerPage customerPage = new CustomerPage();
                stage.setTitle("Client Page");
                return customerPage.createCustomerPage();


            default:
                label = new Label("Unknown role! Cannot redirect.");
                break;
        }
        return new Scene(root, 500, 500);

    }
}
