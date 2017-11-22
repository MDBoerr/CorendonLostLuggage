package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerHomeViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/fxml/SelectUserRoleView.fxml";
    }
    //switch between screens from managerhome to others

    @FXML
    protected void toFoundView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerGevondenView.fxml");
    }

    @FXML
    protected void reportView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerRapportageView.fxml");
    }

    @FXML
    protected void toLostView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerVerlorenView.fxml");
    }

    @FXML
    protected void toRetrievedView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerTerugView.fxml");
    }
}
