package is103.lostluggage.Controllers.Service;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataMatch;
import is103.lostluggage.Model.Service.Data.ServiceDataMore;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Instance.Matching.FoundLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Instance.Matching.LostLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Instance.Details.LostLuggageDetailsInstance;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class for the matching view
 * 
 * @author Thijs Zijdel - 500782165
 */
public class ServiceMatchingViewController implements Initializable {


    //view title
    private final String title = "Matching";
    
    //popup stage
    public Stage popupStageFound = new Stage();   
    public Stage popupStageLost = new Stage(); 
    
    //refresh rate                           
    public static long timeRate = 1; //s
    
    //setup for manual matching -> stop loop
    public int idCheckFound = 9999;//random value (!= registrationNr) 
    public int idFound      = 9999;//random value (!= registrationNr)
    
    public int idCheckLost = 9999;//random value (!= registrationNr) 
    public int idLost      = 9999;//random value (!= registrationNr)
    
    //luggage's lists
    public static ObservableList<FoundLuggage> foundLuggageList;
    public static ObservableList<LostLuggage> lostLuggageList;
    
    
    //Matching tabs -> 1: automatic matching     2: manual matching
    @FXML public TabPane matchingTabs;

    //Pannels for manual matching
    @FXML public Pane foundPane;
    @FXML public Pane lostPane;
    
    public TableView[] fixingTables = new TableView[3];
    
    //--------------------------------
    //    Table Found initializen
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
    

    //--------------------------------
    //    Table Lost initializen
    @FXML private TableView<LostLuggage> lostLuggageTable;

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
    
    
    //--------------------------------
    //    Table Matches initializen
    @FXML private TableView<MatchLuggage> matchTabbleView;

    @FXML private TableColumn<MatchLuggage, String>  matchIdLost;
    @FXML private TableColumn<MatchLuggage, String>  matchIdFound;
    @FXML private TableColumn<MatchLuggage, String>  matchTag;
    
    @FXML private TableColumn<MatchLuggage, String>  matchPercentage;
    @FXML private TableColumn<MatchLuggage, String>  matchType;
    @FXML private TableColumn<MatchLuggage, String>  matchBrand;
    @FXML private TableColumn<MatchLuggage, Integer> matchMainColor;
    @FXML private TableColumn<MatchLuggage, String>  matchSecondColor;
    @FXML private TableColumn<MatchLuggage, Integer> matchSize;
    @FXML private TableColumn<MatchLuggage, String>  matchWeight;
    @FXML private TableColumn<MatchLuggage, String>  matchOtherCharacteristics;
    @FXML private TableColumn<MatchLuggage, Integer> matchId;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //switch to prev view.
        // -   -   -   -   - 
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //titel boven de pagina zetten
        // -   -   -   -   -
        try {MainViewController.getInstance().getTitle(title);} catch (IOException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Refresh timer setup
        // -   -   -   -   - 
        Timeline refreshTimeLine = new Timeline(new KeyFrame(Duration.seconds(timeRate), ev -> {
            //methode to call all timer required methods.
            callMethods();
        })); 
        refreshTimeLine.setCycleCount(Animation.INDEFINITE);    //cycle count-> no end
        refreshTimeLine.play();                                 //start timeline
        

        //Initialize Table & obj lost
        //          and
        //Initialize Table & obj found 
        //          and
        //initialize matching table 
        //  & set with dataListFound and dataListLost 
        // -   -   -   -   -  
        ServiceDataLost dataListLost;
        ServiceDataFound dataListFound;
        try {
            dataListLost = new ServiceDataLost();
            initializeLostLuggageTable();
            setLostLuggageTable(dataListLost);
        
        
            dataListFound = new ServiceDataFound();
            initializeFoundLuggageTable();
            setFoundLuggageTable(dataListFound);
            
            initializeMatchingLuggageTable();
            setMatchingLuggageTable(dataListFound,dataListLost);
        } catch (SQLException ex) {
            //dataListLost = (ServiceDataLost) ServiceDataLost.getMissedLuggage();
            //dataListFound = (ServiceDataFound) ServiceDataFound.getFoundLuggage();
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        

        //Stop Tables from being able to re position/ order
        // -   -   -   -   -   -   -
        fixingTables[0]=foundLuggageTable;
        fixingTables[1]=lostLuggageTable;
        fixingTables[2]=matchTabbleView;
        
        for (int i = 0; i < fixingTables.length; i++) {
            fixedTableHeader(fixingTables[i]);
        }
        
        resetManualMatching();
    }

    public void resetManualMatching(){
        if (MainApp.refreshMatching == true){
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        }
    }
    
    /**  
     * @void 
     */ 
    public void initializeLostLuggageTable(){
        missedRegistrationNr.setCellValueFactory(      new PropertyValueFactory<>("registrationNr"));
        missedDateLost.setCellValueFactory(            new PropertyValueFactory<>("dateFound"));  //-> lost
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
        
        missedFlight.setCellValueFactory(               new PropertyValueFactory<>("flight"));
        
//        missedEmployeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
//        missedMatchedId.setCellValueFactory(            new PropertyValueFactory<>("matchedId"));    
    }
    public void setLostLuggageTable(ServiceDataLost dataListLost){
        lostLuggageTable.setItems(dataListLost.getLostLuggage());   
    }
    
    /**  
     * @void
     */
    public void initializeFoundLuggageTable(){
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
//        foundEmployeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
//        foundMatchedId.setCellValueFactory(            new PropertyValueFactory<>("matchedId"));  
    }
    public void setFoundLuggageTable(ServiceDataFound dataListFound){
        foundLuggageTable.setItems(dataListFound.getFoundLuggage());
    }
    
    /**  
     * @void doubleClickFoundRow
     */
    public void foundRowClicked() {
        foundLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                ServiceDataMore foundDetails = new ServiceDataMore();
                foundDetails.setDetailsOfRow("found", event, popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
                foundDetails.setAndOpenPopUpDetails("found", popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
               
            }
        });
    }
    
    /**  
     * @void doubleClickMissedRow
     */
    public void lostRowClicked() {
        lostLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                ServiceDataMore lostDetails = new ServiceDataMore();
                lostDetails.setDetailsOfRow("lost", event, popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml", "lost");
                lostDetails.setAndOpenPopUpDetails("lost", popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml", "lost");
                
            }
        });
    }
    
    /**  
     * @void doubleClickMatchRow
     */
    public void matchRowClicked() {
        matchTabbleView.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {  
                //I set the details of the double clicked row (matched here)
                //In 2 objects, FoundLuggageDetailsInstance & lostLuggageDetails)
                ServiceDataMore matchDetails = new ServiceDataMore();
                matchDetails.setDetailsOfRow("match", event, popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml", "match");
                
                //openPopUpDetails() --> popup in this case not neccesary 

                    
                    //Now i am going to set an FoundLuggageManualMatchingInstance obj
                    //this object is being called in the addToManualMatching
                    //note: not the entire object is setted& called-> just the id
                    FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(FoundLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr() );
                    String TempIdFound = FoundLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
                    //Set the idCheckFound (for stop resetting the view) -> return int
                    //initialize the right data in the found pane
                    idCheckFound = addToManualMatching(foundPane, 1, idCheckFound, idFound, TempIdFound, "/Views/Service/ServiceManualMatchingFoundView.fxml");

                    //repeating same steps as found luggage -> here for: lost
                    LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(LostLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr() );
                    String TempIdLost = LostLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
                    idCheckLost = addToManualMatching(lostPane, 1, idCheckLost, idLost, TempIdLost, "/Views/Service/ServiceManualMatchingLostView.fxml");
    
                
                  
            }
        });
    }

    
    
    /**  
     * @void callMethodes@RateOfTimeLine 
     */
    public void callMethods(){
        //Methodes calling at rate of --> int:  timeRate   //2s
        addToManualFound();
        addToManualLost();
        
        if (MainApp.serviceChangeValue != 99){
            if(MainApp.serviceChangeValue == 0){ //0 = potentialMatches 
                                                //-> matching tab to 0
                potentialMatches();
            }
            MainApp.serviceChangeValue = 99;//reset
        }
    }
    
    
    
    //Event listner add luggage to manual matching
    public void addToManualFound() {
        String idManualMatching = FoundLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
        idCheckFound = addToManualMatching(foundPane, 1, idCheckFound, idFound, idManualMatching, "/Views/Service/ServiceManualMatchingFoundView.fxml");
    
    }
    
    
    //Event listner add luggage to manual matching
    public void addToManualLost() {
        String idManualMatching = LostLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
        idCheckLost = addToManualMatching(lostPane, 1, idCheckLost, idLost, idManualMatching, "/Views/Service/ServiceManualMatchingLostView.fxml");
    } 
    
    
    
    //Event listner add luggage to manual matching
    public void removeManualFound() {
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        idCheckFound = 9999;
        foundPane.getChildren().clear();
    }
    
    
    //Event listner add luggage to manual matching
    public void removeManualLost() {
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        idCheckLost = 9999;
        lostPane.getChildren().clear();
        
    } 
   
    
    /**  
     * Methode for setting the right data in the right place for:
     *                     MANUAL MATCHING
     * 
     * -(Checks used for not resetting the views)
     * @param   paneType = id of pane were the view needs to be loaden
     * @param   tab      = tab that needs to be setted.
     * @param   idCheck  = standard value that in the loop gets checked & returned
     * @param   idObj    = id thats been set (from id) in the loop for checking 
     * @param   idDataObj= String id of the added luggage -> used for checking 
     * @param   viewLink = link of fxml that needs to be set in the @paneType
     * @return  idCheck to stop the loop from going to far 
     */
    public int addToManualMatching(Pane paneType, int tab, int idCheck, int idObj, String idDataObj, String viewLink){
        //if found luggage added to manual matching-> asign: iD found to this.id
        if (idDataObj != null) {
            idObj = Integer.parseInt(""+idDataObj+"");
        }
        
        if (idDataObj == null) {
            //No found luggage added to manual matching
        } else {
            //if id of luggage added to manual matching is null
            //check if this id is not the same id
            if (idCheck != idObj) {
                //if this is true, than asaign idcheckfound to this 
                //(so the loop is stoped next time)
                idCheck = idObj;
                
                //now try to load the manualMatchingFoundView in the right (found) pane
                try {
                    //get the right source for MaualFoundView.FXML
                    Pane SourceLink = FXMLLoader.load(getClass()
                          .getResource(viewLink));
                    
                    //clear the found pane
                    paneType.getChildren().clear();
                    
                    //Before asigning -> set matching tab to right page
                    setMatchingTab(1);   
                    
                    //Asign the source to the right pane: foundPane
                    paneType.getChildren().add(SourceLink);
                    return idCheck;
                    
                } catch (IOException ex) {
                    Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //id is the same
                return idCheck;
            }
            return idCheck;
        }  
        return idCheck;
    }
            
    public void setMatchingTab(int tab){
        //get selection of matching tabs
        SingleSelectionModel<Tab> matchingSelectionTabs = matchingTabs.getSelectionModel(); 
        
        //change tab to givven param    //0: auto   tab
                                        //1: manual tab
        matchingSelectionTabs.select(tab); 
    }
    
    
    /**  
     * @void 
     */
    public void initializeMatchingLuggageTable(){
        matchIdLost.setCellValueFactory(               new PropertyValueFactory<>("registrationNrMissed"));
        matchIdFound.setCellValueFactory(              new PropertyValueFactory<>("registrationNrFound"));
        matchTag.setCellValueFactory(                  new PropertyValueFactory<>("luggageTag"));
        
        matchPercentage.setCellValueFactory(           new PropertyValueFactory<>("matchPercentage"));
        matchType.setCellValueFactory(                 new PropertyValueFactory<>("luggageType"));
        matchBrand.setCellValueFactory(                new PropertyValueFactory<>("brand"));
        matchMainColor.setCellValueFactory(            new PropertyValueFactory<>("mainColor"));
        matchSecondColor.setCellValueFactory(          new PropertyValueFactory<>("secondColor"));
        matchSize.setCellValueFactory(                 new PropertyValueFactory<>("size"));
        matchWeight.setCellValueFactory(               new PropertyValueFactory<>("weight"));

        matchOtherCharacteristics.setCellValueFactory( new PropertyValueFactory<>("otherCharacteristics"));
        matchId.setCellValueFactory(                   new PropertyValueFactory<>("matchedId"));
        
        //sort on match percentage
        matchTabbleView.getSortOrder().add(matchPercentage);
        
        //set right matching 
        setMatchingTab(0);
    }
    public void setMatchingLuggageTable(ServiceDataFound dataListFound, ServiceDataLost datalistDataLost) throws SQLException{
        ServiceDataMatch matchData = new ServiceDataMatch();
        matchTabbleView.setItems(matchData.autoMatching(dataListFound.getFoundLuggage(), datalistDataLost.getLostLuggage())); 
    }
    

    @FXML
    public void potentialMatches(){
        setMatchingTab(0);
    }

    
    // Solution by: Alexander Chingarev
    // https://stackoverflow.com/questions/22202782/how-to-prevent-tableview-from-doing-tablecolumn-re-order-in-javafx-8?answertab=active#tab-top
    public void fixedTableHeader(TableView table){
        table.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
    }
    
    
    
    /**  
     * @SwitchViews
     */
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInputView.fxml");
    }
    
    @FXML
    protected void switchToFound(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceFoundOverviewView.fxml");
    }
    
    @FXML
    protected void switchToMissed(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceOverviewLostView.fxml");
    }
    
    
}
