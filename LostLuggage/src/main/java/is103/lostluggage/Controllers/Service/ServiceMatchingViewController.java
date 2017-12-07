package is103.lostluggage.Controllers.Service;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataFound;
import is103.lostluggage.Data.ServiceDataLost;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.FoundLuggageDetails;
import is103.lostluggage.Model.LuggageManualMatchFound;
import is103.lostluggage.Model.LuggageManualMatchMissed;
import is103.lostluggage.Model.LuggageMatching;
import is103.lostluggage.Model.MissedLuggage;
import is103.lostluggage.Model.MissedLuggageDetails;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceMatchingViewController implements Initializable {


    //view title
    private final String title = "Matching";
    
    //popup stage
    public Stage popupStageFound = new Stage();   
    public Stage popupStageMissed = new Stage(); 
    
    //refresh rate                           
    public static long timeRate = 1; //s
    
    //setup for manual matching -> stop loop
    public int idCheckFound = 9999;//random value (!= registrationNr) 
    public int idFound      = 9999;//random value (!= registrationNr)
    
    public int idCheckLost = 9999;//random value (!= registrationNr) 
    public int idLost      = 9999;//random value (!= registrationNr)
    
    //luggage's lists
    public static ObservableList<FoundLuggage> foundLuggageList;
    public static ObservableList<MissedLuggage> missedLuggageList;
    
    
    //Matching tabs -> 1: automatic matching     2: manual matching
    @FXML public TabPane matchingTabs;

    //Pannels for manual matching
    @FXML public Pane foundPane;
    @FXML public Pane missedPane;
    
    
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
    
    
    //--------------------------------
    //    Table Matches initializen
    @FXML private TableView<LuggageMatching> matchTabbleView;

    @FXML private TableColumn<LuggageMatching, String>  matchIdLost;
    @FXML private TableColumn<LuggageMatching, String>  matchIdFound;
    @FXML private TableColumn<LuggageMatching, String>  matchTag;
    
    @FXML private TableColumn<LuggageMatching, String>  matchPercentage;
    @FXML private TableColumn<LuggageMatching, String>  matchType;
    @FXML private TableColumn<LuggageMatching, String>  matchBrand;
    @FXML private TableColumn<LuggageMatching, Integer> matchMainColor;
    @FXML private TableColumn<LuggageMatching, String>  matchSecondColor;
    @FXML private TableColumn<LuggageMatching, Integer> matchSize;
    @FXML private TableColumn<LuggageMatching, String>  matchWeight;
    @FXML private TableColumn<LuggageMatching, String>  matchOtherCharacteristics;
    @FXML private TableColumn<LuggageMatching, Integer> matchId;

    
    
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
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
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
        // -   -   -   -   -  
        ServiceDataLost dataListLost;
        try {
            dataListLost = new ServiceDataLost();
        } catch (SQLException ex) {
            dataListLost = (ServiceDataLost) ServiceDataLost.getMissedLuggage();
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initializeMissedLuggageTable();
        setMissedLuggageTable(dataListLost);
        
        
        //Initialize Table & obj found 
        // -   -   -   -   -   
        ServiceDataFound dataListFound;
        try {
            dataListFound = new ServiceDataFound();
        } catch (SQLException ex) {
            dataListFound = (ServiceDataFound) ServiceDataFound.getFoundLuggage();
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initializeFoundLuggageTable();
        setFoundLuggageTable(dataListFound);
        
        
        //initialize matching table & set with dataListFound and dataListLost 
        // -   -   -   -   -   -   -
        initializeMatchingLuggageTable();
        try {
            setMatchingLuggageTable(dataListFound,dataListLost);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Stop Tables from being able to re position/ order
        // -   -   -   -   -   -   -
        fixedTableHeader(foundLuggageTable);
        fixedTableHeader(missedLuggageTable);
        fixedTableHeader(matchTabbleView);
    }

    /**  
     * @void 
     */ 
    public void initializeMissedLuggageTable(){
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
    public void setMissedLuggageTable(ServiceDataLost dataListLost){
        missedLuggageTable.setItems(dataListLost.getMissedLuggage());   
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
                
               getDetailsOfRow("found", event, popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", "missed");
            }
        });
    }
    
    /**  
     * @void doubleClickMissedRow
     */
    public void missedRowClicked() {
        missedLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                
                getDetailsOfRow("missed", event, popupStageMissed, "/Views/Service/ServiceDetailedMissedLuggageView.fxml", "missed");

            }
        });
    }
    
    
    public void getDetailsOfRow(String type, MouseEvent event, Stage stageType, String stageLink, String popupKey){
        
             Node node = ((Node) event.getTarget() ).getParent();
             TableRow tableRowGet;
             
             if (node instanceof TableRow) {
                    tableRowGet = (TableRow) node;
            } else {
                    // als op de tekst is geklikt -> pak parent van text
                    tableRowGet = (TableRow) node.getParent();
            }
             
        if ("missed".equals(type)){
            //get het goede found luggage object -> plaats in getDetailObj
            MissedLuggage getDetailObj = (MissedLuggage) tableRowGet.getItem();
            
            //Detail object zetten -> zodat hij in volgende view te openen is
            
            //MissedLuggageDetails.getInstance().currentLuggage() .setRegistrationNr(getDetailObj.getRegistrationNr());
            MissedLuggage route = MissedLuggageDetails.getInstance().currentLuggage();
            route.setRegistrationNr(getDetailObj.getRegistrationNr());
            
            
            
            //pp----->
            
        } 
        
        if ("found".equals(type)){
            //get het goede found luggage object -> plaats in getDetailObj
            FoundLuggage getDetailObj = (FoundLuggage) tableRowGet.getItem();
            
            //Detail object zetten -> zodat hij in volgende view te openen is
            FoundLuggageDetails.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
                
        } 
        
        //switchen naar detailed viw dmv: popup
        if ("found".equals(type) || "missed".equals(type)){
            try {
                popUpDetails(stageType, stageLink, popupKey);
            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if ("match".equals(type)){
            //      match to manual matching
            //  -   -   -   -   -   -   -   -   -
             MissedLuggage getDetailObj = (MissedLuggage) tableRowGet.getItem();
             
            
            
            //          !IMPORTANT;
            
            
            
            
            //  -   -   -   -   -   -   -   -   -
        }
    }
    
    /**  
     * @void popupDetails 
     */
    public void popUpDetails(Stage stage, String viewLink, String type) throws IOException { 
            try { 
                //get popup fxml resource   
                Parent popup = FXMLLoader.load(getClass().getResource(viewLink));
                stage.setScene(new Scene(popup));

                //no functies -> close / fullscreen/ topbar
                //stage.initStyle(StageStyle.TRANSPARENT);

                //stage altijd on top
                stage.setAlwaysOnTop(true);

                if (stage.isShowing()){
                    //Stage was open -> refresh
                    stage.close();
                    stage.show();
                } else {
                    //Stage was gesloten -> alleen openen
                    stage.show();
                    System.out.println("Popup opend");
                }


            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    /**  
     * @void callMethodes@RateOfTimeLine 
     */
    public void callMethods(){
        //Methodes calling at rate of --> int:  timeRate   //2s
        addToManualFound();
        addToManualMissed();
    }
    
    
    
    //Event listner add luggage to manual matching
    @FXML public void addToManualFound() {
        String idManualMatching = LuggageManualMatchFound.getInstance().currentLuggage().getRegistrationNr();
        idCheckFound = addToManualMatching(foundPane, 1, idCheckFound, idFound, idManualMatching, "/Views/Service/ServiceManualMatchingFoundView.fxml");
    
    }
    
    
    //Event listner add luggage to manual matching
    @FXML public void addToManualMissed() {
        String idManualMatching = LuggageManualMatchMissed.getInstance().currentLuggage().getRegistrationNr();
        idCheckLost = addToManualMatching(missedPane, 1, idCheckLost, idLost, idManualMatching, "/Views/Service/ServiceManualMatchingMissedView.fxml");
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
        
        
        //Debug:
        //        System.out.println("---------DEBUG-----------");
        //        System.out.println("idLost:      "+idObj);
        //        System.out.println("idCheckLost: "+idCheck);
        //        System.out.println("-------------------------");
        
        
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
        
        setMatchingTab(0);
    }
    public void setMatchingLuggageTable(ServiceDataFound dataListFound, ServiceDataLost datalistDataLost) throws SQLException{
        matchTabbleView.setItems(autoMatching(dataListFound.getFoundLuggage(), datalistDataLost.getMissedLuggage())); 
    }
    
    
    /**  
     * @params 
     * @params
     * @return matchingList
     */
    public ObservableList<LuggageMatching> autoMatching(ObservableList<FoundLuggage> foundList, ObservableList<MissedLuggage> missedList){
        ObservableList<LuggageMatching> matchingList = FXCollections.observableArrayList();
        
        //Start looping every item of missed list
        missedList.forEach((lost) -> {

            //With every item of found list
            foundList.forEach((found) -> {
                //set match id and percentage on zero.
                int matchedId = 0;
                int matchingPercentage = 0;
                
                //Contorle of different match possibilities:
                if (lost.getLuggageType() == found.getLuggageType()
                                        && lost.getLuggageType() != 0 
                                        && found.getLuggageType() != 0){
                    matchingPercentage += 10;
                }
                if (lost.getBrand().equals(found.getBrand())
                                        && lost.getBrand() != null 
                                        && found.getBrand() != null){
                    matchingPercentage += 10;
                }
                if (lost.getMainColor()==found.getMainColor()
                                        && lost.getMainColor() != 0 
                                        && found.getMainColor() != 0){
                    matchingPercentage += 10;
                }
                if (lost.getSecondColor()==found.getSecondColor()
                                        && lost.getSecondColor() != 0 
                                        && found.getSecondColor() != 0){
                    matchingPercentage += 10;
                }
                if (lost.getFlight()==found.getArrivedWithFlight()
                                        && lost.getFlight() != null 
                                        && found.getArrivedWithFlight() != null){
                    matchingPercentage += 10;
                }
                
                if (lost.getWeight() != 0 && found.getWeight() != 0){
                    if ( ((lost.getWeight()/found.getWeight())-1)*100 < 50 ){
                        matchingPercentage += 10;
                    }
                }
                
                if (lost.getSize() != 0 && found.getSize() != 0){
                    if ( ((lost.getSize()/found.getSize())-1)*100 < 70 ){
                        matchingPercentage += 10;
                    }
                }
                
                if (lost.getLuggageTag()==found.getLuggageTag() 
                                        && lost.getLuggageTag() != null 
                                        && found.getLuggageTag() != null ){
                    matchingPercentage += 50;
                    if (matchingPercentage >= 100){matchingPercentage=100;System.out.println("Same: luggage tag");}
                }
                
                if (matchingPercentage>10){
                    matchingList.add(new LuggageMatching(
                        found.getRegistrationNr(), 
                        lost.getRegistrationNr(), 
                        lost.getLuggageTag()+" | "+found.getLuggageTag(), 
                        matchingPercentage, 
                        lost.getLuggageType()+" | "+found.getLuggageType(), 
                        lost.getBrand()+" | "+found.getBrand(), 
                        lost.getMainColor()+" | "+found.getMainColor(), 
                        lost.getSecondColor()+" | "+found.getSecondColor(), 
                        lost.getSize()+" | "+found.getSize(), 
                        lost.getWeight()+" | "+found.getWeight(), 
                        lost.getOtherCharaccteristics()+" | "+found.getOtherCharaccteristics(), 
                        matchedId++));
                }
                
            });
        });  
        return matchingList;
        
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
        MainApp.switchView("/Views/Service/ServiceMissedOverviewView.fxml");
    }
    
    
}
