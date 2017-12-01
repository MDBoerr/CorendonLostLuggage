package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
//import is103.lostluggage.Controllers.Service.Luggage;
//import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerVerlorenViewController implements Initializable {

 //luggage list
//    public static ObservableList<Luggage> luggageList;
    private int id = 0;
    
    @FXML
    public TableView LostTable;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
        
//        luggageList = FXCollections.observableArrayList();
  //      LostTable.setItems(luggageList);
        
        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < LostTable.getColumns().size(); i++  ) {
            TableColumn tc = (TableColumn) LostTable.getColumns().get(i);
            
            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));
            
        }
        
        
        //dummy data invoeren in de tabel 
        luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger"));
        luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
    }      
 
            
}
