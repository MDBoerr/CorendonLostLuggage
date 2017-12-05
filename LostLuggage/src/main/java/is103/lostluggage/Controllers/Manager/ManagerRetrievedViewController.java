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
    public TableView<RetrievedLuggage> retrievedTable;

    @FXML
    private TableColumn<RetrievedLuggage, String> FormID;
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

        FormID.setCellValueFactory(new PropertyValueFactory<RetrievedLuggage, String>("foundluggage.registrationNr"));
        Date.setCellValueFactory(new PropertyValueFactory<RetrievedLuggage, String>("dateMatched"));
        Customer.setCellValueFactory(new PropertyValueFactory<RetrievedLuggage, String>("passenger.name"));
        Employee.setCellValueFactory(new PropertyValueFactory<RetrievedLuggage, String>("employee.firstname"));
        Deliverer.setCellValueFactory(new PropertyValueFactory<RetrievedLuggage, String>("delivered"));

        retrievedTable.setItems(getRetrievedLuggage());
    }

    public ObservableList<RetrievedLuggage> getRetrievedLuggage() {

        ObservableList<RetrievedLuggage> retrieved = FXCollections.observableArrayList();

        try {
            MyJDBC db = MainApp.connectToDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT delivered, dateMatched, employee.firstname, foundluggage.registrationNr, passenger.name  FROM matched \n"
                    + "    INNER JOIN employee ON matched.employeeId = employee.employeeId \n"
                    + "            INNER JOIN foundluggage ON matched.foundluggage = foundluggage.registrationNr \n"
                    + "                    INNER JOIN passenger ON foundluggage.passengerId = passenger.passengerId;");

            while (resultSet.next()) {
                String delivered = resultSet.getString("delivered");
                String date = resultSet.getString("dateMatched");
                String employeename = resultSet.getString("employee.firstname");
                String registrationnr = resultSet.getString("foundluggage.registrationNr");
                String passengername = resultSet.getString("passenger.name");
                
                System.out.println("deliverer: "+delivered + " Date: " +date + " passName: " + passengername+ " empname: " + employeename + " regnr: " + registrationnr);
                retrieved.add(new RetrievedLuggage(registrationnr, date, passengername, employeename, delivered));

            }

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retrieved;
    }
}
