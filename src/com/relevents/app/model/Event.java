package com.relevents.app.model;

import java.sql.Timestamp;

/**
 * The intent for this class is to update/store information about a single event
 */
public class Event {

    private final Integer eventID;
    private final String eventName;
    private final Timestamp eventStart;
    private final Timestamp eventEnd;
    private final String website;
    private final String description;
    private final Integer governingID;

    public Integer getOrganizationID() {
        return organizationID;
    }

    private final Integer organizationID;

    public Event(Integer eventID, String eventName, Timestamp eventStart, Timestamp eventEnd, String website, String description, Integer governingID, Integer organizationID) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.website = website;
        this.description = description;
        this.governingID = governingID;
        this.organizationID = organizationID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public Timestamp getEventStart() {
        return eventStart;
    }

    public Timestamp getEventEnd() {
        return eventEnd;
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
