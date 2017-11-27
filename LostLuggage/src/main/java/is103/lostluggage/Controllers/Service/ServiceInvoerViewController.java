package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.MainViewController;
import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceInvoerViewController implements Initializable {

    //public static ServiceVermisteOverzichtViewController serviceHomeView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    //Choicebox that determines whether the form should be for a missing or found
    //luggage
    private ChoiceBox missingFoundChoiceBox;
    
    
    //Top left fields
    
    @FXML
    //Textfield that represents the time the form was filled in
    private TextField timeField;
    
    @FXML
    private TextField airportField;
    
    @FXML
    private DatePicker dateDatepicker;
    
    
    //Bottom left fields
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField addressField;
    
    @FXML
    private TextField residenceField;
    
    @FXML
    private TextField postalcodeField;
    
    @FXML
    private TextField countryField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField emailField;
    
    
    //Fields on the right
    
    @FXML
    private TextField labelnumberField;
    
    @FXML
    private TextField flightnumberField;
    
    @FXML
    private TextField destinationField;
    
    @FXML
    private TextField typeField;
    
    @FXML
    private TextField brandField;
    
    @FXML
    private TextField colorField;
    
    @FXML
    private TextArea signaturesField;
    
    //Main gridpane containing smaller gridpanes
    
    @FXML
    private GridPane mainGridPane;
        
    @FXML
    private GridPane travellerInfoGridPane;
    
    @FXML
    private GridPane luggageInfoGridPane;
    
    
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "/fxml/ServiceHomeView.fxml";

        //Add options to choicebox
        missingFoundChoiceBox.getItems().addAll("Gevonden", "Vermist");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        missingFoundChoiceBox.setValue("Vermist");
        
        //Set the current time in the timefield
        timeField.setText(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
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
        
      String value = missingFoundChoiceBox.getValue().toString();
      
        if(value == "Gevonden"){
      
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            mainGridPane.add(luggageInfoGridPane, 0, 1);
            mainGridPane.add(travellerInfoGridPane, 1, 1);
        }
        
        if(value == "Vermist"){
            
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
        }
    }
 

}
