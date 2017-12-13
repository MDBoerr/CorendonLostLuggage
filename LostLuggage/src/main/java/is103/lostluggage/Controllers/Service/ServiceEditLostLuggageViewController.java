package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataDetails;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.getLanguage;
import is103.lostluggage.Model.Service.Data.ServiceDataLost;
import is103.lostluggage.Model.Service.Instance.Matching.LostLuggageManualMatchingInstance;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Instance.Details.LostLuggageDetailsInstance;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
    
    @FXML private JFXTextField timeLost;
    @FXML private JFXTextField dateLost;
    @FXML private JFXTextField flight;
    
    @FXML private JFXComboBox colorPicker1;
    @FXML private JFXComboBox colorPicker2;
    @FXML private JFXComboBox typePicker;
    
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
        
        ServiceDataDetails colors = new ServiceDataDetails("color", "dutch", null);
        try {
            ObservableList<String> colorsStringList = colors.getStringList();
            colorPicker1.getItems().addAll(colorsStringList);
            colorPicker2.getItems().addAll(colorsStringList);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEditFoundLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // -> initialize current luggage's data
        //colorPicker2.setValue("2004");
         

        
        ServiceDataDetails types = new ServiceDataDetails("luggagetype", "dutch", null);
        try {
            ObservableList<String> luggageStringList = types.getStringList();
            typePicker.getItems().addAll(luggageStringList);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEditFoundLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // -> initialize current luggage's data
        //locationPicker.setValue("1");
    }   
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        String id = LostLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr();

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

            
            colorPicker1.setValue(getMainColor);
            colorPicker2.setValue(getSecondColor);
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
            
            //locationFound.setText(getLocationFound);
            dateLost.setText(getDateLost);
            timeLost.setText(getTimeLost);
            flight.setText(getFlight);
            
            
            
            
            
            

            }    
    }
    
    
    @FXML
    public void manualMatch() throws IOException{
        System.out.println("added to manual matching");
        LostLuggage passObject =  LostLuggageDetailsInstance.getInstance().currentLuggage();
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
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
