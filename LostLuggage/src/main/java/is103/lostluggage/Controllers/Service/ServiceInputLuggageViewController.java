package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.PdfDocument;
import is103.lostluggage.Model.Service.Model.Form;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class for the inputluggage view
 *
 * @author Arthur Krom
 */
public class ServiceInputLuggageViewController implements Initializable {

    @FXML
    private JFXComboBox missingFoundComboBox, flightJFXComboBox, typeJFXComboBox, colorJFXComboBox,
            secondColorJFXComboBox, locationJFXComboBox, airportJFXComboBox, destinationJFXComboBox;
    
    //Hashmap containing all the comboboxes
    private Map<String, JFXComboBox> comboBoxes = new HashMap<>();
    
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
    
    //Hashmap containing all the text fields
    private Map<String, JFXTextField> textFields = new HashMap<>();
    
    //Form object
    private Form form;
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Define the previous view
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //set the title of the view, default form to be displayed is Missing
        changeTitle("Lost luggage form");
        
        //Make a new form.
        this.form = new Form();
        
        //Method to initialize the hashmaps
        setHashMaps();
        
        try {
            //Import the form options
            Map<String, ArrayList<String>> formOptions = this.form.getFormOptions();

            //Add the all the options to the comboboxes
            missingFoundComboBox.getItems().addAll(formOptions.get("foundorlost"));
            colorJFXComboBox.getItems().addAll(formOptions.get("colors"));
            secondColorJFXComboBox.getItems().addAll(formOptions.get("colors"));
            locationJFXComboBox.getItems().addAll(formOptions.get("locations"));
            airportJFXComboBox.getItems().addAll(formOptions.get("airports"));
            flightJFXComboBox.getItems().addAll(formOptions.get("flights"));
            typeJFXComboBox.getItems().addAll(formOptions.get("luggagetypes"));
            destinationJFXComboBox.getItems().addAll(formOptions.get("airports"));
            
            //Set default value for the type of form combobox
            missingFoundComboBox.setValue("Lost");
            this.form.setType("Lost");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceInputLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    //Method to change the title of the current view
    public void changeTitle(String title){
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(ServiceInputLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Method to initialize all the hashmaps
    private void setHashMaps(){
        
        //initializing the combobox hashmap
        
        comboBoxes.put("missingfound", missingFoundComboBox);
        comboBoxes.put("flight", flightJFXComboBox);
        comboBoxes.put("type", typeJFXComboBox);
        comboBoxes.put("color", colorJFXComboBox);
        comboBoxes.put("secondcolor", secondColorJFXComboBox);
        comboBoxes.put("location", locationJFXComboBox);
        comboBoxes.put("airport", airportJFXComboBox);
        comboBoxes.put("destination", destinationJFXComboBox);
        
        //initializing the textfield hashmap
        
        textFields.put("name", nameJFXTextField);
        textFields.put("address", addressJFXTextField);
        textFields.put("place", placeJFXTextField);
        textFields.put("postalcode", postalcodeJFXTextField);
        textFields.put("country", countryJFXTextField);
        textFields.put("phone", phoneJFXTextField);
        textFields.put("email", emailJFXTextField);
        textFields.put("labelnumber", labelnumberJFXTextField);
        textFields.put("brand", brandJFXTextField);
        textFields.put("character", characterJFXTextField);
        textFields.put("size", sizeJFXTextField);
        textFields.put("weight", weightJFXTextField);

    }
    
    //Method that loops through all the fields checking whether the ones that are not allowed to be empty aren't
    public boolean checkFields(){
        
        boolean appropriate = true;
        
        //Date picker and timepicker cannot be looped through since theyre not inside an array
        
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
        
        //Loop through the comboboxes
        for(Map.Entry<String, JFXComboBox> entry: comboBoxes.entrySet()){
            
            String key = entry.getKey();
            JFXComboBox value = entry.getValue();
            
            //first check for the comboboxes that have to be selected in both forms
            if(key.equals("color") || key.equals("type") || key.equals("airport")){
            
                if(value.getValue() == null || value.getValue().toString().isEmpty()){
                    value.setStyle("-fx-background-color: #f47142");
                    appropriate =  false;
                }else{
                    value.setStyle(null);
                }
            }
            
            //check the comboboxes for the lost form, in this case only the destination is an additional combobox
            //where an option has to be selected
            if(form.getType().equals("Lost") && key.equals("destination")){
                if(value.getValue() == null || value.getValue().toString().isEmpty()){
                    value.setStyle("-fx-background-color: #f47142");
                    appropriate =  false;
                }else{
                    value.setStyle(null);
                }
            }
        }
        
        //loop through the textfields
        for(Map.Entry<String, JFXTextField> entry: textFields.entrySet()){
            
            String key = entry.getKey();
            JFXTextField value = entry.getValue();
            
            //If its a lost form, the following fields cannot be empty
            if(form.getType().equals("Lost")){         
                if(key.equals("name") || key.equals("address") || key.equals("place")
                   || key.equals("postalcode") || key.equals("country") || key.equals("phone") || key.equals("email")){
                    if(value.getText() == null || value.getText().isEmpty()){
                        value.setStyle("-fx-background-color: #f47142");
                        appropriate =  false;
                    }else{
                        value.setStyle(null);
                    }
                }
            }
        }
        return appropriate;
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
        String airport = airportJFXComboBox.getValue().toString();
        
        //Default passenger information if the form is foundluggage, if its lostluggage it will
        //get check in the checkFields method.
        String name = "", address = "", place = "", postalcode = "", country = "", phone = "", email = "";

        //Luggage information
        String labelnumber = "", flight = "", type = "", brand = "", color = "", secondColor = "", size = "", characteristics = "", location = "", weight = "";

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
        
        if(weight.isEmpty()){
            weight = "0";
        }
        
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
              weight = weightJFXTextField.getText();
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
                        + "'"+flight+"', (SELECT locationId FROM location WHERE english ='"+location+"'), 'aa', '"+passengerId+"', NULL, (SELECT IATACode FROM destination WHERE airport =  '"+airport+"'), NULL )";

                //execute the missing luggage query
                int affectedRowsLuggageQuery = MainApp.getDatabase().executeUpdateQuery(addFoundLuggageQuery);

            }else{
            
            //Query to add the missing luggage to the database
            String addFoundLuggageQuery = "INSERT INTO foundluggage VALUES(NULL, '"+date+"','"+time+"', '"+labelnumber+"', (SELECT luggageTypeId FROM luggagetype WHERE english ='"+type+"'), "
                    + "'"+brand+"'"
                    + ", (SELECT ralCode FROM color WHERE english = '"+color+"'), (SELECT ralCode FROM COLOR WHERE english = '"+secondColor+"'), '"+size+"', '"+weight+"', '"+characteristics+"', "
                    + "'"+flight+"', (SELECT locationId FROM location WHERE english ='"+location+"'), 'aa', NULL, NULL, (SELECT IATACode FROM destination WHERE airport =  '"+airport+"'), NULL  )";
            
            //execute the missing luggage query
            int affectedRowsLuggageQuery = MainApp.getDatabase().executeUpdateQuery(addFoundLuggageQuery);
            }
            
        }else{

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
            String addMissingLuggageQuery = "INSERT INTO lostluggage VALUES(NULL, '"+date+"','"+time+"', '"+labelnumber+"', (SELECT luggageTypeId FROM luggagetype WHERE english ='"+type+"'), "
                    + "'"+brand+"'"
                    + ", (SELECT ralCode FROM color WHERE english = '"+color+"'), (SELECT ralCode FROM COLOR WHERE english = '"+secondColor+"'), '"+size+"', '"+weight+"', '"+characteristics+"', "
                    + "'"+flight+"', 'AK1', '"+passengerId+"', NULL, (SELECT IATACode FROM destination WHERE airport =  '"+airport+"'), NULL )";

            //execute the missing luggage query
            int affectedRowsLuggageQuery = MainApp.getDatabase().executeUpdateQuery(addMissingLuggageQuery);
        }
    }
    
    //Method to export the current form to pdf
    @FXML
    public void exportPDF() throws IOException{
        
        //Check if all the fields have been filled in properly
        if(checkFields() == false){
            System.out.println("form not filled in properly");
            return;
        }
        
        //Fileobject
        File file = MainApp.selectFileToSave("*.pdf");
        
        //If fileobject has been initialized
        if(file != null){
            
            //get the location to store the file
            String fileName = file.getAbsolutePath();
            String type = "Found";

            ArrayList<String> formText = new ArrayList();
            String[] strings = {"43434", "ak", "2018-01-01", "13:44", "Schiphol", "aas33", "CAIO4", "Suitcase", "Nike", "Blue", "Pink", "30x30x30", "5", "Blue stars",
                "Toilet", "A. Baars", "Dares 44", "Amsterdam", "3887QW", "The Netherlands", "0684883", "abaars@gmail.com"};
            
            formText.addAll(Arrays.asList(strings));

           PdfDocument Pdf = new PdfDocument(type, formText, fileName);
        }else{
            System.out.println("Setting a location has been cancelled");
        }
    }
    
    @FXML
    public void emailPDF(){
        System.out.println("emailing the PDF");
    }
    

    
    @FXML
    //Method to switch between found/lost form
    public void foundOrMissing(){
        
      String value = missingFoundComboBox.getValue().toString();

        if("Found".equals(value)){
            
            this.form.setType("Found");
                  
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
        
        if("Lost".equals(value)){

            this.form.setType("Lost");
            
            //Remove the gridpanes
            mainGridPane.getChildren().remove(travellerInfoGridPane);
            mainGridPane.getChildren().remove(luggageInfoGridPane);
            
            //Add them again on different positions
            mainGridPane.add(travellerInfoGridPane, 0, 1);
            mainGridPane.add(luggageInfoGridPane, 1, 1);
            
            passengerInformationLbl.setText("Passenger information");
            
            
            //Change the title of the view
            changeTitle("Lost luggage form");
            
            //hide the location combobox
            locationJFXComboBox.setVisible(false);     
        }
    }
}
