/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Arthur
 *
 */
public class AdminAddUserViewController implements Initializable {

    public static ObservableList<String> roleList;

    public static ObservableList<String> statusList;

    public static UUID uid;

    @FXML
    private AnchorPane navList;

    @FXML
    private JFXComboBox statusComboBox;

    @FXML
    private JFXComboBox roleComboBox;

    @FXML
    //This label displays a errors
    private Label errorMessageLbl;

    @FXML
    //Button to add the user to the system
    private Button addUserBtn;

    @FXML
    //Field that contains the employeeId
    private JFXTextField employeeIdField;

    @FXML
    //Field that contains the firstname
    private JFXTextField firstnameField;

    @FXML
    //Field that contains the surname
    private JFXTextField lastnameField;

    @FXML
    //Field that contains the phonenumber
    private TextField locationField;

    @FXML
    //Choicebox that contains the status of the employee
    private ChoiceBox statusChoiceBox;

    @FXML
    //Choicebox that contains the role of the employee
    private ChoiceBox roleChoiceBox;

    //Title of the view
    private String header = "Voeg een Gebruiker toe";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //navList.toBack();
        //activateSlideErrorMessageAnimation(false);
        //navList.setTranslateY(-(navList.getHeight()));
        //Set which view was previous
        MainViewController.previousView = "/Views/HomeUserView.fxml";

        //Add options to List
        roleList = FXCollections.observableArrayList(
                "Administrator",
                "Manager",
                "Service Employee"
        );
        statusList = FXCollections.observableArrayList(
                "Active",
                "Inactive"
        );

        //Add options to Combobox
        roleComboBox.setItems(roleList);
        statusComboBox.setItems(statusList);

        //Default value of role is set to Service Employee as Administrator will most likely add a user with that role.
        //statusChoiceBox.setValue("Active");
    }

    @FXML
    protected void backToHomeUserView(ActionEvent event) throws IOException {
        //MainApp.switchView("/Views/HomeUserView.fxml");
        activateSlideErrorMessageAnimation(false);
    }

    @FXML
    public void addUser(ActionEvent event) {

        //default error message is empty
        String errorMessage = "";
        activateSlideErrorMessageAnimation(true);
        String firstname = firstnameField.getText();

        if (firstname.isEmpty()) {
            errorMessage += "Firstname cannot be empty\n";
        }

        String lastname = lastnameField.getText();

        if (lastname.isEmpty()) {
            errorMessage += "Surname cannot be empty\n";
        }

        String location = locationField.getText();

        if (location.isEmpty()) {
            errorMessage += "Phone cannot be empty";
        }

        if (statusComboBox.getValue() != null
                && !statusComboBox.getValue().toString().isEmpty()) {
            String statusChoice = statusComboBox.getValue().toString();
            System.out.println(statusChoice);
        } else {
            errorMessage += "You have not selected a recipient!";
        }
        if (roleComboBox.getValue() != null
                && !roleComboBox.getValue().toString().isEmpty()) {
            String roleChoice = roleComboBox.getValue().toString();
            System.out.println(roleChoice);
        }

        //Put the error message on the label
        errorMessageLbl.setText(errorMessage);

//        //The role of the added user
//        String role = roleChoiceBox.getValue().toString();
//
//        //The status of the added user
//        String status = statusChoiceBox.getValue().toString();
        //If the error message is empty it means there are no errors 
        if (errorMessage.isEmpty()) {
            //add user to database with password hashed

        } else {
            //Wait until the user solved all the errors
        }
        System.out.println(uid.randomUUID());

    }

    public void activateSlideErrorMessageAnimation(Boolean showError) {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        //openNav.setToY(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);
        if (showError == true) {
            openNav.setToY(0);
            openNav.play();

        } else {
            // System.out.println(navList.getHeight());
            closeNav.setToY(-(navList.getHeight()));
            closeNav.play();
        }

    }

//function to generate the employeeid by using the first letter of the firstname
//and the first letter of the surname
    public void generateEmployeeId() {

        //initialy the employee id field is empty
        String employeeId = "";

        //array of characters in the employeeId
        char[] employeeIdChars = new char[2];

        //Check whether the input string is empty or not
        if (!firstnameField.getText().isEmpty()) {

            //If its not empty, then the first letter of the string will be the first
            //letter of the employeeId
            employeeIdChars[0] = firstnameField.getText().charAt(0);
        }

        if (!lastnameField.getText().isEmpty()) {

            employeeIdChars[1] = lastnameField.getText().charAt(0);
        }

        //employeeId in string lowercase
        employeeId = String.valueOf(employeeIdChars).toLowerCase();

        //Set the employeeId in the employeeId Field
        employeeIdField.setText(employeeId);

        //check if the field contains characters, if it check in the database
        //whether the employeeId already exists, if it does add a number to the id
        //check how many ak's exist.. forexample ak, ak1, ak2 so make sure the id in this field
        //will be ak3
    }

//    public static UUID randomUUID() {
//            UUID id = new UUID();
//            return id;
//    }
}
