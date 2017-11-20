package is103.lostluggage.Controllers.Service;

import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
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
        
        luggageList = FXCollections.observableArrayList();
        VermistTable.setItems(luggageList);
        
        //voor elke colum data vullen (bij verandering en initializatie
        for (int i = 0; i < VermistTable.getColumns().size(); i++  ) {
            TableColumn tc = (TableColumn) VermistTable.getColumns().get(i);
            
            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));
            
        }
        
        
        //dummy data invoeren in de tabel 
        luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger (id?) "));
        luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
    }   
    
     
    
        
    @FXML 
    protected void backHomeButton(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceHomeView.fxml");
    }
        
    
    // Voor het toevoegen en verwijderen van rijen
    // Weggecommend omdat in onze applicatie dit niet bij het overzicht wordt gedaan
    // maar bij de 'invoer' pagina.         
    
    
    //TOCH TESTEN!!
    
//    @FXML
//    public void addRow(ActionEvent event) {
//        System.out.println("Add");
//        
//        luggageList.add(new Luggage((id++), "label", "merk", "type", "vlucht", "luchthaven", "kenmerken", "reiziger"));
//    }
//    
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