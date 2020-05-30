CREATE TABLE CITY (
    cityID integer PRIMARY KEY,
    cityName varchar(20),
    stateProvince varchar(20),
    country varchar(20),
    postalCode varchar(20)
);

CREATE TABLE LOCATION (
    locationID integer PRIMARY KEY,
    locationName varchar(20),
    unit varchar(20),
    houseNum integer,
    cityID integer NOT NULL,
    FOREIGN KEY (cityID) REFERENCES CITY
);

CREATE TABLE APPUSER (
    userID integer PRIMARY KEY,
    fname varchar(20),
    lname varchar(20),
    birthdate varchar(20),
    phone varchar(20),
    email varchar(20),
    cityID integer,
    FOREIGN KEY (cityID) REFERENCES CITY
);

CREATE TABLE ATTENDEE (
    userID integer,
    attendeeID integer,
    FOREIGN KEY (userID) REFERENCES APPUSER,
    PRIMARY KEY (userID, attendeeID)
);

CREATE TABLE ORGANISEE (
    userID integer,
    organiseeID integer,
    FOREIGN KEY (userID) REFERENCES APPUSER,
    PRIMARY KEY (userID, organiseeID)
);

CREATE TABLE ORGANIZATION (
    organizationID integer PRIMARY KEY,
    orgName varchar(20),
    description varchar(4000),
    email varchar(20),
    website varchar(50)
);

CREATE TABLE EVENT (
    eventID integer PRIMARY KEY,
    eventName varchar(50),
    eventDate date,
    eventTime timestamp,
    website varchar(50),
    description varchar(4000),
    governingID integer,
    FOREIGN KEY (governingID) REFERENCES GOVERNINGBODY
);

CREATE TABLE EVENTFAQ (
    FAQID integer PRIMARY KEY,
    question varchar(200) NOT NULL,
    answer varchar(200) NOT NULL,
    eventID integer NOT NULL,
    FOREIGN KEY (eventID) REFERENCES EVENT
);

CREATE TABLE EVENTFOOD (
    foodID integer PRIMARY KEY,
    foodName varchar(20),
    cuisine varchar(20),
    caterer varchar(20),
    eventID integer NOT NULL,
    FOREIGN KEY (eventID) REFERENCES EVENT
);

CREATE TABLE GOVERNINGBODY (
    governingID integer PRIMARY KEY,
    governingName varchar(20),
    email varchar(20),
    phone varchar(20)
);

CREATE TABLE TOPIC (
    topicName varchar(20) PRIMARY KEY
);

CREATE TABLE DESCRIBES (
    topicName varchar(20),
    eventID integer,
    FOREIGN KEY (topicName) REFERENCES TOPIC,
    FOREIGN KEY (eventID) REFERENCES EVENT,
    PRIMARY KEY (topicName, eventID)
);

CREATE TABLE PREFERS (
    attendeeID integer,
    topicName varchar(20),
    FOREIGN KEY (attendeeID) REFERENCES ATTENDEE,
    FOREIGN KEY (topicName) REFERENCES TOPIC,
    PRIMARY KEY (attendeeID, topicName)
);

CREATE TABLE FOLLOWS (
    attendeeID integer,
    organizationID varchar(20),
    FOREIGN KEY (attendeeID) REFERENCES ATTENDEE,
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION,
    PRIMARY KEY (attendeeID, organizationID)
);

CREATE TABLE MANAGES (
    organiseeID integer,
    organizationID integer,
    eventID integer,
    FOREIGN KEY (organiseeID) REFERENCES ORGANISEE,
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION,
    PRIMARY KEY (organiseeID, organizationID)
);

CREATE TABLE ATTENDS (
    attendeeID integer,
    eventID integer,
    FOREIGN KEY (attendeeID) REFERENCES ATTENDEE,
    FOREIGN KEY (eventID) REFERENCES EVENT,
    PRIMARY KEY (attendeeID, eventID)
);

CREATE TABLE HOSTS (
    organizationID integer,
    eventID integer,
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION,
    FOREIGN KEY (eventID) REFERENCES EVENT,
    PRIMARY KEY (organizationID, eventID)
);

CREATE TABLE HELDAT (
    eventID integer,
    locationID integer,
    FOREIGN KEY (eventID) REFERENCES EVENT,
    FOREIGN KEY (locationID) REFERENCES LOCATION,
    PRIMARY KEY (eventID, locationID)
);
