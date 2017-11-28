package is103.lostluggage.Controllers.Admin;

import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.User;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

//import javax.swing.text.TableView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

public class OverviewUserController implements Initializable {

    private String header = "Overzicht Gebruikers";

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> id;
    @FXML
    private TableColumn<User, String> lastName;
    @FXML
    private TableColumn<User, String> firstName;
    @FXML
    private TableColumn<User, String> level;
    @FXML
    private TableColumn<User, String> status;

//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//    }
    @FXML
    private void goToAddView(ActionEvent event) {
        try {
            MainApp.switchView("/fxml/AdminAddUserView.fxml");

        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        id.setCellValueFactory(new PropertyValueFactory<User, String>("Id"));
        lastName.setCellValueFactory(new PropertyValueFactory<User, String>("LastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<User, String>("FirstName"));
        level.setCellValueFactory(new PropertyValueFactory<User, String>("Level"));
        status.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));

        tableView.setItems(getThings());

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
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
                        MainApp.switchView("/fxml/AdminAddUserView.fxml");
                    } catch (IOException ex) {
                        Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        //To Previous Scene
        MainViewController.previousView = "/Views/HomeUserView.fxml";

    }

    public ObservableList<User> getThings() {

        ObservableList<User> users = FXCollections.observableArrayList();

        try {
            MyJDBC db = new MyJDBC("AirlineDemo");

            ResultSet resultSet;

            resultSet = db.executeResultSetQuery("SELECT IATACode, Name, TimeZone FROM Airport");

            while (resultSet.next()) {
                String iATACode = resultSet.getString("IATACode");
                String name = resultSet.getString("Name");
                int timeZone = resultSet.getInt("TimeZone");

                System.out.println("IATACode: " + iATACode + "  Name: " + name + " TimeZone: " + timeZone);
                users.add(new User(iATACode, name, "Mil", "Admin", "Active"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //users.add(new User("6666", "de Boer", "Michael", "Admin", "Active"));

        return users;
    }

}
