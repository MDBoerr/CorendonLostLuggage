package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class voor vermiste bagage overzicht
 *
 * @author Thijs Zijdel
 */
public class ServiceVermisteOverzichtViewController implements Initializable {

    
        //view title
    private final String title = "Overzicht Vermiste Bagage";
    
    public static ObservableList<Luggage> luggageList;
    
    /* -----------------------------------------
         TableView missed luggage's colommen
    ----------------------------------------- */
    
    @FXML
    private TableView<Luggage> missedLuggageTable;

    @FXML
    private TableColumn<Luggage, String> idMissedLuggageColumn;
    @FXML
    private TableColumn<Luggage, String> timeColumn;
    @FXML
    private TableColumn<Luggage, String> airportColumn;
    @FXML
    private TableColumn<Luggage, String> dateColumn;
    
    @FXML
    private TableColumn<Luggage, String> nameColumn;
    @FXML
    private TableColumn<Luggage, String> addressColumn;
    @FXML
    private TableColumn<Luggage, String> residenceColumn;
    @FXML
    private TableColumn<Luggage, String> postalcodeColumn;
    @FXML
    private TableColumn<Luggage, String> countryColumn;
    @FXML
    private TableColumn<Luggage, String> emailColumn;
    
    @FXML
    private TableColumn<Luggage, String> labelnumberColumn;
    @FXML
    private TableColumn<Luggage, String> flightnumberColumn;
    @FXML
    private TableColumn<Luggage, String> destinationColumn;
    
    @FXML
    private TableColumn<Luggage, String> typeColumn;
    @FXML
    private TableColumn<Luggage, String> brandColumn;
    @FXML
    private TableColumn<Luggage, String> colorColumn;
    @FXML
    private TableColumn<Luggage, String> signaturesColumn;

                        
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
        
            //-> colum id (fxml)                                    <Luggage, String>        //-> 
        idMissedLuggageColumn.setCellValueFactory(new PropertyValueFactory<>("idmissedLuggage"));
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
    
        
        missedLuggageTable.setItems(getLostLuggage());
        
    }

    /** -----------------------------------------
     * Vermiste bagage uit de database tabel -> missedLuggage halen
     * Deze gegevens vervolgens (tijdelijk) in variabelen opslaan
     * Deze gegevens in object (Luggage) plaatsen
     * 
     * Resultaten per resultaat (koffer) -> (voor check) uiprinten (console)
     * 
     * 
     * @return luggages --> lijst met data van vermiste koffers
     /*----------------------------------------- */
    public ObservableList<Luggage> getLostLuggage() {

        ObservableList<Luggage> lostLuggageList = FXCollections.observableArrayList();
        
        try {
            MyJDBC db = new MyJDBC("AirlineDemo");

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM missedLuggage");
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("               alles geselecteerd van missed luggage tabel            ");
            System.out.println(" ---------------------------------------------------------------------");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String get_idmissedLuggage =    resultSet.getString("idmissedLuggage");
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
                lostLuggageList.add(new Luggage(get_idmissedLuggage, get_time, get_airport, get_date, get_name, get_adress, get_residence, get_postalcode, get_country, get_email, get_labelnumber, get_flightnumber, get_destination, get_type, get_brand, get_color, get_signatures));
                //luggages.add(new Luggage(get_idmissedLuggage, get_timeField, get_airportField, get_dateDatepicker, get_nameField, get_adressField, get_residenceField, get_postalcodeField, get_countryField, get_emailField, get_labelnumberField, get_flightnumberField, get_destinationField, get_typeField, get_brandField, get_colorField, get_signaturesField));
                
                
                
                // Alle gegevens per result (koffer) ->  printen
                System.out.println("Gegevens voor koffer id: "+get_idmissedLuggage);
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
        return lostLuggageList;
    }
    

     /* -----------------------------------------
                terug knop 
    ----------------------------------------- */
    @FXML
    protected void backHomeButton(ActionEvent event) throws IOException {
        try {
            MainApp.switchView("/fxml/ServiceHomeView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceInvoerView.fxml");
    }
    
@   FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceMatchingView.fxml");
    }
    
    
    
    
    
    /* -----------------------------------------
                Komt later..! 
                Is voor het invoeren..
    ----------------------------------------- */
    
//    public static List addToList(int id, String label, String merk, String type, String vlucht, String luchthaven, String kenmerken, String reiziger) {
//        luggageList.add(new Luggage(id, label, merk, type, vlucht, luchthaven, kenmerken, reiziger));
//
//        //System.out.println(luggageList);
//        return luggageList;
//    
//    INSERT INTO `AirlineDemo`.`missedLuggage` (`idmissedLuggage`, `timeField`, `airportField`, `dateDatepicker`, `nameField`, `adressField`, `residenceField`, `postalcodeField`, `countryField`, `emailField`, `labelnumberField`, `flightnumberField`, `destinationField`, `typeField`, `brandField`, `colorField`) VALUES ('13', '13:32', 'ANT', '12-02-2017', 'Henk Astrid', 'Julianalaan 4', 'Amstelveen', '1932 SA', 'Nederland', 'Henk.Astrid@mail.com', '2983DLO932', 'ANT9389AMD', 'AMD', 'Rugtas', 'Eastpak', 'Zwart');
//        luggages.add(new Luggage("......,.....,...."));
//
//    }

   
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