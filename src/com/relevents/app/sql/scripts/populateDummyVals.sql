INSERT INTO CITY (CITYID, CITYNAME, STATEPROVINCE, COUNTRY, POSTALCODE)
    WITH names AS (
        SELECT 1, 'Vancouver', 'British Columbia', 'Canada', 'V57 1V4' FROM dual UNION ALL
        SELECT 2, 'Diamond Bar', 'California', 'United States', '91765' FROM dual UNION ALL
        SELECT 3, 'Orlando', 'Florida', 'United States', '32825' FROM dual UNION ALL
        SELECT 4, 'Los Angeles', 'California', 'United States', '90001' FROM dual UNION ALL
        SELECT 5, 'Richmond', 'British Columbia', 'Canada', 'V3M 0A6' FROM dual
    )
    SELECT * FROM names;

INSERT INTO APPUSER (USERID, FNAME, LNAME, BIRTHDATE, PHONE, EMAIL, CITYID)
    WITH names AS (
        SELECT 1, 'Ricky', 'Ma', TO_DATE('1999-12-29','YYYY-MM-DD') , '9095699045', 'ricky.ma@alumni.ubc.ca', 2 FROM dual UNION ALL
        SELECT 2, 'John', 'Smith', TO_DATE('1989-10-20','YYYY-MM-DD'), '', 'johnny@hotmail.com', 1 FROM dual UNION ALL
        SELECT 3, 'Bob', 'Ross', TO_DATE('1956-09-28','YYYY-MM-DD'), '6267819847', 'painting101@gmail.com', 3 FROM dual UNION ALL
        SELECT 4, 'Bon', 'Appleteeth', TO_DATE('2000-04-01','YYYY-MM-DD'), '4204204200', 'bonappetit@yum.com', 1 FROM dual UNION ALL
        SELECT 5, 'Will', 'Smith', TO_DATE('1979-03-30','YYYY-MM-DD'), '', 'will@smith.com', 4 FROM dual
    )
    SELECT * FROM names;

