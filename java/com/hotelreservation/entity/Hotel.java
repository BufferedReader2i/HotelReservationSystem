package com.hotelreservation.entity;

public class Hotel {
    private int hotelID; // 主键, INT, 不可空, 自动增长
    private String name;  // VARCHAR, 不可空, 长度255
    private String address; // VARCHAR, 不可空, 长度255
    private String city;   // VARCHAR, 不可空, 长度100
    private String district; // VARCHAR, 可空, 长度100

    // 构造函数
    public Hotel() {
    }

    // 使用所有字段的构造函数
    public Hotel(int hotelID, String name, String address, String city, String district) {
        this.hotelID = hotelID;
        this.name = name;
        this.address = address;
        this.city = city;
        this.district = district;
    }

    // Getters 和 Setters
    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    // toString 方法，用于打印对象信息
    @Override
    public String toString() {
        return "Hotel{" +
                "hotelID=" + hotelID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}