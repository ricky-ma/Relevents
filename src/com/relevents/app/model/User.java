package com.relevents.app.model;

import java.sql.Date;
import java.util.ArrayList;

public class User {

    private final Integer userID;
    private final String fname;
    private final String lname;
    private final Date birthdate;
    private final String phone;
    private final String email;
    private final Integer cityID;

    public ArrayList<Organization> following;
    public ArrayList<Event> managing;
    public ArrayList<String> topicPreferences;
    public ArrayList<Event> attending;

    public User(Integer userID, String fname, String lname, Date birthdate, String phone, String email, Integer cityID) {
        this.userID = userID;
        this.fname = fname;
        this.lname = lname;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.cityID = cityID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCityID() {
        return cityID;
    }
}
