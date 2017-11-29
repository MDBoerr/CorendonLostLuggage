package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Controllers.Service.Luggage;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerGevondenViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //luggage list
    public static ObservableList<Luggage> luggageList;
    private int id = 0;

    @FXML
    private TableView vermistTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";

        luggageList = FXCollections.observableArrayList();
        vermistTable.setItems(luggageList);

        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < vermistTable.getColumns().size(); i++) {
            TableColumn tc = (TableColumn) vermistTable.getColumns().get(i);

            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));

        }

        vermistTable.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                Node node = ((Node) event.getTarget()).getParent();
                TableRow row;
                //Get the info from the object that is selected
                if (node instanceof TableRow) {
                    row = (TableRow) node;
                } else {
                    // clicking on text part
                    row = (TableRow) node.getParent();
                }
                //This will print the object
                System.out.println(row.getItem());
                
                // Add the File that needs to be loaded here
                try {
                    MainApp.switchView("/fxml/AdminAddUserView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        //dummy data invoeren in de tabel 
        luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger"));
        luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
    }

    String getAliasName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
