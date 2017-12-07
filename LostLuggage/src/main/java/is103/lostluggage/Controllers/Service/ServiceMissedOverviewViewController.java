package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Model.MissedLuggage;
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
import javafx.scene.paint.Paint;

/**
 * FXML Controller class voor vermiste bagage overzicht
 *
 * @author Thijs Zijdel
 */
public class ServiceMissedOverviewViewController implements Initializable {

    
        //view title
    private final String title = "Overzicht Vermiste Bagage";
    
    public static ObservableList<MissedLuggage> MissedLuggageList;
    
    @FXML  
    private JFXTextField searchField;
    
    //value of input
    private String searchInput; 
    
    /* -----------------------------------------
         TableView missed luggage's colommen
    ----------------------------------------- */
    
    @FXML private TableView<MissedLuggage> missedLuggageTable;

    @FXML private TableColumn<MissedLuggage, String>  missedRegistrationNr;
    @FXML private TableColumn<MissedLuggage, String>  missedDateLost;
    @FXML private TableColumn<MissedLuggage, String>  missedTimeLost;
    
    @FXML private TableColumn<MissedLuggage, String>  missedLuggageTag;
    @FXML private TableColumn<MissedLuggage, String>  missedLuggageType;
    @FXML private TableColumn<MissedLuggage, String>  missedBrand;
    @FXML private TableColumn<MissedLuggage, Integer> missedMainColor;
    @FXML private TableColumn<MissedLuggage, String>  missedSecondColor;
    @FXML private TableColumn<MissedLuggage, Integer> missedSize;
    @FXML private TableColumn<MissedLuggage, String>  missedWeight;
    @FXML private TableColumn<MissedLuggage, String>  missedOtherCharacteristics;
    @FXML private TableColumn<MissedLuggage, Integer> missedPassengerId;
    
    @FXML private TableColumn<MissedLuggage, String>  missedFlight;
    @FXML private TableColumn<MissedLuggage, String>  missedEmployeeId;
    @FXML private TableColumn<MissedLuggage, Integer> missedMatchedId;
    
   

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //switch to previous view
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
           

        initializeMissedLuggageTable();
    }
    
    
    
    public void initializeMissedLuggageTable(){
        missedRegistrationNr.setCellValueFactory(       new PropertyValueFactory<>("registrationNr"));
        missedDateLost.setCellValueFactory(            new PropertyValueFactory<>("dateFound"));
        missedTimeLost.setCellValueFactory(            new PropertyValueFactory<>("timeFound"));
        
        missedLuggageTag.setCellValueFactory(           new PropertyValueFactory<>("luggageTag"));
        missedLuggageType.setCellValueFactory(          new PropertyValueFactory<>("luggageType"));
        missedBrand.setCellValueFactory(                new PropertyValueFactory<>("brand"));
        missedMainColor.setCellValueFactory(            new PropertyValueFactory<>("mainColor"));
        missedSecondColor.setCellValueFactory(          new PropertyValueFactory<>("secondColor"));
        missedSize.setCellValueFactory(                 new PropertyValueFactory<>("size"));
        missedWeight.setCellValueFactory(               new PropertyValueFactory<>("weight"));

        missedOtherCharacteristics.setCellValueFactory( new PropertyValueFactory<>("otherCharacteristics"));
        missedPassengerId.setCellValueFactory(          new PropertyValueFactory<>("passengerId"));
        
        missedFlight.setCellValueFactory(    new PropertyValueFactory<>("flight"));
        missedEmployeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
        missedMatchedId.setCellValueFactory(            new PropertyValueFactory<>("matchedId")); 

        missedLuggageTable.setItems(getMissedLuggage());
    }
    

    /**  
     * @return missedLuggages
     */
    public ObservableList<MissedLuggage> getMissedLuggage() {

        ObservableList<MissedLuggage> missedLuggageList = FXCollections.observableArrayList();
        
        try {
            MyJDBC db = MainApp.connectToDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM lostLuggage");
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("               alles geselecteerd van missed luggage tabel            ");
            System.out.println(" ---------------------------------------------------------------------");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateFound =          resultSet.getString("dateLost");
                String timeFound =          resultSet.getString("timeLost");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                int size =                  resultSet.getInt("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String flight =  resultSet.getString("flight"); 
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");


                //Per result -> toevoegen aan Luggages  (observable list) 
                missedLuggageList.add(
                        new MissedLuggage(
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
                                
                                flight, 
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
        return missedLuggageList;
    }
    


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
        try {
            MainApp.switchView("/fxml/ServiceInvoerView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        try {
            MainApp.switchView("/fxml/ServiceMatchingView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @FXML
    protected void searchStarted(ActionEvent event) throws IOException {
        if (searchField.getText().isEmpty()) {
            System.out.println("Pleas fill something in the search box");
            searchField.setUnFocusColor(Paint.valueOf("#f03e3e"));
            searchField.setFocusColor(Paint.valueOf("#f03e3e"));
        } else {
            searchInput = searchField.getText();
            System.out.println("Search for: "+searchInput);
            searchField.setUnFocusColor(Paint.valueOf("#4d4d4d"));
            searchField.setFocusColor(Paint.valueOf("#4d4d4d"));
        }
        
        
    }
    
   

}