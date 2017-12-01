package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
<<<<<<< HEAD
import is103.lostluggage.Controllers.Service.Luggage;
import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
import is103.lostluggage.MainApp;
=======
//import is103.lostluggage.Controllers.Service.Luggage;
>>>>>>> 5a62f54fede7709f61c2d2f207b669b4e62036b5
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerGevondenViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
<<<<<<< HEAD
    //luggage list
    public static ObservableList<Luggage> luggageList;
=======

 //luggage list
    //public static ObservableList<Luggage> luggageList;
>>>>>>> 5a62f54fede7709f61c2d2f207b669b4e62036b5
    private int id = 0;

    @FXML
    public TableView vermistTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
<<<<<<< HEAD

        luggageList = FXCollections.observableArrayList();
        vermistTable.setItems(luggageList);

=======
        
        //luggageList = FXCollections.observableArrayList();
        //VermistTable.setItems(luggageList);
        
>>>>>>> 5a62f54fede7709f61c2d2f207b669b4e62036b5
        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < vermistTable.getColumns().size(); i++) {
            TableColumn tc = (TableColumn) vermistTable.getColumns().get(i);

            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));

        }
       
            

        //dummy data invoeren in de tabel 
        //luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger"));
        //luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
   
    } 
    

}
