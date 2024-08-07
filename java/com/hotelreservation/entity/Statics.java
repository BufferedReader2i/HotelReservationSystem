package com.hotelreservation.entity;

public class Statics {


    // 实例变量
    private  String name;
    private  int value;

    // 私有构造函数，确保外部不能创建实例
    public Statics(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Statics() {

    }

    // name属性的getter方法
    public String getName() {
        return name;
    }

    // value属性的getter方法
    public int getValue() {
        return value;
    }
    public void setName(String name) {
       this.name=name;
    }


    public void setValue(int value) {
        this.value=value;
    }

    // 可以添加其他方法，例如toString()等
    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}