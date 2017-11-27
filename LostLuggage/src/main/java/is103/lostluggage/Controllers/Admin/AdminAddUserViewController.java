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
import java.util.Arrays;
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
    //This label displays a errors
    private Label errorMessageLbl;

    @FXML
    //Button to add the user to the system
    private Button addUserBtn;
    
    @FXML
    //Field that contains the employeeId
    private TextField employeeIdField;

    @FXML
    //Field that contains the firstname
    private TextField firstnameField;

    @FXML
    //Field that contains the surname
    private TextField surnameField;

    @FXML
    //Field that contains the phonenumber
    private TextField phoneField;

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

        //Set which view was previous
        MainViewController.previousView = "/Views/HomeUserView.fxml";

        //Add options to choicebox
        roleChoiceBox.getItems().addAll("Administrator", "Manager", "Service Employee");

        //Default value of role is set to Service Employee as Administrator will most likely add a user with that role.
        roleChoiceBox.setValue("Service Employee");

        //Add options to choicebox
        statusChoiceBox.getItems().addAll("Active", "Inactive");

        //Default value of role is set to Service Employee as Administrator will most likely add a user with that role.
        statusChoiceBox.setValue("Active");
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

        if(firstname.isEmpty()){
            errorMessage += "Firstname cannot be empty\n";
        }

        String surname = surnameField.getText();

        if(surname.isEmpty()) {
            errorMessage += "Surname cannot be empty\n";
        }
        
        String phone = phoneField.getText();
        
        if(phone.isEmpty()){
            errorMessage += "Phone cannot be empty";
        }

        //Put the error message on the label
        errorMessageLbl.setText(errorMessage);

        //The role of the added user
        String role = roleChoiceBox.getValue().toString();
        
        //The status of the added user
        String status = statusChoiceBox.getValue().toString();
        
        //If the error message is empty it means there are no errors 
        if(errorMessage.isEmpty()){
            //add user to database with password hashed
            
        }else{
            //Wait until the user solved all the errors
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
        if(!firstnameField.getText().isEmpty()){
            
            //If its not empty, then the first letter of the string will be the first
            //letter of the employeeId
            employeeIdChars[0] = firstnameField.getText().charAt(0);
        }
            
        if(!surnameField.getText().isEmpty()){
            
            employeeIdChars[1] = surnameField.getText().charAt(0);
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

}
