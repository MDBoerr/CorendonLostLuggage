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
    //observable lists
    public static ObservableList<FoundLuggage> foundLuggageList = FXCollections.observableArrayList();
    private static ObservableList<FoundLuggage> resultsetList = FXCollections.observableArrayList(); 

    private static ResultSet resultSet;
    
    //connection to database
    private static final MyJDBC DB = MainApp.getDatabase();
    
    //getting the main language
    private final String LANGUAGE = MainApp.getLanguage();
    
    /**  
     * Here will the found luggage list been set with the data from the db
     * This will be checked in a private method
     * 
     * @throws SQLException      getting data from the db
     */   
    public ServiceDataFound() throws SQLException{
        ServiceDataFound.foundLuggageList = getFoundLuggageList();
    }
    
    /**  
     * Way of getting the found luggage list
     * If the list isn't set, a empty list will be returned 
     * 
     * @return ObservableList      of the type: found luggage  
     */
    public static ObservableList<FoundLuggage> getFoundLuggage(){
         return ServiceDataFound.foundLuggageList;
    }
    
    /**  
     * Way of getting the found luggage list
     * If the list isn't set, a empty list will be returned 
     * 
     * @throws SQLException        getting the resultSet from the db
     * @param  id                  registrationNr of the wanted luggage
     * @return ResultSet           resultSet of the right luggage  
     */
    public ResultSet getFoundResultSet(String id) throws SQLException{
         return DB.executeResultSetQuery("SELECT * FROM foundluggage WHERE registrationNr='"+id+"';");
    }
    
    /**  
     * Way of getting the found luggage resultSet for matched 
     * when matched is is true, only matched luggage will be gotten
     * 
     * @throws SQLException        getting the resultSet from the db
     * @param  matched             boolean for getting the matched items or not
     * @return ResultSet           resultSet of the matched of not matched luggage  
     */
    public ResultSet getFoundResultSet(Boolean matched) throws SQLException{
        if (matched == true){
            return DB.executeResultSetQuery("SELECT * FROM foundluggage WHERE matchedId IS NOT NULL;");
        } else {
            return DB.executeResultSetQuery("SELECT * FROM foundluggage WHERE matchedId IS NULL OR matchedId = '0';");
        }
    }
    
          
    /**  
     * Method to get the full details of found luggage  
     * Note: innerJoints are used for getting the full data of a luggage
     * 
     * @throws SQLException        data will be get from db
     * @param id                   registrationNr of the wanted lost luggage
     * @return resultSet           for the given id
     */ 
    public ResultSet getAllDetailsFound(String id) throws SQLException{
        return DB.executeResultSetQuery("SELECT " +
            "COALESCE(NULLIF(F.registrationNr,''), 0) as `F.registrationNr`," +
            "COALESCE(NULLIF(F.dateFound,''), 'unknown') as `F.dateFound`, " +
            "COALESCE(NULLIF(F.timeFound,''), 'unknown') as `F.timeFound`, " +
            "COALESCE(NULLIF(F.luggageTag,''), 'unknown') as `F.luggageTag`,  " +
            "COALESCE(NULLIF(T."+LANGUAGE+",''), 'unknown') as `T."+LANGUAGE+"`, " +
            "COALESCE(NULLIF(F.brand,''), 'unknown') as `F.brand`," +
            "COALESCE(NULLIF(C1."+LANGUAGE+",''), 0) as `C1."+LANGUAGE+"`,  " +
            "COALESCE(NULLIF(C2."+LANGUAGE+",''), 0) as `C2."+LANGUAGE+"`," +
            "COALESCE(NULLIF(F.size,''), 'unknown')	as `F.size`,  " +
            "COALESCE(NULLIF(F.weight,''), 'unknown') as `F.weight`," +
            "COALESCE(NULLIF(F.otherCharacteristics,''), 'none') as `F.otherCharacteristics`," +
            "COALESCE(NULLIF(F.arrivedWithFlight,''), 'unknown') as `F.arrivedWithFlight`," +
            "COALESCE(NULLIF(L."+LANGUAGE+" ,''), 'unknown') as `L."+LANGUAGE+"`," +
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
     
    /**  
     * Here is the given resultSet converted to a list
     * The return type can be initialized (set) in a table
     * 
     * @throws SQLException        data will be get from db
     * @param  resultSet           set from the db that needs to be converted to
     * @return ObservableList      of the type: found luggage  
     */ 
    public ObservableList<FoundLuggage> getObservableList(ResultSet resultSet) throws SQLException{
        //clear the previous list 
        resultsetList.clear();
        
        //loop trough al the items of the resultset
        while (resultSet.next() ) {
               
            //Set all the columns to the right variables
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

            
            //add the data in a found luggage objects and put that in the list 
            resultsetList.add(
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

        }
        //the full list
        return resultsetList;
     }
    
    /**  
     * Method to get the full list of found luggage s in the db   
     * Note: the resultSet will be converted to the right list
     * 
     * @throws SQLException        data will be get from db
     * @return ObservableList      of the type: found luggage  
     */
    public static ObservableList<FoundLuggage> getFoundLuggageList() throws SQLException{
        try {
            //get the resultset of all the found luggage s
            resultSet = DB.executeResultSetQuery("SELECT * FROM foundLuggage");
            
            //clear previous list -> so there wont be any duplicate luggage
            ServiceDataFound.foundLuggageList.clear();
            
            //loop trough al the items of the resultset
            while (resultSet.next()) {
                //Set all the columns to the right variables
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

                //if the match id is unasigned put the luggage in the list
                if (matchedId == 0) {
                //add the data in a found luggage objects and put that in the list 
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
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDataFound.class.getName()).log(Level.SEVERE, null, ex);
        }
        //the full list
        return foundLuggageList;
    }

}
