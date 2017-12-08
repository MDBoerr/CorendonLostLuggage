package is103.lostluggage.Controllers.Service;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataFound;
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
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.FoundLuggageDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceFoundOverviewViewController implements Initializable {

        public Stage popupStageFound = new Stage();   

    
        //view title
    private final String title = "Overview Found Luggage";
    
    public static ObservableList<FoundLuggage> foundLuggageList;
    public static ObservableList<FoundLuggage> foundLuggageListSearchResults;
    
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
        
        
        searchTypeComboBox.getItems().addAll(
                "All fields",
                "RegistrationNr", 
                "LuggageTag", 
                "Brand",
                "Color",
                "Characteristics");
        searchTypeComboBox.setValue("All fields");
        
        
        
        
        ServiceDataFound dataListFound;
        try {
            dataListFound = new ServiceDataFound();
            initializeFoundLuggageTable(dataListFound.getFoundLuggage());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceFoundOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        
        
    }
    
    @FXML
    public void search(){
        String query = searchType();
        System.out.println(query);
        
        String search = searchField.getText();
        
        
        String finalQuery = query.replaceAll("replace", search);
        
        System.out.println("Query: " +finalQuery);
        
        
        
        

        try {
            MyJDBC db = MainApp.connectToDatabase();
            ResultSet resultSet;
            resultSet = db.executeResultSetQuery(finalQuery);
            
            System.out.println("Resultset:   "+resultSet);
            while (resultSet.next()) {
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

                foundLuggageListSearchResults.add(
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
            foundLuggageTable.setItems(foundLuggageListSearchResults);
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceFoundOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }
    
    public String searchType(){
        
      String value = searchTypeComboBox.getValue().toString();
      
        if("All fields".equals(value)){
            return "SELECT * FROM foundluggage WHERE * LIKE '%replace%'";
        }
        
        if("RegistrationNr".equals(value)){
            return "SELECT * FROM foundluggage WHERE registrationNr LIKE '%replace%'";
        }
        
        if ("LuggageTag".equals(value)){
            return "SELECT * FROM foundluggage WHERE luggageTag LIKE '%replace%'";
        }
        
        if ("Brand".equals(value)){
            return "SELECT * FROM foundluggage WHERE brand LIKE '%replace%'";
        }
        
        if ("Color".equals(value)){
            return "SELECT * FROM foundluggage WHERE mainColor LIKE '%replace%'";
        }
        
        if ("Characteristics".equals(value)){
            return "SELECT * FROM foundluggage WHERE otherCharacteristics LIKE '%replace%'";
        }
        
        return "NonSelected - failed";
    }
    
    /**  
     * @void 
     */
    public void initializeFoundLuggageTable(ObservableList<FoundLuggage> dataListFound){
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

        foundLuggageTable.setItems(dataListFound);
    }
    
    /**  
     * @void doubleClickFoundRow
     */
    public void foundRowClicked() {
        foundLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                System.out.println("foundd");
                
                
                
             Node node = ((Node) event.getTarget() ).getParent();
             TableRow tableRowGet;
             
             if (node instanceof TableRow) {
                    tableRowGet = (TableRow) node;
            } else {
                    // if text is clicked -> get parent
                    tableRowGet = (TableRow) node.getParent();
            }
             
             
             FoundLuggage getDetailObj = (FoundLuggage) tableRowGet.getItem();
            
            //repeat.. 
            FoundLuggageDetails.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
              
                try {
                    ServiceDataFound getDataFound = new ServiceDataFound();
                     getDataFound.popUpDetails(popupStageFound);
                } catch (SQLException ex) {
                    Logger.getLogger(ServiceFoundOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServiceFoundOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
               //setDetailsOfRow("found", event, popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
                //setAndOpenPopUpDetails("found", popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
              
               
            }
        });
    }
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInvoerView.fxml");
    }
    
    @FXML
    protected void switchToMatching(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
    }
    
}