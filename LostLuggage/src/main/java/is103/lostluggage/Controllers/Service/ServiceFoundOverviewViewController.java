package is103.lostluggage.Controllers.Service;


import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Controllers.MainViewController;
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
import static is103.lostluggage.Controllers.Service.ServiceMissedOverviewViewController.MissedLuggageList;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.Model.FoundLuggage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel
 */
public class ServiceFoundOverviewViewController implements Initializable {

    
    
        //view title
    private final String title = "Overzicht Gevonden Bagage";
    
    public static ObservableList<FoundLuggage> foundLuggageList;
    
    /* -----------------------------------------
         TableView found luggage's colommen
    ----------------------------------------- */
    
    @FXML
    private TableView<FoundLuggage> foundLuggageTable;

    @FXML private TableColumn<FoundLuggage, String>  found_registrationNr;
    @FXML private TableColumn<FoundLuggage, String>  found_dateFound;
    @FXML private TableColumn<FoundLuggage, String>  found_timeFound;
    
    @FXML private TableColumn<FoundLuggage, String>  found_luggageTag;
    @FXML private TableColumn<FoundLuggage, String>  found_luggageType;
    @FXML private TableColumn<FoundLuggage, String>  found_brand;
    @FXML private TableColumn<FoundLuggage, Integer> found_mainColor;
    @FXML private TableColumn<FoundLuggage, String>  found_secondColor;
    @FXML private TableColumn<FoundLuggage, Integer> found_size;
    @FXML private TableColumn<FoundLuggage, String>  found_weight;
    @FXML private TableColumn<FoundLuggage, String>  found_otherCharacteristics;
    @FXML private TableColumn<FoundLuggage, Integer> found_passengerId;
    
    @FXML private TableColumn<FoundLuggage, String> found_arrivedWithFlight;
    @FXML private TableColumn<FoundLuggage, Integer> found_locationFound;
    @FXML private TableColumn<FoundLuggage, String> found_employeeId;
    @FXML private TableColumn<FoundLuggage, Integer> found_matchedId;


         
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        /** -----------------------------------------
        *    DATA vanuit database in tabel plaatsen
        * 
        * @return data vanuit database in tabel.
       /*----------------------------------------- */       
        
  
        found_registrationNr.setCellValueFactory(       new PropertyValueFactory<>("registrationNr"));
        found_dateFound.setCellValueFactory(            new PropertyValueFactory<>("dateFound"));
        found_timeFound.setCellValueFactory(            new PropertyValueFactory<>("timeFound"));
        
        found_luggageTag.setCellValueFactory(           new PropertyValueFactory<>("luggageTag"));
        found_luggageType.setCellValueFactory(          new PropertyValueFactory<>("luggageType"));
        found_brand.setCellValueFactory(                new PropertyValueFactory<>("brand"));
        found_mainColor.setCellValueFactory(            new PropertyValueFactory<>("mainColor"));
        found_secondColor.setCellValueFactory(          new PropertyValueFactory<>("secondColor"));
        found_size.setCellValueFactory(                 new PropertyValueFactory<>("size"));
        found_weight.setCellValueFactory(               new PropertyValueFactory<>("weight"));

        found_otherCharacteristics.setCellValueFactory( new PropertyValueFactory<>("otherCharacteristics"));
        found_passengerId.setCellValueFactory(          new PropertyValueFactory<>("passengerId"));
        
        found_arrivedWithFlight.setCellValueFactory(    new PropertyValueFactory<>("arrivedWithFlight"));
        found_locationFound.setCellValueFactory(        new PropertyValueFactory<>("locationFound"));
        found_employeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
        found_matchedId.setCellValueFactory(             new PropertyValueFactory<>("matchedId"));

    
        
        
        foundLuggageTable.setItems(getFoundLuggage());
        
        
    }
    
    
    
    
    /** *  -----------------------------------------
     * Vermiste bagage uit de database tabel -> foundLuggage halen
        Deze gegevens vervolgens (tijdelijk) in variabelen opslaan
        Deze gegevens in object (FoundLuggage) plaatsen
 
        Resultaten per resultaat (koffer) -> (voor check) uiprinten (console)
     * 
     * 
     * @return luggages --> lijst met data van vermiste koffers
     /*----------------------------------------- */
    public ObservableList<FoundLuggage> getFoundLuggage() {

        ObservableList<FoundLuggage> foundLuggageList = FXCollections.observableArrayList();
        
        try {
            MyJDBC db = new MyJDBC("LostLuggage");

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage");
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("               alles geselecteerd van found luggage tabel            ");
            System.out.println(" ---------------------------------------------------------------------");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (foundLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateFound =          resultSet.getString("dateFound");
                String timeFound =          resultSet.getString("timeFound");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                int size =                  resultSet.getInt("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String arrivedWithFlight =  resultSet.getString("arrivedWithFlight"); 
                int locationFound =         resultSet.getInt("locationFound");
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =              resultSet.getInt("matchedId");

                


                //Per result -> toevoegen aan Luggages  (observable list) 
                foundLuggageList.add(
                        new FoundLuggage(
                                registrationNr, 
                                dateFound, 
                                timeFound, 
                                
                                luggageTag, 
                                luggageType, 
                                brand, 
                                mainColor, 
                                secondColor, 
                                size, 
                                weight, 
                                otherCharacteristics, 
                                passengerId, 
                                
                                arrivedWithFlight, 
                                locationFound, 
                                employeeId, 
                                matchedId
                            ));
                
                
                // Alle gegevens per result (koffer) (alleen id) om spam te voorkomen) ->  printen
                System.out.println("Gegevens voor koffer id: "+registrationNr+" |       Zijn: Correct");
                System.out.println("---------------------------------------------------------------------");
                
                // Alle gegevens per result (koffer) ->  printen  --> volledig overzicht
//                System.out.println("Gegevens voor koffer id: "+get_idfoundLuggage);
//                System.out.println("Time: "+ get_time + " Airport: " + get_airport+" Datum:"+get_date);
//                System.out.println("Naam: "+ get_name + " Adress: "+get_adress+"Plaats: "+get_residence);
//                System.out.println("Postcode: "+get_postalcode+" Land: "+ get_country+" Email: "+get_email);
//                System.out.println("Labelnum: "+get_labelnumber+" Vlucht: "+get_flightnumber+" Bestemming: "+get_destination);
//                System.out.println("Type bagage: "+get_type+" Merk: "+get_brand+" Kleur: "+get_color+" Kenmerken: "+get_signatures);
//                System.out.println(" ---------------------------------------------------------------------");
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInvoerView.fxml");
    }
    
@   FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
    }
    
}