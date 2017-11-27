package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceVermisteOverzichtViewController implements Initializable {

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

        if (luggageList == null) {
            luggageList = FXCollections.observableArrayList();

            //dummy data invoeren in de tabel 
            luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger (id?) "));
            luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
        }

        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < VermistTable.getColumns().size(); i++) {
            TableColumn tc = (TableColumn) VermistTable.getColumns().get(i);

            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));

        }
        
        VermistTable.setItems(luggageList);
    }

    @FXML
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
    }

    public static List addToList(int id, String label, String merk, String type, String vlucht, String luchthaven, String kenmerken, String reiziger) {
        luggageList.add(new Luggage(id, label, merk, type, vlucht, luchthaven, kenmerken, reiziger));

        //System.out.println(luggageList);
        return luggageList;

    }

   
//    @FXML
//    public void removeRow(ActionEvent event) {
//        
//        Object row = VermistTable.getSelectionModel().getSelectedItem();
//        
//        if (row == null) {
//            System.out.println("Geen rij geselecteerd");
//        } else {
//            luggageList.remove(row);
//            System.out.println("Verwijderd");
//        }
//    }
}
