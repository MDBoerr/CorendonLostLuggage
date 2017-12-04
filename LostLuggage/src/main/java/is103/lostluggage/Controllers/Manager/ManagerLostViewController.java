package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import static is103.lostluggage.Controllers.Manager.ManagerRetrievedViewController.retrievedLuggage;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.User;
//import is103.lostluggage.Controllers.Service.Luggage;
//import static is103.lostluggage.Controllers.Service.ServiceVermisteOverzichtViewController.luggageList;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerLostViewController implements Initializable {

    //luggage list
//    public static ObservableList<Luggage> luggageList;
    private int id = 0;

    @FXML
    public TableView LostTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
        
        
            

        

        

            

            

               

                

            }


    }

    

