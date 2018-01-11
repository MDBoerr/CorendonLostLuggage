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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.Model.Service.Data.ServiceSearchData;
import is103.lostluggage.Model.Service.Interface.LostLuggageTable;
import is103.lostluggage.Model.Service.Interface.Search;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Ahmet
 * @author Thijs Zijdel - 500782165             
 */
public class ManagerLostViewController implements Initializable, LostLuggageTable, Search {
    //view title
    private final String TITLE = "Overview Lost Luggage";
    
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
    
        //Object for getting the data
    private ServiceDataLost dataListLost;
    
        //show alos matched luggage state, by default on false
    private boolean showMatchedLuggage = false;
    //resultSet for the items when changing the state of the showMatchedLuggage
    private ResultSet matchedLuggageResultSet;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
         try {
            MainViewController.getInstance().getTitle(TITLE);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
         
        initializeComboFilterBox(); 
         
         
   
        
        initializeLostLuggageTable();
        
        try {
            //Initialize Table & obj lost
            dataListLost = new ServiceDataLost();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerLostViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLostLuggageTable(dataListLost.getLostLuggage());
    
        
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
    /**  
     * @author Thijs Zijdel - 500782165
     * 
     * This method is for toggle showing only the matched luggage
     * Note: this is being called by the -fx- toggle button
     * 
     * @throws java.sql.SQLException        getting data from the database
     * @void - No direct output             only changing results in the table
     */
    @FXML
    protected void showOnlyMatchedLuggage() throws SQLException{
        //if state of show only matched is false
        if (showMatchedLuggage == false){
            //set it to true
            showMatchedLuggage = true;
            
            //asign the right resultset to the matchedLuggageResultSet
            //note: this is based on the boolean status that's set previously
            matchedLuggageResultSet = dataListLost.getLostResultSet(showMatchedLuggage);
        
            //set the table with the right data, the resultset will be converted
            setLostLuggageTable(dataListLost.getObservableList(matchedLuggageResultSet));
        
        //if the state of only matched was already true
        } else if (showMatchedLuggage == true){
            //set the state to false, so only non matched luggage's are shown
            showMatchedLuggage = false;

            //asign the right resultset to the matchedLuggageResultSet
            //note: this is based on the boolean status that's set previously
            matchedLuggageResultSet = dataListLost.getLostResultSet(showMatchedLuggage);
        
            //set the table with the right data, the resultset will be converted
            setLostLuggageTable(dataListLost.getObservableList(matchedLuggageResultSet));
        }  
    }
    /**  
     * @author Thijs Zijdel - 500782165
     * 
     * Here is lost luggage table overview initialized with the right values
     * 
     * @void - No direct output 
     */
    @Override
    public void initializeLostLuggageTable(){
        managerLostRegistrationNr.setCellValueFactory(      new PropertyValueFactory<>("registrationNr"));
        managerLostDateLost.setCellValueFactory(            new PropertyValueFactory<>("dateLost"));
        managerLostTimeLost.setCellValueFactory(            new PropertyValueFactory<>("timeLost"));
        
        managerLostLuggageTag.setCellValueFactory(          new PropertyValueFactory<>("luggageTag"));
        managerLostLuggageType.setCellValueFactory(         new PropertyValueFactory<>("luggageType"));
        managerLostBrand.setCellValueFactory(               new PropertyValueFactory<>("brand"));
        managerLostMainColor.setCellValueFactory(           new PropertyValueFactory<>("mainColor"));
        managerLostSecondColor.setCellValueFactory(         new PropertyValueFactory<>("secondColor"));
        managerLostSize.setCellValueFactory(                new PropertyValueFactory<>("size"));
        managerLostWeight.setCellValueFactory(              new PropertyValueFactory<>("weight"));

        managerLostOtherCharacteristics.setCellValueFactory(new PropertyValueFactory<>("otherCharacteristics"));
        managerLostPassengerId.setCellValueFactory(         new PropertyValueFactory<>("passengerId"));
        
        managerLostFlight.setCellValueFactory(              new PropertyValueFactory<>("flight"));
        
        managerLostEmployeeId.setCellValueFactory(          new PropertyValueFactory<>("employeeId"));
        managerLostMatchedId.setCellValueFactory(           new PropertyValueFactory<>("matchedId"));
         
        //set place holder text when there are no results
        lostTable.setPlaceholder(new Label("No lost luggage's to display"));
    }
    
    /**  
     * @author Thijs Zijdel - 500782165
     * 
     * Here will the lost luggage table be set with the right data
     * The data (observable< lostluggage>list) comes from the dataListLost
     * 
     * @param list
     * @void - No direct output 
     * @call - set lostLuggageTable             
     */
    @Override
    public void setLostLuggageTable(ObservableList<LostLuggage> list){
        lostTable.setItems(list);  
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
    @Override
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