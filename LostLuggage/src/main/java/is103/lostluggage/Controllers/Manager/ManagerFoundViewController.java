package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.Admin.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.connectToDatabase;
<<<<<<< HEAD
import is103.lostluggage.Model.LostLuggage;
=======
import is103.lostluggage.Model.Service.Model.FoundLuggage;
>>>>>>> dad3c85d8f66711a1501493a0133135480028361
//import is103.lostluggage.Controllers.Service.Luggage;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmet
 */
public class ManagerFoundViewController implements Initializable {
private final String title = "Overzicht Gevonden Bagage";
    
    public static ObservableList<LostLuggage> foundLuggageList;
    
    
    //TableView found luggage's colommen
    
    
    @FXML
    private TableView<LostLuggage> foundLuggage;

    @FXML private TableColumn<LostLuggage, String>  managerFoundRegistrationNr;
    @FXML private TableColumn<LostLuggage, String>  managerFoundDateFound;
    @FXML private TableColumn<LostLuggage, String>  managerFoundTimeFound;
    
    @FXML private TableColumn<LostLuggage, String>  managerFoundLuggageTag;
    @FXML private TableColumn<LostLuggage, String>  managerFoundLuggageType;
    @FXML private TableColumn<LostLuggage, String>  managerFoundBrand;
    @FXML private TableColumn<LostLuggage, Integer> managerFoundMainColor;
    @FXML private TableColumn<LostLuggage, String>  managerFoundSecondColor;
    @FXML private TableColumn<LostLuggage, Integer> managerFoundSize;
    @FXML private TableColumn<LostLuggage, String>  managerFoundWeight;
    @FXML private TableColumn<LostLuggage, String>  managerFoundOtherCharacteristics;
    @FXML private TableColumn<LostLuggage, Integer> managerFoundPassengerId;
    
    @FXML private TableColumn<LostLuggage, String> managerFoundArrivedWithFlight;
    @FXML private TableColumn<LostLuggage, Integer> managerFoundLocationFound;
    @FXML private TableColumn<LostLuggage, String> managerFoundEmployeeId;
    @FXML private TableColumn<LostLuggage, Integer> managerFoundMatchedId;


    /**
     * Initializes the controller class.
     */

 //luggage list
    //public static ObservableList<Luggage> luggageList;
    private int id = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
         try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        //de data vanuit de database halen     
        
  
        managerFoundRegistrationNr.setCellValueFactory(new PropertyValueFactory<>("registrationNr"));
        managerFoundDateFound.setCellValueFactory(new PropertyValueFactory<>("dateFound"));
        managerFoundTimeFound.setCellValueFactory(new PropertyValueFactory<>("timeFound"));
        
        managerFoundLuggageTag.setCellValueFactory(new PropertyValueFactory<>("luggageTag"));
        managerFoundLuggageType.setCellValueFactory(new PropertyValueFactory<>("luggageType"));
        managerFoundBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        managerFoundMainColor.setCellValueFactory(new PropertyValueFactory<>("mainColor"));
        managerFoundSecondColor.setCellValueFactory(new PropertyValueFactory<>("secondColor"));
        managerFoundSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        managerFoundWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        managerFoundOtherCharacteristics.setCellValueFactory(new PropertyValueFactory<>("otherCharacteristics"));
        managerFoundPassengerId.setCellValueFactory(new PropertyValueFactory<>("passengerId"));
        
        managerFoundArrivedWithFlight.setCellValueFactory(new PropertyValueFactory<>("arrivedWithFlight"));
        managerFoundLocationFound.setCellValueFactory(new PropertyValueFactory<>("locationFound"));
        managerFoundEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        managerFoundMatchedId.setCellValueFactory(new PropertyValueFactory<>("matchedId"));

    
        
        
        foundLuggage.setItems(getFoundLuggage());
        
            foundLuggage.setOnMousePressed(new EventHandler<MouseEvent>() {
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

                        Logger.getLogger(ManagerFoundViewController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }

            }

        });
        
    }
    
    
    
    
    
     
    public ObservableList<LostLuggage> getFoundLuggage() {

        ObservableList<LostLuggage> foundLuggageList = FXCollections.observableArrayList();
        
        try {
             MyJDBC db = connectToDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM foundluggage");
            
            
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
                int matchedId =              resultSet.getInt("matchedId");

                


                //Per result -> toevoegen aan Luggages  (observable list) 
                foundLuggageList.add(new LostLuggage(
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
                
                
                
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundLuggageList;
    }
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerFoundView.fxml");
    }
    

    
}
        
       
 
            


