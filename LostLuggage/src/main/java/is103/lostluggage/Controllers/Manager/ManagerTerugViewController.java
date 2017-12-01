package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
//import is103.lostluggage.Controllers.Service.Luggage;
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
import javafx.scene.input.MouseEvent;
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
    public TableView retrievedTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";

        retrievedLuggage = FXCollections.observableArrayList();
        retrievedTable.setItems(retrievedLuggage);

        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < retrievedTable.getColumns().size(); i++) {
            TableColumn tc = (TableColumn) retrievedTable.getColumns().get(i);

            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));

        }

        retrievedTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Node node = ((Node) event.getTarget()).getParent();

                    TableRow row;

                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    System.out.println(row.getItem());

                    try {

                        MainApp.switchView("/Views/ManagerPassengerInfoView.fxml");

                    } catch (IOException ex) {

                        Logger.getLogger(ManagerTerugViewController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }

            }

        });

        //dummy data invoeren in de tabel 
        retrievedLuggage.add(new RetrievedLuggage((id++), "0234", "04-11-2018", "Arthur", "Poek", "DHL"));
        retrievedLuggage.add(new RetrievedLuggage((id++), "0323", "05-11-2018", "Henry", "Poek", "Locale bezorger"));
    }
}
