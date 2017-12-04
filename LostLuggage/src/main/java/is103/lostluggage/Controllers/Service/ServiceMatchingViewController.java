package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.LuggageDetails;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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
    
    @FXML private TableView<FoundLuggage> foundLuggageTable;

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
    
    
    
    
    public Stage popupStage = new Stage();
    
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
        
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        
        
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
                int matchedId =             resultSet.getInt("matchedId");

                


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
                
                //get het goede found luggage object -> plaats in getDetailObj
                FoundLuggage getDetailObj = (FoundLuggage) found_row.getItem();
                
                //get row item
                System.out.println("row item; " +found_row.getItem());
                
                     
                
                //Detail object zetten -> zodat hij in volgende view te openen is
//                LuggageDetails.getInstance().currentLuggage().setIdfoundLuggage(getDetailObj.getIdfoundLuggage());
//                LuggageDetails.getInstance().currentLuggage().setObj_type(getDetailObj.getObj_type());
//                LuggageDetails.getInstance().currentLuggage().setObj_brand(getDetailObj.getObj_brand());
//                LuggageDetails.getInstance().currentLuggage().setObj_color(getDetailObj.getObj_color());
//                LuggageDetails.getInstance().currentLuggage().setObj_signatures(getDetailObj.getObj_signatures());
//        
                //Switchen naar de detailed view
//                try {
//                    MainApp.switchView("/Views/Service/ServiceDetailedLuggageView.fxml");
//                } catch (IOException ex) {
//                    Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
                //switchen naar detailed viw dmv: popup
                try {
                    popUpDetails(popupStage);
                } catch (IOException ex) {
                    Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void popUpDetails(Stage stage) throws IOException {
        try { 
            //get popup fxml resource   
            Parent popup = FXMLLoader.load(getClass().getResource("/Views/Service/ServiceDetailedLuggageView.fxml"));
            stage.setScene(new Scene(popup));
                
            //no functies -> close / fullscreen/ topbar
            //stage.initStyle(StageStyle.TRANSPARENT);
            
            //stage altijd on top
            stage.setAlwaysOnTop(true);
            
            if (stage.isShowing()){
                //Stage was open -> refresh
                stage.close();
                stage.show();
            } else {
                //Stage was gesloten -> alleen openen
                stage.show();
                System.out.println("Popup opend");
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    
    
    
}
