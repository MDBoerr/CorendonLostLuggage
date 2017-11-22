package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.MainViewController;
import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
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
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
    }

    @FXML
    public void addLuggage(ActionEvent event) {
        System.out.println("Add");

        //Luggage list --> aangemaakt bij 'serviceVermistViewController'      *vind hij nog niet!
        luggageList.add(new Luggage(99, "label", "merk", "type", "vlucht", "luchthaven", "kenmerken", "reiziger"));

        //ServiceVermisteOverzichtViewController.VermistTable.refresh();
        //controller.VermistTable.refresh();

//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/ServiceVermisteOverzichtView.fxml"));
//        Parent root = (Parent) loader.load();
//       
//
//
//        ServiceVermisteOverzichtViewController controller = loader.getController();
//        controller.VermistTable.refresh();
    }

    public void assignProperties() {

    }

}
