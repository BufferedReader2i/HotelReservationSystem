package com.hotelreservation.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

public class Room {
    private StringProperty name;
    private StringProperty description;
    private DoubleProperty price;
    private int occupancy;


    public Room(String name, String description, double price,int occupancy) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.occupancy=occupancy;

    }

    // Name属性
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Description属性
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Price属性
    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }
    public int getOccupancy() {
        return occupancy;
    }

    public void setName(int occupancy) {
        this.occupancy=occupancy;
    }
}