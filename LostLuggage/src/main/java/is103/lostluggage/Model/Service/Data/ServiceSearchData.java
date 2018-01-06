package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Controllers.Service.ServiceOverviewFoundViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
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
    
    //connection to database
    private static final MyJDBC DB = MainApp.getDatabase();      
    
    public ObservableList<FoundLuggage> getFoundLuggageSearchList(ResultSet resultSet){
        ServiceDataFound dataList;
        try { 
            dataList = new ServiceDataFound();
            return dataList.getObservableList(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOverviewFoundViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ObservableList<LostLuggage> getLostLuggageSearchList(ResultSet resultSet){
        ServiceDataLost dataList;
        try { 
            dataList = new ServiceDataLost();
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
        String query = "SELECT * FROM tablename WHERE ";
        
        if ("foundluggage".equals(luggageType)){
            query = ServiceDataFound.DETAILED_QUERY+" WHERE ";
        } else if ("lostluggage".equals(luggageType)){
            query = ServiceDataLost.DETAILED_QUERY+" WHERE ";
        }
        switch (value) {
            case "all fields":
                query += "registrationNr LIKE '%replace%' OR "
                    + " luggageTag LIKE '%replace%' OR "
                    + " brand LIKE '%replace%' OR "
                    + " mainColor LIKE '%replace%' OR "
                    + " otherCharacteristics LIKE '%replace%' OR ";
                query += generateColorQuery(search);
                query += generatePassengerQuery(search);
                
                if ("foundluggage".equals(luggageType)){
                    query += generateLocationQuery(search);
                }
                break;
            case "registrationnr":
                query += "registrationNr LIKE '%replace%' OR ";
                break;
            case "luggagetag":
                query += "luggageTag LIKE '%replace%' OR ";
                break;    
            case "brand":
                query += "brand LIKE '%replace%' OR";
                break;
            case "color":
                
                query += generateColorQuery(search);
                
                break;
            case "characteristics":
                query += "otherCharacteristics LIKE '%replace%' OR ";
                break;
            case "weight":
                query += "weight LIKE '%replace%' OR ";
                break;
            case "date":
                if ("foundluggage".equals(luggageType.toLowerCase())){
                    query += "dateFound LIKE '%replace%' OR "
                    + " timeFound LIKE '%replace%' OR ";
                } else if ("lostluggage".equals(luggageType.toLowerCase())) {
                    query += "dateLost LIKE '%replace%' OR "
                    + " timeLost LIKE '%replace%' OR ";
                } else {
                    query = "date LIKE '%replace%' OR "
                    + " time LIKE '%replace%' OR ";
                }
                break;
            case "passenger":
                
                query += generatePassengerQuery(search);
                
                break;
            case "location":
                
                query += generateLocationQuery(search);
                
                break;
            default:
                query += "registrationNr LIKE '%replace%' OR "
                    + " luggageTag LIKE '%replace%' OR "
                    + " brand LIKE '%replace%' OR "
                    + " mainColor LIKE '%replace%' OR "
                    + " otherCharacteristics LIKE '%replace%' OR ";
        }
        
        
        
        if (!query.contains("OR")){
            //query = query.substring(0,(query.indexOf("WHERE")));
            query += "mainColor LIKE '%99999%'";
        } else {
            query = query.substring(0,query.lastIndexOf("OR"));
        }
        query += ";";
        
        query = query.replaceAll("tablename", luggageType.toLowerCase());
        
        String finalQuery = query.replaceAll("replace", search);
        
        if (null == finalQuery || "".equals(finalQuery)){
            finalQuery = "*";
        }
        return finalQuery;
    }

    private String generateColorQuery(String search) throws SQLException{
        String generateQuery = "";
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

        for (String colorListItem : stringList) {
            generateQuery += " mainColor LIKE '%"+colorListItem+"%' OR "
                   + " secondColor LIKE '%"+colorListItem+"%' OR ";          
        }
        
        return generateQuery;
    }

    private String generatePassengerQuery(String search) throws SQLException{
        String generateQuery = "";
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

        for (String passengerListItem : stringListPassenger) {
            generateQuery += " passengerId LIKE '"+passengerListItem+"' OR ";          
        }

        return generateQuery;
    }
    
        private String generateLocationQuery(String search) throws SQLException{
        String generateQuery = "";
        String locationQuery = "SELECT locationId FROM location WHERE "
                + " english LIKE '%replace%' OR "
                + " dutch LIKE '%replace%' OR "
                + " locationId LIKE '%replace%';";

        locationQuery = locationQuery.replaceAll("replace", search);

        ResultSet resultSetLocation = DB.executeResultSetQuery(locationQuery);
        ObservableList<String> stringListLocation =  FXCollections.observableArrayList(); 

        while (resultSetLocation.next()){
            String colorId = resultSetLocation.getString("locationId");
            stringListLocation.add(colorId);
        }

        for (String locationListItem : stringListLocation) {
            generateQuery += " locationId LIKE '"+locationListItem+"' OR ";          
        }

        return generateQuery;
    }
}
