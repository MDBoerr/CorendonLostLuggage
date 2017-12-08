package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author gebruiker
 */
public class ServiceInputLuggageViewController implements Initializable {

    //public static ServiceMissedOverviewViewController serviceHomeView;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    //Choicebox that determines whether the form should be for a missing or found
    //luggage
    private JFXComboBox missingFoundComboBox, airportJFXComboBox, flightJFXComboBox, destinationJFXComboBox, typeJFXComboBox, colorJFXComboBox,
            secondColorJFXComboBox;
    
    @FXML
    private GridPane mainGridPane,travellerInfoGridPane, luggageInfoGridPane ;
        
    @FXML
    private Label passengerInformationLbl, missingLbl;
    
    @FXML
    private JFXDatePicker dateJFXDatePicker;
    
    @FXML
    private JFXTimePicker timeJFXTimePicker;
    
    @FXML
    private JFXTextField timeJFXTextField, nameJFXTextField, addressJFXTextField, placeJFXTextField, 
            postalcodeJFXTextField, countryJFXTextField, phoneJFXTextField, emailJFXTextField, labelnumberJFXTextField, brandJFXTextField,
            characterJFXTextField;
    private Object locationFoundJFXComboBox;
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        

        
        try {
            //set the value of the comboboxes
            setComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceInputLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //This method puts the values into the combo boxes
    public void setComboBox() throws SQLException{
        
        //Missing or Found combobox
        
        //Add options to choicebox
        missingFoundComboBox.getItems().addAll("Found", "Missing");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        missingFoundComboBox.setValue("Missing");
        
        //connection to the database
        MyJDBC db = MainApp.connectToDatabase();
        
        //Color combo box
        ResultSet colorResultSet = db.executeResultSetQuery("SELECT * FROM color");
        
        //add the colors to the combo box
        while(colorResultSet.next()){
            colorJFXComboBox.getItems().add(colorResultSet.getString(2));
        }
        
        //secondColor combo box
        ResultSet secondColorResultSet = db.executeResultSetQuery("SELECT * FROM color");
        
        while(secondColorResultSet.next()){
            secondColorJFXComboBox.getItems().add(secondColorResultSet.getString(2));
        }
        
        
        //Flight combo box
        ResultSet flightResultSet = db.executeResultSetQuery("SELECT * FROM flight");
        
        while(flightResultSet.next()){
            flightJFXComboBox.getItems().add(flightResultSet.getString(1));
        }
        
        //Luggagetype combo box
        ResultSet typeResultSet = db.executeResultSetQuery("SELECT * FROM luggagetype");
        
        while(typeResultSet.next()){
            typeJFXComboBox.getItems().add(typeResultSet.getString(2));
        }
        
        //Airport/destination combobox
        ResultSet airportResultSet = db.executeResultSetQuery("SELECT * FROM destination");
        
        while(airportResultSet.next()){
            airportJFXComboBox.getItems().add(airportResultSet.getString(2));
            destinationJFXComboBox.getItems().add(airportResultSet.getString(2));
        }
        
    }
    
    @FXML
    //This method switches the form between found or missing
    public void foundOrMissing() throws SQLException{
        
      String value = missingFoundComboBox.getValue().toString();
         
        //connection to the database
        MyJDBC db = MainApp.connectToDatabase();
        
        JFXComboBox locationFoundJFXComboBox = new JFXComboBox();
        locationFoundJFXComboBox.setId("locationFoundJFXComboBox");
        locationFoundJFXComboBox.setPromptText("Location");
        
        
        //location combo box
        ResultSet locationResultSet = db.executeResultSetQuery("SELECT * FROM location");
        
        //add the location to the combo box
        while(locationResultSet.next()){
            locationFoundJFXComboBox.getItems().add(locationResultSet.getString(2));
        }
        
      
        if(value == "Found"){
                  
            //Remove traveller and luggage info so they can change positions
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            
            //Add them again
            mainGridPane.add(luggageInfoGridPane, 0, 1);
            mainGridPane.add(travellerInfoGridPane, 1, 1);
            
            luggageInfoGridPane.add(locationFoundJFXComboBox, 1, 7);
            passengerInformationLbl.setText("Passenger information is not required");
            
            //Change the text in the top left corner
            missingLbl.setText("Found");
        }
        
        if(value == "Missing"){

            //Remove the gridpanes
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            //Add them again on different positions
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
            
            //Change the text
            missingLbl.setText("Missing");
            
            //Remove the locationfound
            luggageInfoGridPane.getChildren().remove(locationFoundJFXComboBox);
        }
    }
    
    //Submit the form
    @FXML
    public void submitForm() throws SQLException{
        
        //first check which form it is
        String value = missingFoundComboBox.getValue().toString();
        
        //connection to the database
        MyJDBC db = MainApp.connectToDatabase();
        
        
        if(value == "Found"){
            
            String table = "foundluggage";
            
            System.out.println("Found form has been submitted");
            
            //get the values from the fields
            
            //date
            String date = dateJFXDatePicker.getValue().toString();
  
            //time
            String time = timeJFXTimePicker.getValue().toString();
            
            //airport
            String airport = airportJFXComboBox.getValue().toString();
           
            //select the IATACODE for the selected airport
            ResultSet iataCodeResult = db.executeResultSetQuery("SELECT * FROM destination WHERE airport='"+airport+"'");

            
            while(iataCodeResult.next()){
                airport = iataCodeResult.getString("IATACode");
            }
            
            System.out.println(airport);
          
            //Passenger

            //name
            String name = nameJFXTextField.getText();
            System.out.println(name);
            
            //address
            String address = addressJFXTextField.getText();
            System.out.println(address);
            
            //place
            String place = placeJFXTextField.getText();
            System.out.println(place);
            
            //postalcode
            String postalCode = postalcodeJFXTextField.getText();
            System.out.println(postalCode);
            
            //country
            String country = countryJFXTextField.getText();
            System.out.println(country);
            
            //phone
            String phone = phoneJFXTextField.getText();
            System.out.println(phone);
            
            //email
            String email = emailJFXTextField.getText();
            System.out.println(email);
            
            //Luggage
            
            //Labelnumber
            String labelNumber = labelnumberJFXTextField.getText();
            System.out.println(labelNumber);
            
            //Flight
            String flight = flightJFXComboBox.getValue().toString();
            System.out.println(flight);
            
            //Destination
            String destination = destinationJFXComboBox.getValue().toString();
            System.out.println(destination);
            
            //Type
            String type = typeJFXComboBox.getValue().toString();
            System.out.println(type);
            
            //Brand
            String brand = brandJFXTextField.getText();
            System.out.println(brand);
            
            //Color
            String color = colorJFXComboBox.getValue().toString();
            System.out.println(color);
            
            //Second Color
            String secondColor = secondColorJFXComboBox.getValue().toString();
            System.out.println(secondColor);
            
            //Characteristics
            String character = characterJFXTextField.getText();
            System.out.println(character);
            
            //Location found need to fix this
            String location = "toilet";
            
            String employeeId = "ak";
            
     
 
        }
        
        if(value == "Missing"){
            System.out.println("Missing form has been submitted");
            
            //get the values from the fields
            
            //date
            String date = dateJFXDatePicker.getValue().toString();
            System.out.println(date);
  
            //time
            String time = timeJFXTimePicker.getValue().toString();
            System.out.println(time);
            
            //airport
            String airport = airportJFXComboBox.getValue().toString();
            System.out.println(airport);
          
            //Passenger

            //name
            String name = nameJFXTextField.getText();
            System.out.println(name);
            
            //address
            String address = addressJFXTextField.getText();
            System.out.println(address);
            
            //place
            String place = placeJFXTextField.getText();
            System.out.println(place);
            
            //postalcode
            String postalCode = postalcodeJFXTextField.getText();
            System.out.println(postalCode);
            
            //country
            String country = countryJFXTextField.getText();
            System.out.println(country);
            
            //phone
            String phone = phoneJFXTextField.getText();
            System.out.println(phone);
            
            //email
            String email = emailJFXTextField.getText();
            System.out.println(email);
            
            //Luggage
            
            //Labelnumber
            String labelNumber = labelnumberJFXTextField.getText();
            System.out.println(labelNumber);
            
            //Flight
            String flight = flightJFXComboBox.getValue().toString();
            System.out.println(flight);
            
            //Destination
            String destination = destinationJFXComboBox.getValue().toString();
            System.out.println(destination);
            
            //Type
            String type = typeJFXComboBox.getValue().toString();
            System.out.println(type);
            
            //Brand
            String brand = brandJFXTextField.getText();
            System.out.println(brand);
            
            //Color
            String color = colorJFXComboBox.getValue().toString();
            System.out.println(color);
            
            //Second Color
            String secondColor = secondColorJFXComboBox.getValue().toString();
            System.out.println(secondColor);
            
            //Characteristics
            String character = characterJFXTextField.getText();
            System.out.println(character);
            
            //Location found need to fix this
            String location = "toilet";
        }
        
    }
    

}
