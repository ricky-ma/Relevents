package com.relevents.app.model;

import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * The intent for this class is to update/store information about a single event
 */
public class Event {

    private final Integer eventID;
    private final String eventName;
    private final Date eventDate;
    private final Timestamp eventTime;
    private final String website;
    private final String description;
    private final Integer governingID;

    public ArrayList<Triplet<Integer, String, String>> FAQs;
    public ArrayList<Quartet<Integer, String, String, String>> foods;
    public Quartet<Integer, String, String, String> governingBody;
    public ArrayList<User> attendees;
    public ArrayList<String> topics;
    public Location location;

    public Event(Integer eventID, String eventName, Date eventDate, Timestamp eventTime, String website, String description, Integer governingID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.website = website;
        this.description = description;
        this.governingID = governingID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public Timestamp getEventTime() {
        return eventTime;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public Integer getGoverningID() {
        return governingID;
    }



}
