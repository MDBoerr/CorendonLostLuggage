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
import java.util.ArrayList;
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
    private JFXComboBox missingFoundComboBox, flightJFXComboBox, typeJFXComboBox, colorJFXComboBox,
            secondColorJFXComboBox, locationJFXComboBox;
    
    @FXML
    private GridPane mainGridPane,travellerInfoGridPane, luggageInfoGridPane ;
        
    @FXML
    private Label passengerInformationLbl;
    
    @FXML
    private JFXDatePicker dateJFXDatePicker;
    
    @FXML
    private JFXTimePicker timeJFXTimePicker;
    
    @FXML
    private JFXTextField nameJFXTextField, addressJFXTextField, placeJFXTextField, 
            postalcodeJFXTextField, countryJFXTextField, phoneJFXTextField, emailJFXTextField, labelnumberJFXTextField, brandJFXTextField,
            characterJFXTextField, sizeJFXTextField, weightJFXTextField;
  
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
       
        //Add options to choicebox
        missingFoundComboBox.getItems().addAll("Found", "Missing");

        //Default value is set to Service Employee as Administrator will most likely add a user with that role.
        missingFoundComboBox.setValue("Missing");
                
        //Color combo box
        ResultSet colorResultSet = MainApp.getDatabase().executeResultSetQuery("SELECT * FROM color");
        
        //add the colors to the combo boxes
        while(colorResultSet.next()){
            colorJFXComboBox.getItems().add(colorResultSet.getString(2));
            secondColorJFXComboBox.getItems().add(colorResultSet.getString(2));
        }
        
        //Location combo box
        ResultSet locationResultSet = MainApp.getDatabase().executeResultSetQuery("SELECT * FROM location");
        
        while(locationResultSet.next()){
            locationJFXComboBox.getItems().add(locationResultSet.getString(2));
        }
        
        //Flight combo box
        ResultSet flightResultSet = MainApp.getDatabase().executeResultSetQuery("SELECT * FROM flight");
        
        while(flightResultSet.next()){
            flightJFXComboBox.getItems().add(flightResultSet.getString(1));
        }
        
        //Luggagetype combo box
        ResultSet typeResultSet = MainApp.getDatabase().executeResultSetQuery("SELECT * FROM luggagetype");
        
        while(typeResultSet.next()){
            typeJFXComboBox.getItems().add(typeResultSet.getString(2));
        }       
    }
    
    @FXML
    //This method switches the form between found or missing
    public void foundOrMissing(){
        
      String value = missingFoundComboBox.getValue().toString();

        if("Found".equals(value)){
                  
            //Remove traveller and luggage info so they can change positions
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            
            //Add them again
            mainGridPane.add(luggageInfoGridPane, 0, 1);
            mainGridPane.add(travellerInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Passenger information is not required");
            
            //Change the title of the view
            changeTitle("Found luggage form");
            
            //show the location combobox
            locationJFXComboBox.setVisible(true);
        }
        
        if("Missing".equals(value)){

            //Remove the gridpanes
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            //Add them again on different positions
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Passenger information");
            
            
            //Change the title of the view
            changeTitle("Missing luggage form");
            
            //hide the location combobox
            locationJFXComboBox.setVisible(false);
            
        }
    }
    
    @FXML
    //Method that will add the form to the database
    public void submitForm(ActionEvent event){
        
        //Check whether the fields have been filled in appropriately
        if(checkFields() == false){
            System.out.println("form not filled in properly");
            return;
        }
        
        //General information
        String formtype = missingFoundComboBox.getValue().toString();
        String date = dateJFXDatePicker.getValue().toString();
        String time = timeJFXTimePicker.getValue().toString();
        
        //Default passenger information if the form is foundluggage, if its lostluggage it will
        //get check in the checkFields method.
        String name = "", address = "", place = "", postalcode = "", country = "", phone = "", email = "";

        //Luggage information
        String labelnumber = "", flight = "", type = "", brand = "", color = "", secondColor = "", size = "", characteristics = "", location = "";
        
        int weight = 0;

        //Get the passenger information
        name        = nameJFXTextField.getText();
        address     = addressJFXTextField.getText();
        place       = placeJFXTextField.getText();
        postalcode  = postalcodeJFXTextField.getText();
        country     = countryJFXTextField.getText();
        phone       = phoneJFXTextField.getText();
        email       = emailJFXTextField.getText();
        
        //Get the luggage information
        labelnumber = labelnumberJFXTextField.getText();
        type = typeJFXComboBox.getValue().toString();
        color = colorJFXComboBox.getValue().toString();
        brand = brandJFXTextField.getText();
        size = sizeJFXTextField.getText();
        characteristics = characterJFXTextField.getText();
        
        if(flightJFXComboBox.getValue() != null && !flightJFXComboBox.getValue().toString().isEmpty()){
            flight = flightJFXComboBox.getValue().toString();
        }
       
        if(secondColorJFXComboBox.getValue() != null && !secondColorJFXComboBox.getValue().toString().isEmpty()){
            secondColor = secondColorJFXComboBox.getValue().toString();
        }
        
        if(locationJFXComboBox.getValue() != null && !locationJFXComboBox.getValue().toString().isEmpty()){
            location = locationJFXComboBox.getValue().toString();
        }

        if(weightJFXTextField.getText() != null && !weightJFXTextField.getText().isEmpty()){
            weight = Integer.parseInt(weightJFXTextField.getText());
        }
   
        //Is it found luggage? else its missing
        if("Found".equals(formtype)){
    
            //If atleast one of the following fields is not empty the passenger will be added to the database
            if(!name.isEmpty() || !address.isEmpty() || !place.isEmpty() || !postalcode.isEmpty() ||!email.isEmpty() || !phone.isEmpty()){
                
                //Query to add the passenger
                String addPassengerQuery = "INSERT INTO passenger VALUES(NULL ,'"+name+"', '"+address+"', '"+place+"', '"+postalcode+"', '"+country+"', '"+email+"', '"+phone+"')";

                //Execute the query to add a passenger to the database
                int affectedRowsPassengerQuery = MainApp.getDatabase().executeUpdateQuery(addPassengerQuery);

                //select the id of the passenger we just added by email address
                //TODO: has to be changed to return generated keys from prepared statement
                String selectPassengerQuery = "SELECT passengerId FROM passenger ORDER BY passengerId DESC LIMIT 1";
                
                //execute the query to select the recently added passenger, the String we get back is the id of that user
                String passengerId = MainApp.getDatabase().executeStringQuery(selectPassengerQuery);
                
                //Query to add the missing luggage to the database
                String addFoundLuggageQuery = "INSERT INTO foundluggage VALUES(NULL, '"+date+"','"+time+"', '"+labelnumber+"', (SELECT luggageTypeId FROM luggagetype WHERE english ='"+type+"'), "
                        + "'"+brand+"'"
                        + ", (SELECT ralCode FROM color WHERE english = '"+color+"'), (SELECT ralCode FROM COLOR WHERE english = '"+secondColor+"'), '"+size+"', '"+weight+"', '"+characteristics+"', "
                        + "'"+flight+"', (SELECT locationId FROM location WHERE english ='"+location+"'), 'aa', '"+passengerId+"', NULL )";

                //execute the missing luggage query
                int affectedRowsLuggageQuery = MainApp.getDatabase().executeUpdateQuery(addFoundLuggageQuery);

            }else{
            
            //Query to add the missing luggage to the database
            String addFoundLuggageQuery = "INSERT INTO foundluggage VALUES(NULL, '"+date+"','"+time+"', '"+labelnumber+"', (SELECT luggageTypeId FROM luggagetype WHERE english ='"+type+"'), "
                    + "'"+brand+"'"
                    + ", (SELECT ralCode FROM color WHERE english = '"+color+"'), (SELECT ralCode FROM COLOR WHERE english = '"+secondColor+"'), '"+size+"', '"+weight+"', '"+characteristics+"', "
                    + "'"+flight+"', (SELECT locationId FROM location WHERE english ='"+location+"'), 'aa', NULL, NULL )";
            
            //execute the missing luggage query
            int affectedRowsLuggageQuery = MainApp.getDatabase().executeUpdateQuery(addFoundLuggageQuery);
            }
            
        }else{

            //Query to add the passenger
            String addPassengerQuery = "INSERT INTO passenger VALUES(NULL ,'"+name+"', '"+address+"', '"+place+"', '"+postalcode+"', '"+country+"', '"+email+"', '"+phone+"')";
            
            //Execute the query to add a passenger to the database
            int affectedRowsPassengerQuery = MainApp.getDatabase().executeUpdateQuery(addPassengerQuery);

            //select the id of the passenger we just added by email address
            String selectPassengerQuery = "SELECT * FROM passenger WHERE email = '" +email+ "'";

            //execute the query to select the recently added passenger, the String we get back is the id of that user
            String passengerId = MainApp.getDatabase().executeStringQuery(selectPassengerQuery);

            //Query to add the missing luggage to the database
            String addMissingLuggageQuery = "INSERT INTO lostluggage VALUES(NULL, '"+date+"','"+time+"', '"+labelnumber+"', (SELECT luggageTypeId FROM luggagetype WHERE english ='"+type+"'), "
                    + "'"+brand+"'"
                    + ", (SELECT ralCode FROM color WHERE english = '"+color+"'), (SELECT ralCode FROM COLOR WHERE english = '"+secondColor+"'), '"+size+"', '"+weight+"', '"+characteristics+"', "
                    + "'"+flight+"', 'aa', '"+passengerId+"', NULL )";

            //execute the missing luggage query
            int affectedRowsLuggageQuery = MainApp.getDatabase().executeUpdateQuery(addMissingLuggageQuery);
        }
    }
    
    //Method to export the current form to pdf
    @FXML
    public void exportFormPdf(){
        
    }
    
    //This methods checks whether all the fields have been filled in appropriately
    public boolean checkFields(){
        
        boolean appropriate = true;
       
        //Check whether the date field has been filled in appropriately
        //Get the date, if its empty let the user know it has to be filled     
        if(dateJFXDatePicker.getValue() == null || dateJFXDatePicker.getValue().toString().isEmpty()){
            dateJFXDatePicker.setStyle("-fx-background-color: #f47142");
            appropriate =  false;
        }else{
            dateJFXDatePicker.setStyle(null);
        }
        
        //Get the time, if its empty let the user know it has to be filled
        if(timeJFXTimePicker.getValue() == null || timeJFXTimePicker.getValue().toString().isEmpty()){
            timeJFXTimePicker.setStyle("-fx-background-color: #f47142");
            appropriate =  false;
        }else{
            timeJFXTimePicker.setStyle(null);
        }

        if(typeJFXComboBox.getValue() == null || typeJFXComboBox.getValue().toString().isEmpty()){
            typeJFXComboBox.setStyle("-fx-background-color: #f47142");
            appropriate = false;
        }else{
            typeJFXComboBox.setStyle(null);
        }
        
        if(colorJFXComboBox.getValue() == null || colorJFXComboBox.getValue().toString().isEmpty()){
            colorJFXComboBox.setStyle("-fx-background-color: #f47142");
            appropriate =  false;
        }else{
            colorJFXComboBox.setStyle(null);
        }
               
        
        //If its a missing form then we need to check the passenger information as well
        if("Missing".equals(missingFoundComboBox.getValue().toString())){
            
            if(nameJFXTextField.getText() == null || nameJFXTextField.getText().isEmpty()){
                nameJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                nameJFXTextField.setStyle(null);
            }
            
            if(addressJFXTextField.getText() == null || addressJFXTextField.getText().isEmpty()){
                addressJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                addressJFXTextField.setStyle(null);
            }
            
            if(placeJFXTextField.getText() == null || placeJFXTextField.getText().isEmpty()){
                placeJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                placeJFXTextField.setStyle(null);
            }
            
            if(postalcodeJFXTextField.getText() == null || postalcodeJFXTextField.getText().isEmpty()){
                postalcodeJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                postalcodeJFXTextField.setStyle(null);
            }
            
            if(countryJFXTextField.getText() == null || countryJFXTextField.getText().isEmpty()){
                countryJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                countryJFXTextField.setStyle(null);
            }
            
            if(phoneJFXTextField.getText() == null || phoneJFXTextField.getText().isEmpty()){
                phoneJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                phoneJFXTextField.setStyle(null);
            }
            
            if(emailJFXTextField.getText() == null || emailJFXTextField.getText().isEmpty()){
                emailJFXTextField.setStyle("-fx-background-color: #f47142");
                appropriate =  false;
            }else{
                emailJFXTextField.setStyle(null);
            }
        }
        
        //return the decision
        return appropriate;
    }
}
