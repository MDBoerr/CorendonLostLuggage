/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Admin;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Arthur
 *
 */
public class AdminAddUserViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button backBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //To Previous Scene
        MainViewController.previousView = "/Views/HomeUserView.fxml";
    }

    @FXML
    protected void backHomeUserView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/HomeUserView.fxml");
    }

}
