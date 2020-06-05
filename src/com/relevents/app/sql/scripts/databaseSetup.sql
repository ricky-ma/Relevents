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
    street varchar(50),
    cityID integer NOT NULL,
    FOREIGN KEY (cityID) REFERENCES CITY
);

CREATE TABLE APPUSER (
    userID integer PRIMARY KEY,
    fname varchar(20),
    lname varchar(20),
    birthdate date,
    phone varchar(20),
    email varchar(50),
    cityID integer,
    FOREIGN KEY (cityID) REFERENCES CITY
);

CREATE TABLE ORGANIZATION (
    organizationID integer PRIMARY KEY,
    orgName varchar(20),
    description varchar(4000),
    email varchar(50),
    website varchar(50)
);

CREATE TABLE GOVERNINGBODY (
    governingID integer PRIMARY KEY,
    governingName varchar(20),
    email varchar(50),
    phone varchar(20)
);

CREATE TABLE EVENT (
    eventID integer PRIMARY KEY,
    eventName varchar(50),
    eventStart timestamp,
    eventEnd timestamp,
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
    foodName varchar(20) NOT NULL,
    caterer varchar(20),
    cuisine varchar(20),
    eventID integer NOT NULL,
    FOREIGN KEY (eventID) REFERENCES EVENT
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
    userID integer,
    topicName varchar(20),
    FOREIGN KEY (userID) REFERENCES APPUSER (userID),
    FOREIGN KEY (topicName) REFERENCES TOPIC (topicName),
    PRIMARY KEY (userID, topicName)
);

CREATE TABLE FOLLOWS (
    userID integer,
    organizationID integer,
    FOREIGN KEY (userID) REFERENCES APPUSER (userID),
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION (organizationID),
    PRIMARY KEY (userID, organizationID)
);

CREATE TABLE MANAGES (
    userID integer,
    organizationID integer,
    eventID integer,
    FOREIGN KEY (userID) REFERENCES APPUSER (userID),
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION (organizationID),
    PRIMARY KEY (organizationID)
);

CREATE TABLE ATTENDS (
    userID integer,
    eventID integer,
    FOREIGN KEY (userID) REFERENCES APPUSER (userID),
    FOREIGN KEY (eventID) REFERENCES EVENT (eventID),
    PRIMARY KEY (userID, eventID)
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
