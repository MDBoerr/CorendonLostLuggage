package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Arthur Krom
 */
public class ServiceInputLuggageViewController implements Initializable {

    //public static ServiceMissedOverviewViewController serviceHomeView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    //Choicebox that determines whether the form should be for a missing or found
    //luggage
    private JFXComboBox missingFoundComboBox, airportJFXComboBox, flightJFXComboBox, destinationJFXComboBox, typeJFXComboBox, colorJFXComboBox;
    
    @FXML
    private GridPane mainGridPane,travellerInfoGridPane, luggageInfoGridPane ;
        
    @FXML
    private Label passengerInformationLbl, missingLbl;
    
    @FXML
    private JFXTextField timeJFXTextField, nameJFXTextField, addressJFXTextField, placeJFXTextField, 
            postalcodeJFXTextField, countryJFXTextField, phoneJFXTextField, emailJFXTextField, labelnumberJFXTextField, brandJFXTextField,
            characteristicsJFXTextField;
            
               
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";

        //Add options to choicebox
        missingFoundComboBox.getItems().addAll("Found", "Missing");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        missingFoundComboBox.setValue("Missing");
        
    }
    
    //This method puts the values into the combo boxes
    public void setComboBox() throws SQLException{
        
        //connection to the database
        MyJDBC db = MainApp.connectToDatabase();
        
        //set with results
        ResultSet resultSet = db.executeResultSetQuery("SELECT * FROM color");
        
        
    }
    
    @FXML
    //This method switches the form between found or missing
    public void foundOrMissing(){
        
      String value = missingFoundComboBox.getValue().toString();
      
        if(value == "Found"){
      
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            mainGridPane.add(luggageInfoGridPane, 0, 1);
            mainGridPane.add(travellerInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Passenger information is not required");
            missingLbl.setText("Found");
        }
        
        if(value == "Missing"){

            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
            passengerInformationLbl.setText("Passenger information");
            missingLbl.setText("Missing");
        }
    }
    

}
