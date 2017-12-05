/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Model.FoundLuggage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;






import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.connectToDatabase;
import is103.lostluggage.Model.LuggageDetails;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
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
 * @author thijszijdel
 */
public class ServiceDetailedLuggageController implements Initializable {
    
    
    public String language = "English";
    
    @FXML private JFXTextField registrationNr;
    @FXML private JFXTextField luggageTag;
    @FXML private JFXTextField type;
    @FXML private JFXTextField brand;
    @FXML private JFXTextField mainColor;
    @FXML private JFXTextField secondColor;
    @FXML private JFXTextField size;
    @FXML private JFXTextField weight;    
    @FXML private JFXTextArea signatures;

    @FXML private JFXTextField passengerId;
    @FXML private JFXTextField passangerName;
    @FXML private JFXTextField address;        
    @FXML private JFXTextField place;
    @FXML private JFXTextField postalCode;
    @FXML private JFXTextField country;
    @FXML private JFXTextField email;   
    @FXML private JFXTextField phone;   
    
    @FXML private JFXTextField timeFound;
    @FXML private JFXTextField dateFound;
    @FXML private JFXTextField locationFound;
    @FXML private JFXTextField flight;
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("switched!!");
        
        //try to load initialize methode
        try {
            initializeFoundFields();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDetailedLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }   
    
    

    
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        String id = LuggageDetails.getInstance().currentLuggage().getRegistrationNr();
        System.out.println("iD: "+id);
            MyJDBC db = MainApp.connectToDatabase();
            
            ResultSet resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage WHERE registrationNr='"+id+"'");
                
            while (resultSet.next()) {
                String getRegistrationNr =     resultSet.getString("registrationNr");
                String getDateFound =          resultSet.getString("dateFound");
                String getTimeFound =          resultSet.getString("timeFound");
                
                String getLuggageTag =         resultSet.getString("luggageTag");
                int getLuggageType =           resultSet.getInt("luggageType");
                String getBrand =              resultSet.getString("brand");
                int getMainColor =             resultSet.getInt("mainColor");
                int getSecondColor =           resultSet.getInt("secondColor");
                int getSize =                  resultSet.getInt("size");
                int getWeight =                resultSet.getInt("weight");   
                String getOtherCharacteristics=resultSet.getString("otherCharacteristics");
                int getPassengerId =           resultSet.getInt("passengerId");
                
                String getFlight =              resultSet.getString("arrivedWithFlight"); 
                int getLocationFound =         resultSet.getInt("locationFound");
                //String employeeId =         resultSet.getString("employeeId");
                //int matchedId =              resultSet.getInt("matchedId");
 
            registrationNr.setText(getRegistrationNr);  
            setType(db,getLuggageType);
            brand.setText(getBrand);
            
            dateFound.setText(getDateFound);
            timeFound.setText(getTimeFound);
            luggageTag.setText(getLuggageTag);
            
            setColor(db, getMainColor); 
            setSecondColor(db, getSecondColor);
            
            signatures.setText(getOtherCharacteristics);
            
            String setSize = Integer.toString(getSize);
            size.setText(setSize);
            
            String setWeight = Integer.toString(getWeight);
            weight.setText(setWeight);
            
            flight.setText(getFlight);
            setPassenger(db, getPassengerId);
            
            setLocation(db, getLocationFound);
            }
        
    }
    
    @FXML
    private void setColor(MyJDBC db, int colorD) throws SQLException {
        ResultSet result_color = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+colorD+"'");
        while (result_color.next()) {    
            String color = result_color.getString(language);
            mainColor.setText(color);
        }
    }
    private void setType(MyJDBC db, int luggageType) throws SQLException {
        ResultSet result_type = db.executeResultSetQuery("SELECT * FROM luggagetype WHERE luggageTypeId='"+luggageType+"'");
        while (result_type.next()) {    
            String typeGotten = result_type.getString(language);
            type.setText(typeGotten);
        }
    }
    
    private void setSecondColor(MyJDBC db, int secondColor2) throws SQLException {
        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+secondColor2+"'");
        while (result_second.next()) {    
            String color = result_second.getString(language);
            secondColor.setText(color);
        }
    }     

    private void setPassenger(MyJDBC db, int passengerIdG) throws SQLException {
        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM passenger WHERE passengerId='"+passengerIdG+"'");
        while (result_second.next()) {    
            String idG = result_second.getString("passengerId");
            String nameG = result_second.getString("name");
            String addressG = result_second.getString("address");
            String placeG = result_second.getString("place");
            String postalCodeG = result_second.getString("postalCode");
            
            String countryG = result_second.getString("country");
            String emailG = result_second.getString("email");
            String phoneG = result_second.getString("phone");
            
            passengerId.setText(idG);
            passangerName.setText(nameG);
            address.setText(addressG);
            place.setText(placeG);
            postalCode.setText(postalCodeG);
            country.setText(countryG);
            email.setText(emailG);
            phone.setText(phoneG);
        }
    }

    private void setLocation(MyJDBC db, int locationFoundG) throws SQLException {
        ResultSet result = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+locationFoundG+"'");
        while (result.next()) {    
            String location = result.getString(language);
            locationFound.setText(location);
        }   
    }
   
    @FXML
    protected void saveLuggageChanges(ActionEvent event) throws SQLException {
//        String luggageId = idField.getText();
//        String luggageType = typeField.getText();
//        String luggageBrand = brandField.getText();
//        String luggageColor = colorField.getText();
//        String luggageSignatures = signaturesField.getText();
//        
//        
//        MyJDBC db = MainApp.connectToDatabase();
//        ResultSet resultSet;
//        resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage WHERE idfoundLuggage='"+luggageId+"'");
//        System.out.println("result is:"+resultSet);
//        if (    luggageType == null || "".equals(luggageType) ||
//                luggageBrand == null || "".equals(luggageBrand) ||
//                luggageColor == null || "".equals(luggageColor) ||
//                luggageSignatures == null || "".equals(luggageSignatures)
//                ) {
//            System.out.println("Een van de velden is leeg of null");
//        } else {
//            db.executeUpdateQuery("UPDATE `LostLuggage`.`foundLuggage` SET `type`='"+luggageType+"', `brand`='"+luggageBrand+"', `color`='"+luggageColor+"', `signatures`='"+luggageSignatures+"' WHERE `idfoundLuggage`='"+luggageId+"'");
//            System.out.println("DB row is updated!");
//        }
   
    }
    
    @FXML
    protected void viewPotentials(ActionEvent event){
        
    }
    
    @FXML
    protected void editLuggage(ActionEvent event){
        
    }
    
    @FXML
    protected void manualMatching(ActionEvent event){
        
    }

    
    

    
}
