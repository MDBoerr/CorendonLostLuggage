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
import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.MissedLuggageList;
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
public class ServiceGevondenOverzichtViewController implements Initializable {

    
    
        //view title
    private final String title = "Overzicht Gevonden Bagage";
    
    public static ObservableList<FoundLuggage> foundLuggageList;
    
    /* -----------------------------------------
         TableView found luggage's colommen
    ----------------------------------------- */
    
    @FXML
    private TableView<FoundLuggage> foundLuggageTable;

    @FXML
    private TableColumn<FoundLuggage, String> idFoundLuggageColumn;
    @FXML
    private TableColumn<FoundLuggage, String> timeColumn;
    @FXML
    private TableColumn<FoundLuggage, String> airportColumn;
    @FXML
    private TableColumn<FoundLuggage, String> dateColumn;
    
    @FXML
    private TableColumn<FoundLuggage, String> nameColumn;
    @FXML
    private TableColumn<FoundLuggage, String> addressColumn;
    @FXML
    private TableColumn<FoundLuggage, String> residenceColumn;
    @FXML
    private TableColumn<FoundLuggage, String> postalcodeColumn;
    @FXML
    private TableColumn<FoundLuggage, String> countryColumn;
    @FXML
    private TableColumn<FoundLuggage, String> emailColumn;
    
    @FXML
    private TableColumn<FoundLuggage, String> labelnumberColumn;
    @FXML
    private TableColumn<FoundLuggage, String> flightnumberColumn;
    @FXML
    private TableColumn<FoundLuggage, String> destinationColumn;
    
    @FXML
    private TableColumn<FoundLuggage, String> typeColumn;
    @FXML
    private TableColumn<FoundLuggage, String> brandColumn;
    @FXML
    private TableColumn<FoundLuggage, String> colorColumn;
    @FXML
    private TableColumn<FoundLuggage, String> signaturesColumn;

         
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MainViewController.previousView = "/fxml/ServiceHomeView.fxml";
        
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
        
  
        idFoundLuggageColumn.setCellValueFactory(new PropertyValueFactory<>("idfoundLuggage"));
        timeColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_time"));
        airportColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_airport"));
        dateColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_date"));
        
        nameColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_name"));
        addressColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_address"));
        residenceColumn.setCellValueFactory(    new PropertyValueFactory<>("obj_residence"));
        postalcodeColumn.setCellValueFactory(   new PropertyValueFactory<>("obj_postalcode"));
        countryColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_country"));
        emailColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_email"));

        labelnumberColumn.setCellValueFactory(  new PropertyValueFactory<>("obj_labelnumber"));
        flightnumberColumn.setCellValueFactory( new PropertyValueFactory<>("obj_flightnumber"));
        destinationColumn.setCellValueFactory(  new PropertyValueFactory<>("obj_destination"));
            
        typeColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_type"));
        brandColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_brand"));
        colorColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_color"));
        signaturesColumn.setCellValueFactory(   new PropertyValueFactory<>("obj_signatures"));
    
        
        
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
                String get_idfoundLuggage =    resultSet.getString("idfoundLuggage");
                String get_time =          resultSet.getString("time");
                String get_airport =       resultSet.getString("airport");
                String get_date =          resultSet.getString("date");
                
                String get_name =          resultSet.getString("name");
                String get_adress =        resultSet.getString("adress");
                String get_residence =     resultSet.getString("residence");
                String get_postalcode =    resultSet.getString("postalcode");
                String get_country =       resultSet.getString("country");
                String get_email =         resultSet.getString("email");
                
                String get_labelnumber =   resultSet.getString("labelnumber");
                String get_flightnumber =  resultSet.getString("flightnumber");
                String get_destination =   resultSet.getString("destination");
                
                String get_type =          resultSet.getString("type");
                String get_brand =         resultSet.getString("brand");
                String get_color =         resultSet.getString("color");
                String get_signatures =    resultSet.getString("signatures");
                


                //Per result -> toevoegen aan Luggages  (observable list) 
                foundLuggageList.add(new FoundLuggage(get_idfoundLuggage, get_time, get_airport, get_date, get_name, get_adress, get_residence, get_postalcode, get_country, get_email, get_labelnumber, get_flightnumber, get_destination, get_type, get_brand, get_color, get_signatures));
                
                
                
                // Alle gegevens per result (koffer) ->  printen
                System.out.println("Gegevens voor koffer id: "+get_idfoundLuggage);
                System.out.println("Time: "+ get_time + " Airport: " + get_airport+" Datum:"+get_date);
                System.out.println("Naam: "+ get_name + " Adress: "+get_adress+"Plaats: "+get_residence);
                System.out.println("Postcode: "+get_postalcode+" Land: "+ get_country+" Email: "+get_email);
                System.out.println("Labelnum: "+get_labelnumber+" Vlucht: "+get_flightnumber+" Bestemming: "+get_destination);
                System.out.println("Type bagage: "+get_type+" Merk: "+get_brand+" Kleur: "+get_color+" Kenmerken: "+get_signatures);
                System.out.println(" ---------------------------------------------------------------------");
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceInvoerView.fxml");
    }
    
@   FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceMatchingView.fxml");
    }
    
}