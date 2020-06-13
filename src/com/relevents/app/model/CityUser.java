package com.relevents.app.model;

public class CityUser {
    private final String cityName;
    private final int numUsers;

    public CityUser(String cityName,int numUsers){
        this.cityName = cityName;
        this.numUsers = numUsers;
    }

    public String getCityName() {
        return cityName;
    }

    public int getNumUsers() {
        return numUsers;
    }
}
