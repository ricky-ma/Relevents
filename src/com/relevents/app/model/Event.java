package com.relevents.app.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single event
 */
public class Event {

    private final int eventID;
    private final String eventName;
    private final Date eventDate;
    private final Timestamp eventTime;
    private final String website;
    private final String description;
    private final int governingID;

    public Event(int eventID, String eventName, Date eventDate, Timestamp eventTime, String website, String description, int governingID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.website = website;
        this.description = description;
        this.governingID = governingID;
    }

    public int getEventID() {
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

    public int getGoverningID() {
        return governingID;
    }

}
