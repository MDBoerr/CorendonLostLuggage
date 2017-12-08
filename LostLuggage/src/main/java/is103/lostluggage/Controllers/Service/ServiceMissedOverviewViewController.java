package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Model.MissedLuggage;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataFound;
import is103.lostluggage.Data.ServiceDataLost;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.FoundLuggageDetails;
import is103.lostluggage.Model.MissedLuggageDetails;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
public class ServiceMissedOverviewViewController implements Initializable {

            public Stage popupStageLost = new Stage();   

        //view title
    private final String title = "Overview Lost Luggage";
    
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
           
        ServiceDataLost dataListLost;
        try {
            dataListLost = new ServiceDataLost();
            initializeMissedLuggageTable(dataListLost.getMissedLuggage());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceFoundOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void initializeMissedLuggageTable(ObservableList<MissedLuggage> dataList){
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

        missedLuggageTable.setItems(dataList);
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
    
    /**  
     * @void doubleClickFoundRow
     */
    public void lostRowClicked() {
        missedLuggageTable.setOnMousePressed((MouseEvent event) -> {
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
             
             
             MissedLuggage getDetailObj = (MissedLuggage) tableRowGet.getItem();
            
            //repeat.. 
            MissedLuggageDetails.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
              
                try {
                    ServiceDataLost getDataLost = new ServiceDataLost();
                     getDataLost.popUpDetails(popupStageLost);
                } catch (SQLException ex) {
                    Logger.getLogger(ServiceMissedOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServiceMissedOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
               //setDetailsOfRow("found", event, popupStageLost, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
                //setAndOpenPopUpDetails("found", popupStageLost, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
              
               
            }
        });
    }
    
   

}