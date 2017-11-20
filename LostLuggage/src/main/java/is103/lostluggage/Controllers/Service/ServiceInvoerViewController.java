package is103.lostluggage.Controllers.Service;


import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceInvoerViewController implements Initializable {

    

    
    
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
    
    @FXML
    public void addLuggage(ActionEvent event) {
        System.out.println("Add");
        
        
        
        //Luggage list --> aangemaakt bij 'serviceInvoerViewController'      *vind hij nog niet!
        
        //luggageList.add(new Luggage(99, "label", "merk", "type", "vlucht", "luchthaven", "kenmerken", "reiziger"));
    }
    
    
}
