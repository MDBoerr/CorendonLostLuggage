package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class voor vermiste bagage overzicht
 *
 * @author Thijs Zijdel
 */
public class ServiceVermisteOverzichtViewController implements Initializable {

    //luggage list
    public static ObservableList<Luggage> luggageList;

    
    
    
    
    
    /* -----------------------------------------
            TableView luggage's colommen
    ----------------------------------------- */
    
    @FXML
    private TableView<Luggage> vermistTable;

    @FXML
    private TableColumn<Luggage, String> idmissedLuggage;
    @FXML
    private TableColumn<Luggage, String> timeField;
    @FXML
    private TableColumn<Luggage, String> aiportField;
    @FXML
    private TableColumn<Luggage, String> dateDatepicker;
    
    
    @FXML
    private TableColumn<Luggage, String> nameField;
    @FXML
    private TableColumn<Luggage, String> addressField;
    @FXML
    private TableColumn<Luggage, String> residenceField;
    @FXML
    private TableColumn<Luggage, String> postalcodeField;
    @FXML
    private TableColumn<Luggage, String> countryField;
    @FXML
    private TableColumn<Luggage, String> emailField;
    
    @FXML
    private TableColumn<Luggage, String> labelnumberField;
    @FXML
    private TableColumn<Luggage, String> flightnumberField;
    @FXML
    private TableColumn<Luggage, String> destinationField;
    
    @FXML
    private TableColumn<Luggage, String> typeField;
    @FXML
    private TableColumn<Luggage, String> brandField;
    @FXML
    private TableColumn<Luggage, String> colorField;
    @FXML
    private TableColumn<Luggage, String> signaturesField;

                        

    
    
    
    //view title
    private final String title = "Overzicht Vermiste Bagage";
    
    
   

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
        
        



//** ----------------------------------------------------------------------------------------------------------
//                          OUDE MANIER             
//                                          
//                     nog niet verwijderen aub     
//                                          
//     
//        //als de luggelist leeg is --> dan dit uitvoeren \/
//        if (luggageList == null) {
//            luggageList = FXCollections.observableArrayList();
//
//            //dummy data invoeren in de tabel             /-> momenteel meer gegevens
//            luggageList.add(new Luggage((id++), "A392D4K", "Tomos", "Trolley", "D383D", "Amsterdam Airport", "rode sticker", "reiziger (id?) "));
//            luggageList.add(new Luggage((id++), "C38DKE3", "East Pack", "Rugzak", "A74D0", "Antalya Aiport", "zonder handvat", "reiziger"));
//        }
//
//        //voor elke colum data vullen (bij verandering en initializatie
//        for (int i = 0; i < vermistTable.getColumns().size(); i++) {
//            TableColumn tc = (TableColumn) vermistTable.getColumns().get(i);
//
//            tc.setCellValueFactory(new PropertyValueFactory<>(tc.getId()));
//        }
///--------------------------------------------------------------------------------------------------------- */         
      
        
        
        







    //Het probleem:




        
    /** -----------------------------------------
     * De ids van de tabel: vermistTable  toekennen
     * En vervolgens de luggages hierin plaatsen
     * 
     * WERKT NIET...  -> //Nul pointer....
     * 
     * 
     * @return data vanuit database in tabel.
    /*----------------------------------------- */       
        
            //-> colum id (fxml)                                    <Luggage, String>        //-> 
        idmissedLuggage.setCellValueFactory(new PropertyValueFactory<>("idmissedLuggage"));
        timeField.setCellValueFactory(      new PropertyValueFactory<>("timeField"));
        aiportField.setCellValueFactory(    new PropertyValueFactory<>("airportField"));
        dateDatepicker.setCellValueFactory( new PropertyValueFactory<>("dateDatepicker"));
        
        nameField.setCellValueFactory(      new PropertyValueFactory<>("nameField"));
        addressField.setCellValueFactory(    new PropertyValueFactory<>("addressField"));
        residenceField.setCellValueFactory( new PropertyValueFactory<>("residenceField"));
        postalcodeField.setCellValueFactory(new PropertyValueFactory<>("postalcodeField"));
        countryField.setCellValueFactory(   new PropertyValueFactory<>("countryField"));
        emailField.setCellValueFactory(     new PropertyValueFactory<>("emailField"));

        labelnumberField.setCellValueFactory(   new PropertyValueFactory<>("labelnumberField"));
        flightnumberField.setCellValueFactory(  new PropertyValueFactory<>("flightnumberField"));
        destinationField.setCellValueFactory(   new PropertyValueFactory<>("destinationField"));
            
        typeField.setCellValueFactory(      new PropertyValueFactory<>("typeField"));
        brandField.setCellValueFactory(     new PropertyValueFactory<>("brandField"));
        colorField.setCellValueFactory(     new PropertyValueFactory<>("colorField"));
        signaturesField.setCellValueFactory(new PropertyValueFactory<>("signaturesField"));
    
        
        vermistTable.setItems(getLuggage());
        
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
    public ObservableList<Luggage> getLuggage() {

        ObservableList<Luggage> luggages = FXCollections.observableArrayList();
        
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
                String get_timeField =          resultSet.getString("timeField");
                String get_airportField =       resultSet.getString("airportField");
                String get_dateDatepicker =     resultSet.getString("dateDatepicker");
                
                String get_nameField =          resultSet.getString("nameField");
                String get_adressField =        resultSet.getString("adressField");
                String get_residenceField =     resultSet.getString("residenceField");
                String get_postalcodeField =    resultSet.getString("postalcodeField");
                String get_countryField =       resultSet.getString("countryField");
                String get_emailField =         resultSet.getString("emailField");
                
                String get_labelnumberField =   resultSet.getString("labelnumberField");
                String get_flightnumberField =  resultSet.getString("flightnumberField");
                String get_destinationField =   resultSet.getString("destinationField");
                
                String get_typeField =          resultSet.getString("typeField");
                String get_brandField =         resultSet.getString("brandField");
                String get_colorField =         resultSet.getString("colorField");
                String get_signaturesField =    resultSet.getString("signaturesField");
                


                //Per result -> toevoegen aan Luggages  (observable list) 
                luggages.add(new Luggage(get_idmissedLuggage, get_timeField, get_airportField, get_dateDatepicker, get_nameField, get_adressField, get_residenceField, get_postalcodeField, get_countryField, get_emailField, get_labelnumberField, get_flightnumberField, get_destinationField, get_typeField, get_brandField, get_colorField, get_signaturesField));
                //luggages.add(new Luggage(get_idmissedLuggage, get_timeField, get_airportField, get_dateDatepicker, get_nameField, get_adressField, get_residenceField, get_postalcodeField, get_countryField, get_emailField, get_labelnumberField, get_flightnumberField, get_destinationField, get_typeField, get_brandField, get_colorField, get_signaturesField));
                
                
                
                // Alle gegevens per result (koffer) ->  printen
                System.out.println("Gegevens voor koffer id: "+get_idmissedLuggage);
                System.out.println("Time: "+ get_timeField + " Airport: " + get_airportField+" Datum:"+get_dateDatepicker);
                System.out.println("Naam: "+ get_nameField + " Adress: "+get_adressField+"Plaats: "+get_residenceField);
                System.out.println("Postcode: "+get_postalcodeField+" Land: "+ get_countryField+" Email: "+get_emailField);
                System.out.println("Labelnum: "+get_labelnumberField+" Vlucht: "+get_flightnumberField+" Bestemming: "+get_destinationField);
                System.out.println("Type bagage: "+get_typeField+" Merk: "+get_brandField+" Kleur: "+get_colorField+" Kenmerken: "+get_signaturesField);
                System.out.println(" ---------------------------------------------------------------------");
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return luggages;
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

    
    
    
    
    
    
    
    
    /* -----------------------------------------
                Komt later..! 
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
