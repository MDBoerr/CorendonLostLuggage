package is103.lostluggage.Controllers.Service;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.Model.Service.Data.ServiceMoreDetails;
import is103.lostluggage.Model.Service.Data.ServiceSearchData;
import is103.lostluggage.Model.Service.Interface.FoundLuggageTable;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceOverviewFoundViewController implements Initializable, FoundLuggageTable {

        public Stage popupStageFound = new Stage();   

    
        //view title
    private final String title = "Overview Found Luggage";
    
    private static ObservableList<FoundLuggage> foundLuggageList;
    private static ObservableList<FoundLuggage> foundLuggageListSearchResults = FXCollections.observableArrayList();
    
    @FXML JFXTextField searchField;
    @FXML JFXComboBox searchTypeComboBox;
    
    @FXML private TableView<FoundLuggage> foundLuggageTable;

    @FXML private TableColumn<FoundLuggage, String>  foundRegistrationNr;
    @FXML private TableColumn<FoundLuggage, String>  foundDateFound;
    @FXML private TableColumn<FoundLuggage, String>  foundTimeFound;
    
    @FXML private TableColumn<FoundLuggage, String>  foundLuggageTag;
    @FXML private TableColumn<FoundLuggage, String>  foundLuggageType;
    @FXML private TableColumn<FoundLuggage, String>  foundBrand;
    @FXML private TableColumn<FoundLuggage, Integer> foundMainColor;
    @FXML private TableColumn<FoundLuggage, String>  foundSecondColor;
    @FXML private TableColumn<FoundLuggage, Integer> foundSize;
    @FXML private TableColumn<FoundLuggage, String>  foundWeight;
    @FXML private TableColumn<FoundLuggage, String>  foundOtherCharacteristics;
    @FXML private TableColumn<FoundLuggage, Integer> foundPassengerId;
    
    @FXML private TableColumn<FoundLuggage, String>  foundArrivedWithFlight;
    @FXML private TableColumn<FoundLuggage, Integer> foundLocationFound;
    @FXML private TableColumn<FoundLuggage, String>  foundEmployeeId;
    @FXML private TableColumn<FoundLuggage, Integer> foundMatchedId;

    public ServiceDataFound dataListFound;
    private boolean showMatchedLuggage = false;
    private ResultSet matchedLuggageResultSet;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
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
        searchTypeComboBox.setValue("All fields");
        
        
        searchTypeComboBox.setValue("All fields");
        
//        ServiceDataFound dataListFound;
        try {
                        //Initialize Table & obj found 
            dataListFound = new ServiceDataFound();
            initializeFoundLuggageTable();
            
//            dataListFound.getFoundLuggage();
            setFoundLuggageTable(dataListFound.getFoundLuggage());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOverviewFoundViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        
        //set screen status
        MainApp.setOnMatchingView(false);
    }

    @FXML
    protected void showOnlyMatchedLuggage() throws SQLException{
        if (showMatchedLuggage == false){
            showMatchedLuggage = true;
            
            matchedLuggageResultSet = dataListFound.getFoundResultSet(showMatchedLuggage);
        
        setFoundLuggageTable(dataListFound.getObservableList(matchedLuggageResultSet));
        
        
        } else if (showMatchedLuggage == true){
            showMatchedLuggage = false;


            
        matchedLuggageResultSet = dataListFound.getFoundResultSet(showMatchedLuggage);
        
        setFoundLuggageTable(dataListFound.getObservableList(matchedLuggageResultSet));
        }
        
    }

    
     
    @FXML
    public void search() throws SQLException{
        String value = searchTypeComboBox.getValue().toString();
        String search = searchField.getText();
         
        ServiceSearchData searchData = new ServiceSearchData();
        
       
        
        String finalQuery = searchData.getSearchQuery(value, search, "foundluggage");
        
        
        
        
        System.out.println(finalQuery);
        
        
        
        
       
        
        //System.out.println("Query: " +finalQuery);
        
        
        foundLuggageListSearchResults.clear();
        

        try {
            MyJDBC db = MainApp.getDatabase();
            ResultSet resultSet;
            resultSet = db.executeResultSetQuery(finalQuery);
            
            foundLuggageListSearchResults=searchData.getSearchList(resultSet);
            foundLuggageTable.setItems(foundLuggageListSearchResults);
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOverviewFoundViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }
    
    
    
    

    //@Override
    public void setFoundLuggageTable(ObservableList<FoundLuggage> list) {
         foundLuggageTable.setItems(list);
    }

    @Override
    public void initializeFoundLuggageTable() {
        foundRegistrationNr.setCellValueFactory(       new PropertyValueFactory<>("registrationNr"));
        foundDateFound.setCellValueFactory(            new PropertyValueFactory<>("dateFound"));
        foundTimeFound.setCellValueFactory(            new PropertyValueFactory<>("timeFound"));
        
        foundLuggageTag.setCellValueFactory(           new PropertyValueFactory<>("luggageTag"));
        foundLuggageType.setCellValueFactory(          new PropertyValueFactory<>("luggageType"));
        foundBrand.setCellValueFactory(                new PropertyValueFactory<>("brand"));
        foundMainColor.setCellValueFactory(            new PropertyValueFactory<>("mainColor"));
        foundSecondColor.setCellValueFactory(          new PropertyValueFactory<>("secondColor"));
        foundSize.setCellValueFactory(                 new PropertyValueFactory<>("size"));
        foundWeight.setCellValueFactory(               new PropertyValueFactory<>("weight"));

        foundOtherCharacteristics.setCellValueFactory( new PropertyValueFactory<>("otherCharacteristics"));
        foundPassengerId.setCellValueFactory(          new PropertyValueFactory<>("passengerId"));
        
        foundArrivedWithFlight.setCellValueFactory(    new PropertyValueFactory<>("arrivedWithFlight"));
        foundLocationFound.setCellValueFactory(        new PropertyValueFactory<>("locationFound"));
        foundEmployeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
        foundMatchedId.setCellValueFactory(            new PropertyValueFactory<>("matchedId"));

    }
    /**  
     * @void doubleClickFoundRow
     */
    public void foundRowClicked() {
        foundLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                ServiceMoreDetails foundDetails = new ServiceMoreDetails();
                foundDetails.setDetailsOfRow("found", event, popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml");
                foundDetails.setAndOpenPopUpDetails(popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
               
            }
        });
    }

    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInputLuggageView.fxml");
    }
    
    @FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
    }

    @Override
    public void setFoundLuggageTable(ServiceDataFound dataListFound) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}