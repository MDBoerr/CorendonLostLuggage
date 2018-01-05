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

    //get the main app's language again, from this static method
    private final String LANGUAGE = MainApp.getLanguage();
    
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
            query = "SELECT "+
                "COALESCE(NULLIF(F.registrationNr,''), '') as registrationNr, "+
                "COALESCE(NULLIF(F.dateFound,''), '') as dateFound, "+
                "COALESCE(NULLIF(F.timeFound,''), '') as timeFound,"+
                "COALESCE(NULLIF(F.luggageTag,''), '') as luggageTag, "+
                "COALESCE(NULLIF(F.luggageType,''), '') as luggageType, "+
                "COALESCE(NULLIF(F.brand,''), ' ') as brand, " +
                "COALESCE(NULLIF(C1."+LANGUAGE+",''), '') as mainColor,  " +
                "COALESCE(NULLIF(C2."+LANGUAGE+",''), '') as secondColor, " +
                "COALESCE(NULLIF(F.size,''), ' ') as size, "+
                "COALESCE(NULLIF(F.weight,''), '') as weight, "+
                "COALESCE(NULLIF(F.otherCharacteristics,''), '') as otherCharacteristics, "+
                "COALESCE(NULLIF(F.arrivedWithFlight,''), '') as arrivedWithFlight," +
                "COALESCE(NULLIF(L."+LANGUAGE+" ,''), 'unknown') AS locationFound, " +
                "COALESCE(NULLIF(F.employeeId,''), '') as employeeId, "+
                "COALESCE(NULLIF(F.matchedId,''), '') as matchedId, "+
                "COALESCE(NULLIF(F.passengerId,''), '') as passengerId " +     
                    "FROM tablename AS F " +
                        "LEFT JOIN color AS C1 ON F.mainColor = C1.ralCode " +
                        "LEFT JOIN color AS C2 ON F.secondColor = C2.ralCode " +
                        "LEFT JOIN location AS L ON F.locationFound = L.locationId WHERE ";
        } else if ("lostluggage".equals(luggageType)){
            query = "SELECT "+
                "COALESCE(NULLIF(L.registrationNr,''), '') as registrationNr, "+
                "COALESCE(NULLIF(L.dateLost,''), '') as dateLost, "+
                "COALESCE(NULLIF(L.timeLost,''), '') as timeLost, "+
                "COALESCE(NULLIF(L.luggageTag,''), '') as luggageTag, "+
                "COALESCE(NULLIF(L.luggageType,''), '') as luggageType, "+
                "COALESCE(NULLIF(L.brand,''), '') as brand," +
                "COALESCE(NULLIF(C1."+LANGUAGE+",''), '') as mainColor,  " +
                "COALESCE(NULLIF(C2."+LANGUAGE+",''), '') as secondColor, " +
                "COALESCE(NULLIF(L.size,''), '') as size, "+
                "COALESCE(NULLIF(L.weight,''), '') as weight, "+
                "COALESCE(NULLIF(L.otherCharacteristics,''), '') as otherCharacteristics, "+
                "COALESCE(NULLIF(L.flight,''), '') as flight, " +
                "COALESCE(NULLIF(L.employeeId,''), '') as employeeId, "+
                "COALESCE(NULLIF(L.matchedId,''), '') as matchedId, "+
                "COALESCE(NULLIF(L.passengerId,''), '') as passengerId " +     
                    "FROM tablename AS L " +
                        "LEFT JOIN color AS C1 ON L.mainColor = C1.ralCode " +
                        "LEFT JOIN color AS C2 ON L.secondColor = C2.ralCode WHERE ";
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
}
