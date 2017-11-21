package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.Service.Luggage;
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
public class ManagerTerugViewController implements Initializable {

 //luggage list
    public static ObservableList<RetrievedLuggage> retrievedLuggage;
    private int id = 0;
    
    @FXML
    public TableView RetrievedTable;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        retrievedLuggage = FXCollections.observableArrayList();
        RetrievedTable.setItems(retrievedLuggage);
        
        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < RetrievedTable.getColumns().size(); i++  ) {
            TableColumn tc = (TableColumn) RetrievedTable.getColumns().get(i);
            
            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));
            
        }
        
        
        //dummy data invoeren in de tabel 
        retrievedLuggage.add(new RetrievedLuggage((id++), "0234", "04-11-2018", "Arthur", "Poek", "DHL"));
        retrievedLuggage.add(new RetrievedLuggage((id++), "0323", "05-11-2018", "Henry", "Poek", "Locale bezorger"));
    }
}