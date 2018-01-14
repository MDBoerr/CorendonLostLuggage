package is103.lostluggage.Controllers.Manager;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.Admin.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.settings;
//import is103.lostluggage.Controllers.Service.Luggage;
import java.io.IOException;
import java.net.URL;
import static java.sql.JDBCType.NULL;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private JFXTextField formtextid;
    @FXML
    private JFXTextField customerid;
    @FXML
    private JFXTextField deivererid;
    @FXML
    private JFXTextField employeeservice;
    @FXML
    private JFXTextField emailid;
    @FXML
    private JFXTextField adresid;
    @FXML
    private JFXTextField dateid;

    
        //conection to the db
    private final MyJDBC DB = MainApp.getDatabase();  
    
    @Override

    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerReportView.fxml";

        FormID.setCellValueFactory(new PropertyValueFactory<>("FormID"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Customer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        Employee.setCellValueFactory(new PropertyValueFactory<>("Employee"));
        Deliverer.setCellValueFactory(new PropertyValueFactory<>("Deliverer"));
        retrievedTable.setItems(getRetrievedLuggage());

        retrievedTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    
//                    ----zomaar iets
//                    customerdetails email = new customerdetails(emailid, adresid);

                    Node node = ((Node) event.getTarget()).getParent();

                    TableRow row;

                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }

                    //get value of selected row in tableview
                    int formid = retrievedTable.getSelectionModel().getSelectedItem().getFormID();
                    String customer = retrievedTable.getSelectionModel().getSelectedItem().getCustomer();
                    String deliver = retrievedTable.getSelectionModel().getSelectedItem().getDeliverer();
                    String employee = retrievedTable.getSelectionModel().getSelectedItem().getEmployee();
                    String date = retrievedTable.getSelectionModel().getSelectedItem().getDate();
//                    String email = getcustomerdetails(emailid);

                    //convert int to string
                    String formidString = Integer.toString(formid);

                    // set the values of tableview in textfield
                    formtextid.setText(formidString);
                    customerid.setText(customer);
                    deivererid.setText(deliver);
                    employeeservice.setText(employee);
                    dateid.setText(date);
                    
                    
                    //zorg dat hier de LostluggageRegistrationId  in komt (die je dus wel vanuit je tabel kan halen)
                    String LostluggageRegistrationId = "1";
                    
                    try {
                        ResultSet resultSetPassenger = DB.executeResultSetQuery("SELECT * FROM passenger " +
                                "LEFT JOIN lostluggage ON lostluggage.passengerId = passenger.passengerId " +
                                "WHERE lostluggage.registrationNr = '"+LostluggageRegistrationId+"'; ");
                        
                                            //let op: hij loopt door de resultset maaar..!!
                                            //--> het is maar een resultaat aangezien je op een PK had gezocht.
                                    while (resultSetPassenger.next()) {
                                                                                       //note: passenger
                                                                                       //de tabel van de juiste data
                                                                                       
                                                                                       //run eventueel de query hierboven om het te zien
                                        String passname = resultSetPassenger.getString("passenger.name");
                                        String passmail = resultSetPassenger.getString("passenger.email");

                                        //doe wat je met de gegevens wilt
                                        //customerdetails.add(new customerdetails(passname, passmail));

                                    }
                        
//                    -->proberen email en adres
//                   emailid.setText() = getcustomerdetails();
//                    emailid.setText();
//                    adresid.setText(emailid);
//                    System.out.println(email);
//                   -->end
                    } catch (SQLException ex) {
                        Logger.getLogger(ManagerRetrievedViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        });

    }
//      ------> retrieve customer adres and email where matched fooundluggage
    public ObservableList<customerdetails> getcustomerdetails() {
        ObservableList<customerdetails> customerdetails = FXCollections.observableArrayList();
        try {
            MyJDBC db = MainApp.getDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT adres, email FROM passenger JOIN foundluggage ON foundluggage.passengerId = passenger.passengerId INNER JOIN matched ON matched.foundluggage = foundluggage.registrationNr WHERE matched.foundluggage");

            while (resultSet.next()) {

                String passname = resultSet.getString("name");
                String passmail = resultSet.getString("email");

                customerdetails.add(new customerdetails(passname, passmail));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerReportViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerdetails;

    }
//    ------->end

    public ObservableList<RetrievedLuggage> getRetrievedLuggage() {

        ObservableList<RetrievedLuggage> retrievedList = FXCollections.observableArrayList();

        try {
            MyJDBC db = MainApp.getDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT delivery, dateMatched, employee.firstname, matched.matchedId, passenger.name  FROM matched INNER JOIN employee ON matched.employeeId = employee.employeeId INNER JOIN foundluggage ON matched.foundluggage = foundluggage.registrationNr INNER JOIN passenger ON foundluggage.passengerId = passenger.passengerId");

            while (resultSet.next()) {
                String delivercheck = resultSet.getString("matched.delivery");
                if (!"".equals(delivercheck)) {
                    int registrationnr = resultSet.getInt("matched.matchedId");
                    String date = resultSet.getString("matched.dateMatched");
                    String passengername = resultSet.getString("passenger.name");
                    String employeename = resultSet.getString("employee.firstname");
                    String delivered = resultSet.getString("matched.delivery");

                    retrievedList.add(new RetrievedLuggage(registrationnr, date, passengername, employeename, delivered));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerReportViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retrievedList;
    }

}
