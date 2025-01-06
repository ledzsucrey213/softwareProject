package com.autocare.vehicle.ui;

import com.autocare.vehicle.Brand;
import com.autocare.vehicle.FuelType;
import com.autocare.vehicle.Vehicle;
import com.autocare.vehicle.VehicleType;
import com.autocare.vehicle.factory.BrandDAOMySQLFactory;
import com.autocare.vehicle.factory.VehicleDAOMySQLFactory;
import com.autocare.vehicle.service.VehicleService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

public class VehicleView {

    private final VehicleService          vehicleService;
    private       TableView<Vehicle>      vehicleTable;
    private       ObservableList<Vehicle> vehicles;

    public VehicleView() {
        vehicleService = new VehicleService(new VehicleDAOMySQLFactory(),
                                            new BrandDAOMySQLFactory()
        );

    }

    public Scene createVehicleScene() {
        // Initialize the vehicles list and table
        vehicles = FXCollections.observableArrayList();
        vehicleTable = new TableView<>();

        // Define columns
        TableColumn<Vehicle, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getId().isPresent()) {
                return new SimpleLongProperty(cellData.getValue()
                                                      .getId()
                                                      .get()).asObject();
            }
            return null;
        });

        TableColumn<Vehicle, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                cellData.getValue().getYear().getValue()).asObject());

        TableColumn<Vehicle, String> colorColumn = new TableColumn<>("Color");
        colorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getColor()));

        TableColumn<Vehicle, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getType().toString()));

        TableColumn<Vehicle, String> fuelTypeColumn = new TableColumn<>(
                "Fuel Type");
        fuelTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFuelType().toString()));

        TableColumn<Vehicle, Long> engineSizeColumn = new TableColumn<>(
                "Engine Size");
        engineSizeColumn.setCellValueFactory(cellData -> new SimpleLongProperty(
                cellData.getValue().getEngineSize()).asObject());

        TableColumn<Vehicle, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue()
                        .getBrand()
                        .getName()));  // Access the brand's name

        TableColumn<Vehicle, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getModel()));


        // Add columns to the table
        vehicleTable.getColumns().addAll(idColumn,
                                         yearColumn,
                                         colorColumn,
                                         typeColumn,
                                         fuelTypeColumn,
                                         engineSizeColumn,
                                         brandColumn,
                                         modelColumn
        );

        vehicleTable.setItems(vehicles);

        // Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showVehicleForm("Add Vehicle"));

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            Vehicle selectedVehicle = vehicleTable.getSelectionModel()
                                                  .getSelectedItem();
            showEditingForm(selectedVehicle);
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            try {
                removeSelectedVehicle();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox buttonBox = new HBox(10, addButton, editButton, removeButton);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(vehicleTable);
        layout.setBottom(buttonBox);
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, "
                + "#2c3e50, #34495e);");

        loadVehicles();

        // Return the scene
        return new Scene(layout, 800, 600);
    }

    private void showVehicleForm(String title) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        TextField colorField = new TextField();
        colorField.setPromptText("Color");

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Sedan",
                                                                "Hatchback",
                                                                "Coupe",
                                                                "Convertible",
                                                                "SUV",
                                                                "Crossover",
                                                                "Minivan",
                                                                "PickupTruck",
                                                                "BoxTruck",
                                                                "FlatbedTruck",
                                                                "DumpTruck",
                                                                "TowTruck",
                                                                "SemiTruck",
                                                                "CargoVan",
                                                                "PassengerVan",
                                                                "SprinterVan",
                                                                "MiniVan",
                                                                "CityBus",
                                                                "CoachBus",
                                                                "SchoolBus",
                                                                "Bulldozer",
                                                                "Excavator",
                                                                "CementMixerTruck",
                                                                "FireTruck",
                                                                "Ambulance",
                                                                "PoliceCar",
                                                                "Motorhome",
                                                                "CamperVan",
                                                                "FifthWheelTrailer",
                                                                "ATV",
                                                                "UTV",
                                                                "Motorcycle",
                                                                "Scooter",
                                                                "Bicycle",
                                                                "Tractor"
        ));
        typeComboBox.setPromptText("Type");

        ComboBox<String> fuelTypeComboBox = new ComboBox<>();
        fuelTypeComboBox.setItems(FXCollections.observableArrayList("DIESEL",
                                                                    "GASOLINE",
                                                                    "ELECTRIC",
                                                                    "HYBRID",
                                                                    "HYDROGEN",
                                                                    "ETHANOL",
                                                                    "GAS"
        ));
        fuelTypeComboBox.setPromptText("Fuel Type");

        TextField engineSizeField = new TextField();
        engineSizeField.setPromptText("Engine Size");

        ComboBox<Brand> brandComboBox = new ComboBox<>();

        try {
            // Fetching brands from the database
            List<Brand>
                    brands
                    = vehicleService.getAllBrands();  // Assuming this
            // returns a List<Brand>

            // Set the items of the ComboBox to the Brand objects
            brandComboBox.setItems(FXCollections.observableArrayList(brands));

            // Set the prompt text
            brandComboBox.setPromptText("Select Brand");

            // Use a custom cell factory to display only the brand name
            brandComboBox.setCellFactory(param -> new ListCell<Brand>() {
                @Override
                protected void updateItem(Brand brand, boolean empty) {
                    super.updateItem(brand, empty);
                    if (empty || brand == null) {
                        setText(null);
                    }
                    else {
                        setText(brand.getName());  // Display the brand name
                        // in the list
                    }
                }
            });

            // Display the selected brand name in the ComboBox (when an item
            // is selected)
            brandComboBox.setButtonCell(new ListCell<Brand>() {
                @Override
                protected void updateItem(Brand brand, boolean empty) {
                    super.updateItem(brand, empty);
                    if (empty || brand == null) {
                        setText(null);
                    }
                    else {
                        setText(brand.getName());  // Display the brand name
                        // on the button
                    }
                }
            });

        } catch (SQLException ex) {
            showAlert("ERROR", "Could not load brands from database.");
        }


        TextField modelField = new TextField();
        modelField.setPromptText("Model");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                // Convert year (String) to Year type
                int yearInt = Integer.parseInt(yearField.getText());
                Year year = Year.of(yearInt);  // Convert to Year

                // Get color (String)
                String color = colorField.getText();

                // Convert type (String) to VehicleType enum
                VehicleType type = VehicleType.valueOf(typeComboBox.getValue()
                                                                   .toUpperCase());  // Convert to VehicleType enum

                // Convert fuelType (String) to FuelType enum
                FuelType fuelType = FuelType.valueOf(fuelTypeComboBox.getValue()
                                                                     .toUpperCase());  // Convert to FuelType enum

                // Convert engineSize (String) to long
                long
                        engineSize
                        = Long.parseLong(engineSizeField.getText());  //
                // Convert engine size to long

                // Get the selected brand (String) and fetch the
                // corresponding Brand object
                Brand
                        selectedBrand
                        = brandComboBox.getValue();  // Fetch the Brand
                // object from database

                // Validate brand selection
                if (selectedBrand == null) {
                    showAlert("Invalid Input", "Please select a valid brand.");
                    return;
                }

                String model = modelField.getText();

                // Create the new vehicle and add it
                Vehicle newVehicle = new Vehicle(year,
                                                 color,
                                                 type,
                                                 fuelType,
                                                 engineSize,
                                                 selectedBrand,
                                                 model
                );

                vehicleService.addVehicle(newVehicle);

                // Close the popup
                loadVehicles();
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid input values.");
            } catch (SQLException ex) {
                showAlert("ERROR", "Could not add vehicle.");
            } catch (IllegalArgumentException ex) {
                showAlert("Invalid Input",
                          "Invalid selection for type, fuel type, or brand."
                );
            }
        });

        // Add all the form elements to the VBox
        form.getChildren().addAll(new Label("Year:"),
                                  yearField,
                                  new Label("Color:"),
                                  colorField,
                                  new Label("Type:"),
                                  typeComboBox,
                                  new Label("Fuel Type:"),
                                  fuelTypeComboBox,
                                  new Label("Engine Size:"),
                                  engineSizeField,
                                  new Label("Brand:"),
                                  brandComboBox,
                                  new Label("Model:"),
                                  modelField,
                                  submitButton
        );

        // Set up the scene and show the popup
        Scene scene = new Scene(form, 400, 500);
        popup.setScene(scene);
        popup.showAndWait();
    }


    private void removeSelectedVehicle() throws SQLException {
        Vehicle selected = vehicleTable.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getId().isPresent()) {
            vehicleService.deleteVehicle(selected.getId().get());
            vehicles.remove(selected);
        }
    }

    private void showEditingForm(Vehicle vehicleToEdit) {
        if (vehicleToEdit.getId().isEmpty()) {
            throw new IllegalArgumentException("Vehicle id cannot be empty");
        }

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Edit Vehicle");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        // Pre-fill the year field
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        yearField.setText(String.valueOf(vehicleToEdit.getYear()
                                                      .getValue()));  //
        // Pre-fill with the vehicle's year

        // Pre-fill the color field
        TextField colorField = new TextField();
        colorField.setPromptText("Color");
        colorField.setText(vehicleToEdit.getColor());  // Pre-fill with the
        // vehicle's color

        // Pre-fill the type combo box
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList("Sedan",
                                                                "Hatchback",
                                                                "Coupe",
                                                                "Convertible",
                                                                "SUV",
                                                                "Crossover",
                                                                "Minivan",
                                                                "PickupTruck",
                                                                "BoxTruck",
                                                                "FlatbedTruck",
                                                                "DumpTruck",
                                                                "TowTruck",
                                                                "SemiTruck",
                                                                "CargoVan",
                                                                "PassengerVan",
                                                                "SprinterVan",
                                                                "MiniVan",
                                                                "CityBus",
                                                                "CoachBus",
                                                                "SchoolBus",
                                                                "Bulldozer",
                                                                "Excavator",
                                                                "CementMixerTruck",
                                                                "FireTruck",
                                                                "Ambulance",
                                                                "PoliceCar",
                                                                "Motorhome",
                                                                "CamperVan",
                                                                "FifthWheelTrailer",
                                                                "ATV",
                                                                "UTV",
                                                                "Motorcycle",
                                                                "Scooter",
                                                                "Bicycle",
                                                                "Tractor"
        ));
        typeComboBox.setPromptText("Type");
        typeComboBox.setValue(vehicleToEdit.getType()
                                           .name());  // Pre-fill with the
        // vehicle's type

        // Pre-fill the fuel type combo box
        ComboBox<String> fuelTypeComboBox = new ComboBox<>();
        fuelTypeComboBox.setItems(FXCollections.observableArrayList("DIESEL",
                                                                    "GASOLINE",
                                                                    "ELECTRIC",
                                                                    "HYBRID",
                                                                    "HYDROGEN",
                                                                    "ETHANOL",
                                                                    "GAS"
        ));
        fuelTypeComboBox.setPromptText("Fuel Type");
        fuelTypeComboBox.setValue(vehicleToEdit.getFuelType()
                                               .name());  // Pre-fill with
        // the vehicle's fuel type

        // Pre-fill the engine size field
        TextField engineSizeField = new TextField();
        engineSizeField.setPromptText("Engine Size");
        engineSizeField.setText(String.valueOf(vehicleToEdit.getEngineSize()));  // Pre-fill with the vehicle's engine size

        // Pre-fill the brand combo box
        ComboBox<Brand> brandComboBox = new ComboBox<>();
        try {
            // Fetching brands from the database
            List<Brand>
                    brands
                    = vehicleService.getAllBrands();  // Assuming this
            // returns a List<Brand>

            // Set the items of the ComboBox to the Brand objects
            brandComboBox.setItems(FXCollections.observableArrayList(brands));

            // Set the prompt text
            brandComboBox.setPromptText("Select Brand");

            // Use a custom cell factory to display only the brand name in
            // the dropdown list
            brandComboBox.setCellFactory(param -> new ListCell<Brand>() {
                @Override
                protected void updateItem(Brand brand, boolean empty) {
                    super.updateItem(brand, empty);
                    if (empty || brand == null) {
                        setText(null);
                    }
                    else {
                        setText(brand.getName());  // Display the brand name
                        // in the list
                    }
                }
            });

            // Display the selected brand name in the ComboBox (when an item
            // is selected)
            brandComboBox.setButtonCell(new ListCell<Brand>() {
                @Override
                protected void updateItem(Brand brand, boolean empty) {
                    super.updateItem(brand, empty);
                    if (empty || brand == null) {
                        setText(null);
                    }
                    else {
                        setText(brand.getName());  // Display the brand name
                        // on the button
                    }
                }
            });

            // Pre-select the brand based on the vehicle's brand, and display
            // the brand name
            Brand selectedBrand = vehicleToEdit.getBrand();
            brandComboBox.setValue(selectedBrand);  // Set the value of the
            // combo box to the selected brand
            brandComboBox.setPromptText(selectedBrand.getName());  // Show
            // the name of the brand as the prompt text

        } catch (SQLException ex) {
            showAlert("ERROR", "Could not load brands from database.");
        }


        // Pre-fill the model field
        TextField modelField = new TextField();
        modelField.setPromptText("Model");
        modelField.setText(vehicleToEdit.getModel());  // Pre-fill with the
        // vehicle's model

        // Submit button for editing the vehicle
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                // Convert year (String) to Year type
                int yearInt = Integer.parseInt(yearField.getText());
                Year year = Year.of(yearInt);  // Convert to Year

                // Get color (String)
                String color = colorField.getText();

                // Convert type (String) to VehicleType enum
                VehicleType type = VehicleType.valueOf(typeComboBox.getValue()
                                                                   .toUpperCase());  // Convert to VehicleType enum

                // Convert fuelType (String) to FuelType enum
                FuelType fuelType = FuelType.valueOf(fuelTypeComboBox.getValue()
                                                                     .toUpperCase());  // Convert to FuelType enum

                // Convert engineSize (String) to long
                long
                        engineSize
                        = Long.parseLong(engineSizeField.getText());  //
                // Convert engine size to long

                // Get the selected brand (Brand object)
                Brand
                        selectedBrand
                        = brandComboBox.getValue();  // Fetch the Brand
                // object from the ComboBox

                // Validate brand selection
                if (selectedBrand == null) {
                    showAlert("Invalid Input", "Please select a valid brand.");
                    return;
                }

                String model = modelField.getText();


                // Create the updated vehicle object and edit it
                Vehicle updatedVehicle = new Vehicle(vehicleToEdit.getId()
                                                                  .get(),
                                                     year,
                                                     color,
                                                     type,
                                                     fuelType,
                                                     engineSize,
                                                     selectedBrand,
                                                     model
                );

                vehicleService.updateVehicle(updatedVehicle);  // Assuming
                // `updateVehicle` method exists

                // Close the popup
                loadVehicles();  // Reload the list of vehicles (optional)
                popup.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid input values.");
            } catch (SQLException ex) {
                showAlert("ERROR", "Could not update vehicle.");
            } catch (IllegalArgumentException ex) {
                showAlert("Invalid Input",
                          "Invalid selection for type, fuel type, or brand."
                );
            }
        });

        // Add all the form elements to the VBox
        form.getChildren().addAll(new Label("Year:"),
                                  yearField,
                                  new Label("Color:"),
                                  colorField,
                                  new Label("Type:"),
                                  typeComboBox,
                                  new Label("Fuel Type:"),
                                  fuelTypeComboBox,
                                  new Label("Engine Size:"),
                                  engineSizeField,
                                  new Label("Brand:"),
                                  brandComboBox,
                                  new Label("Model:"),
                                  modelField,
                                  submitButton
        );

        // Set up the scene and show the popup
        Scene scene = new Scene(form, 400, 500);
        popup.setScene(scene);
        popup.showAndWait();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadVehicles() {
        try {
            vehicleTable.getItems().clear();
            vehicles.setAll(vehicleService.getAllVehicles());
        } catch (SQLException ex) {
            showAlert("ERROR", "Could not load vehicles.");
        }
    }
}
