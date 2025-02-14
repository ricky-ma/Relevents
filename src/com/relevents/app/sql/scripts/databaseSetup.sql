-- SELECT 'DROP TABLE "' || TABLE_NAME || '" CASCADE CONSTRAINTS;' FROM user_tables;

DROP TABLE APPUSER CASCADE CONSTRAINTS;
DROP TABLE ATTENDS CASCADE CONSTRAINTS;
DROP TABLE CITY CASCADE CONSTRAINTS;
DROP TABLE DESCRIBES CASCADE CONSTRAINTS;
DROP TABLE EVENT CASCADE CONSTRAINTS;
DROP TABLE EVENTFAQ CASCADE CONSTRAINTS;
DROP TABLE EVENTFOOD CASCADE CONSTRAINTS;
DROP TABLE FOLLOWS CASCADE CONSTRAINTS;
DROP TABLE GOVERNINGBODY CASCADE CONSTRAINTS;
DROP TABLE HELDAT CASCADE CONSTRAINTS;
DROP TABLE LOCATION CASCADE CONSTRAINTS;
DROP TABLE MANAGES CASCADE CONSTRAINTS;
DROP TABLE ORGANIZATION CASCADE CONSTRAINTS;
DROP TABLE PREFERS CASCADE CONSTRAINTS;
DROP TABLE TOPIC CASCADE CONSTRAINTS;


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
    email varchar(50) PRIMARY KEY,
    fname varchar(20),
    lname varchar(20),
    birthdate date,
    phone varchar(20),
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
    eventID integer GENERATED ALWAYS AS IDENTITY,
    eventName varchar(50),
    eventStart timestamp,
    eventEnd timestamp,
    website varchar(50),
    description varchar(4000),
    governingID integer,
    organizationID integer,
    PRIMARY KEY (eventID),
    FOREIGN KEY (governingID) REFERENCES GOVERNINGBODY,
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION
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
    email varchar2(50),
    topicName varchar(20),
    FOREIGN KEY (email) REFERENCES APPUSER (email),
    FOREIGN KEY (topicName) REFERENCES TOPIC (topicName),
    PRIMARY KEY (email, topicName)
);

CREATE TABLE FOLLOWS (
    email varchar2(50),
    organizationID integer,
    FOREIGN KEY (email) REFERENCES APPUSER (email),
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION (organizationID),
    PRIMARY KEY (email, organizationID)
);

CREATE TABLE MANAGES (
    email varchar2(50),
    organizationID integer,
    eventID integer,
    FOREIGN KEY (email) REFERENCES APPUSER (email),
    FOREIGN KEY (organizationID) REFERENCES ORGANIZATION (organizationID),
    PRIMARY KEY (email, organizationID)
);

CREATE TABLE ATTENDS (
    email varchar2(50),
    eventID integer,
    FOREIGN KEY (email) REFERENCES APPUSER (email),
    FOREIGN KEY (eventID) REFERENCES EVENT (eventID),
    PRIMARY KEY (email, eventID)
);

CREATE TABLE HELDAT (
    eventID integer,
    locationID integer,
    FOREIGN KEY (eventID) REFERENCES EVENT,
    FOREIGN KEY (locationID) REFERENCES LOCATION,
    PRIMARY KEY (eventID, locationID)
);
