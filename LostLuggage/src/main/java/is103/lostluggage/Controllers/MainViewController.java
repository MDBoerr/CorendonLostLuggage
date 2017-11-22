package is103.lostluggage.Controllers;

import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class MainViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button backButton;

    public static String previousView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void goBackToPreviousScene(ActionEvent event) throws IOException {


            if (previousView != null) {
                System.out.println("Is not empty");

                MainApp.switchView(previousView);

            } else {
                System.out.println("Is empty");
            }

        
        //backButton.getScene().h
        //((Node)(event.getSource())).getScene().getWindow().hide();                      

    }

}
