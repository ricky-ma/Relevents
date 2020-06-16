package com.relevents.app.model;

import java.sql.Date;

public class User {

    private final String email;
    private final String fname;
    private final String lname;
    private final Date birthdate;
    private final String phone;
    private final Integer cityID;

    public User(String email, String fname, String lname, Date birthdate, String phone, Integer cityID) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.birthdate = birthdate;
        this.phone = phone;
        this.cityID = cityID;
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
