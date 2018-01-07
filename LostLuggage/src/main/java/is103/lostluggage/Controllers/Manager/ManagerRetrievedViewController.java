package is103.lostluggage.Controllers.Manager;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.Admin.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
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
    private JFXTextField formid;
    @FXML
    private JFXTextField customerid;
    @FXML
    private JFXTextField deivererid;
    @FXML
    private JFXTextField emailid;
    @FXML
    private JFXTextField adresid;
    @FXML
    private JFXTextField dateid;

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

                formid = new JFXTextField();

                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Node node = ((Node) event.getTarget()).getParent();

                    TableRow row;

                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    System.out.println(row.getItem());
                    
                    
                    
                    int formid = retrievedTable.getSelectionModel().getSelectedItem().getFormID();
                    
                    String formidString = Integer.toString(formid);
                    
//                   formid.setText(formidString);
                    
                    
                    System.out.println(formid);
//        retrievedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        retrievedTable.getSelectionModel().setCellSelectionEnabled(true);
//
//        retrievedTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
//            if (event.isShortcutDown() || event.isShiftDown()) {
//                event.consume();
//            }
//        });
//
//        retrievedTable.getFocusModel().focusedCellProperty().addListener((obs, oldVal, newVal) -> {
//
//            if (newVal.getTableColumn() != null) {
//                retrievedTable.getSelectionModel().selectRange(0, newVal.getTableColumn(), retrievedTable.getItems().size(), newVal.getTableColumn());
//                System.out.println("Selected TableColumn: " + newVal.getTableColumn().getText());
//                System.out.println("Selected column index: " + newVal.getColumn());
//            }
//        });
//
//        retrievedTable.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
//            if (event.isShortcutDown() || event.isShiftDown()) {
//                event.consume();
//            }
//        });
                }

            }

        });

    }
    
//        public TextField<customerdetails> getcustomerdetails(){
//                            
//                    int formid = retrievedTable.getSelectionModel().getSelectedItem().getFormID();
//                    
//                    String formidString = Integer.toString(formid);
//                    
//                   formid.setText(formidString);
//                    
//                    
//                    System.out.println(formid);
//    }


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
