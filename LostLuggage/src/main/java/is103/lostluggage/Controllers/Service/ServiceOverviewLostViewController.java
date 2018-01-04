package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Data.ServiceMoreDetails;
import is103.lostluggage.Model.Service.Data.ServiceSearchData;
import is103.lostluggage.Model.Service.Interface.LostLuggageTable;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FXML Controller class voor vermiste bagage overzicht
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceOverviewLostViewController implements Initializable, LostLuggageTable {

            private Stage popupStageLost = new Stage();   

        //view title
    private final String title = "Overview Lost Luggage";
    
    private static ObservableList<LostLuggage> foundLuggageList;
    private static ObservableList<LostLuggage> lostLuggageListSearchResults = FXCollections.observableArrayList();
    
    @FXML JFXTextField searchField;
    @FXML JFXComboBox searchTypeComboBox;
    
    
//    //value of input
//    private String searchInput; 
    
    /* -----------------------------------------
         TableView missed luggage's colommen
    ----------------------------------------- */
    
    @FXML private TableView<LostLuggage> missedLuggageTable;

    @FXML private TableColumn<LostLuggage, String>  missedRegistrationNr;
    @FXML private TableColumn<LostLuggage, String>  missedDateLost;
    @FXML private TableColumn<LostLuggage, String>  missedTimeLost;
    
    @FXML private TableColumn<LostLuggage, String>  missedLuggageTag;
    @FXML private TableColumn<LostLuggage, String>  missedLuggageType;
    @FXML private TableColumn<LostLuggage, String>  missedBrand;
    @FXML private TableColumn<LostLuggage, Integer> missedMainColor;
    @FXML private TableColumn<LostLuggage, String>  missedSecondColor;
    @FXML private TableColumn<LostLuggage, Integer> missedSize;
    @FXML private TableColumn<LostLuggage, String>  missedWeight;
    @FXML private TableColumn<LostLuggage, String>  missedOtherCharacteristics;
    @FXML private TableColumn<LostLuggage, Integer> missedPassengerId;
    
    @FXML private TableColumn<LostLuggage, String>  missedFlight;
    @FXML private TableColumn<LostLuggage, String>  missedEmployeeId;
    @FXML private TableColumn<LostLuggage, Integer> missedMatchedId;
    
    public ServiceDataLost dataListLost;
    private boolean showMatchedLuggage = false;
    private ResultSet matchedLuggageResultSet;

    
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
           
        //set screen status
        MainApp.setOnMatchingView(false);
        
        
        
        
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

        try {
            //Initialize Table & obj lost 
            dataListLost = new ServiceDataLost();
            initializeLostLuggageTable();
            

            setLostLuggageTable(dataListLost.getLostLuggage());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOverviewLostViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    @FXML
    protected void showOnlyMatchedLuggage() throws SQLException{
        if (showMatchedLuggage == false){
            showMatchedLuggage = true;
            
            matchedLuggageResultSet = dataListLost.getLostResultSet(showMatchedLuggage);
        
        setLostLuggageTable(dataListLost.getObservableList(matchedLuggageResultSet));
        
        
        } else if (showMatchedLuggage == true){
            showMatchedLuggage = false;


            
        matchedLuggageResultSet = dataListLost.getLostResultSet(showMatchedLuggage);
        
        setLostLuggageTable(dataListLost.getObservableList(matchedLuggageResultSet));
        }
        
    }
 
    @FXML
    public void search() throws SQLException{
        String value = searchTypeComboBox.getValue().toString();
        String search = searchField.getText();
         
        ServiceSearchData searchData = new ServiceSearchData();
        
       
        
        String finalQuery = searchData.getSearchQuery(value, search, "lostluggage");
        
        
        lostLuggageListSearchResults.clear();
        

        try {
            MyJDBC db = MainApp.getDatabase();
            ResultSet resultSet;
            resultSet = db.executeResultSetQuery(finalQuery);
            
            lostLuggageListSearchResults=searchData.getLostLuggageSearchList(resultSet);
            missedLuggageTable.setItems(lostLuggageListSearchResults);
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOverviewLostViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }
    
    /**  
     * @void doubleClickFoundRow
     */
    public void lostRowClicked() {
        missedLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                ServiceMoreDetails lostDetails = new ServiceMoreDetails();
                lostDetails.setDetailsOfRow("lost", event, popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml");
                lostDetails.setAndOpenPopUpDetails(popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml", "lost");
                
            }
        });
    }

    /**  
     * Here is lost luggage table overview initialized with the right values
     * 
     * @void - No direct output 
     */
    @Override
    public void initializeLostLuggageTable(){
        missedRegistrationNr.setCellValueFactory(       new PropertyValueFactory<>("registrationNr"));
        missedDateLost.setCellValueFactory(            new PropertyValueFactory<>("dateLost"));
        missedTimeLost.setCellValueFactory(            new PropertyValueFactory<>("timeLost"));
        
        missedLuggageTag.setCellValueFactory(           new PropertyValueFactory<>("luggageTag"));
        missedLuggageType.setCellValueFactory(          new PropertyValueFactory<>("luggageType"));
        missedBrand.setCellValueFactory(                new PropertyValueFactory<>("brand"));
        missedMainColor.setCellValueFactory(            new PropertyValueFactory<>("mainColor"));
        missedSecondColor.setCellValueFactory(          new PropertyValueFactory<>("secondColor"));
        missedSize.setCellValueFactory(                 new PropertyValueFactory<>("size"));
        missedWeight.setCellValueFactory(               new PropertyValueFactory<>("weight"));

        missedOtherCharacteristics.setCellValueFactory( new PropertyValueFactory<>("otherCharacteristics"));
        missedPassengerId.setCellValueFactory(          new PropertyValueFactory<>("passengerId"));
        
        missedFlight.setCellValueFactory(               new PropertyValueFactory<>("flight"));
        missedEmployeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
        missedMatchedId.setCellValueFactory(            new PropertyValueFactory<>("matchedId"));
            
    }
    /**  
     * Here will the lost luggage table be set with the right data
     * The data (observable<lostluggage>list) comes from the dataListLost
     * 
     * @param dataListLost
     * @void - No direct output 
     * @call - set lostLuggageTable             
     */
    @Override
    public void setLostLuggageTable(ObservableList<LostLuggage> list){
        missedLuggageTable.setItems(list);  
    }
    
       
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInputLuggageView.fxml");
    }
    
    @FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
    }

}