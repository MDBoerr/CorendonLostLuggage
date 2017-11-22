package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.MainViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class RapportagesViewController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //To Previous Scene
        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
        // TODO
    }    
    
    
}
