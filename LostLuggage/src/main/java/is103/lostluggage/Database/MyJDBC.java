/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Database;

import java.sql.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Part 1 = Hva - Part 2 = Michael de Boer - Part 3 = Arthur Krom 
 * @subAuthor(Part 4 = Thijs Zijdel | I pasted the create query (that was made by arthur) in this file)
 */
public class MyJDBC {
    //Part 1

    private static final String DB_DEFAULT_DATABASE = "sys";
    private static final String DB_DEFAULT_SERVER_URL = "localhost:3306";
    private static final String DB_DEFAULT_ACCOUNT = "root";
    private static final String DB_DEFAULT_PASSWORD = "admin";

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

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    

    /*
    
    Part 2 
    
     */
    /**
     * *
     * Executes an SQL query that yields a ResultSet with a user if the filled
     * in fields match a id and password in the user table.
     * Else returns an ResultSet 
     *
     * Using a Prepared Statement. Prepared statements are used against SQL
     * injection
     *
     *
     * @param id the username from the user.
     * @param password the password that the user entered
     * @return a ResultSet object (User)
     */
    public ResultSet executeLogInResultSetQuery(String id, String password) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM User  "
                + "WHERE ID = ? AND Location = ?");
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    public static void createLostLuggageDatabase(String dbName) {

        System.out.println("Creating the " + dbName + " database...");

        // use the sys schema for creating another db
        MyJDBC sysJDBC = new MyJDBC("sys");
        sysJDBC.executeUpdateQuery("CREATE DATABASE IF NOT EXISTS " + dbName);
        sysJDBC.close();

        // create or truncate User table in the Airline database
        System.out.println("Creating the User table...");
        MyJDBC myJDBC = new MyJDBC(dbName);

//        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS missedLuggage ("
//                + " idmissedLuggage VARCHAR(10) NOT NULL PRIMARY KEY,"
//                + " time VARCHAR(8),"
//                + " airport VARCHAR(45),"
//                + " date VARCHAR(45),"
//                + " name VARCHAR(45),"
//                + " adress VARCHAR(45),"
//                + " residence VARCHAR(40),"
//                + " postalcode VARCHAR(40),"
//                + " country VARCHAR(40),"
//                + " email VARCHAR(40),"
//                + " labelnumber VARCHAR(40),"
//                + " flightnumber VARCHAR(40),"
//                + " type VARCHAR(40),"
//                + " destination VARCHAR(40),"
//                + " brand VARCHAR(40),"
//                + " color VARCHAR(40),"
//                + " signatures VARCHAR(40) )");
//
//        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS foundLuggage ("
//                + " idfoundLuggage VARCHAR(10) NOT NULL PRIMARY KEY,"
//                + " time VARCHAR(8),"
//                + " airport VARCHAR(45),"
//                + " date VARCHAR(45),"
//                + " name VARCHAR(45),"
//                + " adress VARCHAR(45),"
//                + " residence VARCHAR(40),"
//                + " postalcode VARCHAR(40),"
//                + " country VARCHAR(40),"
//                + " email VARCHAR(40),"
//                + " labelnumber VARCHAR(40),"
//                + " flightnumber VARCHAR(40),"
//                + " type VARCHAR(40),"
//                + " destination VARCHAR(40),"
//                + " brand VARCHAR(40),"
//                + " color VARCHAR(40),"
//                + " signatures VARCHAR(40) )");
        myJDBC.executeUpdateQuery("CREATE TABLE IF NOT EXISTS User ("
                + " ID VARCHAR(10) NOT NULL PRIMARY KEY,"
                + " Password VARCHAR(20) NOT NULL"
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
                + "'MB1', 'Amsterdam', 'Michael', 'Boer de', 'Amsterdam', 'Active', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'AA1', 'Amsterdam', 'Ahmet', 'Aksu', 'Amsterdam', 'Manager', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'AK1', 'Amsterdam', 'Arthur', 'Krom', 'Amsterdam', 'Service', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'TZ1', 'Amsterdam', 'Thijs', 'Zijdel', 'Amsterdam', 'Service', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'DO1', 'Amsterdam', 'Daron', 'Özdemir', 'Amsterdam', 'Manager', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'PL1', 'Amsterdam', 'Poek', 'Ligthart', 'Amsterdam', 'Service', 'Adminstrator' )");
        myJDBC.executeUpdateQuery("INSERT INTO User VALUES ("
                + "'MB2', 'Amsterdam', 'Michael', 'Boer de', 'Amsterdam', 'Manager', 'Adminstrator' )");

//        myJDBC.executeUpdateQuery("INSERT INTO foundLuggage VALUES ("
//                + "'170', '09:21', 'AMS', '05-07-2017', 'Unknown', 'Unknown', 'Unknown', 'Unknown', 'Unknown', 'Unknown', '298438738AB', 'AMS328LON', 'Trolley', 'LON', 'Nomad', 'Silver', 'Steel, red dot' )");
//
//        myJDBC.executeUpdateQuery("INSERT INTO missedLuggage VALUES ("
//                + "'123', '12:00', 'AKE', '01-02-2015', 'Dave', 'Streename 3', 'Amsterdam', '1432 AD', 'Netherlands', 'Dave@mail.com', '298438738AB', 'AMS328LON', 'Trolley', 'LON', 'Nomad', 'Silver', 'Steel, dot' )");
//        myJDBC.executeUpdateQuery("INSERT INTO missedLuggage VALUES ("
//                + "'192', '10:02', 'SDA', '05-03-2017', 'Lenart', 'Wibautsat 2', 'Amsterdam', '1932 AM', 'Netherlands', 'Lenny@hva.nl', '26374738KE', 'AES128LEP', 'Badpack', 'EKL', 'TULU', 'Blue', 'Expensive laptop' )");
        // echo all airports in timezone 1
        
        //Create Script proudly made by: ARTHUR KROM
        try {
            myJDBC.executeResultSetQuery("-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)\n" +
                    "--\n" +
                    "-- Host: localhost    Database: lostluggage\n" +
                    "-- ------------------------------------------------------\n" +
                    "-- Server version	5.7.20-log\n" +
                    "\n" +
                    "/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;\n" +
                    "/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;\n" +
                    "/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;\n" +
                    "/*!40101 SET NAMES utf8 */;\n" +
                    "/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;\n" +
                    "/*!40103 SET TIME_ZONE='+00:00' */;\n" +
                    "/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;\n" +
                    "/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;\n" +
                    "/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;\n" +
                    "/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `color`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `color`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `color` (\n" +
                    "  `ralCode` int(11) NOT NULL,\n" +
                    "  `english` varchar(45) NOT NULL,\n" +
                    "  `dutch` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`ralCode`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `color`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `color` WRITE;\n" +
                    "/*!40000 ALTER TABLE `color` DISABLE KEYS */;\n" +
                    "INSERT INTO `color` VALUES (1003,'Yellow','Geel'),(1015,'Cream','Crème'),(1024,'Olive','Olijf'),(2004,'Orange','Oranje'),(3000,'Red','Rood'),(3005,'Darkred','Donkerrood'),(3017,'Pink','Roze'),(4005,'Purple','Paars'),(4010,'Violet','Violet'),(5002,'Blue','Blauw'),(5015,'Lightblue','Lichtblauw'),(5022,'Darkblue','Donkerblauw'),(6002,'Green','Groen'),(6004,'Bluegreen','Blauwgroen'),(6022,'Darkgreen','Donkergroen'),(6038,'Lightgreen','Lichtgroen'),(7000,'Lightgray','Lichtgrijs'),(7015,'Gray','Grijs'),(8002,'Brown','Bruin'),(8011,'Darkbrown','Donkerbruin'),(8023,'Lightbrown','Lichtbruin'),(9001,'White','Wit'),(9005,'Black','Zwart'),(9011,'Darkgray','Donkergrijs');\n" +
                    "/*!40000 ALTER TABLE `color` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `destination`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `destination`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `destination` (\n" +
                    "  `IATAcode` varchar(45) NOT NULL,\n" +
                    "  `airport` varchar(45) NOT NULL,\n" +
                    "  `country` varchar(45) NOT NULL,\n" +
                    "  `timeZone` varchar(45) NOT NULL,\n" +
                    "  `daylightSaving` tinyint(4) NOT NULL,\n" +
                    "  PRIMARY KEY (`IATAcode`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `destination`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `destination` WRITE;\n" +
                    "/*!40000 ALTER TABLE `destination` DISABLE KEYS */;\n" +
                    "INSERT INTO `destination` VALUES ('ACE','Lanzarote','Spain','0',1),('ADB','Izmir','Turkey','3',0),('AGA','Agadir','Morocco','0',0),('AGP','Malaga','Spain','0',1),('AMS','Amsterdam','The Netherlands','1',1),('AYT','Antalya','Turkey','3',0),('BJL','Banjul','Gambia','0',0),('BJV','Bodrum','Turkey','3',0),('BOJ','Burgas','Bulgaria','2',1),('BRU','Brussels','Belgium','1',1),('CFU','Corfu','Greece','2',1),('CTA','Sicily (Catania)','Italy','1',1),('DLM','Dalaman','Turkey','3',0),('DXB','Dubai','United Arab Emirates','4',0),('ECN','Nicosia-Ercan','Cyprus (North)','2',1),('EIN','Eindhoven','The Netherlands','1',1),('FAO','Faro','Portugal','0',1),('FUE','Fuerteventura','Spain','0',1),('GZP','Gazipasa-Alanya','Turkey','3',0),('HER','Heraklion','Greece','2',1),('HRG','Hurghada','Egypt','2',0),('IST','Istanbul','Turkey','3',0),('KGS','Kos','Greece','2',1),('LPA','Gran Canaria','Spain','0',1),('MJT','Mytilene','Greece','2',1),('NBE','Enfidha','Tunesië','1',0),('OHD','Ohrid','Macedonia','1',1),('PAR','Paris','France','1',1),('PMI','Palma de Mallorca','Spain','0',1),('RAK','Marrakech','Morocco','0',1),('RHO','Rodes','Greece','2',1),('SMI','Samos','Greece','2',1),('TFO','Tenerife','Spain','0',1),('ZTH','Zakynthos','Greece','2',1);\n" +
                    "/*!40000 ALTER TABLE `destination` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `employee`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `employee`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `employee` (\n" +
                    "  `employeeId` varchar(45) NOT NULL,\n" +
                    "  `firstname` varchar(45) NOT NULL,\n" +
                    "  `surname` varchar(45) NOT NULL,\n" +
                    "  `password` varchar(45) NOT NULL,\n" +
                    "  `department` varchar(45) NOT NULL,\n" +
                    "  `airport` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`employeeId`),\n" +
                    "  UNIQUE KEY `employeeId_UNIQUE` (`employeeId`),\n" +
                    "  KEY `works at airport_idx` (`airport`),\n" +
                    "  CONSTRAINT `works at airport` FOREIGN KEY (`airport`) REFERENCES `destination` (`IATAcode`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='			';\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `employee`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `employee` WRITE;\n" +
                    "/*!40000 ALTER TABLE `employee` DISABLE KEYS */;\n" +
                    "INSERT INTO `employee` VALUES ('ak','arthur','krom','asd','Administrator','AMS'),('do','Daron','Ozdemir','ppok','Management','BOJ'),('md','michael','boer, de','feyenoord1','Administrator','AYT'),('tz','Thijs','Zijdel','asd','Service','AMS');\n" +
                    "/*!40000 ALTER TABLE `employee` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `flight`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `flight`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `flight` (\n" +
                    "  `flightNr` varchar(45) NOT NULL,\n" +
                    "  `airline` varchar(45) NOT NULL,\n" +
                    "  `from` varchar(45) NOT NULL,\n" +
                    "  `to` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`flightNr`),\n" +
                    "  KEY `this flight leaves from_idx` (`from`),\n" +
                    "  KEY `this flight arrives at_idx` (`to`),\n" +
                    "  CONSTRAINT `this flight arrives at` FOREIGN KEY (`to`) REFERENCES `destination` (`IATAcode`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `this flight leaves from` FOREIGN KEY (`from`) REFERENCES `destination` (`IATAcode`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `flight`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `flight` WRITE;\n" +
                    "/*!40000 ALTER TABLE `flight` DISABLE KEYS */;\n" +
                    "INSERT INTO `flight` VALUES ('CAI020','Corendon','AMS','AYT'),('CAI021','Corendon','AYT','AMS'),('CAI023','Corendon','AYT','AMS'),('CAI040','Corendon','EIN','AYT'),('CAI041','Corendon','AYT','EIN'),('CAI1827','Corendon','AYT','BRU'),('CAI1828','Corendon','BRU','AYT'),('CAI201','Corendon','BJV','AMS'),('CAI202','Corendon','AMS','BJV'),('CAI421','Corendon','AYT','BRU'),('CAI524','Corendon','BRU','AYT'),('CAI723','Corendon','AYT','BRU'),('CAI724','Corendon','BRU','AYT'),('CAI805','Corendon','AYT','AMS'),('CAI806','Corendon','AMS','AYT'),('CND117','Corendon','AMS','AGP'),('CND118','Corendon','AGP','AMS'),('CND513','Corendon','AMS','HER'),('CND593','Corendon','AMS','AGP'),('CND594','Corendon','AGP','AMS'),('CND712','Corendon','HER','AMS'),('HV355','Transavia','AMS','BJV'),('HV356','Transavia','BJV','AMS'),('HV6115','Transavia','AMS','AGP'),('HV6224','Transavia','AGP','AMS'),('HV649','Transavia','AMS','AYT'),('HV650','Transavia','AYT','AMS'),('HV740','Transavia','AYT','AMS'),('HV799','Transavia','AMS','AYT'),('KL1039','KLM','AMS','AGP'),('KL1040','KLM','AGP','AMS'),('KL1041','KLM','AMS','AGP'),('KL1042','KLM','AGP','AMS'),('PC5665','Pegasus','AYT','AMS'),('TK1823','Turkish Airlines','IST','PAR'),('TK1824','Turkish Airlines','PAR','IST'),('TK1827','Turkish Airlines','IST','PAR'),('TK1830','Turkish Airlines','PAR','IST'),('TK1938','Turkish Airlines','BRU','IST'),('TK1939','Turkish Airlines','IST','BRU'),('TK1942','Turkish Airlines','BRU','IST'),('TK1943','Turkish Airlines','IST','BRU'),('TK1951','Turkish Airlines','IST','AMS'),('TK1952','Turkish Airlines','AMS','IST'),('TK1955','Turkish Airlines','IST','AMS'),('TK1958','Turkish Airlines','AMS','IST'),('TK2409','Turkish Airlines','AYT','IST'),('TK2414','Turkish Airlines','IST','AYT'),('TK2425','Turkish Airlines','AYT','IST'),('TK2430','Turkish Airlines','IST','AYT'),('TK2505','Turkish Airlines','BJV','IST'),('TK2510','Turkish Airlines','IST','BJV'),('TO3002','Transavia','PAR','AGA'),('TO3005','Transavia','AGA','PAR'),('TO3160','Transavia','PAR','AGP'),('TO3163','Transavia','AGP','PAR'),('VY2150','Vueling','AGP','BRU'),('VY2151','Vueling','BRU','AGP');\n" +
                    "/*!40000 ALTER TABLE `flight` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `foundluggage`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `foundluggage`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `foundluggage` (\n" +
                    "  `registrationNr` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `dateFound` date NOT NULL,\n" +
                    "  `timeFound` varchar(45) NOT NULL,\n" +
                    "  `luggageTag` varchar(45) DEFAULT NULL,\n" +
                    "  `luggageType` int(11) NOT NULL,\n" +
                    "  `brand` varchar(45) DEFAULT NULL,\n" +
                    "  `mainColor` int(11) NOT NULL,\n" +
                    "  `secondColor` int(11) DEFAULT NULL,\n" +
                    "  `size` varchar(45) DEFAULT NULL,\n" +
                    "  `weight` int(11) DEFAULT NULL,\n" +
                    "  `otherCharacteristics` mediumtext,\n" +
                    "  `arrivedWithFlight` varchar(45) DEFAULT NULL,\n" +
                    "  `locationFound` int(11) DEFAULT NULL,\n" +
                    "  `employeeId` varchar(45) DEFAULT NULL,\n" +
                    "  `matchedId` int(11) DEFAULT NULL,\n" +
                    "  `passengerId` int(11) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`registrationNr`),\n" +
                    "  UNIQUE KEY `registrationNr_UNIQUE` (`registrationNr`),\n" +
                    "  KEY `the type of luggage_idx` (`luggageType`),\n" +
                    "  KEY `the main color of found luggage_idx` (`mainColor`),\n" +
                    "  KEY `the second color of found luggage_idx` (`secondColor`),\n" +
                    "  KEY `arrived with flight_idx` (`arrivedWithFlight`),\n" +
                    "  KEY `is found at location_idx` (`locationFound`),\n" +
                    "  KEY `formulier has been submitted by_idx` (`employeeId`),\n" +
                    "  KEY `matched with_idx` (`matchedId`),\n" +
                    "  KEY `Belongs to a passenger like this_idx` (`passengerId`),\n" +
                    "  CONSTRAINT `Belongs to a passenger like this` FOREIGN KEY (`passengerId`) REFERENCES `passenger` (`passengerId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `This luggage has this main color` FOREIGN KEY (`mainColor`) REFERENCES `color` (`ralCode`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `This luggage has this second color` FOREIGN KEY (`secondColor`) REFERENCES `color` (`ralCode`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `formulier has been submitted by` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `matched with` FOREIGN KEY (`matchedId`) REFERENCES `matched` (`matchedId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `this lugage is of this type` FOREIGN KEY (`luggageType`) REFERENCES `luggagetype` (`luggageTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `this luggage has arrived with flight` FOREIGN KEY (`arrivedWithFlight`) REFERENCES `flight` (`flightNr`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `this luggage has been found at` FOREIGN KEY (`locationFound`) REFERENCES `location` (`locationId`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=458 DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `foundluggage`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `foundluggage` WRITE;\n" +
                    "/*!40000 ALTER TABLE `foundluggage` DISABLE KEYS */;\n" +
                    "INSERT INTO `foundluggage` VALUES (410,'2016-06-17','19:25','1153481443',4,'',1015,NULL,'',0,'',NULL,NULL,NULL,NULL,1),(411,'2016-09-07','10:04','1297047756',1,'Perry Mackin',6004,4010,'80x60x40',0,'holywood sticker',NULL,6,NULL,NULL,2),(412,'2016-07-04','20:05','1321391290',6,'Eastsport',3017,8011,'',0,'red-bull sticker','CAI020',1,NULL,NULL,3),(413,'2016-09-09','13:18','1557534916',5,'Baggallini',1024,3005,'60x30x30',15,'','CAI724',1,NULL,NULL,4),(414,'2015-11-25','12:00','1688722916',2,'Baggallini',9005,5002,'60x30x30',15,'Orange stripes','CAI1828',2,NULL,NULL,5),(415,'2016-09-10','16:30','1957629307',1,'Ivy',2004,NULL,'70x50x20',0,'',NULL,7,NULL,NULL,6),(416,'2016-09-09','11:56','1963627893',1,'Nautica',1003,6038,'80x60x30',20,'many scratches',NULL,7,NULL,NULL,7),(417,'2015-10-20','11:50','2771896151',6,'Ivy',5002,NULL,'50x40x15',10,'','TK2414',0,NULL,NULL,8),(418,'2016-09-08','11:29','2973839061',4,'',4010,NULL,'60x40x30',15,'chain lock','HV799',0,NULL,NULL,9),(419,'2016-08-23','7:30','3217712035',1,'Travel Gear',8023,1003,'100x60x40',30,'ajax stickers',NULL,NULL,NULL,NULL,10),(420,'2016-03-13','19:23','3260024106',3,'Hedgren',3000,1015,'',0,'football stickers','TK2430',1,NULL,NULL,11),(421,'2016-09-02','9:25','3299609395',5,'Fjallraven',8023,NULL,'60x30x30',0,'','HV799',2,NULL,NULL,NULL),(422,'2016-01-17','14:13','3794786696',1,'Glove It',4010,9001,'80x60x30',15,'','CAI724',5,NULL,NULL,12),(423,'2016-09-04','9:40','4497537549',6,'Glove It',4005,NULL,'50x40x15',10,'','CAI1828',0,NULL,NULL,13),(424,'2016-08-24','8:10','4811246270',2,'Fjallraven',7015,NULL,'50x40x15',10,'Orange stripes','HV649',3,NULL,NULL,14),(425,'2016-08-31','8:11','5364334705',3,'Travel Gear',9001,NULL,'60x30x30',15,'',NULL,NULL,NULL,NULL,NULL),(426,'2016-07-19','21:05','5703242384',2,'Samsonite',8002,NULL,'',0,'Bicycle stickers',NULL,8,NULL,NULL,NULL),(427,'2016-08-11','23:00','5877130095',6,'Baggallini',5022,NULL,'',0,'red-bull sticker','CAI040',0,NULL,NULL,15),(428,'2016-07-25','22:00','5941005772',4,'',6038,NULL,'',0,'','CAI806',4,NULL,NULL,NULL),(429,'2016-07-15','20:35','5955243509',1,'Everest',5015,NULL,'60x40x20',15,'','TK2430',5,NULL,NULL,16),(430,'2016-08-06','22:15','6175011250',5,'Samsonite',8011,NULL,'',0,'',NULL,8,NULL,NULL,17),(431,'2016-09-08','10:17','6327958189',3,'Perry Mackin',6002,NULL,'60x30x30',10,'',NULL,6,NULL,NULL,NULL),(432,'2016-09-10','16:00','6377992003',6,'Everest',5015,3017,'50x40x15',10,'','CAI806',0,NULL,NULL,18),(433,'2016-06-18','19:40','6895742082',5,'Briggs',3005,NULL,'',0,'','HV649',1,NULL,NULL,NULL),(434,'2016-05-24','18:44','7620963089',1,'Hedgren',1015,NULL,'70x50x20',10,'','CAI806',2,NULL,NULL,19),(435,'2016-09-10','14:28','7686938228',2,'AmeriLeather',5022,7015,'60x30x30',10,'Olympic rings','HV799',4,NULL,NULL,NULL),(436,'2016-04-13','17:17','7975308223',2,'Delsey',5002,9005,'',0,'Olympic rings','CAI524',5,NULL,NULL,20),(437,'2016-09-10','15:50','9896064347',5,'AmeriLeather',8011,6004,'60x30x30',10,'BRT television sticker','CAI524',3,NULL,NULL,NULL),(438,'2016-09-01','9:10','',4,'',3017,NULL,'60x40x30',15,'','HV649',5,NULL,NULL,NULL),(439,'2016-09-06','9:58','',7,'',6038,4005,'100x60x40',30,'',NULL,8,NULL,NULL,NULL),(440,'2016-09-07','10:13','',2,'Eastsport',9011,1024,'50x40x15',0,'Orange stripes','CAI724',3,NULL,NULL,21),(441,'2016-09-08','11:43','',5,'Eastsport',9001,NULL,'60x30x30',10,'','CAI040',2,NULL,NULL,22),(442,'2016-09-09','11:54','',7,'',7015,6022,'80x60x40',25,'red name tag',NULL,8,NULL,NULL,NULL),(443,'2016-09-09','12:01','',3,'Nautica',1024,2004,'60x30x30',0,'',NULL,7,NULL,NULL,23),(444,'2015-12-25','12:04','',4,'',7000,3000,'60x40x30',0,'','TK2430',3,NULL,NULL,24),(445,'2016-09-09','13:21','',6,'Hedgren',8002,NULL,'50x40x15',10,'ajax stickers','CAI020',3,NULL,NULL,25),(446,'2016-09-10','13:37','',7,'',4005,8023,'80x60x30',0,'','CAI020',5,NULL,NULL,NULL),(447,'2016-09-10','15:31','',3,'Glove It',6004,5015,'60x30x30',10,'','CAI040',4,NULL,NULL,26),(448,'2016-09-10','15:40','',4,'',9011,5022,'',0,'frech national flag sticker',NULL,8,NULL,NULL,27),(449,'2016-02-10','16:22','',7,'',7000,NULL,'80x60x30',15,'',NULL,8,NULL,NULL,NULL),(450,'2016-04-27','17:34','',3,'Ivy',3000,9011,'',0,'',NULL,7,NULL,NULL,28),(451,'2016-04-30','17:44','',4,'',9005,7000,'',0,'broken lock',NULL,NULL,NULL,NULL,NULL),(452,'2015-12-25','18:09','',5,'Delsey',6002,8002,'',0,'','CAI1828',4,NULL,NULL,29),(453,'2016-05-03','18:10','',6,'Fjallraven',2004,6002,'',0,'','CAI524',1,NULL,NULL,NULL),(454,'2016-05-14','18:37','',7,'',6022,NULL,'70x50x20',20,'duvel sticker',NULL,6,NULL,NULL,NULL),(455,'2016-06-04','19:20','',2,'Briggs',1003,NULL,'',0,'Olympic rings','TK2414',2,NULL,NULL,30),(456,'2016-07-09','20:18','',7,'',3005,NULL,'70x50x20',10,'',NULL,7,NULL,NULL,31),(457,'2016-08-17','21:45','',3,'Everest',6022,NULL,'',0,'','TK2414',4,NULL,NULL,32);\n" +
                    "/*!40000 ALTER TABLE `foundluggage` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `location`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `location`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `location` (\n" +
                    "  `locationId` int(11) NOT NULL,\n" +
                    "  `english` varchar(45) NOT NULL,\n" +
                    "  `dutch` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`locationId`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `location`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `location` WRITE;\n" +
                    "/*!40000 ALTER TABLE `location` DISABLE KEYS */;\n" +
                    "INSERT INTO `location` VALUES (0,'belt-06','band-06'),(1,'belt-05','band-05'),(2,'belt-04','band-04'),(3,'belt-03','band-03'),(4,'belt-02','band-02'),(5,'belt-01','band-01'),(6,'departure hall','vertrekhal'),(7,'arrival hall','aankomsthal'),(8,'toilet','toilet');\n" +
                    "/*!40000 ALTER TABLE `location` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `lostluggage`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `lostluggage`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `lostluggage` (\n" +
                    "  `registrationNr` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `dateLost` datetime NOT NULL,\n" +
                    "  `timeLost` varchar(45) NOT NULL,\n" +
                    "  `luggageTag` varchar(45) DEFAULT NULL,\n" +
                    "  `brand` varchar(45) DEFAULT NULL,\n" +
                    "  `mainColor` int(11) NOT NULL,\n" +
                    "  `secondColor` int(11) DEFAULT NULL,\n" +
                    "  `size` int(11) DEFAULT NULL,\n" +
                    "  `weight` int(11) DEFAULT NULL,\n" +
                    "  `otherCharacteristics` mediumtext,\n" +
                    "  `flight` varchar(45) DEFAULT NULL,\n" +
                    "  `employeeId` varchar(45) NOT NULL,\n" +
                    "  `luggageType` int(11) NOT NULL,\n" +
                    "  `matchedId` int(11) DEFAULT NULL,\n" +
                    "  `passengerId` int(11) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`registrationNr`),\n" +
                    "  KEY `the main color of the luggage_idx` (`mainColor`),\n" +
                    "  KEY `the second color of the luggage_idx` (`secondColor`),\n" +
                    "  KEY `form has been submitted by employee_idx` (`employeeId`),\n" +
                    "  KEY `type of luggage_idx` (`luggageType`),\n" +
                    "  KEY `should have arrived with this flight` (`flight`),\n" +
                    "  KEY `matched with_idx` (`matchedId`),\n" +
                    "  KEY `belongs to this passenger_idx` (`passengerId`),\n" +
                    "  CONSTRAINT `belongs to this passenger` FOREIGN KEY (`passengerId`) REFERENCES `passenger` (`passengerId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `form has been submitted by employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `matched with found` FOREIGN KEY (`matchedId`) REFERENCES `matched` (`matchedId`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `should have arrived with this flight` FOREIGN KEY (`flight`) REFERENCES `flight` (`flightNr`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `the main color of the luggage` FOREIGN KEY (`mainColor`) REFERENCES `color` (`ralCode`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `the second color of the luggage` FOREIGN KEY (`secondColor`) REFERENCES `color` (`ralCode`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `type of luggage` FOREIGN KEY (`luggageType`) REFERENCES `luggagetype` (`luggageTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `lostluggage`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `lostluggage` WRITE;\n" +
                    "/*!40000 ALTER TABLE `lostluggage` DISABLE KEYS */;\n" +
                    "/*!40000 ALTER TABLE `lostluggage` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `luggagetype`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `luggagetype`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `luggagetype` (\n" +
                    "  `luggageTypeId` int(11) NOT NULL,\n" +
                    "  `english` varchar(45) NOT NULL,\n" +
                    "  `dutch` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`luggageTypeId`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `luggagetype`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `luggagetype` WRITE;\n" +
                    "/*!40000 ALTER TABLE `luggagetype` DISABLE KEYS */;\n" +
                    "INSERT INTO `luggagetype` VALUES (1,'Suitcase','Koffer'),(2,'Bag','Tas'),(3,'Bagpack','Rugzak'),(4,'Box','Doos'),(5,'Sports Bag','Sporttas'),(6,'Business Case','Zakenkoffer'),(7,'Case','Kist'),(8,'Other','Anders');\n" +
                    "/*!40000 ALTER TABLE `luggagetype` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `matched`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `matched`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `matched` (\n" +
                    "  `matchedId` int(11) NOT NULL,\n" +
                    "  `foundluggage` int(11) NOT NULL,\n" +
                    "  `lostluggage` int(11) NOT NULL,\n" +
                    "  `employeeId` varchar(45) NOT NULL,\n" +
                    "  `dateMatched` varchar(45) NOT NULL,\n" +
                    "  `delivery` varchar(45) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`matchedId`),\n" +
                    "  KEY `submitted by employee_idx` (`employeeId`),\n" +
                    "  KEY `lostluggage form_idx` (`lostluggage`),\n" +
                    "  KEY `foundluggage form_idx` (`foundluggage`),\n" +
                    "  CONSTRAINT `foundluggage form` FOREIGN KEY (`foundluggage`) REFERENCES `foundluggage` (`registrationNr`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `lostluggage form` FOREIGN KEY (`lostluggage`) REFERENCES `lostluggage` (`registrationNr`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                    "  CONSTRAINT `submitted by employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `matched`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `matched` WRITE;\n" +
                    "/*!40000 ALTER TABLE `matched` DISABLE KEYS */;\n" +
                    "/*!40000 ALTER TABLE `matched` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "\n" +
                    "--\n" +
                    "-- Table structure for table `passenger`\n" +
                    "--\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS `passenger`;\n" +
                    "/*!40101 SET @saved_cs_client     = @@character_set_client */;\n" +
                    "/*!40101 SET character_set_client = utf8 */;\n" +
                    "CREATE TABLE `passenger` (\n" +
                    "  `passengerId` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(45) DEFAULT NULL,\n" +
                    "  `address` varchar(45) DEFAULT NULL,\n" +
                    "  `place` varchar(45) DEFAULT NULL,\n" +
                    "  `postalcode` varchar(45) DEFAULT NULL,\n" +
                    "  `country` varchar(45) DEFAULT NULL,\n" +
                    "  `email` varchar(45) DEFAULT NULL,\n" +
                    "  `phone` varchar(45) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`passengerId`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;\n" +
                    "/*!40101 SET character_set_client = @saved_cs_client */;\n" +
                    "\n" +
                    "--\n" +
                    "-- Dumping data for table `passenger`\n" +
                    "--\n" +
                    "\n" +
                    "LOCK TABLES `passenger` WRITE;\n" +
                    "/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;\n" +
                    "INSERT INTO `passenger` VALUES (1,'P. Curie','Versaille',NULL,NULL,NULL,NULL,NULL),(2,'R. Hauer','Bussum',NULL,NULL,NULL,NULL,NULL),(3,'M. Verstappen','Monaco',NULL,NULL,NULL,NULL,NULL),(4,'S. Appelmans','De Panne',NULL,NULL,NULL,NULL,NULL),(5,'A. van Buren','Wassenaar',NULL,NULL,NULL,NULL,NULL),(6,'D. Kuyt','Rotterdam',NULL,NULL,NULL,NULL,NULL),(7,'C. van Houten','Naarden',NULL,NULL,NULL,NULL,NULL),(8,'M. Messi','Barcelona',NULL,NULL,NULL,NULL,NULL),(9,'N. Bonaparte','Paris',NULL,NULL,NULL,NULL,NULL),(10,'M. van Basten','Alkmaar',NULL,NULL,NULL,NULL,NULL),(11,'F. van der Elst','Brussel',NULL,NULL,NULL,NULL,NULL),(12,'R. van Persie','Rotterdam',NULL,NULL,NULL,NULL,NULL),(13,'M. Rutte','den Haag',NULL,NULL,NULL,NULL,NULL),(14,'W.A. van Buren','Wassenaar',NULL,NULL,NULL,NULL,NULL),(15,'J. Verstappen','Oss',NULL,NULL,NULL,NULL,NULL),(16,'A. Gerritse','Ilpendam',NULL,NULL,NULL,NULL,NULL),(17,'D. de Munck','Amsterdam',NULL,NULL,NULL,NULL,NULL),(18,'R. de Boer','Southhampton',NULL,NULL,NULL,NULL,NULL),(19,'S. Kramer','Heerenveen',NULL,NULL,NULL,NULL,NULL),(20,'I. de Bruijn','Leiden',NULL,NULL,NULL,NULL,NULL),(21,'M. van Buren','Wassenaar',NULL,NULL,NULL,NULL,NULL),(22,'E. Gruyaert','Antwerpen',NULL,NULL,NULL,NULL,NULL),(23,'Mw. Hollande','Paris',NULL,NULL,NULL,NULL,NULL),(24,'G. d\\'Esting','Paris',NULL,NULL,NULL,NULL,NULL),(25,'F. de Boer','Amsterdam',NULL,NULL,NULL,NULL,NULL),(26,'Mw. Zoetemelk','Lyon',NULL,NULL,NULL,NULL,NULL),(27,'F. Mitterand','Paris',NULL,NULL,NULL,NULL,NULL),(28,'L.. Van Moortsel','Breda',NULL,NULL,NULL,NULL,NULL),(29,'E. Leyers','Turnhout',NULL,NULL,NULL,NULL,NULL),(30,'P. van den Hoogenband','Eindhoven',NULL,NULL,NULL,NULL,NULL),(31,'E. de Munck','Brugge',NULL,NULL,NULL,NULL,NULL),(32,'F. van der Sande','Wuustwezel',NULL,NULL,NULL,NULL,NULL);\n" +
                    "/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;\n" +
                    "UNLOCK TABLES;\n" +
                    "/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;\n" +
                    "\n" +
                    "/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;\n" +
                    "/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;\n" +
                    "/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;\n" +
                    "/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n" +
                    "/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n" +
                    "/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n" +
                    "/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;\n" +
                    "\n" +
                    "-- Dump completed on 2017-12-06 15:56:22");
        } catch (SQLException ex) {
            Logger.getLogger(MyJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
                
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

}
