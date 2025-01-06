package com.autocare.autocare;

import com.autocare.autocare.Pages.AdminPage;
import com.autocare.user.Role;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class PageManager {
    private final Role role;

    public PageManager(Role role) {
        this.role = role;
    }

    public Scene getSceneForRole() {
        StackPane root = new StackPane();
        Label label;

        switch (role) {
            case ADMIN:
                AdminPage adminPage = new AdminPage();
                return adminPage.createAdminPage();


            case MANAGER:
                label = new Label("Welcome, Manager! You are redirected to the Manager Dashboard.");
                // You can replace this with an actual Manager Scene or layout
                break;

            case CLIENT:
                label = new Label("Welcome, Client! You are redirected to the Client Dashboard.");
                // You can replace this with an actual Client Scene or layout
                break;

            default:
                label = new Label("Unknown role! Cannot redirect.");
                break;
        }
        return new Scene(root, 500, 500);

    }
}
