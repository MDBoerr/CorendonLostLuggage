package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.LuggageDetails;
import is103.lostluggage.Model.LuggageManualMatchFound;
import is103.lostluggage.Model.MissedLuggage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author Thijs Zijdel
 */
public class ServiceMatchingViewController implements Initializable {


            //view title
    private final String title = "Matching";
    
    public Stage popupStage = new Stage();    
    
    
    
    
    
    public static ObservableList<FoundLuggage> foundLuggageList;
    public static ObservableList<MissedLuggage> missedLuggageList;
    
    
    
    @FXML public TabPane matchingTabs;
    @FXML public Tab possibleTab;
    
    @FXML public Tab manualTab;
    @FXML public AnchorPane manualPane;
    @FXML public GridPane manualGrid;
    @FXML public Pane foundPane;
    
    /* -----------------------------------------
         TableView found luggage's colommen
    ----------------------------------------- */
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
//    @FXML private TableColumn<FoundLuggage, String>  foundEmployeeId;
//    @FXML private TableColumn<FoundLuggage, Integer> foundMatchedId;
    

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
    //@FXML private TableColumn<MissedLuggage, String>  missedEmployeeId;
    //@FXML private TableColumn<MissedLuggage, Integer> missedMatchedId;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //switch to prev view.
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        //refreshTimer();
        final ServiceMatchingViewController ttc = new ServiceMatchingViewController();
        
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            
//            @Override
//            public void run() {
//                callMethods();
//                System.out.println("called!!");
//            }
//        }, 0, ServiceMatchingViewController.timeR);

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                callMethods();
                System.out.println("called!!");
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
                
        initializeMissedLuggageTable();
        initializeFoundLuggageTable();
        
    }

    /**  
     * @void 
     */
    public void initializeMissedLuggageTable(){
        missedRegistrationNr.setCellValueFactory(      new PropertyValueFactory<>("registrationNr"));
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
        
        missedFlight.setCellValueFactory(               new PropertyValueFactory<>("flight"));
        
//        missedEmployeeId.setCellValueFactory(           new PropertyValueFactory<>("employeeId"));
//        missedMatchedId.setCellValueFactory(            new PropertyValueFactory<>("matchedId"));    
        
        missedLuggageTable.setItems(getMissedLuggage());   
    }
    
    
    /**  
     * @return missedLuggages
     */
    public ObservableList<MissedLuggage> getMissedLuggage() {

        ObservableList<MissedLuggage> missedLuggageList = FXCollections.observableArrayList();
        
        try {
            MyJDBC db = MainApp.connectToDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM lostLuggage");
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("               alles geselecteerd van missed luggage tabel            ");
            System.out.println(" ---------------------------------------------------------------------");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (missedLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateFound =          resultSet.getString("dateLost");
                String timeFound =          resultSet.getString("timeLost");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                int size =                  resultSet.getInt("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String flight =             resultSet.getString("flight"); 
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =             resultSet.getInt("matchedId");


                //Per result -> toevoegen aan Luggages  (observable list) 
                missedLuggageList.add(
                        new MissedLuggage(
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
                                
                                flight, 
                                employeeId, 
                                matchedId
                            ));
                
                
                // Alle gegevens per result (koffer) (alleen id) om spam te voorkomen) ->  printen
                System.out.println("Gegevens voor koffer id: "+registrationNr+" |       Zijn: Correct");
                System.out.println("---------------------------------------------------------------------");
                      
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return missedLuggageList;
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

        foundLuggageTable.setItems(getFoundLuggage());
    }
    
    
    /**  
     * @return foundLuggages
     */
    public ObservableList<FoundLuggage> getFoundLuggage() {

        ObservableList<FoundLuggage> foundLuggageList = FXCollections.observableArrayList();
        
        try {
            MyJDBC db = MainApp.connectToDatabase();;

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage");
            System.out.println(" ---------------------------------------------------------------------");
            System.out.println("               alles geselecteerd van found luggage tabel            ");
            System.out.println(" ---------------------------------------------------------------------");
            
            
            while (resultSet.next()) {
        //Alle gegevens van de database (foundLuggage tabel) in variabelen plaatsen
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

                


                //Per result -> toevoegen aan Luggages  (observable list) 
                foundLuggageList.add(
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
                
                
                // Alle gegevens per result (koffer) (alleen id) om spam te voorkomen) ->  printen
                System.out.println("Gegevens voor koffer id: "+registrationNr+" |       Zijn: Correct");
                System.out.println("---------------------------------------------------------------------");
                      

            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
    
    /**  
     * @void doubleClickFoundRow
     */
    public void foundRowClicked() {
        foundLuggageTable.setOnMousePressed((MouseEvent event) -> {
                                //--> event         //--> double click
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                //obj hash code vinden
                Node node_data = ((Node) event.getTarget() ).getParent();
                TableRow found_row;
                
                if (node_data instanceof TableRow) {
                    found_row = (TableRow) node_data;
                } else {
                    // als op de tekst is geklikt -> pak parent van text
                    found_row = (TableRow) node_data.getParent();
                }
                
                //get het goede found luggage object -> plaats in getDetailObj
                FoundLuggage getDetailObj = (FoundLuggage) found_row.getItem();
                
                //get row item
                System.out.println("row item; " +found_row.getItem());
                
                //Detail object zetten -> zodat hij in volgende view te openen is
                LuggageDetails.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
                
                //switchen naar detailed viw dmv: popup
                try {
                    popUpDetails(popupStage, "/Views/Service/ServiceDetailedLuggageView.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
   
            }
        });
    }
    
    /**  
     * @void popupDetails 
     */
    public void popUpDetails(Stage stage, String viewLink) throws IOException {
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

    public static long timeR = 1244;
    public void refreshTimer(){
        
        
    }
    
    
    public void callMethods(){
        //ServiceMatchingViewController ttc = new ServiceMatchingViewController();
        addToManualFound();
        System.out.println("called  2   !!");
    }
    public int idCheck = 9999;  
    public int idObject = 9999;
    public void addToManualFound() {
        //foundPane.setContent();
        
       
        String id = LuggageManualMatchFound.getInstance().currentLuggage().getRegistrationNr();
        if (id != null) {
            idObject = Integer.parseInt(""+id+"");
            
            System.out.println("-----------");
            System.out.println("runned   !!");
            System.out.println("Id check:"+idCheck);
            System.out.println("Id:"+idObject);
            System.out.println("-----------");
        }
        
        
        if (id == null) {
            System.out.println("No manual matching found luggage selected - yet");
        } else {
            if (idCheck != idObject) {
                idCheck = idObject;
                System.out.println("shout be added now ----------------------------");
                try {
                    Pane root1 = FXMLLoader.load(getClass().getResource("/Views/Service/ServiceManualMatchingFound.fxml"));
                    foundPane.getChildren().add(root1);
                } catch (IOException ex) {
                    Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Id is the same");
            }
        }
            //Parent root1 = (Parent) fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root1));
//            stage.show();
           
         
        
//                 try {
//                    popUpDetails(popupStage, "/Views/Service/ServiceDetailedLuggageView.fxml");
//                } catch (IOException ex) {
//                    Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
//                }
        
//     for this function:
//    @FXML public TabPane matchingTabs;
//    @FXML public Tab possibleTab;
//    
//    @FXML public Tab manualTab;
//    @FXML public AnchorPane manualPane;
//    @FXML public GridPane manualGrid;
     
    
    }
    
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceInvoerView.fxml");
    }
    
    @FXML
    protected void switchToFound(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceGevondenOverzichtView.fxml");
    }
    
    @FXML
    protected void switchToMissed(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/ServiceVermisteOverzichtView.fxml");
    }
    
    
}
