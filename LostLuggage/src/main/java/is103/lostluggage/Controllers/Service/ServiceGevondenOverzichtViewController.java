package is103.lostluggage.Controllers.Service;


import is103.lostluggage.Controllers.MainViewController;
import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceGevondenOverzichtViewController implements Initializable {

        //luggage list
    public static ObservableList<Luggage> luggageList;
    private int id = 0;

    @FXML
    public TableView VermistTable;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //To Previous Scene
        MainViewController.previousView = "/fxml/ServiceHomeView.fxml";
    }    
    @FXML 
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
        
   if (luggageList == null) {
            luggageList = FXCollections.observableArrayList();

            //dummy data invoeren in de tabel 
            luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger (id?) "));
            luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
        }

    }
}
