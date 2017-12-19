package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDataLost {
    private static ObservableList<LostLuggage> lostLuggageList = FXCollections.observableArrayList(); 
    private static ObservableList<LostLuggage> resultsetList = FXCollections.observableArrayList(); 
    private static final MyJDBC db = MainApp.connectToDatabase();
    private static ResultSet resultSet;
    
       private String language = MainApp.getLanguage();
       
       
    public ServiceDataLost() throws SQLException{
        ServiceDataLost.lostLuggageList = getLostLuggageList();
    }
    
    public static ObservableList<LostLuggage> getLostLuggageList() throws SQLException{
        try {

            resultSet = db.executeResultSetQuery("SELECT * FROM lostLuggage");
            
            //clear previous list -> so there wont be any duplicate luggage
            ServiceDataLost.lostLuggageList.clear();
            
            while (resultSet.next()) {
               
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateLost =           resultSet.getString("dateLost");
                String timeLost =           resultSet.getString("timeLost");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                String size =               resultSet.getString("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String flight =             resultSet.getString("flight"); 
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");

                if (matchedId == 0) {
                //Per result -> toevoegen aan Luggages  (observable list) 
                lostLuggageList.add(new LostLuggage(
                                registrationNr, 
                                dateLost, 
                                timeLost, 
                                
                                luggageTag, 
                                luggageType, 
                                brand, 
                                mainColor, 
                                secondColor, 
                                size, 
                                weight, 
                                otherCharacteristics, 
                                passengerId, 
                                
                                flight, 
                                employeeId, 
                                matchedId
                            ));   
                } else {
                    //Luggage is already matched
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDataFound.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lostLuggageList;
    }
      
      
    public static ObservableList<LostLuggage> getLostLuggage(){
         return ServiceDataLost.lostLuggageList;
    }
     
     public ResultSet getAllDetailsLost(String id) throws SQLException{
         return db.executeResultSetQuery("SELECT " +
                        "COALESCE(NULLIF(F.registrationNr,''), 'none') as `F.registrationNr`," +
                        "COALESCE(NULLIF(F.dateLost,''), 'unknown') as `F.dateLost`, " +
                        "COALESCE(NULLIF(F.timeLost,''), 'unknown') as `F.timeLost`, " +
                        "COALESCE(NULLIF(F.luggageTag,''), 'unknown') as `F.luggageTag`,  " +
                        "COALESCE(NULLIF(T."+language+",''), 'unknown') as `T."+language+"`, " +
                        "COALESCE(NULLIF(F.brand,''), 'unknown') as `F.brand`," +
                        "COALESCE(NULLIF(C1."+language+",''), 'unknown') as `C1."+language+"`,  " +
                        "COALESCE(NULLIF(C2."+language+",''), 'none') as `C2."+language+"`," +
                        "COALESCE(NULLIF(F.size,''), 'unknown')	as `F.size`,  " +
                        "COALESCE(NULLIF(F.weight,''), 'unknown') as `F.weight`," +
                        "COALESCE(NULLIF(F.otherCharacteristics,''), 'none') as `F.otherCharacteristics`," +
                        "COALESCE(NULLIF(F.flight,''), 'unknown') as `F.flight`," +
                        "COALESCE(NULLIF(F.passengerId,''), 'none') as `F.passengerId`," +
                        "COALESCE(NULLIF(P.name,''), 'unknown')  as `P.name`," +
                        "COALESCE(NULLIF(P.address,''), 'unknown') as `P.address`," +
                        "COALESCE(NULLIF(P.place,''), 'unknown') as `P.place`," +
                        "COALESCE(NULLIF(P.postalcode,''), 'unknown')  as `P.postalcode`," +
                        "COALESCE(NULLIF(P.country,''), 'unknown') as `P.country`," +
                        "COALESCE(NULLIF(P.email,''), 'unknown') as `P.email`," +
                        "COALESCE(NULLIF(P.phone,''), 'unknown') as `P.phone` " +
                            "FROM lostluggage AS F " +
                                "LEFT JOIN luggagetype AS T " +
                                "	ON F.luggageType = T.luggageTypeId " +
                                "LEFT JOIN color AS C1 " +
                                "	ON F.mainColor = C1.ralCode " +
                                "LEFT JOIN color AS C2 " +
                                "	ON F.secondColor = C2.ralCode " +
                                "LEFT JOIN passenger AS P " +
                                "	ON (F.passengerId = P.passengerId) " +
                            "WHERE registrationNr='"+id+"';");
     }
    //Refresh methode ?
    //Update methode ?
     
     public ResultSet getLostResultSet(String id) throws SQLException{
         return db.executeResultSetQuery("SELECT * FROM lostluggage WHERE registrationNr='"+id+"';");
     }
     
    public ObservableList<LostLuggage> getObservableList(ResultSet resultSet) throws SQLException{
        
         while (resultSet.next() ) {
               
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateLost =           resultSet.getString("dateLost");
                String timeLost =           resultSet.getString("timeLost");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                String size =               resultSet.getString("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String flight =             resultSet.getString("flight"); 
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");

                
                //Per result -> toevoegen aan Luggages  (observable list) 
                resultsetList.add(new LostLuggage(
                                registrationNr, 
                                dateLost, 
                                timeLost, 
                                
                                luggageTag, 
                                luggageType, 
                                brand, 
                                mainColor, 
                                secondColor, 
                                size, 
                                weight, 
                                otherCharacteristics, 
                                passengerId, 
                                
                                flight, 
                                employeeId, 
                                matchedId
                            ));   
                
         }
         return resultsetList;
     }
}
