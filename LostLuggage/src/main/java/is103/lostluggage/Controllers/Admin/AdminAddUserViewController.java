/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Michael de Boer GenerateId() Arthur & Michael
 *
 */
public class AdminAddUserViewController implements Initializable {

    public static ObservableList<String> roleList;

    public static ObservableList<String> statusList;

    private double navListPrefHeight = 70.0d;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox errorMessageHBox;
    @FXML
    private AnchorPane errorMessageView;
    @FXML
    private AnchorPane whiteAnchorPane;
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
    private JFXTextField locationField;

    //Title of the view
    private String header = "Add User";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set Header
        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Set which view was previous
        MainViewController.previousView = "/Views/Admin/HomeUserView.fxml";

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

        mainBorderPane.setTop(null);

    }

    @FXML
    protected void backToHomeUserView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Admin/HomeUserView.fxml");

    }

    @FXML
    public void addUser(ActionEvent event) {

        //default error message is empty
        String errorMessage = "";

        //Get values from TextFields
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String location = locationField.getText();
        Object status = statusComboBox.getValue();
        Object role = roleComboBox.getValue();

        //Counter for empty fields
        int amount = 0;
        //Array of input fields & array of fields that are empty
        String[] fields = {"Firstname", "Lastname", "Aiport / City", "Status", "Role"};
        String[] emptyfields = new String[amount];

        //If one or more fields are empty show errorMessageView, setFocusColor to red 
        if (firstname.isEmpty() || lastname.isEmpty() || location.isEmpty() || statusComboBox.getValue() == null
                || statusComboBox.getValue().toString().isEmpty() || roleComboBox.getValue() == null
                || roleComboBox.getValue().toString().isEmpty()) {

            emptyfields = new String[fields.length];

            if (firstnameField.getText().isEmpty()) {
                emptyfields[amount] = fields[0];
                firstnameField.setUnFocusColor(Paint.valueOf("#f03e3e"));
                amount++;
            } else {
                firstnameField.setUnFocusColor(Paint.valueOf("#4d4d4d"));

            }

            if (lastnameField.getText().isEmpty()) {
                emptyfields[amount] = fields[1];
                amount++;
                lastnameField.setUnFocusColor(Paint.valueOf("#f03e3e"));
            } else {
                lastnameField.setUnFocusColor(Paint.valueOf("#4d4d4d"));
            }

            if (location.isEmpty()) {
                emptyfields[amount] = fields[2];
                amount++;
                locationField.setUnFocusColor(Paint.valueOf("#f03e3e"));

            } else {
                locationField.setUnFocusColor(Paint.valueOf("#4d4d4d"));
            }

            if (statusComboBox.getValue() != null
                    && !statusComboBox.getValue().toString().isEmpty()) {
                String statusChoice = statusComboBox.getValue().toString();
                System.out.println(statusChoice);

            } else {
                emptyfields[amount] = fields[3];
                amount++;
                statusComboBox.setUnFocusColor(Paint.valueOf("#f03e3e"));

            }
            if (roleComboBox.getValue() != null
                    && !roleComboBox.getValue().toString().isEmpty()) {
                String roleChoice = roleComboBox.getValue().toString();
                System.out.println(roleChoice);
            } else {
                emptyfields[amount] = fields[4];
                amount++;
                roleComboBox.setUnFocusColor(Paint.valueOf("#f03e3e"));

            }
            //Main Error message
            errorMessage = "The following fields can't be empty:  ";

            //Add empty fields to Error Message
            for (int i = 0; i <= emptyfields.length - 1; i++) {
                if (emptyfields[i] != null) {
                    errorMessage += emptyfields[i];

                    if (i < (amount - 1)) {
                        errorMessage += ", ";

                    }
                    System.out.print(emptyfields[i]);
                }

            }
            //Put the error message on the label
            errorMessageLbl.setText(errorMessage);

            //Start errorMessageView animation after error message contains all empty fields
            startAnimation();

            System.out.println(UUID.randomUUID());

        } //All fields are valid, there are no errors
        else {
            //Temporary id
            String id = UUID.randomUUID().toString().substring(0, 8);
            String roleString = role.toString();
            String statusString = status.toString();

            MyJDBC db = MainApp.connectToDatabase();

            String query = String.format("INSERT INTO User VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", id, firstname, lastname, location, statusString, roleString);

            int result = db.executeUpdateQuery(query);
            System.out.println(" This is the result:  " + result);

        }
    }

    public void startAnimation() {
        errorMessageView.prefHeight(0.0d);
        mainBorderPane.setTop(errorMessageView);
        errorMessageHBox.getChildren().add(errorMessageLbl);
        errorMessageLbl.setVisible(true);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                new KeyValue(errorMessageView.prefHeightProperty(), 0)
        ),
                new KeyFrame(Duration.millis(300.0d),
                        new KeyValue(errorMessageView.prefHeightProperty(), navListPrefHeight)
                )
        );
        addUserBtn.setDisable(true);
        timeline.play();

        PauseTransition wait = new PauseTransition(Duration.seconds(4));
        wait.setOnFinished((e) -> {
            dismissAnimation();
        });
        wait.play();

    }

    public void dismissAnimation() {
        errorMessageView.prefHeight(0.0d);
        mainBorderPane.setTop(errorMessageView);
        errorMessageHBox.getChildren().remove(errorMessageLbl);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(errorMessageView.prefHeightProperty(), navListPrefHeight)
                ),
                new KeyFrame(Duration.millis(300.0d),
                        new KeyValue(errorMessageView.prefHeightProperty(), 0)
                )
        );
        timeline.play();
        addUserBtn.setDisable(false);

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
}
