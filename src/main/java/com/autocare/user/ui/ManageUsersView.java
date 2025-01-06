package com.autocare.user.ui;

import com.autocare.user.Role;
import com.autocare.user.User;
import com.autocare.user.factory.UserDAOMySQLFactory;
import com.autocare.user.service.UserService;
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

import java.sql.SQLException;

public class ManageUsersView {

    private final UserService userService;
    private TableView<User> userTable;
    private ObservableList<User> users;

    public ManageUsersView() {
        userService = new UserService(new UserDAOMySQLFactory());
    }

    public Scene createUserScene() {
        // Initialize the users list and table
        users = FXCollections.observableArrayList();
        userTable = new TableView<>();

        // Define columns
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Add columns to the table
        userTable.getColumns().addAll(nameColumn, surnameColumn, usernameColumn, roleColumn);
        userTable.setItems(users);

        // Buttons for adding, editing, and removing users
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showAddUserForm());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            showEditUserForm(selectedUser);
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeSelectedUser();
            } catch (SQLException ex) {
                showAlert("ERROR", "Could not remove the user.");
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, removeButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(userTable);
        layout.setBottom(buttonBox);

        loadUsers();

        // Return the scene
        return new Scene(layout, 800, 400);
    }

    private void showAddUserForm() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add User");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.setItems(FXCollections.observableArrayList(Role.ADMIN, Role.MANAGER, Role.CLIENT));
        roleComboBox.setValue(Role.CLIENT);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Submit button
        Button submitButton = new Button("Add User");
        submitButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String username = usernameField.getText();
                Role role = roleComboBox.getValue();
                String password = passwordField.getText();

                userService.addUser(name, surname, username, role, password);
                loadUsers();
                popup.close();
            } catch (SQLException ex) {
                showAlert("Error", "Could not save user.");
            }
        });

        // Add elements to the form
        form.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Surname:"), surnameField,
                new Label("Username:"), usernameField,
                new Label("Role:"), roleComboBox,
                new Label("Password:"), passwordField,
                submitButton
        );

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private void showEditUserForm(User userToEdit) {
        if (userToEdit == null) {
            showAlert("No Selection", "Please select a user to edit.");
            return;
        }

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Edit User");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        TextField nameField = new TextField(userToEdit.getName());
        nameField.setPromptText("Name");

        TextField surnameField = new TextField(userToEdit.getSurname());
        surnameField.setPromptText("Surname");

        TextField usernameField = new TextField(userToEdit.getUsername());
        usernameField.setPromptText("Username");

        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.setItems(FXCollections.observableArrayList(Role.ADMIN, Role.MANAGER, Role.CLIENT));
        roleComboBox.setValue(userToEdit.getRole());

        // No password field for editing users
        Label noPasswordLabel = new Label("Password cannot be changed.");

        // Submit button for saving changes
        Button submitButton = new Button("Save Changes");
        submitButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String username = usernameField.getText();
                Role role = roleComboBox.getValue();

                userToEdit.setName(name);
                userToEdit.setSurname(surname);
                userToEdit.setUsername(username);
                userToEdit.setRole(role);

                userService.updateUser(userToEdit);
                loadUsers();
                popup.close();
            } catch (SQLException ex) {
                showAlert("Error", "Could not update user.");
            }
        });

        // Add elements to the form
        form.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Surname:"), surnameField,
                new Label("Username:"), usernameField,
                new Label("Role:"), roleComboBox,
                noPasswordLabel,
                submitButton
        );

        // Set up the scene and display the popup
        Scene scene = new Scene(form, 300, 400);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private void removeSelectedUser() throws SQLException {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userService.deleteUser(selectedUser.getId());
            users.remove(selectedUser);
        } else {
            showAlert("No Selection", "Please select a user to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadUsers() {
        try {
            userTable.getItems().clear();
            var users = userService.getAllUsers();
            userTable.getItems().addAll(users);
        } catch (SQLException ex) {
            showAlert("ERROR", "Could not load users.");
        }
    }
}
