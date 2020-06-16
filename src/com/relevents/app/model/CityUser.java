package com.relevents.app.model;

public class CityUser {
    private final String cityName;
    private final Integer numUsers;

    public CityUser(String cityName,Integer numUsers){
        this.cityName = cityName;
        this.numUsers = numUsers;
    }

    public String getCityName() {
        return cityName;
    }

    public Integer getNumUsers() {
        return numUsers;
    }
}
