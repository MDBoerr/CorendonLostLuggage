/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Admin;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    private String header = "Voeg een Gebruiker toe";

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //To Previous Scene
        MainViewController.previousView = "/Views/HomeUserView.fxml";
    }

    @FXML
    protected void backHomeUserView(ActionEvent event) throws IOException, SQLException {

        String userName = userNameField.getText();
        String userPassword = passwordField.getText();

        System.out.println("username: " + userName + "password: " + userPassword);

        MyJDBC db = new MyJDBC("AirlineDemo");

//        ResultSet resultSet;
//
//        resultSet = db.executeResultSetQuery("SELECT IATACode, Name, TimeZone FROM Airport");
        try {

            String query = String.format("INSERT INTO Airport(`IATACode` , `Name` , `TimeZone` ) VALUES (%s, %s, 2)", userName, userPassword);
            int result;
            result = db.executeUpdateQuery(query);
        } catch (Exception e) {
        }

        MainApp.switchView("/Views/UserScene.fxml");
    }

}
