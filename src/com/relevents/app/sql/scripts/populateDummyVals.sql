INSERT INTO CITY (CITYID, CITYNAME, STATEPROVINCE, COUNTRY, POSTALCODE)
    WITH names (CITYID, CITYNAME, STATEPROVINCE, COUNTRY, POSTALCODE) AS (
        SELECT 1, 'Vancouver', 'British Columbia', 'Canada', 'V57 1V4' FROM dual UNION ALL
        SELECT 2, 'Diamond Bar', 'California', 'United States', '91765' FROM dual UNION ALL
        SELECT 3, 'Orlando', 'Florida', 'United States', '32825' FROM dual UNION ALL
        SELECT 4, 'Los Angeles', 'California', 'United States', '90001' FROM dual UNION ALL
        SELECT 5, 'Richmond', 'British Columbia', 'Canada', 'V3M 0A6' FROM dual
    )
    SELECT * FROM names;


INSERT INTO LOCATION (locationid, locationname, unit, housenum, street, cityid)
    WITH names (locationid, locationname, unit, housenum, street, cityid) AS (
        SELECT 1, 'location1', '25A', 1345,'street1', 1 FROM dual UNION ALL
        SELECT 2, 'location2', NULL, 2238,'street2', 2 FROM dual UNION ALL
        SELECT 3, 'location3', 'C', 56,'street3', 3 FROM dual UNION ALL
        SELECT 4, 'location4', NULL, 42001,'street4', 4 FROM dual UNION ALL
        SELECT 5, 'location5', '101', 2190,'street5', 5 FROM dual
    )
    SELECT * FROM names;


INSERT INTO APPUSER (USERID, FNAME, LNAME, BIRTHDATE, PHONE, EMAIL, CITYID)
    WITH names (USERID, FNAME, LNAME, BIRTHDATE, PHONE, EMAIL, CITYID) AS (
        SELECT 1, 'Ricky', 'Ma', TO_DATE('1999-12-29','YYYY-MM-DD') , '9095699045', 'ricky.ma@alumni.ubc.ca', 2 FROM dual UNION ALL
        SELECT 2, 'John', 'Smith', TO_DATE('1989-10-20','YYYY-MM-DD'), '', 'johnny@hotmail.com', 1 FROM dual UNION ALL
        SELECT 3, 'Bob', 'Ross', TO_DATE('1956-09-28','YYYY-MM-DD'), '6267819847', 'painting101@gmail.com', 3 FROM dual UNION ALL
        SELECT 4, 'Bon', 'Appleteeth', TO_DATE('2000-04-01','YYYY-MM-DD'), '4204204200', 'bonappetit@yum.com', 1 FROM dual UNION ALL
        SELECT 5, 'Will', 'Smith', TO_DATE('1979-03-30','YYYY-MM-DD'), '', 'will@smith.com', 4 FROM dual
    )
    SELECT * FROM names;


INSERT INTO ORGANIZATION (ORGANIZATIONID, ORGNAME, DESCRIPTION, EMAIL, WEBSITE)
    WITH names (ORGANIZATIONID, ORGNAME, DESCRIPTION, EMAIL, WEBSITE) AS (
        SELECT 1, 'Organization A', 'description about Organiztion A', 'OrganizationA@gmail.com', 'www.organizationA.net' FROM dual UNION ALL
        SELECT 2, 'Organization B', 'description about Organiztion B', 'OrganizationB@gmail.com', 'www.organizationB.net' FROM dual UNION ALL
        SELECT 3, 'Organization C', NULL, 'OrganizationC@gmail.com', 'www.organizationC.com' FROM dual UNION ALL
        SELECT 4, 'Organization D', 'description about Organiztion D', 'OrganizationD@gmail.com', 'www.organizationD.net' FROM dual UNION ALL
        SELECT 5, 'Organization E', 'description about Organization E', 'OrganizationE@gmail.com', 'www.organizationE.com' FROM dual
    )
    SELECT * FROM names;

INSERT INTO GOVERNINGBODY (GOVERNINGID, GOVERNINGNAME, EMAIL, PHONE)
    WITH names (GOVERNINGID, GOVERNINGNAME, EMAIL, PHONE) AS (
        SELECT 1, 'GoverningBody A', 'governingBodyA@gmail.com', '604123456' FROM dual UNION ALL
        SELECT 2, 'GoverningBody B', 'governingBodyB@gmail.com', '604789101' FROM dual UNION ALL
        SELECT 3, 'GoverningBody C', 'governingBodyC@gmail.com', '604777777' FROM dual UNION ALL
        SELECT 4, 'GoverningBody D', 'governingBodyD@gmail.com', '604888888' FROM dual UNION ALL
        SELECT 5, 'GoverningBody E', 'governingBodyE@gmail.com', '604999999' FROM dual
    )
    SELECT * FROM names;

INSERT INTO EVENT (eventID, eventName, eventStart,eventEnd,website,description,governingID)
    WITH names (eventID, eventName, eventStart,eventEnd,website,description,governingID) AS (
        SELECT 1, 'eventName1',TO_TIMESTAMP('10-SEP-2014:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),TO_TIMESTAMP('11-SEP-2012:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),'website1','description1',1 FROM dual UNION ALL
        SELECT 2, 'eventName2', TO_TIMESTAMP('10-SEP-2014:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),TO_TIMESTAMP('12-SEP-2015:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),'website2','description1',2 FROM dual UNION ALL
        SELECT 3, 'eventName3', TO_TIMESTAMP('10-SEP-2014:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),TO_TIMESTAMP('10-SEP-2014:30:10.123000','DD-MON-RRHH24:MI:SS.FF'),'website3','description3',3 FROM dual UNION ALL
        SELECT 4, 'eventName4', TO_TIMESTAMP('10-SEP-2014:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),TO_TIMESTAMP('13-SEP-2014:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),'website4','description4',4 FROM dual UNION ALL
        SELECT 5, 'eventName5', TO_TIMESTAMP('10-SEP-2014:10:10.123000','DD-MON-RRHH24:MI:SS.FF'),TO_TIMESTAMP('10-SEP-2015:00:10.123000','DD-MON-RRHH24:MI:SS.FF'),'website5','description5',5 FROM dual
    )
    SELECT * FROM names;


INSERT INTO EVENTFAQ (FAQID, QUESTION, ANSWER, EVENTID)
    WITH names (FAQID, QUESTION, ANSWER, EVENTID) AS (
        SELECT 1, 'How do I contact organzier', 'Email us at OrganizationA@gmail.com', 1 FROM dual UNION ALL
        SELECT 2, 'How do I contact organzier', 'Email us at OrganizationB@gmail.com', 2 FROM dual UNION ALL
        SELECT 3, 'How do I contact organzier', 'Email us at OrganizationC@gmail.com', 3 FROM dual UNION ALL
        SELECT 4, 'How do I contact organzier', 'Email us at OrganizationD@gmail.com', 4 FROM dual UNION ALL
        SELECT 5, 'How do I contact organzier', 'Email us at OrganizationE@gmail.com', 5 FROM dual
    )
    SELECT * FROM names;


INSERT INTO EVENTFOOD (FOODID, FOODNAME, CATERER, CUISINE, EVENTID)
    WITH names (FOODID, FOODNAME, CATERER, CUISINE, EVENTID) AS (
        SELECT 1, 'Pizza', 'Pizza House', 'Italian', 1 FROM dual UNION ALL
        SELECT 2, 'Hamburger', 'McDonald', NULL, 2 FROM dual UNION ALL
        SELECT 3, 'Sushi',  'Sushi House', 'Japanese', 3 FROM dual UNION ALL
        SELECT 4, 'Steak', 'Steak House', NULL, 4 FROM dual UNION ALL
        SELECT 5, 'Hamburger', 'KFC', NULL, 5 FROM dual
    )
    SELECT * FROM names;


INSERT INTO TOPIC (TOPICNAME)
    WITH names (TOPICNAME) AS (
        SELECT 'Art' FROM dual UNION ALL
        SELECT 'Soccer' FROM dual UNION ALL
        SELECT 'Sports' FROM dual UNION ALL
        SELECT 'Basketball' FROM dual UNION ALL
        SELECT 'Music' FROM dual
    )
    SELECT * FROM names;

INSERT INTO DESCRIBES (TOPICNAME, EVENTID)
    WITH names (TOPICNAME, EVENTID) AS (
        SELECT 'Art', 1 FROM dual UNION ALL
        SELECT 'Soccer', 2 FROM dual UNION ALL
        SELECT 'Music', 3 FROM dual UNION ALL
        SELECT 'Basketball', 4 FROM dual UNION ALL
        SELECT 'Sports', 5 FROM dual
    )
    SELECT * FROM names;

INSERT INTO PREFERS (USERID, TOPICNAME)
    WITH names (USERID, TOPICNAME) AS (
        SELECT 1, 'Art' FROM dual UNION ALL
        SELECT 2, 'Soccer' FROM dual UNION ALL
        SELECT 3, 'Music' FROM dual UNION ALL
        SELECT 4, 'Sports' FROM dual UNION ALL
        SELECT 5, 'Basketball' FROM dual
    )
    SELECT * FROM names;

INSERT INTO FOLLOWS (USERID, ORGANIZATIONID)
    WITH names (USERID, ORGANIZATIONID) AS (
        SELECT 1, 1 FROM dual UNION ALL
        SELECT 2, 2 FROM dual UNION ALL
        SELECT 3, 3 FROM dual UNION ALL
        SELECT 4, 4 FROM dual UNION ALL
        SELECT 5, 5 FROM dual
    )
    SELECT * FROM names;

INSERT INTO MANAGES (USERID, ORGANIZATIONID, EVENTID)
    WITH names (USERID, ORGANIZATIONID, EVENTID) AS (
        SELECT 1, 1, 1 FROM dual UNION ALL
        SELECT 2, 2, 2 FROM dual UNION ALL
        SELECT 3, 3, 3 FROM dual UNION ALL
        SELECT 4, 4, 4 FROM dual UNION ALL
        SELECT 5, 5, 5 FROM dual
    )
    SELECT * FROM names;

INSERT INTO ATTENDS (USERID, EVENTID)
    WITH names (USERID, EVENTID) AS (
        SELECT 1, 1 FROM dual UNION ALL
        SELECT 2, 2 FROM dual UNION ALL
        SELECT 3, 3 FROM dual UNION ALL
        SELECT 4, 4 FROM dual UNION ALL
        SELECT 5, 5 FROM dual
    )
    SELECT * FROM names;

INSERT INTO HOSTS (ORGANIZATIONID, EVENTID)
    WITH names (ORGANIZATIONID, EVENTID) AS (
        SELECT 1, 1 FROM dual UNION ALL
        SELECT 2, 2 FROM dual UNION ALL
        SELECT 3, 3 FROM dual UNION ALL
        SELECT 4, 4 FROM dual UNION ALL
        SELECT 5, 5 FROM dual
    )
    SELECT * FROM names;

INSERT INTO HELDAT (EVENTID, LOCATIONID)
    WITH names (EVENTID, LOCATIONID) AS (
        SELECT 1, 1 FROM dual UNION ALL
        SELECT 2, 2 FROM dual UNION ALL
        SELECT 3, 3 FROM dual UNION ALL
        SELECT 4, 4 FROM dual UNION ALL
        SELECT 5, 5 FROM dual
    )
    SELECT * FROM names;