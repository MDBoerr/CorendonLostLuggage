package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.getLanguage;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.Model.Service.Instance.Matching.LuggageManualMatchLost;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Instance.Details.LostLuggageDetails;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceEditLostLuggageViewController implements Initializable {

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
    
    
         //view title
    private final String title = "Edit Lost Luggage";
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
        
                //try to load initialize methode
        try {
            initializeFoundFields();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDetailedFoundLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }   
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        String id = LostLuggageDetails.getInstance().currentLuggage().getRegistrationNr();

        System.out.println("iD: "+id);
            //            MyJDBC db = MainApp.connectToDatabase();
            ServiceDataLost detailsItem = new ServiceDataLost();
            ResultSet resultSet = detailsItem.getAllDetailsLost(id);
            
            while (resultSet.next()) {             
                int getRegistrationNr =     resultSet.getInt("F.registrationNr");
                String getDateLost =          resultSet.getString("F.dateLost");
                String getTimeLost =          resultSet.getString("F.timeLost");
                
                String getLuggageTag =         resultSet.getString("F.luggageTag");
                String getLuggageType =        resultSet.getString("T.dutch");
                String getBrand =              resultSet.getString("F.brand");
                String getMainColor =          resultSet.getString("c1.dutch");
                String getSecondColor =        resultSet.getString("c2.dutch");
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
    
    
    @FXML
    public void manualMatch() throws IOException{
        System.out.println("added to manual matching");
        LostLuggage passObject =  LostLuggageDetails.getInstance().currentLuggage();
        LuggageManualMatchLost.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        MainApp.refreshMatching = false;
        
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        
    }
    
    @FXML
    public void saveEditings(){
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
    
}
