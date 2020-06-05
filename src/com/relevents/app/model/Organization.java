package com.relevents.app.model;

import java.util.ArrayList;

public class Organization {

    private final Integer organizationID;
    private final String orgName;
    private final String description;
    private final String email;
    private final String website;

    public ArrayList<Event> hosting;

    public Organization(Integer organizationID, String orgName, String description, String email, String website) {
        this.organizationID = organizationID;
        this.orgName = orgName;
        this.description = description;
        this.email = email;
        this.website = website;
    }

    public Integer getOrganizationID() {
        return organizationID;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }


}
