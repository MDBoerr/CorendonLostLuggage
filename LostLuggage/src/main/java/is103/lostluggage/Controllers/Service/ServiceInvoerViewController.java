package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.MainViewController;
import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    //All the fields in the form
    private TextField labelField;
    
    @FXML
    private TextField brandField;
    
    @FXML
    private TextField typeField;
    
    @FXML
    private TextField flightField;
    
    @FXML
    private TextField airportField;
    
    @FXML
    private TextField signaturesField;
    
    @FXML
    private TextArea travellerInformationArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "/fxml/ServiceHomeView.fxml";

//        try {
//            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ServiceVermisteOverzichtView.fxml"));
//            Parent root = (Parent) loader.load();
//        } catch (IOException ex) {
//            Logger.getLogger(ServiceInvoerViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ServiceVermisteOverzichtViewController controller = loader.getController();
    }

    @FXML
    //This method will return the user to the previous screen
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
    }

    @FXML
    //This method will catch the the data from the form and add it to the list
    public void addLuggage(ActionEvent event) {
        
        //The label of the luggage "AS9948EA" for example
        String label = labelField.getText();
        
        //The brand of the luggage "samsonite" for example
        String brand = brandField.getText();
        
        //The type of luggage, suitcase, sportsbag for example
        String type = typeField.getText();
        
        //flight flight QW9912 for example
        String flight = flightField.getText();
                
        //airport?
        String airport = airportField.getText();
        
        //signatures can be a long text such as "The briefcase has smiley stickers on it"
        String signatures = signaturesField.getText();
      
        //Details of the owner of the luggage
        String travellerInformation = travellerInformationArea.getText();

        //add it to the list
         ServiceVermisteOverzichtViewController.addToList(99, "label", "brand", "type", "flight", "airpoprt", "signatures", "travellerinfo");
    }



}
