/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Database;

import java.sql.*;
import java.util.Enumeration;

/**
 *
 * @author hva
 */
public class MyJDBC {

    private static final String DB_DEFAULT_DATABASE = "sys";
    private static final String DB_DEFAULT_SERVER_URL = "localhost:3306";
    private static final String DB_DEFAULT_ACCOUNT = "root";
    private static final String DB_DEFAULT_PASSWORD = "root";

    private final static String DB_DRIVER_URL = "com.mysql.jdbc.Driver";
    private final static String DB_DRIVER_PREFIX = "jdbc:mysql://";
    private final static String DB_DRIVER_PARAMETERS = "?useSSL=false";

    private Connection connection = null;

    // set for verbose logging of all queries
    private boolean verbose = true;

    // remembers the first error message on the connection 
    private String errorMessage = null;

    // constructors
    public MyJDBC() {
        this(DB_DEFAULT_DATABASE, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public MyJDBC(String dbName) {
        this(dbName, DB_DEFAULT_SERVER_URL, DB_DEFAULT_ACCOUNT, DB_DEFAULT_PASSWORD);
    }

    public MyJDBC(String dbName, String account, String password) {
        this(dbName, DB_DEFAULT_SERVER_URL, account, password);
    }

    public MyJDBC(String dbName, String serverURL, String account, String password) {
        try {
            // verify that a proper JDBC driver has been installed and linked
            if (!selectDriver(DB_DRIVER_URL)) {
                return;
            }

            if (password == null) {
                password = "";
            }

            // establish a connection to a named database on a specified server	
            String connStr = DB_DRIVER_PREFIX + serverURL + "/" + dbName + DB_DRIVER_PARAMETERS;
            log("Connecting " + connStr);
            this.connection = DriverManager.getConnection(connStr, account, password);

        } catch (SQLException eSQL) {
            error(eSQL);
            this.close();
        }
    }

    public final void close() {

        if (this.connection == null) {
            // db has been closed earlier already
            return;
        }
        try {
            this.connection.close();
            this.connection = null;
            this.log("Data base has been closed");
        } catch (SQLException eSQL) {
            error(eSQL);
        }
    }

    /**
     * *
     * elects proper loading of the named driver for database connections. This
     * is relevant if there are multiple drivers installed that match the JDBC
     * type
     *
     * @param driverName the name of the driver to be activated.
     * @return indicates whether a suitable driver is available
     */
    private Boolean selectDriver(String driverName) {
        try {
            Class.forName(driverName);
            // Put all non-prefered drivers to the end, such that driver selection hits the first
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver d = drivers.nextElement();
                if (!d.getClass().getName().equals(driverName)) {   // move the driver to the end of the list
                    DriverManager.deregisterDriver(d);
                    DriverManager.registerDriver(d);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            error(ex);
            return false;
        }
        return true;
    }

    /**
     * *
     * Executes a DDL, DML or DCL query that does not yield a result set
     *
     * @param sql the full sql text of the query.
     * @return the number of rows that have been impacted, -1 on error
     */
    public int executeUpdateQuery(String sql) {
        try {
             Statement s = this.connection.createStatement();
            log(sql);
            int n = s.executeUpdate(sql);
            s.close();
            return (n);
        } catch (SQLException ex) {
            // handle exception
            error(ex);
            return -1;
        }
    }

    /**
     * *
     * Executes an SQL query that yields a ResultSet with the outcome of the
     * query. This outcome may be a single row with a single column in case of a
     * scalar outcome.
     *
     * @param sql the full sql text of the query.
     * @return a ResultSet object that can iterate along all rows
     * @throws SQLException
     */
    public ResultSet executeResultSetQuery(String sql) throws SQLException {
        Statement s = this.connection.createStatement();
        log(sql);
        ResultSet rs = s.executeQuery(sql);
        // cannot close the statement, because that also closes the resultset
        return rs;
    }

    /**
     * *
     * Executes query that is expected to return a single String value
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringQuery(String sql) {
        String result = null;
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
            // close both statement and resultset
            s.close();
        } catch (SQLException ex) {
            error(ex);
        }

        return result;
    }

    /**
     * *
     * Executes query that is expected to return a list of String values
     *
     * @param sql the full sql text of the query.
     * @return the string result, null if no result or error
     */
    public String executeStringListQuery(String sql) {
        String result = null;
        try {
            Statement s = this.connection.createStatement();
            log(sql);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                result = rs.getString(1);
            }
            // close both statement and resultset
            s.close();
        } catch (SQLException ex) {
            error(ex);
        }

        return result;
    }

    /**
     * *
     * echoes a message on the system console, if run in verbose mode
     *
     * @param message
     */
    public void log(String message) {
        if (isVerbose()) {
            System.out.println("MyJDBC: " + message);
        }
    }

    /**
     * *
     * echoes an exception and its stack trace on the system console. remembers
     * the message of the first error that occurs for later reference. closes
     * the connection such that no further operations are possible.
     *
     * @param e
     */
    public final void error(Exception e) {
        String msg = "MyJDBC-" + e.getClass().getName() + ": " + e.getMessage();

        // capture the message of the first error of the connection
        if (this.errorMessage == null) {
            this.errorMessage = msg;
        }
        System.out.println(msg);
        e.printStackTrace();

        // if an error occurred, close the connection to prevent further operations
        this.close();
    }

    /**
     * *
     * builds a sample database with sample content
     *
     * @param dbName name of the sample database.
     */
    public static void createTestDatabase(String dbName) {

        System.out.println("Creating the " + dbName + " database...");

        // use the sys schema for creating another db
        MyJDBC sysJDBC = new MyJDBC("sys");
        sysJDBC.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + dbName);
        sysJDBC.close();

        // create or truncate Airport table in the Airline database
        System.out.println("Creating the Airport table...");
        MyJDBC myJDBC = new MyJDBC(dbName);
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS Airport ("
                + " IATACode VARCHAR(3) NOT NULL PRIMARY KEY,"
                + " Name VARCHAR(45),"
                + " TimeZone INT(3) )");

        // truncate Airport, in case some data was already there
        myJDBC.executeUpdateQuery("TRUNCATE TABLE Airport");

        // Populate the Airport table in the Airline database        
        System.out.println("Populating with Airport information...");
        myJDBC.executeUpdateQuery("INSERT INTO Airport VALUES ("
                + "'AMS', 'Schiphol Amsterdam', 1 )");
        myJDBC.executeUpdateQuery("INSERT INTO Airport VALUES ("
                + "'LHR', 'London Heathrow', 0 )");
        myJDBC.executeUpdateQuery("INSERT INTO Airport VALUES ("
                + "'BRU', 'Brussels Airport', 1 )");
        myJDBC.executeUpdateQuery("INSERT INTO Airport VALUES ("
                + "'ESB', 'Ankara Esenboğa Airport', 2 )");
        myJDBC.executeUpdateQuery("INSERT INTO Airport VALUES ("
                + "'SUF', 'Sant\\'Eufemia Lamezia International Airport', 1 )");
        myJDBC.executeUpdateQuery("INSERT INTO Airport VALUES ("
                + "'HKG', 'Hong Kong International', 8 )");

        // echo all airports in timezone 1
        System.out.println("Known Airports in time zone 1:");
        try {
            ResultSet rs = myJDBC.executeResultSetQuery(
                    "SELECT IATACode, Name FROM AirPort WHERE TimeZone=1");
            while (rs.next()) {
                // echo the info of the next airport found
                System.out.println(
                        rs.getString("IATACode")
                        + " " + rs.getString("Name"));
            }
            // close and release the resources
            rs.close();

        } catch (SQLException ex) {
            myJDBC.error(ex);
        }

        // close the connection with the database
        myJDBC.close();
    }

    public static void createLostLuggageDatabase(String dbName) {

        System.out.println("Creating the " + dbName + " database...");

        // use the sys schema for creating another db
        MyJDBC sysJDBC = new MyJDBC("sys");
        sysJDBC.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + dbName);
        sysJDBC.close();
        
        
        // create or truncate User table in the Airline database
        System.out.println("Creating the User, missedLuggage and foundLuggage table...");
        MyJDBC myJDBC = new MyJDBC(dbName);
        
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS missedLuggage ("
                + " idmissedLuggage VARCHAR(10) NOT NULL PRIMARY KEY,"
                + " time VARCHAR(8),"
                + " airport VARCHAR(45),"
                + " date VARCHAR(45),"
                + " name VARCHAR(45),"
                + " adress VARCHAR(45),"
                + " residence VARCHAR(40),"
                + " postalcode VARCHAR(40),"
                + " country VARCHAR(40),"
                + " email VARCHAR(40),"
                + " labelnumber VARCHAR(40),"
                + " flightnumber VARCHAR(40),"
                + " type VARCHAR(40),"
                + " destination VARCHAR(40),"
                + " brand VARCHAR(40),"
                + " color VARCHAR(40),"
                + " signatures VARCHAR(40) )");
        
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS foundLuggage ("
                + " idfoundLuggage VARCHAR(10) NOT NULL PRIMARY KEY,"
                + " time VARCHAR(8),"
                + " airport VARCHAR(45),"
                + " date VARCHAR(45),"
                + " name VARCHAR(45),"
                + " adress VARCHAR(45),"
                + " residence VARCHAR(40),"
                + " postalcode VARCHAR(40),"
                + " country VARCHAR(40),"
                + " email VARCHAR(40),"
                + " labelnumber VARCHAR(40),"
                + " flightnumber VARCHAR(40),"
                + " type VARCHAR(40),"
                + " destination VARCHAR(40),"
                + " brand VARCHAR(40),"
                + " color VARCHAR(40),"
                + " signatures VARCHAR(40) )");
                
                
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS User ("
                + " ID VARCHAR(10) NOT NULL PRIMARY KEY,"
                + " Firstname VARCHAR(45),"
                + " Lastname VARCHAR(45),"
                + " Location VARCHAR(45),"
                + " Status VARCHAR(10),"
                + " Role VARCHAR(20) )");
        
        // truncate Tables, in case some data was already there
        myJDBC.executeUpdateQuery("TRUNCATE TABLE User");
        myJDBC.executeUpdateQuery("TRUNCATE TABLE foundLuggage");
        myJDBC.executeUpdateQuery("TRUNCATE TABLE missedLuggage");

        // Populate the tables in the Airline database        
        System.out.println("Populating with User Account, Found and Missed Luggage...");
        
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'MB1', 'Michael', 'Boer de', 'Amsterdam', 'Active', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'AA1', 'Ahmet', 'Boer de', 'Amsterdam', 'Active', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'MB2', 'Arthur', 'Krom', 'Amsterdam', 'Active', 'Adminstrator' )");
        
        myJDBC.executeUpdateQuery("INSERT INTO foundLuggage VALUES ("
                + "'170', '09:21', 'AMS', '05-07-2017', 'Unknown', 'Unknown', 'Unknown', 'Unknown', 'Unknown', 'Unknown', '298438738AB', 'AMS328LON', 'Trolley', 'LON', 'Nomad', 'Silver', 'Steel, red dot' )");
        
        
        myJDBC.executeUpdateQuery("INSERT INTO missedLuggage VALUES ("
                + "'123', '12:00', 'AKE', '01-02-2015', 'Dave', 'Streename 3', 'Amsterdam', '1432 AD', 'Netherlands', 'Dave@mail.com', '298438738AB', 'AMS328LON', 'Trolley', 'LON', 'Nomad', 'Silver', 'Steel, dot' )");
        myJDBC.executeUpdateQuery("INSERT INTO missedLuggage VALUES ("
                + "'192', '10:02', 'SDA', '05-03-2017', 'Lenart', 'Wibautsat 2', 'Amsterdam', '1932 AM', 'Netherlands', 'Lenny@hva.nl', '26374738KE', 'AES128LEP', 'Badpack', 'EKL', 'TULU', 'Blue', 'Expensive laptop' )");


        // echo all airports in timezone 1
        System.out.println("Known User in time zone 1:");
        try {
            ResultSet rs = myJDBC.executeResultSetQuery(
                    "SELECT ID, Firstname FROM User WHERE Status='Active'");
            while (rs.next()) {
                // echo the info of the next airport found
                System.out.println(
                        rs.getString("ID")
                        + " " + rs.getString("Firstname"));
            }
            // close and release the resources
            rs.close();

        } catch (SQLException ex) {
            myJDBC.error(ex);
        }

        // close the connection with the database
        myJDBC.close();
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
