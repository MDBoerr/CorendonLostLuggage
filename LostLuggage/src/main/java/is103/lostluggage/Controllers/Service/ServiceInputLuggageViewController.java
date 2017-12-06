package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceInputLuggageViewController implements Initializable {

    //public static ServiceMissedOverviewViewController serviceHomeView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    //Choicebox that determines whether the form should be for a missing or found
    //luggage
    private JFXComboBox missingFoundComboBox;
    
    
    
    @FXML
    private GridPane mainGridPane;
        
    @FXML
    private GridPane travellerInfoGridPane;
    
    @FXML
    private GridPane luggageInfoGridPane;
    
    @FXML
    private Label passengerInformationLbl;
    
    
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";

        //Add options to choicebox
        missingFoundComboBox.getItems().addAll("Gevonden", "Vermist");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        missingFoundComboBox.setValue("Vermist");
        
    }

    @FXML
    //This method will return the user to the previous screen
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
    }
    
    @FXML
    //This method adds the missing or found luggage to the system
    public void addLuggage(ActionEvent event){
        
    }
    
    @FXML
    //This method switches the form between found or missing
    public void foundOrMissing(){
        
      String value = missingFoundComboBox.getValue().toString();
      
        if(value == "Gevonden"){
      
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            mainGridPane.add(luggageInfoGridPane, 0, 1);
            mainGridPane.add(travellerInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Reizigers informatie is niet verplicht");
        }
        
        if(value == "Vermist"){
            
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
            passengerInformationLbl.setText("Reizigers informatie");
        }
    }
 

}
