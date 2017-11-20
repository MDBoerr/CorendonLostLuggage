package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.HomeUserViewController;
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
public class ManagerRapportageViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

        @FXML 
    protected void naarVermisteOverzichtScherm(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerHomeView.fxml");
    }
}
