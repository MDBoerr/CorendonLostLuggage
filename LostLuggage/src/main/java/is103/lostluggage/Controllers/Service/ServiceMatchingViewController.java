package is103.lostluggage.Controllers.Service;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataMatch;
import is103.lostluggage.Model.Service.Data.ServiceMoreDetails;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Instance.Matching.FoundLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Instance.Matching.LostLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
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
import javafx.collections.FXCollections;
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
    //main match data
    public ServiceDataMatch data = MainApp.getMatchData();
     
    //view title
    private final String TITLE = "Matching";
    
    //popup stage
    private final Stage popupStageFound = new Stage();   
    private final Stage popupStageLost = new Stage(); 
    private final Stage popupStageMatch = new Stage(); 
    
    //refresh rate                           
    private static final long REFRESH_TIME = 1; //s
    
    //setup for manual matching -> stop loop
    private int idCheckFound = 9999;//random value (!= registrationNr) 
    private int idFound      = 9999;//random value (!= registrationNr)
    
    private int idCheckLost = 9999;//random value (!= registrationNr) 
    private int idLost      = 9999;//random value (!= registrationNr)
    
    //luggage's lists
    public static ObservableList<FoundLuggage> foundLuggageList;
    public static ObservableList<LostLuggage> lostLuggageList;
    private ObservableList<MatchLuggage> potentialList  = FXCollections.observableArrayList(); 
    
    //Matching tabs -> 1: automatic matching     2: manual matching
    @FXML private TabPane matchingTabs;

    //Pannels for manual matching
    @FXML private Pane foundPane;
    @FXML private Pane lostPane;
   
    
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
    @FXML private TableColumn<FoundLuggage, String>  foundSize;
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
    @FXML private TableColumn<LostLuggage, Integer> missedSecondColor;
    @FXML private TableColumn<LostLuggage, String>  missedSize;
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

    
    
 
    //--------------------------------
    //    Table potential initializen
    @FXML public TableView<MatchLuggage> potentialMatchingTable;

    @FXML private TableColumn<MatchLuggage, String>  potentialIdLost;
    @FXML private TableColumn<MatchLuggage, String>  potentialIdFound;
    @FXML private TableColumn<MatchLuggage, String>  potentialTag;
    
    @FXML private TableColumn<MatchLuggage, String>  potentialPercentage;
    @FXML private TableColumn<MatchLuggage, String>  potentialType;
    @FXML private TableColumn<MatchLuggage, String>  potentialBrand;
    @FXML private TableColumn<MatchLuggage, String>  potentialMainColor;
    @FXML private TableColumn<MatchLuggage, String>  potentialSecondColor;
    @FXML private TableColumn<MatchLuggage, String>  potentialSize;
    @FXML private TableColumn<MatchLuggage, String>  potentialWeight;
    @FXML private TableColumn<MatchLuggage, String>  potentialCharacteristics;
    
    //Create instance of this class
    public static ServiceMatchingViewController instance = null;
    
    //Get instance
    public static ServiceMatchingViewController getInstance() {
        //check if the instance already is setted
        if (instance == null) {
            synchronized(ServiceMatchingViewController.class) {
                if (instance == null) {
                    instance = new ServiceMatchingViewController();
                }
            }
        }
        return instance;
    }
    
    /*--------------------------------------------------------------------------
                  Initialize   Service Matching View Controller
    --------------------------------------------------------------------------*/
    /**
     * In the initialize will the matching view started with the right content
     * 
     * The following setups will be started:
     * - Set the back button 
     * - Set the title of the view
     * - Make a instance
     * - Setup a refresh timer and start this
     * - Make a Lost and Found data object
     * - Initialize the different tables
     * - Set the tables with the right data
     * - Stop the tables from being moved
     * //Reset manual matching
     * - Set the on matching view to true
     * 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //switch to prev view.
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //titel boven de pagina zetten
        try {MainViewController.getInstance().getTitle(TITLE);} catch (IOException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //initialize instance
        instance = this;
        
        //Refresh timer setup
        Timeline refreshTimeLine = new Timeline(new KeyFrame(Duration.seconds(REFRESH_TIME), ev -> {
            //methode to call all timer required methods.
            callMethods();
        })); 
        refreshTimeLine.setCycleCount(Animation.INDEFINITE);    //cycle count-> no end
        refreshTimeLine.play();                                 //start timeline
        
        

        ServiceDataLost dataListLost;
        ServiceDataFound dataListFound;
        try {
            //Initialize PotentialMatchingTable();
            initializePotentialLuggageTable();
            
            //Initialize Table & obj lost
            dataListLost = new ServiceDataLost();
            initializeLostLuggageTable();
            setLostLuggageTable(dataListLost);
        
            //Initialize Table & obj found 
            dataListFound = new ServiceDataFound();
            initializeFoundLuggageTable();
            setFoundLuggageTable(dataListFound);
            
            //Initialize matching table 
            //  & set with dataListFound and dataListLost
            initializeMatchingLuggageTable();
            setMatchingLuggageTable(dataListFound,dataListLost);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }


        //Stop Tables from being able to re position/ order
        //Placed them in an array for using same methode for each table
        TableView[] fixingTables = new TableView[4];
        fixingTables[0]=foundLuggageTable;
        fixingTables[1]=lostLuggageTable;
        fixingTables[2]=matchTabbleView;
        fixingTables[3]=potentialMatchingTable;
        for (TableView fixingTable : fixingTables) { fixedTableHeader(fixingTable);}
        
        //methode
        resetManualMatching();
        
        //setOnMatchingView status
        MainApp.setOnMatchingView(true);
    } 
    
    /*--------------------------------------------------------------------------
                                Methods 
    --------------------------------------------------------------------------*/
    
    /**  
     * Here will be checked what the value is of the reset status
     * If the status equals true, than de manual matching instances will be cleared
     * This way will the manual matching fields in this view empty
     * 
     * @void - No direct output 
     */
    public void resetManualMatching(){
        if (MainApp.resetMatching == true){
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        }
    }
    
    /**  
     * Here are all the functions that are called by the refresh timer
     * Note: this will happen on an interval of the: REFRESH_TIME
     * 
     * @void - No direct output 
     * @call - setpotentialMatchingTable    refresh this table by setting it. 
     * @call - addToManualFound & Lost      refresh the manual screens by adding
     */
    public void callMethods(){
        try {
            setPotentialMatchingTable();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Methodes calling at rate of --> int:  timeRate   //2s
        addToManualFound();
        addToManualLost();
    }
    
    /**  
     * Here is the matching tabs set to the right index
     * The index of the tabs are as following:
     *      0:   automatic matching tab 
     *      1:   manual matching tab
     *      2:   potential matching tab
     * 
     * @param tab  index of the wanted matching tab
     * @void - No direct output 
     */
    public void setMatchingTab(int tab){
        //check the index (tab) given
        if (tab > 3 || tab < 0){
            tab = 0; //set tab to automatich matching
        }
        //get selection of matching tabs
        SingleSelectionModel<Tab> matchingSelectionTabs = matchingTabs.getSelectionModel(); 
        
        //set the right tab
        matchingSelectionTabs.select(tab); 
    }
    
    /*--------------------------------------------------------------------------
                     Initialize and setting all the tables
    --------------------------------------------------------------------------*/
    /**  
     * Here is lost luggage table overview initialized with the right values
     * 
     * @void - No direct output 
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
            
    }
    /**  
     * Here will the lost luggage table be set with the right data
     * The data (observable<lostluggage>list) comes from the dataListLost
     * 
     * @void - No direct output 
     * @call - set lostLuggageTable             
     */
    public void setLostLuggageTable(ServiceDataLost dataListLost){
        lostLuggageTable.setItems(dataListLost.getLostLuggage());   
    }
    
    /**  
     * Here is found luggage table overview initialized with the right values
     * 
     * @void - No direct output 
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
    }
    
    /**  
     * Here will the found luggage table be set with the right data
     * The data (observable<foundluggage>list) comes from the dataListFound
     * 
     * @void - No direct output 
     * @call - set foundLuggageTable             
     */
    public void setFoundLuggageTable(ServiceDataFound dataListFound){
        foundLuggageTable.setItems(dataListFound.getFoundLuggage());
    }
    
    /**  
     * Here is automatic matching table overview initialized with the right values
     * Also the matching tabs will be set on the right tab
     * 
     * @void - No direct output 
     */
    public void initializeMatchingLuggageTable(){
        matchIdLost.setCellValueFactory(               new PropertyValueFactory<>("registrationNrLost"));
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
    
    /**  
     * Here will the (automatic) matching luggage table be set with the right data
     * The data (observable<matchluggage>list) comes from the dataListMatch
     * 
     * @void - No direct output 
     * @call - set matching table              
     */
    public void setMatchingLuggageTable(ServiceDataFound dataListFound, ServiceDataLost datalistDataLost) throws SQLException{
        ServiceDataMatch matchData = new ServiceDataMatch();
        matchTabbleView.setItems(matchData.autoMatching(dataListFound.getFoundLuggage(), datalistDataLost.getLostLuggage(), 10)); 
    }
    
    
    
    
    /*--------------------------------------------------------------------------
                          Double click functionality 
    --------------------------------------------------------------------------*/
    
    
    /**  
     * Here is checked of a row in the found luggage table is clicked
     * If this is the case, two functions will be activated. 
     * 
     * @void - No direct output 
     * @call - setDetailsOfRow           set the details of the clicked row
     * @call - setAndOpenPopUpDetails    opens the right more details pop up 
     */
    public void foundRowClicked() {
        foundLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                //Make a more details object called foundDetails.
                ServiceMoreDetails foundDetails = new ServiceMoreDetails();
                //Set the detailes of the clicked row and pass a stage and link
                foundDetails.setDetailsOfRow("found", event, popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml");
                //Open the more details pop up.
                foundDetails.setAndOpenPopUpDetails(popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "found");
               
            }
        });
    }
    
    /**  
     * Here is checked of a row in the lost luggage table is clicked
     * If this is the case, two functions will be activated. 
     * 
     * @void - No direct output 
     * @call - setDetailsOfRow           set the details of the clicked row
     * @call - setAndOpenPopUpDetails    opens the right more details pop up 
     */
    public void lostRowClicked() {
        lostLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                //Make a more details object called lostDetails.
                ServiceMoreDetails lostDetails = new ServiceMoreDetails();
                //Set the detailes of the clicked row and pass a stage and link
                lostDetails.setDetailsOfRow("lost", event, popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml");
                //Open the more details pop up.
                lostDetails.setAndOpenPopUpDetails(popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml", "lost");
                
            }
        });
    }
    
    /**  
     * Here is checked of a row in the automatic match or potential luggage table is clicked
     * If this is the case, two functions will be activated. 
     * 
     * @void - No direct output 
     * @call - setDetailsOfRow           set the details of the clicked row
     * @call - setAndOpenPopUpDetails    opens the right more details pop up 
     */
    public void matchRowClicked()  {
        //Automatic matching table
        matchTabbleView.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {  
                //Make a more details object called matchDetails.
                // note: this is a match row so a lost and found id will be setted
                ServiceMoreDetails matchDetails = new ServiceMoreDetails();
                //Set the detailes of the clicked row and pass a stage and link
                matchDetails.setDetailsOfRow("match", event, popupStageMatch, "/Views/Service/ServiceDetailedMatchLuggageView.fxml");
                //Open the more details pop up.
                matchDetails.setAndOpenPopUpDetails(popupStageMatch, "/Views/Service/ServiceDetailedMatchLuggageView.fxml", "match");
                  
            }
        });
        //Potential matching table
        potentialMatchingTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {  
                //Make a more details object called matchDetails.
                // note: this is a match row so a lost and found id will be setted
                ServiceMoreDetails matchDetails = new ServiceMoreDetails();
                //Set the detailes of the clicked row and pass a stage and link
                matchDetails.setDetailsOfRow("match", event, popupStageMatch, "/Views/Service/ServiceDetailedMatchLuggageView.fxml");
                matchDetails.setAndOpenPopUpDetails(popupStageMatch, "/Views/Service/ServiceDetailedMatchLuggageView.fxml", "match");
                  
            }
        });
    }

    
    
    /*--------------------------------------------------------------------------
                      Manual matching related method s (add, remove)
    --------------------------------------------------------------------------*/
    /**  
     * When the addToManualFound button is clicked a function is called
     * There will also a id be set
     * 
     * @void - No direct output 
     * @set  - idManualMatching     get instance of found luggage manual matching
     * @call - addToManualMatching  return this to idCheckFound
     */
    public void addToManualFound() {
        //id manual matching wil be setted from the FoundLuggageManualMatching Instance
        String idManualMatching = FoundLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
        //idCheckFound will be setted trough addToManualMatching
        //Note: This id will be checked in this function. 
        idCheckFound = addToManualMatching(
                foundPane,                  //Found pane
                1,                          //Tab index of manual matching
                idCheckFound,               //The id that will be checked
                idFound,                    //The id that will be changed
                idManualMatching,           //The id of the instance 
                "/Views/Service/ServiceManualMatchingFoundView.fxml"
        );
    
    }
    
    
    /**  
     * When the addToManualLost button is clicked a function is called
     * There will also a id be set
     * 
     * @void - No direct output 
     * @set  - idManualMatching     get instance of lost luggage manual matching
     * @call - addToManualMatching  return this to idCheckLost
     */
    public void addToManualLost() {
        //id manual matching wil be setted from the LostLuggageManualMatching Instance
        String idManualMatching = LostLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
        //idCheckLost will be setted trough addToManualMatching
        //Note: This id will be checked in this function. 
        idCheckLost = addToManualMatching(lostPane, 1, idCheckLost, idLost, idManualMatching, "/Views/Service/ServiceManualMatchingLostView.fxml");
    } 
    
    
    
    /**  
     * When the remove from manual matching  (found) button is clicked:
     * The instance will be set on null
     * The idCheckFound will be reset
     * The found (manual matching) pane will be cleared
     * 
     * @void - No direct output 
     */
    public void removeManualFound() {
        //FoundLuggageManualMatchingInstance instance wille be cleared
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        //check id will be resetted so the add to manual matching loop stops
        idCheckFound = 9999;
        //manual matching found pane will be cleared
        foundPane.getChildren().clear();
    }
    
    
    /**  
     * When the remove from manual matching  (lost) button is clicked:
     * The instance will be set on null
     * The idCheckLostwill be reset
     * The lost (manual matching) pane will be cleared
     * 
     * @void - No direct output 
     */
    public void removeManualLost() {
        //LostLuggageManualMatchingInstance instance wille be cleared
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(null);
        //check id will be resetted so the add to manual matching loop stops
        idCheckLost = 9999;
        //manual matching lost pane will be cleared
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
    
    
    
    
    /*--------------------------------------------------------------------------
                 Potential matching table functions & initializing 
    --------------------------------------------------------------------------*/
    /**  
     * Here is potential matching table overview initialized with the right values
     * 
     * @void - No direct output 
     */
    public void initializePotentialLuggageTable(){
        potentialIdLost.setCellValueFactory(               new PropertyValueFactory<>("registrationNrLost"));
        potentialIdFound.setCellValueFactory(              new PropertyValueFactory<>("registrationNrFound"));
        potentialTag.setCellValueFactory(                  new PropertyValueFactory<>("luggageTag"));
        
        potentialPercentage.setCellValueFactory(           new PropertyValueFactory<>("matchPercentage"));
        potentialType.setCellValueFactory(                 new PropertyValueFactory<>("luggageType"));
        potentialBrand.setCellValueFactory(                new PropertyValueFactory<>("brand"));
        potentialMainColor.setCellValueFactory(            new PropertyValueFactory<>("mainColor"));
        potentialSecondColor.setCellValueFactory(          new PropertyValueFactory<>("secondColor"));
        potentialSize.setCellValueFactory(                 new PropertyValueFactory<>("size"));
        potentialWeight.setCellValueFactory(               new PropertyValueFactory<>("weight"));

        potentialCharacteristics.setCellValueFactory( new PropertyValueFactory<>("otherCharacteristics"));
    }
    
    /**  
     * Here will the potential matching luggage table be set with the right data
     * The data (observable<matchluggage>list) comes from the potentialList
     * 
     * @void - No direct output 
     * @call - getPotentialMatchesList
     * @call - set potential matching table              
     */
    public void setPotentialMatchingTable() throws SQLException{
        //if new potential list is not the same
        if (data.getPotentialMatchesList() != potentialList){
            //if the potential list is not empty- clear it.
            if (!potentialList.isEmpty()){
                potentialList.clear();
            }
            //get the new potential list
            potentialList = data.getPotentialMatchesList();
            
            //set the potential matching table with the right list 
            potentialMatchingTable.setItems( potentialList); 
        }
            
        //if the potential reset status is true, reset the table
        if (MainApp.getPotentialResetStatus()){
            //function that cleares the potential matching table 
            resetPotentialMatchingTable();
        }
    }
    /**
     * Here will the potential matching luggage table be reset
     * 
     * @void - No direct output next to clearing the table          
     */
    public void resetPotentialMatchingTable() {
        //clear the list 
        this.potentialList.clear();
            
            //testing 
            System.out.println("-               -");
            System.out.println("should be cleared");
            System.out.println("-               -");
            
            
        //clear the table
        potentialMatchingTable.getItems().clear();
        //refresh the table
        potentialMatchingTable.refresh();
        //set te matching tabs tot the potential matching 
        setMatchingTab(2);
        //reset te reset status from true to false
        MainApp.setPotentialResetStatus(false);
        
    }
    

    /*--------------------------------------------------------------------------
                              Switch view buttons
    --------------------------------------------------------------------------*/
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
    
    
    
    
    
    
    
    
    
    

    
    // Solution by: Alexander Chingarev
    // for stopping the fx tables to resize and move collumns 
    //
    //link:
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
    
    
}
