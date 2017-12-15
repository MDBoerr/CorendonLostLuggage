package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.connectToDatabase;
import is103.lostluggage.Model.LostLuggage;

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
public class ManagerLostViewController implements Initializable {
private final String title = "Overzicht Vermiste Bagage";
    
    public static ObservableList<LostLuggage> lostLuggageList;
    
    
    //TableView found luggage's colommen
    
    
    @FXML
    private TableView<LostLuggage> lostTable;

    @FXML private TableColumn<LostLuggage, String>  managerLostRegistrationNr;
    @FXML private TableColumn<LostLuggage, String>  managerLostDateLost;
    @FXML private TableColumn<LostLuggage, String>  managerLostTimeLost;
    
    @FXML private TableColumn<LostLuggage, String>  managerLostLuggageTag;
    @FXML private TableColumn<LostLuggage, String>  managerLostLuggageType;
    @FXML private TableColumn<LostLuggage, String>  managerLostBrand;
    @FXML private TableColumn<LostLuggage, Integer> managerLostMainColor;
    @FXML private TableColumn<LostLuggage, String>  managerLostSecondColor;
    @FXML private TableColumn<LostLuggage, Integer> managerLostSize;
    @FXML private TableColumn<LostLuggage, String>  managerLostWeight;
    @FXML private TableColumn<LostLuggage, String>  managerLostOtherCharacteristics;
    @FXML private TableColumn<LostLuggage, Integer> managerLostPassengerId;
    
    @FXML private TableColumn<LostLuggage, String> managerLostFlight;
    
    @FXML private TableColumn<LostLuggage, String> managerLostEmployeeId;
    @FXML private TableColumn<LostLuggage, Integer> managerLostMatchedId;


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
        
  
        managerLostRegistrationNr.setCellValueFactory(new PropertyValueFactory<>("registrationNr"));
        managerLostDateLost.setCellValueFactory(new PropertyValueFactory<>("dateLost"));
        managerLostTimeLost.setCellValueFactory(new PropertyValueFactory<>("timeLost"));
        
        managerLostLuggageTag.setCellValueFactory(new PropertyValueFactory<>("luggageTag"));
        managerLostLuggageType.setCellValueFactory(new PropertyValueFactory<>("luggageType"));
        managerLostBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        managerLostMainColor.setCellValueFactory(new PropertyValueFactory<>("mainColor"));
        managerLostSecondColor.setCellValueFactory(new PropertyValueFactory<>("secondColor"));
        managerLostSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        managerLostWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        managerLostOtherCharacteristics.setCellValueFactory(new PropertyValueFactory<>("otherCharacteristics"));
        managerLostPassengerId.setCellValueFactory(new PropertyValueFactory<>("passengerId"));
        
        managerLostFlight.setCellValueFactory(new PropertyValueFactory<>("flight"));
        
        managerLostEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        managerLostMatchedId.setCellValueFactory(new PropertyValueFactory<>("matchedId"));

    
        
        
        lostTable.setItems(getLostLuggage());
        
            lostTable.setOnMousePressed(new EventHandler<MouseEvent>() {
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

                        Logger.getLogger(ManagerLostViewController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                }

            }

        });
        
    }
    
    
    
    
    
     
    public ObservableList<LostLuggage> getLostLuggage() {

        ObservableList<LostLuggage> lostLuggageList = FXCollections.observableArrayList();
        
        try {
             MyJDBC db = connectToDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT * FROM lostluggage");
            
            
            while (resultSet.next()) {
                //Alle gegevens van de database (lostLuggage tabel) in variabelen plaatsen
                String registrationNr =     resultSet.getString("registrationNr");
                String dateLost =          resultSet.getString("dateLost");
                String timeLost =          resultSet.getString("timeFound");
                
                String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                int size =                  resultSet.getInt("size");
                int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                String Flight =              resultSet.getString("Flight"); 
                
                String employeeId =         resultSet.getString("employeeId");
                int matchedId =              resultSet.getInt("matchedId");

                


                //Per result -> toevoegen aan Luggages  (observable list) 
                lostLuggageList.add(
                        new LostLuggage(
                                registrationNr, 
                                dateLost, 
                                timeLost, 
                                
                                luggageTag, 
                                luggageType, 
                                brand, 
                                mainColor, 
                                secondColor, 
                                size, 
                                weight, 
                                otherCharacteristics, 
                                passengerId, 
                                
                                Flight, 
                                
                                employeeId, 
                                matchedId
                            ));
                
                
                
            
            }//-> stop als er geen resultaten meer zijn!

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lostLuggageList;
    }
    
    
    
    
    @FXML
    protected void switchToInput(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerLostView.fxml");
    }
    

    
}
        
       
 
            


