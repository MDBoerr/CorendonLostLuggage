package is103.lostluggage.Controllers.Manager;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
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

import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<RetrievedLuggage, String> koffernr;

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
    @FXML
    private JFXTextField lostluggageid;

    private String header = "Retrieved lugagge";
    //conection to the db
    public final MyJDBC DB = MainApp.getDatabase();

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";

        FormID.setCellValueFactory(new PropertyValueFactory<>("FormID"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        koffernr.setCellValueFactory(new PropertyValueFactory<>("Koffer"));
        Customer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        Employee.setCellValueFactory(new PropertyValueFactory<>("Employee"));
        Deliverer.setCellValueFactory(new PropertyValueFactory<>("Deliverer"));
        retrievedTable.setItems(getRetrievedLuggage());

        retrievedTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {

                    Node node = ((Node) event.getTarget()).getParent();

                    TableRow row;

                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }

                    //get value of selected row in tableview
                    int lostluggage = retrievedTable.getSelectionModel().getSelectedItem().getKoffer();
                    int formid = retrievedTable.getSelectionModel().getSelectedItem().getFormID();
                    String customer = retrievedTable.getSelectionModel().getSelectedItem().getCustomer();
                    String deliver = retrievedTable.getSelectionModel().getSelectedItem().getDeliverer();
                    String employee = retrievedTable.getSelectionModel().getSelectedItem().getEmployee();
                    String date = retrievedTable.getSelectionModel().getSelectedItem().getDate();

                    //convert int to string
                    String formidString = Integer.toString(formid);
                    String lostluggagenr = Integer.toString(lostluggage);

                    // set the values of tableview in textfield
                    formtextid.setText(formidString);
                    formtextid.setEditable(false);

                    lostluggageid.setText(lostluggagenr);
                    lostluggageid.setEditable(false);

                    customerid.setText(customer);

                    deivererid.setText(deliver);

                    employeeservice.setText(employee);
                    employeeservice.setEditable(false);

                    dateid.setText(date);
                    dateid.setEditable(false);

                    try {
                        //search mail and adress where registrationnr is the same
                        ResultSet resultSetPassenger = DB.executeResultSetQuery("SELECT * FROM passenger "
                                + "LEFT JOIN lostluggage ON lostluggage.passengerId = passenger.passengerId "
                                + "WHERE lostluggage.registrationNr = '" + lostluggagenr + "'; ");

                        while (resultSetPassenger.next()) {

                            String passadres = resultSetPassenger.getString("passenger.address");

                            //check if customer has a email
                            if (!NULL.equals("passenger.email") || !"".equals("passenger.email")) {
                                String passmail = resultSetPassenger.getString("passenger.email");
                                emailid.setText(passmail);
                            } else {
                                emailid.clear();
                            }

                            adresid.setText(passadres);
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(ManagerRetrievedViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        });

    }
//
//    public void updateFormInfo(ActionEvent event) throws SQLException {
//
//        String customer = retrievedTable.getSelectionModel().getSelectedItem().getCustomer();
//        int lostkoffer = retrievedTable.getSelectionModel().getSelectedItem().getKoffer();
//        String deliverer = retrievedTable.getSelectionModel().getSelectedItem().getDeliverer();
//        String email = emailid.getText();
//        String adres = adresid.getText();
//        String kofferid = Integer.toString(lostkoffer);
//
//        if (koffernr.equals(kofferid)) {
//            int updateInfo = DB.executeUpdateQuery("UPDATE passenger "
//                    + "                                   JOIN lostluggage ON lostluggage.passengerId = passenger.passengerId  "
//                    + "                                             JOIN matched on lostluggage.registrationNr = matched.lostluggage  "
//                    + "                                                     SET passenger.name = '" + customer + "', passenger.email = '" + email + "', passenger.address = '" + adres + "', matched.delivery = '" + deliverer + "' "
//                    + "                                                     WHERE lostluggage.registrationNr = '" + kofferid + "'");
//        } else {
//            System.out.println("nothing updated");
//        }
//    }

    public ObservableList<RetrievedLuggage> getRetrievedLuggage() {

        ObservableList<RetrievedLuggage> retrievedList = FXCollections.observableArrayList();

        try {
            MyJDBC db = MainApp.getDatabase();

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT delivery, dateMatched, employee.firstname, matched.matchedId, matched.lostluggage, passenger.name  FROM matched "
                    + "                                 INNER JOIN employee ON matched.employeeId = employee.employeeId "
                    + "                                     INNER JOIN foundluggage ON matched.foundluggage = foundluggage.registrationNr "
                    + "                                         INNER JOIN passenger ON foundluggage.passengerId = passenger.passengerId");

            while (resultSet.next()) {
                String delivercheck = resultSet.getString("matched.delivery");
                if (!"".equals(delivercheck)) {
                    int registrationnr = resultSet.getInt("matched.matchedId");
                    String date = resultSet.getString("matched.dateMatched");
                    String passengername = resultSet.getString("passenger.name");
                    String employeename = resultSet.getString("employee.firstname");
                    String delivered = resultSet.getString("matched.delivery");
                    int koffer = resultSet.getInt("matched.lostluggage");

                    retrievedList.add(new RetrievedLuggage(registrationnr, koffer, date, passengername, employeename, delivered));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerReportViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retrievedList;
    }

}
