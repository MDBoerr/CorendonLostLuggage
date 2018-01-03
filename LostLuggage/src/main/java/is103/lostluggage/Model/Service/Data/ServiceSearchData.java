/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Controllers.Service.ServiceOverviewFoundViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thijszijdel
 */
public class ServiceSearchData {
    ResultSet searchResultSet;
    
    
//    public 

//    initializeFoundLuggageTable();
//    setFoundLuggageTable(dataList);

    
    
    public ObservableList<FoundLuggage> getSearchList(ResultSet resultSet){
        ServiceDataFound dataList;
        try { 
            dataList = new ServiceDataFound();
            return dataList.getObservableList(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOverviewFoundViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public String getSearchQuery(String value, String search, String luggageType) throws SQLException{
        
        
        value = value.toLowerCase();
        if ("".equals(value) || value == null){
            value = "*";
        }
        String query = null;
        switch (value) {
            case "all fields":
                query = "SELECT * FROM tablename WHERE "
                    + " registrationNr LIKE '%replace%' OR "
                    + " luggageTag LIKE '%replace%' OR "
                    + " brand LIKE '%replace%' OR "
                    + " mainColor LIKE '%replace%' OR "
                    + " otherCharacteristics LIKE '%replace%' ;";
                query = generateColorQuery(search);
                query = generatePassengerQuery(search);
                
                break;
            case "registrationnr":
                query = "SELECT * FROM tablename WHERE "
                    + "registrationNr LIKE '%replace%';";
                break;
            case "luggagetag":
                query = "SELECT * FROM tablename WHERE "
                    + "luggageTag LIKE '%replace%';";
                break;    
            case "brand":
                query = "SELECT * FROM tablename WHERE "
                    + "brand LIKE '%replace%';";
                break;
            case "color":
//                String colorQuery = "SELECT ralCode FROM color WHERE "
//                        + " english LIKE '%replace%' OR "
//                        + " dutch LIKE '%replace%' OR "
//                        + " ralCode LIKE '%replace%';";
//                
//                colorQuery = colorQuery.replaceAll("replace", search);
//
//                ResultSet resultSetColor = DB.executeResultSetQuery(colorQuery);
//                ObservableList<String> stringList =  FXCollections.observableArrayList(); 
//                
//                while (resultSetColor.next()){
//                    String colorId = resultSetColor.getString("ralCode");
//                    stringList.add(colorId);
//                }
//                query = "SELECT * FROM tablename WHERE ";  
//                for (String colorListItem : stringList) {
//                    query += " mainColor LIKE '%"+colorListItem+"%' OR "
//                           + " secondColor LIKE '%"+colorListItem+"%' OR ";          
//                }
//                query += " mainColor LIKE '99999999';";
                                   
                query = generateColorQuery(search);
                
                break;
            case "characteristics":
                query = "SELECT * FROM tablename WHERE "
                    + "otherCharacteristics LIKE '%replace%';";
                break;
            case "weight":
                query = "SELECT * FROM tablename WHERE "
                    + "weight LIKE '%replace%';";
                break;
            case "date":
                if ("foundluggage".equals(luggageType.toLowerCase())){
                    query = "SELECT * FROM tablename WHERE "
                    + " dateFound LIKE '%replace%' OR"
                    + " timeFound LIKE '%replace%';";
                } else if ("lostluggage".equals(luggageType.toLowerCase())) {
                    query = "SELECT * FROM tablename WHERE "
                    + " dateLost LIKE '%replace%' OR"
                    + " timeLost LIKE '%replace%';";
                } else {
                    query = "SELECT * FROM tablename WHERE "
                    + " date LIKE '%replace%' OR"
                    + " time LIKE '%replace%';";
                }
                break;
            case "passenger":
                query = generatePassengerQuery(search);
//                String passengerQuery = "SELECT passengerId FROM passenger WHERE "
//                        + " name LIKE '%replace%' OR "
//                        + " address LIKE '%replace%' OR "
//                        + " place LIKE '%replace%' OR "
//                        + " address LIKE '%replace%' OR "
//                        + " place LIKE '%replace%' OR "
//                        + " postalcode LIKE '%replace%' OR "
//                        + " country LIKE '%replace%' OR "
//                        + " email LIKE '%replace%' OR "
//                        + " phone LIKE '%replace%';";
//                
//                passengerQuery = passengerQuery.replaceAll("replace", search);
//
//                ResultSet resultSetPassenger = DB.executeResultSetQuery(passengerQuery);
//                ObservableList<String> stringListPassenger =  FXCollections.observableArrayList(); 
//                
//                while (resultSetPassenger.next()){
//                    String colorId = resultSetPassenger.getString("passengerId");
//                    stringListPassenger.add(colorId);
//                }
//                
//                query = "SELECT * FROM tablename WHERE ";  
//                for (String passengerListItem : stringListPassenger) {
//                    query += " passengerId LIKE '"+passengerListItem+"' OR ";          
//                }
//                query += " passengerId LIKE '99999999';";
                
                
                break;
            default:
                query = "SELECT * FROM tablename WHERE "
                    + " registrationNr LIKE '%replace%' OR "
                    + " luggageTag LIKE '%replace%' OR "
                    + " brand LIKE '%replace%' OR "
                    + " mainColor LIKE '%replace%' OR "
                    + " otherCharacteristics LIKE '%replace%' ;";
        }
        //if ("foundluggage".equals(luggageType.toLowerCase())){
            query = query.replaceAll("tablename", luggageType);
//        } else if (){
//            
//        }
        
        String finalQuery = query.replaceAll("replace", search);
        
        if (null == finalQuery || "".equals(finalQuery)){
            finalQuery = "*";
        }
        return finalQuery;
    }
    
      //connection to database
    private static final MyJDBC DB = MainApp.getDatabase();  

    private String generateColorQuery(String search) throws SQLException{
        String generateQuery;
        String colorQuery = "SELECT ralCode FROM color WHERE "
                + " english LIKE '%replace%' OR "
                + " dutch LIKE '%replace%' OR "
                + " ralCode LIKE '%replace%';";

        colorQuery = colorQuery.replaceAll("replace", search);

        ResultSet resultSetColor = DB.executeResultSetQuery(colorQuery);
        ObservableList<String> stringList =  FXCollections.observableArrayList(); 

        while (resultSetColor.next()){
            String colorId = resultSetColor.getString("ralCode");
            stringList.add(colorId);
        }
        generateQuery = "SELECT * FROM tablename WHERE ";  
        for (String colorListItem : stringList) {
            generateQuery += " mainColor LIKE '%"+colorListItem+"%' OR "
                   + " secondColor LIKE '%"+colorListItem+"%' OR ";          
        }
        generateQuery += " mainColor LIKE '99999999';";
        return generateQuery;
    }

    private String generatePassengerQuery(String search) throws SQLException{
        String generateQuery;
        String passengerQuery = "SELECT passengerId FROM passenger WHERE "
                + " name LIKE '%replace%' OR "
                + " address LIKE '%replace%' OR "
                + " place LIKE '%replace%' OR "
                + " address LIKE '%replace%' OR "
                + " place LIKE '%replace%' OR "
                + " postalcode LIKE '%replace%' OR "
                + " country LIKE '%replace%' OR "
                + " email LIKE '%replace%' OR "
                + " phone LIKE '%replace%';";

        passengerQuery = passengerQuery.replaceAll("replace", search);

        ResultSet resultSetPassenger = DB.executeResultSetQuery(passengerQuery);
        ObservableList<String> stringListPassenger =  FXCollections.observableArrayList(); 

        while (resultSetPassenger.next()){
            String colorId = resultSetPassenger.getString("passengerId");
            stringListPassenger.add(colorId);
        }

        generateQuery = "SELECT * FROM tablename WHERE ";  
        for (String passengerListItem : stringListPassenger) {
            generateQuery += " passengerId LIKE '"+passengerListItem+"' OR ";          
        }
        generateQuery += " passengerId LIKE '99999999';";
        return generateQuery;
    }
}
