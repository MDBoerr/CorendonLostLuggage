package is103.lostluggage.Controllers.Manager;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.LostLuggage;

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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ahmet
 * @author Thijs Zijdel - 500782165             For the search functionality
 */
public class ManagerLostViewController implements Initializable {
private final String title = "Overzicht Vermiste Bagage";
    
    public static ObservableList<LostLuggage> lostLuggageList;
    
    
    //TableView found luggage's colommen
    
    
    @FXML
    private TableView<LostLuggage> lostTable;

    @FXML private TableColumn<LostLuggage, String>  managerLostRegistrationNr;
    @FXML private TableColumn<LostLuggage, String>  managerLostDateLost;
    @FXML private TableColumn<LostLuggage, String>  managerLostTimeLost;
    
    @FXML private TableColumn<LostLuggage, String>  managerLostLuggageTag;
    @FXML private TableColumn<LostLuggage, String>  managerLostLuggageType;
    @FXML private TableColumn<LostLuggage, String>  managerLostBrand;
    @FXML private TableColumn<LostLuggage, Integer> managerLostMainColor;
    @FXML private TableColumn<LostLuggage, String>  managerLostSecondColor;
    @FXML private TableColumn<LostLuggage, Integer> managerLostSize;
    @FXML private TableColumn<LostLuggage, String>  managerLostWeight;
    @FXML private TableColumn<LostLuggage, String>  managerLostOtherCharacteristics;
    @FXML private TableColumn<LostLuggage, Integer> managerLostPassengerId;
    
    @FXML private TableColumn<LostLuggage, String> managerLostFlight;
    
    @FXML private TableColumn<LostLuggage, String> managerLostEmployeeId;
    @FXML private TableColumn<LostLuggage, Integer> managerLostMatchedId;


    //list for the search results
    private static ObservableList<LostLuggage> lostLuggageListSearchResults 
            = FXCollections.observableArrayList();
    
    //text field for the users search input
    @FXML JFXTextField searchField;
    //combo box for a type/field/column search filter
    @FXML JFXComboBox searchTypeComboBox;

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
         
         
        //de data vanuit de database halen     
        
  
        managerLostRegistrationNr.setCellValueFactory(new PropertyValueFactory<>("registrationNr"));
        managerLostDateLost.setCellValueFactory(new PropertyValueFactory<>("dateLost"));
        managerLostTimeLost.setCellValueFactory(new PropertyValueFactory<>("timeLost"));
        
        managerLostLuggageTag.setCellValueFactory(new PropertyValueFactory<>("luggageTag"));
        managerLostLuggageType.setCellValueFactory(new PropertyValueFactory<>("luggageType"));
        managerLostBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        managerLostMainColor.setCellValueFactory(new PropertyValueFactory<>("mainColor"));
        managerLostSecondColor.setCellValueFactory(new PropertyValueFactory<>("secondColor"));
        managerLostSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        managerLostWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        managerLostOtherCharacteristics.setCellValueFactory(new PropertyValueFactory<>("otherCharacteristics"));
        managerLostPassengerId.setCellValueFactory(new PropertyValueFactory<>("passengerId"));
        
        managerLostFlight.setCellValueFactory(new PropertyValueFactory<>("flight"));
        
        managerLostEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        managerLostMatchedId.setCellValueFactory(new PropertyValueFactory<>("matchedId"));

    
        
        
            lostTable.setItems(getLostLuggage());
        
            lostTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
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

                        Logger.getLogger(ManagerLostViewController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }

            }

        });
        
    }
    
    
    
    
    
     
    public ObservableList<LostLuggage> getLostLuggage() {

        ObservableList<LostLuggage> lostLuggageList = FXCollections.observableArrayList();
        
        try {
             MyJDBC db = getDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM lostluggage.lostluggage");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (lostLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateLost =          resultSet.getString("dateLost");
                String timeLost =          resultSet.getString("timeLost");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                String mainColor =             resultSet.getString("mainColor");
                String secondColor =           resultSet.getString("secondColor");
                String size =                  resultSet.getString("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String flight =              resultSet.getString("flight"); 
                
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =              resultSet.getInt("matchedId");

                


                //Per result -> toevoegen aan Luggages  (observable list) 
                lostLuggageList.add(
                        new LostLuggage(
                                registrationNr, 
                                dateLost, 
                                timeLost, 
                                
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
                
                
                
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lostLuggageList;
    }
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerLostView.fxml");
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
     * @call - getLostLuggageSearchList     the result list based on the resultSet
     */
    @FXML
    public void search() throws SQLException{
        //get the value of the search type combo box for filtering to a specific column
        String value = searchTypeComboBox.getValue().toString();
        //get the user input for the search
        String search = searchField.getText();
         
        //Create a new searchData object
        ServiceSearchData searchData = new ServiceSearchData();
        
        //Get the right query based on the users input
        String finalQuery = searchData.getSearchQuery(value, search, "lostluggage");
        
        //clear the previous search result list 
        lostLuggageListSearchResults.clear();
        
        //try to execute the query from the database
        //@throws SQLException  
        try {
            //get the connection to the datbase
            MyJDBC db = MainApp.getDatabase();
            //create a new resultSet and execute the right query
            ResultSet resultSet = db.executeResultSetQuery(finalQuery);
            
            //get the observableList from the search object and asign this to the list
            lostLuggageListSearchResults = searchData.getLostLuggageSearchList(resultSet);
            //set this list on the table
            lostTable.setItems(lostLuggageListSearchResults);
            
            //set the right place holder message for when there are no hits
            lostTable.setPlaceholder(new Label("No hits based on your search"));
        } catch (SQLException ex) {
            Logger.getLogger(ManagerLostViewController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}