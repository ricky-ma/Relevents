package com.relevents.app.database;

import com.relevents.app.model.*;

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

    public Connection getConnection() {
        return connection;
    }

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
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    // ---------------------------------------------EVENT FUNCTIONS---------------------------------------------------
    public void insertEvent(String s, Timestamp start, Timestamp end, String website, String description, Integer govID, Integer orgID) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " +
                    "Event(EVENTNAME, EVENTSTART, EVENTEND, WEBSITE, DESCRIPTION, GOVERNINGID, ORGANIZATIONID) VALUES (?,?,?,?,?,?,?)");
//            ps.setInt(1, model.getEventID());
            ps.setString(1, s);
            ps.setTimestamp(2, start);
            ps.setTimestamp(3, end);
            ps.setString(4, website);
            ps.setString(5, description);
            ps.setInt(6, govID);
            ps.setInt(7, orgID);

            ps.executeUpdate();
            connection.commit();
            System.out.println("inserted");
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Event getOneEventInfo(Integer eventID) {
        ArrayList<Event> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM EVENT WHERE EVENTID = ?");
            ps.setInt(1, eventID);
            ResultSet rs = ps.executeQuery();

            addEventToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.get(0);
    }

    public void updateEvent(int id, Event model) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Event SET eventName=?, EVENTSTART=?, " +
                    "EVENTEND=?, WEBSITE=?, DESCRIPTION=?, GOVERNINGID=? WHERE eventID = ?");
            ps.setString(1, model.getEventName());
            ps.setTimestamp(2, model.getEventStart());
            ps.setTimestamp(3, model.getEventEnd());
            ps.setString(4, model.getWebsite());
            ps.setString(5, model.getDescription());
            ps.setInt(6, model.getGoverningID());
            ps.setInt(7, model.getEventID());

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

    // select event that has "something" in its name or "something" in topicname
    public Event[] searchEventsByKeyword(String keyword) {
        ArrayList<Event> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM EVENT WHERE lower(EVENT.EVENTNAME) LIKE ? " +
                    "UNION SELECT E.EVENTID, E.EVENTNAME, E.EVENTSTART, E.EVENTEND, E.WEBSITE, E.DESCRIPTION, " +
                    "E.GOVERNINGID, E.ORGANIZATIONID FROM EVENT E INNER JOIN DESCRIBES D on E.EVENTID = D.EVENTID WHERE lower(D.TOPICNAME) LIKE ?");
            String str = "%" + keyword + "%";
            ps.setString(1, str.toLowerCase());
            ps.setString(2, str.toLowerCase());
            ResultSet rs = ps.executeQuery();

            addEventToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }

    public Event nextUserEvent(String userEmail) {
        ArrayList<Event> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select *\n" +
                    "from Event, Attends\n" +
                    "where rownum=1 \n" +
                    "and ATTENDS.EMAIL = ? and EVENT.EVENTID = ATTENDS.EVENTID\n" +
                    "order by EVENT.EVENTSTART");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            addEventToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.get(0);
    }

    private void addEventToResult(ArrayList<Event> result, Statement stmt, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Event model = new Event(rs.getInt("eventID"),
                    rs.getString("eventName"),
                    rs.getTimestamp("eventStart"),
                    rs.getTimestamp("eventEnd"),
                    rs.getString("website"),
                    rs.getString("description"),
                    rs.getInt("governingID"),
                    rs.getInt("organizationID"));
            result.add(model);
        }
        rs.close();
        stmt.close();
    }

    // ---------------------------------------------LOCATION FUNCTIONS--------------------------------------------------
    public CityUser[] getCityUsers(){
        ArrayList<CityUser> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select c.CITYNAME, count(*) as numUsers\n" +
                    "from City c, APPUSER u\n" +
                    "where c.CITYID = u.CITYID\n" +
                    "group by c.cityName");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                // get column name and print it
                System.out.printf("%-15s", rsmd.getColumnName(i + 1));
            }

            while (rs.next()) {
                CityUser model = new CityUser(rs.getString("cityName"),
                        rs.getInt("numUsers"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new CityUser[result.size()]);

    }

    // ---------------------------------------------ORGANIZATION FUNCTIONS----------------------------------------------
    public Organization getOneOrgInfo(Integer orgID) {
        ArrayList<Organization> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = ?");
            ps.setInt(1, orgID);
            addOrgToResult(result, ps);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.get(0);
    }

    public Event[] getOrgEvents(Integer orgID) {
        ArrayList<Event> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Event E, ORGANIZATION O " +
                    "WHERE O.ORGANIZATIONID=E.ORGANIZATIONID AND O.ORGANIZATIONID=?");
            ps.setInt(1, orgID);
            ResultSet rs = ps.executeQuery();

            addEventToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }

    private void addOrgToResult(ArrayList<Organization> result, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Organization model = new Organization(rs.getInt("organizationID"),
                    rs.getString("orgName"),
                    rs.getString("description"),
                    rs.getString("email"),
                    rs.getString("website"));
            result.add(model);
        }

        rs.close();
        ps.close();
    }

    // ---------------------------------------------USER FUNCTIONS------------------------------------------------------
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

            addUserToResult(result, stmt, rs);
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

            addUserToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.get(0);
    }

    // retrieve all organizations a user manages
    public Organization[] userOrganizations(String email) {
        ArrayList<Organization> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT O.ORGANIZATIONID, O.ORGNAME, O.DESCRIPTION, " +
                    "O.EMAIL, O.WEBSITE FROM ORGANIZATION O, APPUSER U, MANAGES M WHERE U.EMAIL = ? " +
                    "AND U.EMAIL = M.EMAIL AND O.ORGANIZATIONID = M.ORGANIZATIONID");
            ps.setString(1, email);
            addOrgToResult(result, ps);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Organization[result.size()]);
    }

    // retrieve all organizations a user follows
    public Organization[] userFollows(String email) {
        ArrayList<Organization> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT O.ORGANIZATIONID, O.ORGNAME, O.DESCRIPTION, " +
                    "O.EMAIL, O.WEBSITE FROM ORGANIZATION O, APPUSER U, FOLLOWS F WHERE U.EMAIL = ? " +
                    "AND U.EMAIL = F.EMAIL AND O.ORGANIZATIONID = F.ORGANIZATIONID");
            ps.setString(1, email);
            addOrgToResult(result, ps);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Organization[result.size()]);
    }

    // retrieve all the events a user is attending
    public Event[] userEvents(String email) {
        ArrayList<Event> result = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT E.EVENTID, E.EVENTNAME, E.EVENTSTART, E.EVENTEND, E.WEBSITE," +
                    " E.DESCRIPTION, E.GOVERNINGID,E.ORGANIZATIONID FROM EVENT E, APPUSER APP, ATTENDS A WHERE APP.EMAIL = ? " +
                    "AND APP.EMAIL = A.EMAIL AND A.EVENTID = E.EVENTID");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            addEventToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Event[result.size()]);
    }

    // retrieve all topics a user prefers
    public String[] userTopics(String email) {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT P.TOPICNAME FROM PREFERS P, APPUSER APP" +
                    " WHERE APP.EMAIL = ? AND APP.EMAIL = P.EMAIL");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("TOPICNAME"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new String[result.size()]);
    }

    // retrieve all the users attending an event
    public User[] eventUsers(int eventID) {
        ArrayList<User> result = new ArrayList<>();

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT APP.EMAIL, APP.FNAME, APP.LNAME, APP.BIRTHDATE, APP.PHONE, " +
                    "APP.EMAIL, APP.CITYID FROM EVENT E, APPUSER APP, ATTENDS A WHERE E.EVENTID = A.EVENTID AND " +
                    "A.EMAIL = APP.EMAIL AND E.EVENTID = ?");
            ps.setInt(1, eventID);
            ResultSet rs = ps.executeQuery();

            addUserToResult(result, ps, rs);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new User[result.size()]);
    }

    private void addUserToResult(ArrayList<User> result, Statement stmt, ResultSet rs) throws SQLException {
        while (rs.next()) {
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
    }

    public ArrayList<ArrayList<String>> selectEventInfo(String columnNames) {
        ArrayList<ArrayList<String>> outerList = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("select " + columnNames + " from Event");
            ResultSet rs = ps.executeQuery();
            int columnsNumber = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ArrayList<String> innerList = new ArrayList<>();
                for (int i = 1; i <= columnsNumber; i++) {
                    innerList.add(rs.getString(i));
                }
                outerList.add(innerList);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return outerList;

    }
}
