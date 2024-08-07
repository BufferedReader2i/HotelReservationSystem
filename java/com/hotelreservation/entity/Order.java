package com.hotelreservation.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Order {
    private int orderID;
    private String hotelName;
    private String roomTypeName;
    private String description;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private String status;
    private String comment;
    private java.sql.Timestamp orderDate;

    // Getters and Setters
    public int getorderID() {
        return orderID;
    }

    public void setorderID(int orderID) {
        this.orderID = orderID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    // toString method for printing order details
    @Override
    public String toString() {
        return "OrderDetails{" +
                "hotelName='" + hotelName + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", description='" + description + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}