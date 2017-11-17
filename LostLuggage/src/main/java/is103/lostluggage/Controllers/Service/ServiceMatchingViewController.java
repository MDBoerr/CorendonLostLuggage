package is103.lostluggage.Controllers.Service;

import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceMatchingViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML 
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
    }    
    
}
