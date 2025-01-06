package com.autocare.payment;


public class PaymentType {

    public long id;
    public String label;
    public double  fees;
    public boolean isAvailable;

    public PaymentType(long id, String label, double fees,
                       boolean isAvailable) {
        this.id = id;
        this.label = label;
        this.fees = fees;
        this.isAvailable = isAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}