package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Model.Service.Data.ServiceDataDetails;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.getLanguage;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Instance.Matching.FoundLuggageManualMatchingInstance;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */

public class ServiceEditFoundLuggageViewController implements Initializable {

    @FXML private JFXTextField registrationNr;
    @FXML private JFXTextField luggageTag;
    @FXML private JFXTextField brand;
    @FXML private JFXTextField size;
    @FXML private JFXTextField weight;    
    @FXML private JFXTextArea signatures;

    @FXML private JFXTextField passangerId;
    @FXML private JFXTextField passangerName;
    @FXML private JFXTextField address;        
    @FXML private JFXTextField place;
    @FXML private JFXTextField postalCode;
    @FXML private JFXTextField country;
    @FXML private JFXTextField email;   
    @FXML private JFXTextField phone;   
    
    @FXML private JFXTextField timeFound;
    @FXML private JFXTextField dateFound;
    @FXML private JFXTextField flight;
    
    
    @FXML private JFXComboBox colorPicker1;
    @FXML private JFXComboBox colorPicker2;
    @FXML private JFXComboBox locationPicker;
    @FXML private JFXComboBox typePicker;
    
    @FXML private StackPane stackPane;
    
    @FXML private JFXButton saveEditings;
    
     //view title
    private final String title = "Edit Found Luggage";
    
    private String changedFields = "";
    private int changes = 0;
    private int changeCountDoubleCheck = 0;
    
    public String[] startValues;
    private String language = MainApp.getLanguage();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(ServiceHomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //set 3 objects to get the right data from the database 
        ServiceDataDetails colors = new ServiceDataDetails("color", language, null);
        
        ServiceDataDetails locations = new ServiceDataDetails("location", language, null);
        
        ServiceDataDetails types = new ServiceDataDetails("luggagetype", language, null);
        try {
            //initialize found fields 
            initializeFoundFields();
                    
            //get the right string list for each combo box         
            ObservableList<String> colorsStringList = colors.getStringList();
            colorPicker1.getItems().addAll(colorsStringList);
            colorPicker2.getItems().addAll(colorsStringList);
            
            
            ObservableList<String> locationStringList = locations.getStringList();
            locationPicker.getItems().addAll(locationStringList);
            
            
            ObservableList<String> luggageStringList = types.getStringList();
            typePicker.getItems().addAll(luggageStringList);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEditFoundLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //get start values of the fields
        startValues = getFields();
        
        stackPane.setVisible(false);
    }    
    
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        String id = FoundLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr();
        System.out.println("iD: "+id);
            //            MyJDBC db = MainApp.connectToDatabase();
            ServiceDataFound detailsItem = new ServiceDataFound();
            ResultSet resultSet = detailsItem.getAllDetailsFound(id);
            
            
            
            while (resultSet.next()) {             
                int getRegistrationNr =     resultSet.getInt("F.registrationNr");
                String getDateFound =          resultSet.getString("F.dateFound");
                String getTimeFound =          resultSet.getString("F.timeFound");
                
                String getLuggageTag =         resultSet.getString("F.luggageTag");
                String getLuggageType =        resultSet.getString("T."+language);
                String getBrand =              resultSet.getString("F.brand");
                String getMainColor =          resultSet.getString("c1."+language);
                String getSecondColor =        resultSet.getString("c2."+language);
                String getSize =               resultSet.getString("F.size");
                String getWeight =                resultSet.getString("F.weight");   
                String getOtherCharacteristics=resultSet.getString("F.otherCharacteristics");
                
                int getPassengerId =           resultSet.getInt("F.passengerId");
                
                String getName =          resultSet.getString("P.name");
                String getAddress =          resultSet.getString("P.address");
                String getPlace =          resultSet.getString("P.place");
                String getPostalcode =          resultSet.getString("P.postalcode");
                String getCountry =          resultSet.getString("P.country");
                String getEmail =          resultSet.getString("P.email");
                String getPhone =          resultSet.getString("P.phone");
                
                String getFlight =              resultSet.getString("F.arrivedWithFlight"); 
                String getLocationFound =       resultSet.getString("L."+language);
                
                //service employee wont be allalowed to see and change this:
                //String employeeId =         resultSet.getString("employeeId");
                //int matchedId =              resultSet.getInt("matchedId");
                //------
                
                
                
                // -> initialize current luggage's data
                colorPicker1.setValue(getMainColor);
                colorPicker2.setValue(getSecondColor);
                locationPicker.setValue(getLocationFound);
                typePicker.setValue(getLuggageType);

                registrationNr.setText( Integer.toString(getRegistrationNr) );  
                luggageTag.setText(getLuggageTag);

                brand.setText(getBrand);

                size.setText(getSize);
                weight.setText(getWeight);
                signatures.setText(getOtherCharacteristics);
                
                passangerId.setText( Integer.toString(getPassengerId) );
                passangerName.setText(getName);
                address.setText(getAddress);
                place.setText(getPlace);
                postalCode.setText(getPostalcode);
                country.setText(getCountry);
                email.setText(getEmail);
                phone.setText(getPhone);

                dateFound.setText(getDateFound);
                timeFound.setText(getTimeFound);
                flight.setText(getFlight);
            }

        
    }
    
    public String[] getFields(){
        String[] values = new String[21];

        values[0] = registrationNr.getText();
        values[1] = luggageTag.getText();

        values[2] = brand.getText();

        values[3] = size.getText();
        values[4] = weight.getText();
        values[5] = signatures.getText();

        values[6] = passangerId.getText();
        values[7] = passangerName.getText();
        values[8] = address.getText();
        values[9] = place.getText();
        values[10] = postalCode.getText();
        values[11] = country.getText();
        values[12] = email.getText();
        values[13] = phone.getText();

        values[14] = dateFound.getText();
        values[15] = timeFound.getText();
        values[16] = flight.getText();
        
        values[17] = colorPicker1.getValue().toString();  //--> 
        values[18] = colorPicker2.getValue().toString();
        values[19] = locationPicker.getValue().toString();
        values[20] = typePicker.getValue().toString();
        
        return values;
    }

    
    @FXML
    public void manualMatch() throws IOException{
        System.out.println("added to manual matching");
        FoundLuggage passObject =  FoundLuggageDetailsInstance.getInstance().currentLuggage();
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        MainApp.refreshMatching = false;
        
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        
    }
    
    @FXML
    public void saveEditings() throws SQLException{
        //-------------------------
        //CHECK FIELDS + FEEDBACK !!   
        //-------------------------
        checkChanges();

        if (changeCountDoubleCheck == 2){
            saveEditings.setText("Save changes");
            //Save is confirmed
            //Update query
            //Alert message -> updated confirmed
        }
        //will be changed 
//        MyJDBC db = MainApp.connectToDatabase();
//        db.executeUpdateQuery("UPDATE `foundluggage` SET "
//                + "`dateFound`='"+dateFound.getText()+"', "
//                + "`timeFound`='"+timeFound.getText()+"', "
//                + "`luggageTag`='"+luggageTag.getText()+"', "
//                + "`luggageType`='"+typePicker.getValue()+"', "
//                + "`brand`='"+brand.getText()+"', "
//                + "`mainColor`='"+colorPicker1.getValue()+"', "
//                + "`secondColor`='"+colorPicker2.getValue()+"', "
//                + "`size`='"+size.getText()+"', "
//                + "`weight`='"+weight.getText()+"', "
//                + "`otherCharacteristics`='"+signatures.getText()+"', "
//                + "`arrivedWithFlight`='NULL', "
//                + "`locationFound`='"+locationPicker.getValue()+"', "
//                + "`passengerId`='"+ passangerId.getText()+"' "
//                + "WHERE `registrationNr`='"+registrationNr.getText()+"';");
        
        
    }
    
    public String unFocusColor = "#ababab";
    public String noticeColor = "#4189fc";
    public String alertColor = "#e03636";
    
    public void checkChanges(){
        //reset changedfield string and changes count
        changedFields = "";
        changes = 0;
        
        //string array to compare fields
        String[] newValues = getFields();
        
        //loop trough al field values
        for (int i = 0; i < startValues.length; i++) {
            //if start value of .. field is the same as new value
            if (startValues[i].equals(newValues[i])){
                //use an switch to set that field to the right unFocusColor
                switch (i) {
                    case 0: registrationNr.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 1: luggageTag.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 2: brand.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 3: size.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 4: weight.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 5: signatures.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 6: passangerId.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 7: passangerName.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 8: address.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 9: place.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 10:postalCode.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 11:country.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 12:email.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 13:phone.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 14:dateFound.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 15:timeFound.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 16:flight.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 17:colorPicker1.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 18:colorPicker2.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 19:locationPicker.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                    case 20:typePicker.setUnFocusColor(Paint.valueOf(unFocusColor));
                            break;
                }
            } else {
                //if the comparison is not equal, 1 changes made
                changes++;
                //use an switch to set that field to the right noticeColor
                //And add the right field to the changed field string for the alert
                switch (i) {
                    case 0: registrationNr.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += "registrationNr";
                            break;
                    case 1: luggageTag.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", luggageTag";
                            break;
                    case 2: brand.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", brand";
                            break;
                    case 3: size.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", size";
                            break;
                    case 4: weight.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", weight";
                            break;
                    case 5: signatures.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", signatures";
                            break;
                    case 6: passangerId.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", id ";
                            break;
                    case 7: passangerName.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", name";
                            break;
                    case 8: address.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", registrationNr";
                            break;
                    case 9: place.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", place";
                            break;
                    case 10:postalCode.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", postal code";
                            break;
                    case 11:country.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", registrationNr";
                            break;
                    case 12:email.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", Email";
                            break;
                    case 13:phone.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", phone";
                            break;
                    case 14:dateFound.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", date";
                            break;
                    case 15:timeFound.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", time";
                            break;
                    case 16:flight.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", flight";
                            break;
                    case 17:colorPicker1.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", main color";
                            break;
                    case 18:colorPicker2.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", secondary color";
                            break;
                    case 19:locationPicker.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", location";
                            break;
                    case 20:typePicker.setUnFocusColor(Paint.valueOf(noticeColor));
                            changedFields += ", type";
                            break;
                    default://no more
                            break;
                }
            } 
            
        }
        
        //reset start fields
        startValues = getFields();
        
        //if amount of changes equals zero
        //no more changes made so change count gets ++
        if (changes == 0){
            changeCountDoubleCheck++;
        } else {
            //if there are some changes:
            //Alert this with an dialog
            alertDialog(changedFields, changes); 
            //reset change count double check 
            changeCountDoubleCheck = 0;
        }
    }
    
    public void alertDialog(String changedFields, int changes){
        //Remove the first , (comma) and space
        changedFields = changedFields.substring(2);
        
        
        //create dialog content/layout and a textflow for the body
        JFXDialogLayout content = new JFXDialogLayout();
        TextFlow alertMessage = new TextFlow();
        
        //Set heading of dialog
        content.setHeading(new Text("Warning"));
        //Set the right text for the dialog body.
        String textPart;
        if (changes == 1){
            textPart = "There is "+changes+" change made. \n"
                + "The changed field is the following: \n\n";
        } else {
            textPart = "There are "+changes+" changes made. \n"
                + "The changed fields are the following: \n\n";
        }
        Text textPart1 = new Text(textPart);
        Text textPart2 = new Text(changedFields);
        Text textPart3 = new Text("\n\nPlease check and confirm them.");
        //set text color of changed field string to red
        textPart2.setFill(Color.web("#e03636"));  
        
        //combine the text parts
        alertMessage.getChildren().addAll(textPart1, textPart2, textPart3);
        //set the text parts (alert message) in the content
        content.setBody(alertMessage);
        
        //create the dialog alert with the content
        //& place het in the center of the stackpane
        JFXDialog alert = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        
        //Create the 'ok'/close button
        JFXButton button = new JFXButton("ok");
        button.setOnAction((ActionEvent event) -> {
            alert.close();
            //hide the stackpane so the fields will be clickable again
            stackPane.setVisible(false);
        });
        //set action button in content for alert
        content.setActions(button);
        
        //Show the alert message (dialog)
        alert.show();
        //show the stackpane so the dialog will be visible 
        stackPane.setVisible(true);
        
        //change the text on the 'save changes'  button
        saveEditings.setText("Confirm Changes");
    }
    
    @FXML
    public void closeStackpane(){
            stackPane.setVisible(false); 
    }
}