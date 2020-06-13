package com.relevents.app.database;

import com.relevents.app.model.Event;
import com.relevents.app.model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//    private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            System.out.println("here");
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // ---------------------------------------------EVENT FUNCTIONS---------------------------------------------------
    public void insertEvent(Event model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Event VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1, model.getEventID());
            ps.setString(2, model.getEventName());
            ps.setTimestamp(3, model.getEventStart());
            ps.setTimestamp(4, model.getEventEnd());
            ps.setString(5, model.getWebsite());
            ps.setString(6, model.getDescription());
            ps.setInt(7, model.getGoverningID());

            ps.executeUpdate();
            connection.commit();
            System.out.println("inserted");

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Event[] getEventInfo() {
        ArrayList<Event> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Event");

    		// get info on ResultSet
    		ResultSetMetaData rsmd = rs.getMetaData();

    		System.out.println(" ");

    		// display column names;
    		for (int i = 0; i < rsmd.getColumnCount(); i++) {
    			// get column name and print it
    			System.out.printf("%-15s", rsmd.getColumnName(i + 1));
    		}

            while(rs.next()) {
                Event model = new Event(rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getTimestamp("eventStart"),
                        rs.getTimestamp("eventEnd"),
                        rs.getString("website"),
                        rs.getString("description"),
                        rs.getInt("governingID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }

    public void updateEvent(int id, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Event SET eventName = ? WHERE eventID = ?");
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Event " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteEvent(int eventID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Event WHERE eventID = ?");
            ps.setInt(1, eventID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Event " + eventID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Event[] sortEventsByDate() {
        ArrayList<Event> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Event ORDER BY Eventstart DESC");

            while(rs.next()) {
                Event model = new Event(rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getTimestamp("eventStart"),
                        rs.getTimestamp("eventEnd"),
                        rs.getString("website"),
                        rs.getString("description"),
                        rs.getInt("governingID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }

    // select event that has "something" in its name or "something" in topicname
    public Event[] searchEventsByKeyword(String keyword) {
        ArrayList<Event> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM EVENT WHERE lower(EVENT.EVENTNAME) LIKE ? " +
                    "UNION SELECT E.EVENTID, E.EVENTNAME, E.EVENTSTART, E.EVENTEND, E.WEBSITE, E.DESCRIPTION, " +
                    "E.GOVERNINGID FROM EVENT E INNER JOIN DESCRIBES D on E.EVENTID = D.EVENTID WHERE lower(D.TOPICNAME) LIKE ?");
            String str = "%" + keyword + "%";
            ps.setString(1, str.toLowerCase());
            ps.setString(2, str.toLowerCase());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Event model = new Event(rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getTimestamp("eventStart"),
                        rs.getTimestamp("eventEnd"),
                        rs.getString("website"),
                        rs.getString("description"),
                        rs.getInt("governingID"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }
    // ---------------------------------------------LOCATION FUNCTIONS--------------------------------------------------
    // TODO

    // ---------------------------------------------ORGANIZATION FUNCTIONS----------------------------------------------
    // TODO

    // ---------------------------------------------USER FUNCTIONS------------------------------------------------------
    // TODO
    // retrieve users who attended all events
    public User[] usersAttendedAllEvents() {
        ArrayList<User> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM APPUSER app WHERE NOT EXISTS ((SELECT E.EVENTID FROM " +
                    "EVENT E) MINUS (SELECT A.EVENTID FROM ATTENDS A WHERE A.EMAIL = APP.EMAIL))");
            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }

            while(rs.next()) {
                User model = new User(rs.getString("EMAIL"),
                        rs.getString("FNAME"),
                        rs.getString("LNAME"),
                        rs.getDate("BIRTHDATE"),
                        rs.getString("PHONE"),
                        rs.getInt("CITYID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }

    public User getOneUserInfo(String email) {
        ArrayList<User> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM APPUSER WHERE EMAIL = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User model = new User(rs.getString("EMAIL"),
                        rs.getString("FNAME"),
                        rs.getString("LNAME"),
                        rs.getDate("BIRTHDATE"),
                        rs.getString("PHONE"),
                        rs.getInt("CITYID"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.get(0);
    }

    public User[] getUserInfo() {
        ArrayList<User> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM APPUSER");

            while(rs.next()) {
                User model = new User(rs.getString("EMAIL"),
                        rs.getString("FNAME"),
                        rs.getString("LNAME"),
                        rs.getDate("BIRTHDATE"),
                        rs.getString("PHONE"),
                        rs.getInt("CITYID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }

    // retrieve all the events a user is attending
    public Event[] eventsUserIsAttending(int userID) {
        ArrayList<Event> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT E.EVENTID, E.EVENTNAME, E.EVENTSTART, E.EVENTEND, E.WEBSITE," +
                    " E.DESCRIPTION, E.GOVERNINGID FROM EVENT E, APPUSER APP, ATTENDS A WHERE APP.EMAIL = ? " +
                    "AND APP.EMAIL = A.EMAIL AND A.EVENTID = E.EVENTID");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Event model = new Event(rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getTimestamp("eventStart"),
                        rs.getTimestamp("eventEnd"),
                        rs.getString("website"),
                        rs.getString("description"),
                        rs.getInt("governingID"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }

    // retrieve all the users attending an event
    public User[] usersOfAnEvent(int eventID) {
        ArrayList<User> result = new ArrayList<>();

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT APP.EMAIL, APP.FNAME, APP.LNAME, APP.BIRTHDATE, APP.PHONE, " +
                    "APP.EMAIL, APP.CITYID FROM EVENT E, APPUSER APP, ATTENDS A WHERE E.EVENTID = A.EVENTID AND " +
                    "A.EMAIL = APP.EMAIL AND E.EVENTID = ?");
            ps.setInt(1, eventID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User model = new User(rs.getString("EMAIL"),
                        rs.getString("FNAME"),
                        rs.getString("LNAME"),
                        rs.getDate("BIRTHDATE"),
                        rs.getString("PHONE"),
                        rs.getInt("CITYID"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }


    public void databaseSetup() {
        dropEventTableIfExists();

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE Event (eventID integer PRIMARY KEY, eventName varchar(50), eventStart timestamp, eventEnd timestamp, website varchar(50), description varchar(4000), governingID integer, FOREIGN KEY (governingID) REFERENCES GOVERNINGBODY)");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

//        BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
//        insertEvent(branch1);
//
//        BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
//        insertEvent(branch2);
    }

    private void dropEventTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("event")) {
                    stmt.execute("DROP TABLE event");
                    break;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
