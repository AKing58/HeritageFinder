package com.example.heritagefinder;

import java.io.Serializable;

public class Building implements Serializable {
    private String name;
    private double latitude;
    private double longitude;
    private int age;
    private String desc;
    public Building(String name, double latitude, double longitude, int age, String desc){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.age = age;
        this.desc = desc;
    }
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public double getLatitude(){return latitude;}
    public void setLatitude(double latitude){this.latitude = latitude;}
    public double getLongitude(){return longitude;}
    public void setLongitude(double longitude){this.longitude = longitude;}
    public int getAge(){return age;}
    public void setAge(int age){this.age = age;}
    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc = desc;}
}
