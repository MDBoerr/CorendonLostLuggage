/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.LuggageDetails;
import is103.lostluggage.Model.LuggageManualMatchFound;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;




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
        
        
        checkFields();
  
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
            luggageTag.setText(getLuggageTag);
            
            setType(db,getLuggageType);
            brand.setText(getBrand);
            
            setColor(db, getMainColor); 
            setSecondColor(db, getSecondColor);
            
            String setSize = Integer.toString(getSize);
            size.setText(setSize);
            
            String setWeight = Integer.toString(getWeight);
            weight.setText(setWeight);
            
            signatures.setText(getOtherCharacteristics);
            
            setPassenger(db, getPassengerId);
            
            setLocation(db, getLocationFound);
            dateFound.setText(getDateFound);
            timeFound.setText(getTimeFound);
            flight.setText(getFlight);
            
            
            

            }
//            if (luggageTag.getText().equals("")){luggageTag.setText("Unknown");}
//            if (brand.getText().equals("")){brand.setText("Unknown");}
//            if (signatures.getText().equals("")){signatures.setText("None");}
//            if (flight.getText().equals("")){flight.setText("Unknown");}
        
    }
    
    @FXML
    private void setColor(MyJDBC db, int colorD) throws SQLException {
        ResultSet result_color = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+colorD+"'");
        while (result_color.next()) {    
            String color = result_color.getString(language);
            mainColor.setText(color);
        }
        
    }
    @FXML
    private void setType(MyJDBC db, int luggageType) throws SQLException {
        ResultSet result_type = db.executeResultSetQuery("SELECT * FROM luggagetype WHERE luggageTypeId='"+luggageType+"'");
        while (result_type.next()) {    
            String typeGotten = result_type.getString(language);
            
            type.setText(typeGotten);
        }
     
    }
    @FXML
    private void setSecondColor(MyJDBC db, int secondColor2) throws SQLException {
        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+secondColor2+"'");
        while (result_second.next()) {    
            String color = result_second.getString(language);
            secondColor.setText(color);
        }
       
    }     
    @FXML
    private void setPassenger(MyJDBC db, int passengerIdG) throws SQLException {
        String idString = Integer.toString(passengerIdG);
         
        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM passenger WHERE passengerId='"+idString+"'");
        System.out.println(idString);
        System.out.println(result_second);
        while (result_second.next()) {    
            int idG = result_second.getInt("passengerId");
            String nameG = result_second.getString("name");
            String addressG = result_second.getString("address");
            String placeG = result_second.getString("place");
            String postalCodeG = result_second.getString("postalcode");
            
            String countryG = result_second.getString("country");
            String emailG = result_second.getString("email");
            String phoneG = result_second.getString("phone");
            
            String id = Integer.toString(idG);
            

            
            
            passangerId.setText(id);
            passangerName.setText(nameG);
            address.setText(addressG);
            place.setText(placeG);
            postalCode.setText(postalCodeG);
            country.setText(countryG);
            email.setText(emailG);
            phone.setText(phoneG);
        }

    }
    @FXML
    private void setLocation(MyJDBC db, int locationFoundG) throws SQLException {
        String locationId = Integer.toString(locationFoundG);
        ResultSet result = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+locationId+"'");
        while (result.next()) {    
            String location = result.getString(language);
            locationFound.setText(location);
        } 
       

    }
    
    @FXML
    public void checkFields(){
        System.out.println("type.getText(): "+type.getText() );
        
        
        //Need to fix!!   --> ER IS GEEN REDEN WAAROM IK EEN NulPointerException KRIJG
        //Althans dat denk ik..
        
//        if (type.getText().equals("")){type.setText("Unknown");}
//        if (luggageTag.getText().equals("")){luggageTag.setText("Unknown");}
//        if (brand.getText().equals("")){brand.setText("Unknown");}
//        if (signatures.getText().equals("")){signatures.setText("None");}
//        if (flight.getText().equals("")){flight.setText("Unknown");}
            
        if (mainColor.getText().equals("")){mainColor.setText("");}
        if (secondColor.getText().equals("")){secondColor.setText("");}
        
        if (passangerId.getText().equals("")){passangerId.setText("");}
        if (passangerName.getText().equals("")){passangerName.setText("Unknown");}
        if (address.getText().equals("")){address.setText("Unknown");}
        if (place.getText().equals("")){place.setText("Unknown");}
        if (postalCode.getText().equals("")){postalCode.setText("Unknown");}
        if (country.getText().equals("")){country.setText("Unknown");}
        if (email.getText().equals("")){email.setText("Unknown");}
        if (phone.getText().equals("")){phone.setText("Unknown");}
        
        if (locationFound.getText().equals("")){locationFound.setText("Unknown");}
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
        System.out.println("added to manual matching");
        FoundLuggage passObject =  LuggageDetails.getInstance().currentLuggage();
        LuggageManualMatchFound.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        
        
        
    }

    
    

    
}
