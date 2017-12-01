package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceHomeViewController implements Initializable {

    
    //view title
    private final String title = "Service Home";
    
    @FXML
    //Textfield that represents the time the form was filled in
    private Label timeDisplay;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //database aanmaken
                                //--> Doe dit nu tijdelijk al bij de select user view
        //MyJDBC.createLostLuggageDatabase("LostLuggage");
        
        //To Previous Scene
        MainViewController.previousView = "/fxml/SelectUserRoleView.fxml";
        
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        //tijd weergeven
        timeDisplay.setText(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
    }

    @FXML
    protected void logOut(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/SelectUserRoleView.fxml");
    }

    @FXML
    protected void toInputView(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceInvoerView.fxml");
    }

    @FXML
    protected void toFoundLuggageView(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceGevondenOverzichtView.fxml");
    }

    @FXML
    protected void toMissedLuggageView(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceVermisteOverzichtView.fxml");
    }

    @FXML
    protected void toMatchingView(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceMatchingView.fxml");
    }

}
