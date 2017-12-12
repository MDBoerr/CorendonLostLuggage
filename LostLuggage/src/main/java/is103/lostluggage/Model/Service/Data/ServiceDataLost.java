package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Controllers.Service.ServiceMatchingViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * 
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDataLost {
    public static ObservableList<LostLuggage> missedLuggageList = FXCollections.observableArrayList(); 
    private static final MyJDBC db = MainApp.connectToDatabase();
    private static ResultSet resultSet;
    
    public ServiceDataLost() throws SQLException{
        ServiceDataLost.missedLuggageList = setMissedLuggage();
    }
    
    public static ObservableList<LostLuggage> setMissedLuggage() throws SQLException{
        try {

            resultSet = db.executeResultSetQuery("SELECT * FROM lostLuggage");
            System.out.println("==  Lost luggage tabel ==");
            System.out.println("=========================");
            
            //clear previous list -> so there wont be any duplicate luggage
            ServiceDataLost.missedLuggageList.clear();
            
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
                missedLuggageList.add(new LostLuggage(
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
      
      
    public static ObservableList<LostLuggage> getLostLuggage(){
         return ServiceDataLost.missedLuggageList;
    }
    
    
     public void popUpDetails(Stage stage) throws IOException { 
            try { 
                //get popup fxml resource   
                Parent popup = FXMLLoader.load(getClass().getResource("/Views/Service/ServiceDetailedMissedLuggageView.fxml"));
                stage.setScene(new Scene(popup));
                
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                
//                if ("found".equals(type)){
                    //stage.setX(screenBounds.getMinX() + screenBounds.getWidth() - 10);
//                } else if ("missed".equals(type)) {
                 stage.setX(screenBounds.getMaxX() - screenBounds.getWidth() - 10);
//                } else if ("match".equals(type)){
//                    //if popup details are being used in the coming changes
//                    //*don't forget to set right position!
//                }
                
                stage.setY(screenBounds.getMaxY() - screenBounds.getHeight() - 10);

                //no functies -> close / fullscreen/ topbar
                //stage.initStyle(StageStyle.TRANSPARENT); //off

                //stage altijd on top
                stage.setAlwaysOnTop(true);

                if (stage.isShowing()){
                    //Stage was open -> refresh
                    stage.close();
                    stage.show();
                } else {
                    //Stage was gesloten -> alleen openen
                    stage.show();
                    System.out.println("Popup opend");
                }


            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    //Refresh methode ?
    //Update methode ?
}
