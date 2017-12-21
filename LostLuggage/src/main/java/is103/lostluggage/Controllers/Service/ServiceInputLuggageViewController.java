package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Arthur Krom
 */
public class ServiceInputLuggageViewController implements Initializable {

    
    @FXML
    //luggage
    private JFXComboBox missingFoundComboBox, airportJFXComboBox, flightJFXComboBox, destinationJFXComboBox, typeJFXComboBox, colorJFXComboBox,
            secondColorJFXComboBox, locationJFXComboBox;
    
    @FXML
    private GridPane mainGridPane,travellerInfoGridPane, luggageInfoGridPane ;
        
    @FXML
    private Label passengerInformationLbl, missingLbl;
    
    @FXML
    private JFXDatePicker dateJFXDatePicker;
    
    @FXML
    private JFXTimePicker timeJFXTimePicker;
    
    @FXML
    private JFXTextField nameJFXTextField, addressJFXTextField, placeJFXTextField, 
            postalcodeJFXTextField, countryJFXTextField, phoneJFXTextField, emailJFXTextField, labelnumberJFXTextField, brandJFXTextField,
            characterJFXTextField;
  
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        //Define the previous view
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //set the title of the view, default form to be displayed is Missing
        changeTitle("Missing luggage form");
        
        //Set the value of the comboboxes
        try {
            setComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceInputLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    //This method changes the title of the view
    public void changeTitle(String title){
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(ServiceInputLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //This method sets all the values for the comboboxes.
    public void setComboBox() throws SQLException{
        
        //Missing or Found combobox
        
        //Add options to choicebox
        missingFoundComboBox.getItems().addAll("Found", "Missing");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        missingFoundComboBox.setValue("Missing");
                
        //Color combo box
        ResultSet colorResultSet = MainApp.db.executeResultSetQuery("SELECT * FROM color");
        
        //add the colors to the combo box
        while(colorResultSet.next()){
            colorJFXComboBox.getItems().add(colorResultSet.getString(2));
        }
        
        //secondColor combo box
        ResultSet secondColorResultSet = MainApp.db.executeResultSetQuery("SELECT * FROM color");
        
        while(secondColorResultSet.next()){
            secondColorJFXComboBox.getItems().add(secondColorResultSet.getString(2));
        }
        
        //Location combo box
        ResultSet locationResultSet = MainApp.db.executeResultSetQuery("SELECT * FROM location");
        
        while(locationResultSet.next()){
            locationJFXComboBox.getItems().add(locationResultSet.getString(2));
        }
        
        //Flight combo box
        ResultSet flightResultSet = MainApp.db.executeResultSetQuery("SELECT * FROM flight");
        
        while(flightResultSet.next()){
            flightJFXComboBox.getItems().add(flightResultSet.getString(1));
        }
        
        //Luggagetype combo box
        ResultSet typeResultSet = MainApp.db.executeResultSetQuery("SELECT * FROM luggagetype");
        
        while(typeResultSet.next()){
            typeJFXComboBox.getItems().add(typeResultSet.getString(2));
        }
        
        //Airport/destination combobox
        ResultSet airportResultSet = MainApp.db.executeResultSetQuery("SELECT * FROM destination");
        
        while(airportResultSet.next()){
            airportJFXComboBox.getItems().add(airportResultSet.getString(2));
            destinationJFXComboBox.getItems().add(airportResultSet.getString(2));
        }
        
    }
    
    @FXML
    //This method switches the form between found or missing
    public void foundOrMissing() throws SQLException{
        
      String value = missingFoundComboBox.getValue().toString();

        if(value == "Found"){
                  
            //Remove traveller and luggage info so they can change positions
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            
            //Add them again
            mainGridPane.add(luggageInfoGridPane, 0, 1);
            mainGridPane.add(travellerInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Passenger information is not required");
            
            //Change the text in the top left corner
            missingLbl.setText("Found");
            
            //Change the title of the view
            changeTitle("Found luggage form");
            
            locationJFXComboBox.setVisible(true);
        }
        
        if(value == "Missing"){

            //Remove the gridpanes
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            //Add them again on different positions
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Passenger information");
            
            //Change the text
            missingLbl.setText("Missing");
            
            //Change the title of the view
            changeTitle("Missing luggage form");
            
            locationJFXComboBox.setVisible(false);
            
        }
    }
    
    @FXML
    public void submitForm(ActionEvent event){
        
        
        //General information
        String date = "", time = "", airport = "", formtype = missingFoundComboBox.getValue().toString();
        
        //Passenger information
        String name = "", address = "", place = "", postalcode = "", country = "", phone = "", email = "";

        //Luggage information
        String labelnumber = "", flight = "", destination = "", type = "", brand = "", color = "", secondColor = "", characteristics = "";
        
        
        //Get the date        
        if(dateJFXDatePicker.getValue() != null && !dateJFXDatePicker.getValue().toString().isEmpty()){
            date = dateJFXDatePicker.getValue().toString();
        }
        
        //Get the time
        if(timeJFXTimePicker.getValue() != null && !timeJFXTimePicker.getValue().toString().isEmpty()){
            time = timeJFXTimePicker.getValue().toString();
        }
        
        //Get the airport the form is filled in from
        if(airportJFXComboBox.getValue() != null && !airportJFXComboBox.getValue().toString().isEmpty()){
            airport = airportJFXComboBox.getValue().toString();
        }
        
        //Print general information
        System.out.println("General information\nFormtype: " + formtype + "\nDate: " + date + "\nTime: " + time + "\nAirport: " + airport + "\n");

        
        //Get the passenger information
        name        = nameJFXTextField.getText();
        address     = addressJFXTextField.getText();
        place       = placeJFXTextField.getText();
        postalcode  = postalcodeJFXTextField.getText();
        country     = countryJFXTextField.getText();
        phone       = phoneJFXTextField.getText();
        email       = emailJFXTextField.getText();
        
        //Print passenger information
        System.out.println("Passenger information\n Name: " + name + "\nAddress: " + address + "\nPlace: " + place + "\nPostalcode: " + postalcode + "\nCountry: " + country
        + "\nPhone: " + phone + "\nEmail: " + email + "\n");
        
        
        //Get the luggage information
        labelnumber = labelnumberJFXTextField.getText();
        
        if(flightJFXComboBox.getValue() != null && !flightJFXComboBox.getValue().toString().isEmpty()){
            flight = flightJFXComboBox.getValue().toString();
        }
        
        if(destinationJFXComboBox.getValue() != null && !destinationJFXComboBox.getValue().toString().isEmpty()){
            destination = destinationJFXComboBox.getValue().toString();
        }
        
        if(typeJFXComboBox.getValue() != null && !typeJFXComboBox.getValue().toString().isEmpty()){
            type = typeJFXComboBox.getValue().toString();
        }
        
        brand = brandJFXTextField.getText();
        
        if(colorJFXComboBox.getValue() != null && !colorJFXComboBox.getValue().toString().isEmpty()){
            color = colorJFXComboBox.getValue().toString();
        }
        
        if(secondColorJFXComboBox.getValue() != null && !secondColorJFXComboBox.getValue().toString().isEmpty()){
            secondColor = secondColorJFXComboBox.getValue().toString();
        }
        
        characteristics = characterJFXTextField.getText();
        
        //Print the information
        System.out.println("Luggage information \nLabel: " + labelnumber + "\nFlight: " + flight + "\nDestination: " + destination + "\nType: " + type + "\nBrand: " + brand + "\nColor: " + color
        + "\nSecond Color: " + secondColor + "\nCharacteristics: " + characteristics + "\n");
        
        //Is it found luggage? else its missing
        if(formtype == "Found"){
            
            String query = "";
            
            int affectedRows = MainApp.db.executeUpdateQuery(query);
        }else{
            
            //Query to add the passenger
            String addPassengerQuery = "INSERT INTO passenger VALUES(NULL ,'"+name+"', '"+address+"', '"+place+"', '"+postalcode+"', '"+country+"', '"+email+"', '"+phone+"')";
                        
            int affectedRows = MainApp.db.executeUpdateQuery(addPassengerQuery);
            
            //Query to add the missing luggage
            String addMissingLuggageQuery = "INSERT INTO missingluggage VALUES()";
            
            //TODO:Check whether the user has already registered with this e-mailaddress
         
            

        }
        
        
        
        
        
        
    }    
}
