package is103.lostluggage.Controllers.Manager;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.Admin.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
//import is103.lostluggage.Controllers.Service.Luggage;
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
import static is103.lostluggage.MainApp.getDatabase;
import is103.lostluggage.Model.Service.Data.ServiceSearchData;
import is103.lostluggage.Model.Service.Interface.Search;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ahmet
 * @author Thijs Zijdel - 500782165             For the search functionality
 */
public class ManagerFoundViewController implements Initializable, Search {
private final String title = "Overzicht Gevonden Bagage";
    
    public static ObservableList<FoundLuggage> foundLuggageList;
    
    
    //TableView found luggage's colommen
   
    
    @FXML
    private TableView<FoundLuggage> foundLuggage;

    @FXML private TableColumn<FoundLuggage, String>  managerFoundRegistrationNr;
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

    

    
    
    
    //list for the search results
    private static ObservableList<FoundLuggage> foundLuggageListSearchResults 
            = FXCollections.observableArrayList();
    
    //text field for the users search input
    @FXML JFXTextField searchField;
    //combo box for a type/field/column search filter
    @FXML JFXComboBox searchTypeComboBox;
    
    
    
    
    /**
     * Initializes the controller class.
     */

 //luggage list
    //public static ObservableList<Luggage> luggageList;
    private int id = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
         try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
         
         initializeComboFilterBox();
         
         
         
         
        /** -----------------------------------------
        *    DATA vanuit database in tabel plaatsen
        * 
        * @return data vanuit database in tabel.
       /*----------------------------------------- */       
        
  
        managerFoundRegistrationNr.setCellValueFactory(       new PropertyValueFactory<>("registrationNr"));
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
       
    
        
        
        foundLuggage.setItems(getFoundLuggage());
        
            foundLuggage.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
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

                        Logger.getLogger(ManagerFoundViewController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }

            }

        });
        
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
             MyJDBC db = getDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM foundluggage");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (foundLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateFound =          resultSet.getString("dateFound");
                String timeFound =          resultSet.getString("timeFound");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                String mainColor =             resultSet.getString("mainColor");
                String secondColor =           resultSet.getString("secondColor");
                String size =                  resultSet.getString("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String arrivedWithFlight =  resultSet.getString("arrivedWithFlight"); 
                String locationFound =         resultSet.getString("locationFound");
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
                
                
                
                

            
            }

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerFoundView.fxml");
    }
    

    
    
    
    /**  
     * @author Thijs Zijdel - 500782165
     * 
     * This method is for initializing the filter combo box
     * The comboBox is used for filtering to a specific:  
     *                                               column/ field/ detail
     *                                                          When searching.
     * @void - No direct output 
     */
    private void initializeComboFilterBox() {
        //add all the filterable fields
        searchTypeComboBox.getItems().addAll(
            "All fields",
            "RegistrationNr", 
            "LuggageTag", 
            "Brand",
            "Color",
            "Weight",
            "Date",
            "Passenger",
            "Location",
            "Characteristics");
        //set the standard start value to all fields
        searchTypeComboBox.setValue("All fields");
    }
    
    /**  
     * @author Thijs Zijdel - 500782165
     * 
     * This method is for searching in the table
     * There will be searched on the typed user input and filter value
     * Note: this method is called after each time the user releases a key
     * 
     * @throws java.sql.SQLException        searching in the database
     * @void - No direct output             only changing results in the table
     * @call - getSearchQuery               for getting the search query
     * @call - executeResultSetQuery        for getting the resultSet
     * @call - getFoundLuggageSearchList    the result list based on the resultSet
     */ 
    @FXML
    @Override
    public void search() throws SQLException{
        //get the value of the search type combo box for filtering to a specific column
        String value = searchTypeComboBox.getValue().toString();
        //get the user input for the search
        String search = searchField.getText();
         
        //Create a new searchData object
        ServiceSearchData searchData = new ServiceSearchData();

        //Get the right query based on the users input
        String finalQuery = searchData.getSearchQuery(value, search, "foundluggage");
        
        //clear the previous search result list 
        foundLuggageListSearchResults.clear();
        
        //try to execute the query from the database
        //@throws SQLException 
        try {
            //get the connection to the datbase
            MyJDBC db = MainApp.getDatabase();
            //create a new resultSet and execute the right query
            ResultSet resultSet = db.executeResultSetQuery(finalQuery);
            
            //get the observableList from the search object and asign this to the list
            foundLuggageListSearchResults=searchData.getFoundLuggageSearchList(resultSet);
            //set this list on the table
            foundLuggage.setItems(foundLuggageListSearchResults);   
            
            //set the right place holder message for when there are no hits
            foundLuggage.setPlaceholder(new Label("No hits based on your search"));
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFoundViewController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
        
       
 
            


