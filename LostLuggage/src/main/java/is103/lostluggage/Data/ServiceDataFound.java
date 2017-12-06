package is103.lostluggage.Data;

import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
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


     public ServiceDataFound() throws SQLException{
        ServiceDataFound.foundLuggageList = setFoundLuggage();
    }
           
    public static ObservableList<FoundLuggage> setFoundLuggage() {

        try {
            resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage");
            System.out.println("=========================");
            System.out.println("== Found luggage tabel ==");
            System.out.println("=========================");
            
            
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
                int size =                  resultSet.getInt("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String arrivedWithFlight =  resultSet.getString("arrivedWithFlight"); 
                int locationFound =         resultSet.getInt("locationFound");
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");

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
      
    //Refresh methode ?
    //Update methode ?
    
}
