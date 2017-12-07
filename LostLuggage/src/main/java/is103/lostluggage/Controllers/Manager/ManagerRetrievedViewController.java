package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.Admin.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
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
 * @author daron
 */
public class ManagerRetrievedViewController implements Initializable {

    //luggage list
    @FXML
    private TableView<RetrievedLuggage> retrievedTable;

    @FXML
    private TableColumn<RetrievedLuggage, Integer> FormID;
    @FXML
    private TableColumn<RetrievedLuggage, String> Date;
    @FXML
    private TableColumn<RetrievedLuggage, String> Customer;
    @FXML
    private TableColumn<RetrievedLuggage, String> Employee;
    @FXML
    private TableColumn<RetrievedLuggage, String> Deliverer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";

        FormID.setCellValueFactory(new PropertyValueFactory<>("foundluggage.registrationNr"));
        Date.setCellValueFactory(new PropertyValueFactory<>("matched.dateMatched"));
        Customer.setCellValueFactory(new PropertyValueFactory<>("passenger.name"));
        Employee.setCellValueFactory(new PropertyValueFactory<>("employee.firstname"));
        Deliverer.setCellValueFactory(new PropertyValueFactory<>("matched.delivered"));
        System.out.println("test --------------* ");
        retrievedTable.setItems(getRetrievedLuggage());
        System.out.println("---- placed ");
       
        
    }

    public ObservableList<RetrievedLuggage> getRetrievedLuggage() {

        ObservableList<RetrievedLuggage> retrieved = FXCollections.observableArrayList();

        try {
            MyJDBC db = MainApp.connectToDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT delivery, dateMatched, employee.firstname, foundluggage.registrationNr, passenger.name  FROM matched INNER JOIN employee ON matched.employeeId = employee.employeeId INNER JOIN foundluggage ON matched.foundluggage = foundluggage.registrationNr INNER JOIN passenger ON foundluggage.passengerId = passenger.passengerId");
            
            while (resultSet.next()) {
                
                
                
                int registrationnr = resultSet.getInt("foundluggage.registrationNr");
                String date = resultSet.getString("matched.dateMatched");
                String passengername = resultSet.getString("passenger.name");
                String employeename = resultSet.getString("employee.firstname");
                String delivered = resultSet.getString("matched.delivery");
                
                System.out.println("deliverer: "+delivered + " Date: " +date + " passName: " + passengername+ " empname: " + employeename + " regnr: " + registrationnr);
               
                retrieved.add(  
                        new RetrievedLuggage(
                                registrationnr, 
                                date, 
                                passengername, 
                                employeename, 
                                delivered));
                
                System.out.println("Test 1 ");
                System.out.println(retrieved);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ManagerReportViewController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        System.out.println("test 2 ");
        return retrieved;
    }
}
