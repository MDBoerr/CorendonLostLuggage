package is103.lostluggage.Model.Service.Data;

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
 * Get all the recuired data for service pages.
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDataFound {
    public static ObservableList<FoundLuggage> foundLuggageList = FXCollections.observableArrayList();
    private static final MyJDBC db = MainApp.connectToDatabase();
    private static ResultSet resultSet;
    
    private String language = MainApp.getLanguage();

     public ServiceDataFound() throws SQLException{
        ServiceDataFound.foundLuggageList = setFoundLuggage();
    }
           
    public static ObservableList<FoundLuggage> setFoundLuggage() {

        try {
            resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage");
            
            //clear previous list -> so there wont be any duplicate luggage
            ServiceDataFound.foundLuggageList.clear();
            
            
            while (resultSet.next()) {
        //Alle gegevens van de database (foundLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateFound =          resultSet.getString("dateFound");
                String timeFound =          resultSet.getString("timeFound");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                String size =               resultSet.getString("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String arrivedWithFlight =  resultSet.getString("arrivedWithFlight"); 
                int locationFound =         resultSet.getInt("locationFound");
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");

                if (matchedId == 0){
                //Per result -> toevoegen aan Luggages  (observable list) 
                foundLuggageList.add(
                        new FoundLuggage(
                                registrationNr, 
                                dateFound, 
                                timeFound, 
                                
                                luggageTag, 
                                luggageType, 
                                brand, 
                                mainColor, 
                                secondColor, 
                                size, 
                                weight, 
                                otherCharacteristics, 
                                passengerId, 
                                
                                arrivedWithFlight, 
                                locationFound, 
                                employeeId, 
                                matchedId
                            ));
                } else {
                    //Luggage is already matched
                }
                //Debug
                //System.out.println("-"+registrationNr);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDataFound.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
      
    public static ObservableList<FoundLuggage> getFoundLuggage(){
         return ServiceDataFound.foundLuggageList;
    }
      
    public ResultSet getAllDetailsFound(String id) throws SQLException{
         return db.executeResultSetQuery("SELECT " +
                        "COALESCE(NULLIF(F.registrationNr,''), 0) as `F.registrationNr`," +
                        "COALESCE(NULLIF(F.dateFound,''), 'unknown') as `F.dateFound`, " +
                        "COALESCE(NULLIF(F.timeFound,''), 'unknown') as `F.timeFound`, " +
                        "COALESCE(NULLIF(F.luggageTag,''), 'unknown') as `F.luggageTag`,  " +
                        "COALESCE(NULLIF(T."+language+",''), 'unknown') as `T."+language+"`, " +
                        "COALESCE(NULLIF(F.brand,''), 'unknown') as `F.brand`," +
                        "COALESCE(NULLIF(C1."+language+",''), 0) as `C1."+language+"`,  " +
                        "COALESCE(NULLIF(C2."+language+",''), 0) as `C2."+language+"`," +
                        "COALESCE(NULLIF(F.size,''), 'unknown')	as `F.size`,  " +
                        "COALESCE(NULLIF(F.weight,''), 'unknown') as `F.weight`," +
                        "COALESCE(NULLIF(F.otherCharacteristics,''), 'none') as `F.otherCharacteristics`," +
                        "COALESCE(NULLIF(F.arrivedWithFlight,''), 'unknown') as `F.arrivedWithFlight`," +
                        "COALESCE(NULLIF(L."+language+" ,''), 'unknown') as `L."+language+"`," +
                        "COALESCE(NULLIF(F.passengerId,''), 0) as `F.passengerId`," +
                        "COALESCE(NULLIF(P.name,''), 'unknown')  as `P.name`," +
                        "COALESCE(NULLIF(P.address,''), 'unknown') as `P.address`," +
                        "COALESCE(NULLIF(P.place,''), 'unknown') as `P.place`," +
                        "COALESCE(NULLIF(P.postalcode,''), 'unknown')  as `P.postalcode`," +
                        "COALESCE(NULLIF(P.country,''), 'unknown') as `P.country`," +
                        "COALESCE(NULLIF(P.email,''), 'unknown') as `P.email`," +
                        "COALESCE(NULLIF(P.phone,''), 'unknown') as `P.phone` " +
                            "FROM foundluggage AS F " +
                                "LEFT JOIN luggagetype AS T " +
                                "	ON F.luggageType = T.luggageTypeId " +
                                "LEFT JOIN color AS C1 " +
                                "	ON F.mainColor = C1.ralCode " +
                                "LEFT JOIN color AS C2 " +
                                "	ON F.secondColor = C2.ralCode " +
                                "LEFT JOIN location AS L " +
                                "	ON F.locationFound = L.locationId " +
                                "LEFT JOIN passenger AS P " +
                                "	ON (F.passengerId = P.passengerId) " +
                            "WHERE registrationNr='"+id+"';");
     }
    
     public ResultSet getFoundResultSet(String id) throws SQLException{
         return db.executeResultSetQuery("SELECT * FROM foundluggage WHERE registrationNr='"+id+"';");
     }
    
}
