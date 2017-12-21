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
    private static final MyJDBC db = MainApp.getDatabase();
    private static ResultSet resultSet;
    
    private String language = MainApp.getLanguage();
       
    /**  
     * Here will the lost luggage list been set with the data from the db
     * This will be checked in a private method
     * 
     * @throws SQLException      getting data from the db
     */   
    public ServiceDataLost() throws SQLException{
        ServiceDataLost.lostLuggageList = getLostLuggageList();
    }
    
    /**  
     * Way of getting the lost luggage list
     * If the list isn't set, a empty list will be returned 
     * 
     * @return ObservableList      of the type: lost luggage  
     */
    public static ObservableList<LostLuggage> getLostLuggage(){
        return ServiceDataLost.lostLuggageList;
    }
    
    /**  
     * Way of getting the lost luggage list
     * If the list isn't set, a empty list will be returned 
     * 
     * @throws SQLException        getting the resultSet from the db
     * @param  id                  registrationNr of the wanted luggage
     * @return ResultSet           resultSet of the right luggage  
     */
    public ResultSet getLostResultSet(String id) throws SQLException{
        return db.executeResultSetQuery("SELECT * FROM lostluggage WHERE registrationNr='"+id+"';");
    }
         
         
      
    /**  
     * Method to get the full details of lost luggage  
     * Note: innerJoints are used for getting the full data of a luggage
     * 
     * @throws SQLException        data will be get from db
     * @param id                   registrationNr of the wanted lost luggage
     * @return resultSet           for the given id
     */ 
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
     
    /**  
     * Here is the given resultSet converted to a list
     * The return type can be initialized (set) in a table
     * 
     * @throws SQLException        data will be get from db
     * @param  resultSet           set from the db that needs to be converted to
     * @return ObservableList      of the type: lost luggage  
     */ 
    public ObservableList<LostLuggage> getObservableList(ResultSet resultSet) throws SQLException{
        //loop trough al the items of the resultSet
        while (resultSet.next() ) {
               
            //Set all the columns to the right variables
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


            //add the data in a lost luggage objects and put that in the list
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
    
    /**  
     * Method to get the full list of lost luggage s in the db   
     * Note: the resultSet will be converted to the right list
     * 
     * @throws SQLException        data will be get from db
     * @return ObservableList      of the type: lost luggage  
     */
    public static ObservableList<LostLuggage> getLostLuggageList() throws SQLException{
        try {
            //get the resultset of all the lost luggage s
            resultSet = db.executeResultSetQuery("SELECT * FROM lostluggage");
            
            //clear previous list -> so there wont be any duplicate luggage
            ServiceDataLost.lostLuggageList.clear();
            
            //loop trough al the items of the resultset
            while (resultSet.next()) {
               
                //Set all the columns to the right variables
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

                //if the match id is unasigned put the luggage in the list
                if (matchedId == 0) {
                //add the data in a lost luggage objects and put that in the list
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
        //the full list
        return lostLuggageList;
    }
}
