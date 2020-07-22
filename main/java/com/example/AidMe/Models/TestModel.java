package com.example.AidMe.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Expose
    @SerializedName("id")
    private int id;

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    @Expose
    @SerializedName("what")
    private int what;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("age")
    private String age;


    @Expose
    @SerializedName("number")
    private String number;


    public String getNeedes() {
        return needes;
    }

    public void setNeedes(String needes) {
        this.needes = needes;
    }

    @Expose
    @SerializedName("needes")
    private String needes;



    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("gives")
    private String gives;


    @Expose
    @SerializedName("pass")
    private String pass;
    @Expose
    @SerializedName("lon")
    private String lon;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGives() {
        return gives;
    }

    public void setGives(String gives) {
        this.gives = gives;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Expose
    @SerializedName("lat")
    private String lat;







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
