package is103.lostluggage.Controllers.Admin;

import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.event.ActionEvent;

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
    private void goBackToPreviousScene(ActionEvent event) {
        
        try {
            MainApp.switchView("/Views/HomeUserView.fxml");
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
        
        //To Previous Scene
        MainViewController.previousView = "/Views/HomeUserView.fxml";
        
       

    }

    public ObservableList<User> getThings() {

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("5555", "de Boer", "Mil", "Admin", "Active"));
        users.add(new User("6666", "de Boer", "Michael", "Admin", "Active"));

        return users;
    }
    

}
