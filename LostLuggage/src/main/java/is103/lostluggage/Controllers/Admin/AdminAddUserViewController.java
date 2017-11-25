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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    private Label firstnameLbl;

    @FXML
    //This label displays an errors
    private Label errorMessageLbl;

    @FXML
    private Button addUserBtn;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox roleChoiceBox;

    private String header = "Voeg een Gebruiker toe";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Set which view was previous
        MainViewController.previousView = "/Views/HomeUserView.fxml";

        //Add options to choicebox
        roleChoiceBox.getItems().addAll("Administrator", "Manager", "Service Employee");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        roleChoiceBox.setValue("Service Employee");
    }

    @FXML
    protected void backHomeUserView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/HomeUserView.fxml");
    }

    @FXML
    //This method allows the administrator to add a new user to the system
    /*
    @TODO
    Verify the entered info (amount of chars, weird chars, etc)
    Enter username/pw or does the system generate it?
    Magic numbers
     */
    public void addUser(ActionEvent event) {

        //default error message is empty
        String errorMessage = "";

        String firstname = firstnameField.getText();

        if (firstname.length() < 4) {
            errorMessage += "Firstname must contain atleast 4 characters\n";
        }

        String surname = surnameField.getText();

        if (surname.length() < 4) {
            errorMessage += ("Surname must contain atleast 4 characters\n");
        }

        String password = passwordField.getText();

        if (password.length() < 6) {
            errorMessage += ("Password must contain atleast 6 charactersa ");
        }
        
        //Put the error message on the label
        errorMessageLbl.setText(errorMessage);

        
        String role = roleChoiceBox.getValue().toString();

    }

}
