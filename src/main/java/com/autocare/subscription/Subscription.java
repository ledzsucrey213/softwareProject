package com.autocare.subscription;

import java.util.Optional;

public class Subscription {

    private Optional<Long> id;
    private String         label;
    private       SubscriptionType type;
    private       Boolean          is_active;
    private       Double           amount;
    private       String           description;


    public Subscription(long id, SubscriptionType type, String label,
                        boolean isActive, Double amount, String description) {
        this.id = Optional.of(id);
        this.type = type;
        this.is_active = isActive;
        this.amount = amount;
        this.description = description;
        this.label = label;
    }

    public Subscription(SubscriptionType type, String label, boolean isActive
            , Double amount, String description) {
        this.id = Optional.empty();
        this.type = type;
        this.is_active = isActive;
        this.amount = amount;
        this.description = description;
        this.label = label;
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(long id) {
        this.id = Optional.of(id);
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String text) {
        this.label = text;
    }

    // Getter for Amount
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return is_active;
    }

    public void setIsActive(boolean isActive) {
        this.is_active = isActive;
    }
}