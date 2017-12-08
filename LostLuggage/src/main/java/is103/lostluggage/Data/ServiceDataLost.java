package is103.lostluggage.Data;

import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.MissedLuggage;
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
    public static ObservableList<MissedLuggage> missedLuggageList = FXCollections.observableArrayList(); 
    private static final MyJDBC db = MainApp.connectToDatabase();
    private static ResultSet resultSet;
    
    public ServiceDataLost() throws SQLException{
        ServiceDataLost.missedLuggageList = setMissedLuggage();
    }
    
    public static ObservableList<MissedLuggage> setMissedLuggage() throws SQLException{
        try {

            resultSet = db.executeResultSetQuery("SELECT * FROM lostLuggage");
            System.out.println("=========================");
            System.out.println("==  Lost luggage tabel ==");
            
            
            while (resultSet.next()) {
               
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateLost =          resultSet.getString("dateLost");
                String timeLost =          resultSet.getString("timeLost");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                int size =                  resultSet.getInt("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String flight =             resultSet.getString("flight"); 
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");


                //Per result -> toevoegen aan Luggages  (observable list) 
                missedLuggageList.add(
                        new MissedLuggage(
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

        } catch (SQLException ex) {
            Logger.getLogger(ServiceDataFound.class.getName()).log(Level.SEVERE, null, ex);
        }
        return missedLuggageList;
    }
      
      
    public static ObservableList<MissedLuggage> getMissedLuggage(){
         return ServiceDataLost.missedLuggageList;
    }
    
    
    //Refresh methode ?
    //Update methode ?
}
