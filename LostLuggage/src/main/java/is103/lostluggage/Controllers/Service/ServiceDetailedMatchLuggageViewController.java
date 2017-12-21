/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Instance.Details.LostLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Instance.Matching.FoundLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Instance.Matching.LostLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thijszijdel
 */
public class ServiceDetailedMatchLuggageViewController implements Initializable {
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
    
    @FXML private JFXTextField timeLost;
    @FXML private JFXTextField dateLost;
    @FXML private JFXTextField flight;
    
    @FXML private JFXTextField registrationNr1;
    @FXML private JFXTextField luggageTag1;
    @FXML private JFXTextField type1;
    @FXML private JFXTextField brand1;
    @FXML private JFXTextField mainColor1;
    @FXML private JFXTextField secondColor1;
    @FXML private JFXTextField size1;
    @FXML private JFXTextField weight1;    
    @FXML private JFXTextArea signatures1;

    @FXML private JFXTextField passangerId1;
    @FXML private JFXTextField passangerName1;
    @FXML private JFXTextField address1;        
    @FXML private JFXTextField place1;
    @FXML private JFXTextField postalCode1;
    @FXML private JFXTextField country1;
    @FXML private JFXTextField email1;   
    @FXML private JFXTextField phone1;   
    
    @FXML private JFXTextField timeFound;
    @FXML private JFXTextField dateFound;
    @FXML private JFXTextField locationFound;
    @FXML private JFXTextField flight1;
    
    
    
          private String language = MainApp.getLanguage();  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                //try to load initialize methode
        try {
            initializeLostFields();
            initializeFoundFields();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDetailedFoundLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
    }

    @FXML
    private void initializeLostFields() throws SQLException{
        String id = LostLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr();

        System.out.println("iD: "+id);
//            MyJDBC db = MainApp.getDatabase();
            ServiceDataLost detailsItem = new ServiceDataLost();
            ResultSet resultSet = detailsItem.getAllDetailsLost(id);
            
            while (resultSet.next()) {             
                int getRegistrationNr =     resultSet.getInt("F.registrationNr");
                String getDateLost =          resultSet.getString("F.dateLost");
                String getTimeLost =          resultSet.getString("F.timeLost");
                
                String getLuggageTag =         resultSet.getString("F.luggageTag");
                String getLuggageType =        resultSet.getString("T."+language+"");
                String getBrand =              resultSet.getString("F.brand");
                String getMainColor =          resultSet.getString("c1."+language+"");
                String getSecondColor =        resultSet.getString("c2."+language+"");
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
                
                String getFlight =              resultSet.getString("F.Flight"); 
                //String employeeId =         resultSet.getString("employeeId");
                //int matchedId =              resultSet.getInt("matchedId");

            
                
            registrationNr.setText( Integer.toString(getRegistrationNr) );  
            luggageTag.setText(getLuggageTag);
            
            type.setText(getLuggageType);
            brand.setText(getBrand);
             
            mainColor.setText(getMainColor);
            secondColor.setText(getSecondColor);
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
            
            //locationFound.setText(getLocationFound);
            dateLost.setText(getDateLost);
            timeLost.setText(getTimeLost);
            flight.setText(getFlight);
            
            
            
            

            }
        
    }  
    
    
    private void initializeFoundFields() throws SQLException{
        String id = FoundLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr();
            //            MyJDBC db = MainApp.getDatabase();
            ServiceDataFound detailsItem = new ServiceDataFound();
            ResultSet resultSet = detailsItem.getAllDetailsFound(id);
            
            
            
            while (resultSet.next()) {             
                int getRegistrationNr =     resultSet.getInt("F.registrationNr");
                String getDateFound =          resultSet.getString("F.dateFound");
                String getTimeFound =          resultSet.getString("F.timeFound");
                
                String getLuggageTag =         resultSet.getString("F.luggageTag");
                String getLuggageType =        resultSet.getString("T."+language+"");
                String getBrand =              resultSet.getString("F.brand");
                String getMainColor =          resultSet.getString("c1."+language+"");
                String getSecondColor =        resultSet.getString("c2."+language+"");
                String getSize =               resultSet.getString("F.size");
                String getWeight =                resultSet.getString("F.weight");   
                String getOtherCharacteristics=resultSet.getString("F.otherCharacteristics");
                
                String getPassengerId =           resultSet.getString("F.passengerId");
                
                String getName =          resultSet.getString("P.name");
                String getAddress =          resultSet.getString("P.address");
                String getPlace =          resultSet.getString("P.place");
                String getPostalcode =          resultSet.getString("P.postalcode");
                String getCountry =          resultSet.getString("P.country");
                String getEmail =          resultSet.getString("P.email");
                String getPhone =          resultSet.getString("P.phone");
                
                String getFlight =              resultSet.getString("F.arrivedWithFlight"); 
                String getLocationFound =       resultSet.getString("L."+language+"");
                //String employeeId =         resultSet.getString("employeeId");
                //int matchedId =              resultSet.getInt("matchedId");

            
                
            registrationNr1.setText( Integer.toString(getRegistrationNr) );  
            luggageTag1.setText(getLuggageTag);
            
            type1.setText(getLuggageType);
            brand1.setText(getBrand);
             
            mainColor1.setText(getMainColor);
            secondColor1.setText(getSecondColor);
            size1.setText(getSize);
            weight1.setText(getWeight);
            signatures1.setText(getOtherCharacteristics);

            passangerId1.setText( getPassengerId );
            passangerName1.setText(getName);
            address1.setText(getAddress);
            place1.setText(getPlace);
            postalCode1.setText(getPostalcode);
            country1.setText(getCountry);
            email1.setText(getEmail);
            phone1.setText(getPhone);
            
            locationFound.setText(getLocationFound);
            dateFound.setText(getDateFound);
            timeFound.setText(getTimeFound);
            flight1.setText(getFlight);
            }
    }
    
        @FXML
    protected void manualMatching(ActionEvent event) throws IOException{
        if (MainApp.isOnMatchingView()==false){
            MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        }
        closeStage();
        
         FoundLuggage passObject2 =  FoundLuggageDetailsInstance.getInstance().currentLuggage();
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passObject2.getRegistrationNr());   
        
        
        LostLuggage passObject =  LostLuggageDetailsInstance.getInstance().currentLuggage();
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        
       
        
        
    }
    
    
        
    @FXML
    public void openEditViewLost() throws IOException{
        MainApp.switchView("/Views/Service/ServiceEditLostLuggageView.fxml");
        closeStage();
    }
   
    @FXML
    public void  openEditViewFound() throws IOException{
        MainApp.switchView("/Views/Service/ServiceEditFoundLuggageView.fxml");
        closeStage();
    }
    public void closeStage(){
        Stage stage = (Stage) registrationNr.getScene().getWindow();
        stage.close();
    }
    @FXML 
    public void viewDetails(){
        System.out.println("details joee hoee");
    }
    
}
