package is103.lostluggage;

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
import javafx.event.ActionEvent;

public class FXMLController implements Initializable {

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<User, String>("Id"));
        lastName.setCellValueFactory(new PropertyValueFactory<User, String>("LastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<User, String>("FirstName"));
        level.setCellValueFactory(new PropertyValueFactory<User, String>("Level"));
        status.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));

        tableView.setItems(getThings());

    }

    public ObservableList<User> getThings() {

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("5555", "de Boer", "Mil", "Admin", "Active"));
        users.add(new User("6666", "de Boer", "Michael", "Admin", "Active"));

        return users;
    }
}
