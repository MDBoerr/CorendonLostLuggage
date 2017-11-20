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
public class ServiceHomeViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    protected void loguit(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/SelectUserRoleView.fxml");
    }
    
    @FXML 
    protected void naarInvoerScherm(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceInvoerView.fxml");
    }
    
    @FXML 
    protected void naarGevondenOverzichtScherm(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceGevondenOverzichtView.fxml");
    }
    
    @FXML 
    protected void naarVermisteOverzichtScherm(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceVermisteOverzichtView.fxml");
    }
    
    @FXML 
    protected void naarMatchingScherm(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceMatchingView.fxml");
    }
    
}
