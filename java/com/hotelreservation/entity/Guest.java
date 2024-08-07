package com.hotelreservation.entity;

public class Guest {
    private String name;           // VARCHAR, 不可空, 长度100
    private String idNumber;       // VARCHAR, 不可空, 长度18
    private Integer age;           // INT, 可空
    private String gender;         // VARCHAR, 可空, 长度10
    private String occupation;     // VARCHAR, 可空, 长度100
    private String educationLevel; // VARCHAR, 可空, 长度100
    private String incomeStatus;   // VARCHAR, 可空, 长度100

    // 构造函数
    public Guest(String name, String idNumber, Integer age, String gender, String occupation, String educationLevel, String incomeStatus) {
        this.name = name;
        this.idNumber = idNumber;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.educationLevel = educationLevel;
        this.incomeStatus = incomeStatus;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getIncomeStatus() {
        return incomeStatus;
    }

    public void setIncomeStatus(String incomeStatus) {
        this.incomeStatus = incomeStatus;
    }

    // toString 方法，用于打印对象信息
    @Override
    public String toString() {
        return "Guest{" +
                "name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                ", incomeStatus='" + incomeStatus + '\'' +
                '}';
    }
}