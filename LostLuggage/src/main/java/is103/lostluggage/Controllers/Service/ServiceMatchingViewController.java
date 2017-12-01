package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.MissedLuggage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
 * @author Thijs Zijdel
 */
public class ServiceMatchingViewController implements Initializable {

            //view title
    private final String title = "Matching";
    
    
    
    
    
    
    
    public static ObservableList<FoundLuggage> foundLuggageList;
    
    /* -----------------------------------------
         TableView found luggage's colommen
    ----------------------------------------- */
    
    @FXML
    private TableView<FoundLuggage> foundLuggageTable;

    @FXML
    private TableColumn<FoundLuggage, String> idFoundLuggageColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_timeColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_airportColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_dateColumn;
    
    @FXML
    private TableColumn<FoundLuggage, String> found_nameColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_addressColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_residenceColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_postalcodeColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_countryColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_emailColumn;
    
    @FXML
    private TableColumn<FoundLuggage, String> found_labelnumberColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_flightnumberColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_destinationColumn;
    
    @FXML
    private TableColumn<FoundLuggage, String> found_typeColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_brandColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_colorColumn;
    @FXML
    private TableColumn<FoundLuggage, String> found_signaturesColumn;
    
    
    
    
    
    
    public static ObservableList<MissedLuggage> MissedLuggageList;
    
    /* -----------------------------------------
         TableView missed luggage's colommen
    ----------------------------------------- */
    
    @FXML
    private TableView<MissedLuggage> missedLuggageTable;

    @FXML
    private TableColumn<MissedLuggage, String> idMissedLuggageColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_timeColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_airportColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_dateColumn;
    
    @FXML
    private TableColumn<MissedLuggage, String> missed_nameColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_addressColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_residenceColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_postalcodeColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_countryColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_emailColumn;
    
    @FXML
    private TableColumn<MissedLuggage, String> missed_labelnumberColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_flightnumberColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_destinationColumn;
    
    @FXML
    private TableColumn<MissedLuggage, String> missed_typeColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_brandColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_colorColumn;
    @FXML
    private TableColumn<MissedLuggage, String> missed_signaturesColumn;
    
    
    
    
    
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
        
        
        
        
        idFoundLuggageColumn.setCellValueFactory(new PropertyValueFactory<>("idfoundLuggage"));
        found_timeColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_time"));
        found_airportColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_airport"));
        found_dateColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_date"));
        
        found_nameColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_name"));
        found_addressColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_address"));
        found_residenceColumn.setCellValueFactory(    new PropertyValueFactory<>("obj_residence"));
        found_postalcodeColumn.setCellValueFactory(   new PropertyValueFactory<>("obj_postalcode"));
        found_countryColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_country"));
        found_emailColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_email"));

        found_labelnumberColumn.setCellValueFactory(  new PropertyValueFactory<>("obj_labelnumber"));
        found_flightnumberColumn.setCellValueFactory( new PropertyValueFactory<>("obj_flightnumber"));
        found_destinationColumn.setCellValueFactory(  new PropertyValueFactory<>("obj_destination"));
            
        found_typeColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_type"));
        found_brandColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_brand"));
        found_colorColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_color"));
        found_signaturesColumn.setCellValueFactory(   new PropertyValueFactory<>("obj_signatures"));
    
        foundLuggageTable.setItems(getFoundLuggage());
        
        
        
        
        
        idMissedLuggageColumn.setCellValueFactory(new PropertyValueFactory<>("idmissedLuggage"));
        missed_timeColumn.setCellValueFactory(    new PropertyValueFactory<>("obj_time"));
        missed_airportColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_airport"));
        missed_dateColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_date"));
        
        missed_nameColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_name"));
        missed_addressColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_address"));
        missed_residenceColumn.setCellValueFactory(    new PropertyValueFactory<>("obj_residence"));
        missed_postalcodeColumn.setCellValueFactory(   new PropertyValueFactory<>("obj_postalcode"));
        missed_countryColumn.setCellValueFactory(      new PropertyValueFactory<>("obj_country"));
        missed_emailColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_email"));

        missed_labelnumberColumn.setCellValueFactory(  new PropertyValueFactory<>("obj_labelnumber"));
        missed_flightnumberColumn.setCellValueFactory( new PropertyValueFactory<>("obj_flightnumber"));
        missed_destinationColumn.setCellValueFactory(  new PropertyValueFactory<>("obj_destination"));
            
        missed_typeColumn.setCellValueFactory(         new PropertyValueFactory<>("obj_type"));
        missed_brandColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_brand"));
        missed_colorColumn.setCellValueFactory(        new PropertyValueFactory<>("obj_color"));
        missed_signaturesColumn.setCellValueFactory(   new PropertyValueFactory<>("obj_signatures"));
    
        
        missedLuggageTable.setItems(getLostLuggage());
        
        
        
    }

    public ObservableList<MissedLuggage> getLostLuggage() {

        ObservableList<MissedLuggage> lostLuggageList = FXCollections.observableArrayList();
        
        try {
            MyJDBC db = new MyJDBC("LostLuggage");

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM missedLuggage");
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("               alles geselecteerd van missed luggage tabel            ");
            System.out.println(" ---------------------------------------------------------------------");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String get_idmissedLuggage =    resultSet.getString("idmissedLuggage");
                String m_get_time =          resultSet.getString("time");
                String m_get_airport =       resultSet.getString("airport");
                String m_get_date =          resultSet.getString("date");
                
                String m_get_name =          resultSet.getString("name");
                String m_get_adress =        resultSet.getString("adress");
                String m_get_residence =     resultSet.getString("residence");
                String m_get_postalcode =    resultSet.getString("postalcode");
                String m_get_country =       resultSet.getString("country");
                String m_get_email =         resultSet.getString("email");
                
                String m_get_labelnumber =   resultSet.getString("labelnumber");
                String m_get_flightnumber =  resultSet.getString("flightnumber");
                String m_get_destination =   resultSet.getString("destination");
                
                String m_get_type =          resultSet.getString("type");
                String m_get_brand =         resultSet.getString("brand");
                String m_get_color =         resultSet.getString("color");
                String m_get_signatures =    resultSet.getString("signatures");
                


                //Per result -> toevoegen aan Luggages  (observable list) 
                lostLuggageList.add(new MissedLuggage(get_idmissedLuggage, m_get_time, m_get_airport, m_get_date, m_get_name, m_get_adress, m_get_residence, m_get_postalcode, m_get_country, m_get_email, m_get_labelnumber, m_get_flightnumber, m_get_destination, m_get_type, m_get_brand, m_get_color, m_get_signatures));
                //luggages.add(new MissedLuggage(get_idmissedLuggage, get_timeField, get_airportField, get_dateDatepicker, get_nameField, get_adressField, get_residenceField, get_postalcodeField, get_countryField, get_emailField, get_labelnumberField, get_flightnumberField, get_destinationField, get_typeField, get_brandField, get_colorField, get_signaturesField));
                
                
                
                // Alle gegevens per result (koffer) ->  printen
                System.out.println("Gegevens voor koffer id: "+get_idmissedLuggage+" |       Zijn: Correct");
                System.out.println("----------------------------------------------------------------------");
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lostLuggageList;
    } 
    
    
    
    
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
                String f_get_time =          resultSet.getString("time");
                String f_get_airport =       resultSet.getString("airport");
                String f_get_date =          resultSet.getString("date");
                
                String f_get_name =          resultSet.getString("name");
                String f_get_adress =        resultSet.getString("adress");
                String f_get_residence =     resultSet.getString("residence");
                String f_get_postalcode =    resultSet.getString("postalcode");
                String f_get_country =       resultSet.getString("country");
                String f_get_email =         resultSet.getString("email");
                
                String f_get_labelnumber =   resultSet.getString("labelnumber");
                String f_get_flightnumber =  resultSet.getString("flightnumber");
                String f_get_destination =   resultSet.getString("destination");
                
                String f_get_type =          resultSet.getString("type");
                String f_get_brand =         resultSet.getString("brand");
                String f_get_color =         resultSet.getString("color");
                String f_get_signatures =    resultSet.getString("signatures");
                


                //Per result -> toevoegen aan Luggages  (observable list) 
                foundLuggageList.add(new FoundLuggage(get_idfoundLuggage, f_get_time, f_get_airport, f_get_date, f_get_name, f_get_adress, f_get_residence, f_get_postalcode, f_get_country, f_get_email, f_get_labelnumber, f_get_flightnumber, f_get_destination, f_get_type, f_get_brand, f_get_color, f_get_signatures));
                
                
                
                // Alle gegevens per result (koffer) ->  printen
                System.out.println("Gegevens voor koffer id: "+get_idfoundLuggage+" |       Zijn: Correct");
                System.out.println("---------------------------------------------------------------------");
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
    
    public void mouseClickedOnRowFound() {
        foundLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                //obj hash code vinden
                Node node_data = ((Node) event.getTarget() ).getParent();
                TableRow found_row;
                
                if (node_data instanceof TableRow) {
                    found_row = (TableRow) node_data;
                } else {
                    // als op de tekst is geklikt -> pak parent van text
                    found_row = (TableRow) node_data.getParent();
                }
                System.out.println("row: item");
                FoundLuggage a = (FoundLuggage) found_row.getItem();
                System.out.println("a: "+a);
                System.out.println("Adress of object: "+ a.getObj_address());
                System.out.println("Label of object: "+ a.getObj_labelnumber());
                System.out.println("type of object: "+ a.getObj_type());
                
                System.out.println("row item; " +found_row.getItem());
                
                
                try {
                    MainApp.switchView("/fxml/ServiceInvoerView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                
            }
        });
    }
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceInvoerView.fxml");
    }
    
    @FXML
    protected void switchToFound(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceGevondenOverzichtView.fxml");
    }
    
    @FXML
    protected void switchToMissed(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceVermisteOverzichtView.fxml");
    }
    
    
    
}
