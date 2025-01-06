package com.autocare.vehicle;

import java.time.Year;
import java.util.Optional;

public class Vehicle {
    private Optional<Long>    id;
    private Year        year;
    private String      color;
    private VehicleType type;
    private FuelType    fuelType;
    private long        engineSize;
    private Brand       brand;
    private String      model;

    public Vehicle(long id, Year year, String color, VehicleType type,
                   FuelType fuelType, long engineSize, Brand brand,
                   String model) {
        this.id = Optional.of(id);
        this.year = year;
        this.color = color;
        this.type = type;
        this.fuelType = fuelType;
        this.engineSize = engineSize;
        this.brand = brand;
        this.model = model;
    }

    public Vehicle(Year year, String color, VehicleType type,
                   FuelType fuelType, long engineSize, Brand brand,
                   String model) {
        this.id = Optional.empty();
        this.year = year;
        this.color = color;
        this.type = type;
        this.fuelType = fuelType;
        this.engineSize = engineSize;
        this.brand = brand;
        this.model = model;
    }


    public Optional<Long> getId() {
        return id;
    }

    public void setId(long id) {
        this.id = Optional.of(id);
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public long getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(long engineSize) {
        this.engineSize = engineSize;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}