package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetails;
import is103.lostluggage.Model.Service.Instance.Details.LostLuggageDetails;
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
public class ServiceOverviewLostViewController implements Initializable {

            public Stage popupStageLost = new Stage();   

        //view title
    private final String title = "Overview Lost Luggage";
    
    public static ObservableList<LostLuggage> MissedLuggageList;
    
    @FXML  
    private JFXTextField searchField;
    
    //value of input
    private String searchInput; 
    
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
            initializeMissedLuggageTable(dataListLost.getLostLuggage());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceFoundOverviewViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void initializeMissedLuggageTable(ObservableList<LostLuggage> dataList){
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
             
             
             LostLuggage getDetailObj = (LostLuggage) tableRowGet.getItem();
            
            //repeat.. 
            LostLuggageDetails.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
              
                try {
                    ServiceDataLost getDataLost = new ServiceDataLost();
                     getDataLost.popUpDetails(popupStageLost);
                } catch (SQLException ex) {
                    Logger.getLogger(ServiceOverviewLostViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServiceOverviewLostViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
               //setDetailsOfRow("found", event, popupStageLost, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
                //setAndOpenPopUpDetails("found", popupStageLost, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
               //setDetailsOfRow("found", event, popupStageLost, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
                //setAndOpenPopUpDetails("found", popupStageLost, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
              
               
            }
        });
    }
    
   

}