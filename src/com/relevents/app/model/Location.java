package com.relevents.app.model;

public class Location {

    private final Integer locationID;
    private final String locationName;
    private final String unit;
    private final Integer houseNum;
    private final String street;
    private final Integer cityID;

    public Location(Integer locationID, String locationName, String unit, Integer houseNum, String street, Integer cityID) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.unit = unit;
        this.houseNum = houseNum;
        this.street = street;
        this.cityID = cityID;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public String getStreet() {
        return street;
    }

    public Integer getCityID() {
        return cityID;
    }

}
