package com.autocare.transaction;

import java.util.Optional;

public class Item {
    private Optional<Long> id;
    private String         label;
    private String         description;
    private double         price;

    public Item(long id, String label, String description, double price) {
        this.id = Optional.of(id);
        this.label = label;
        this.description = description;
        this.price = price;
    }

    public Item(String label, String description, double price) {
        this.id = Optional.empty();
        this.label = label;
        this.description = description;
        this.price = price;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Item item = (Item) o;

        return id == item.id;
    }

    public Optional<Long> getId() {return id;}

    public void setId(long id) {
        this.id = Optional.of(id);
    }

    public String getLabel() {return label;}

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {return price;}

    public void setPrice(double price) {
        this.price = price;
    }
}