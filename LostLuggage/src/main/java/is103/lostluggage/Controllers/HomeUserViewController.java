/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers;

import is103.lostluggage.MainApp;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mike
 */
public class HomeUserViewController implements Initializable {

    @FXML
    private Label label1;
    @FXML
    private Button button;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        MainViewController.previousView = "/fxml/SelectUserRoleView.fxml";

    }

    @FXML
    protected void handleButtonAction(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/UserScene.fxml");
    }

    @FXML
    protected void viewAddUserWindow(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/AdminAddUserView.fxml");
    }

}
