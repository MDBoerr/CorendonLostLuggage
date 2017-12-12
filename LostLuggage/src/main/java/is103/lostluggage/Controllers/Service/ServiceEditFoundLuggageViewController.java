package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataDetails;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.getLanguage;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetails;
import is103.lostluggage.Model.Service.Instance.Matching.LuggageManualMatchFound;
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

public class ServiceEditFoundLuggageViewController implements Initializable {

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
    
    
    @FXML private JFXComboBox colorPicker1;
    @FXML private JFXComboBox colorPicker2;
    @FXML private JFXComboBox locationPicker;
    @FXML private JFXComboBox typePicker;
     //view title
    private final String title = "Edit Found Luggage";
    
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
         

        ServiceDataDetails locations = new ServiceDataDetails("location", "dutch", null);
        try {
            ObservableList<String> locationStringList = locations.getStringList();
            locationPicker.getItems().addAll(locationStringList);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEditFoundLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // -> initialize current luggage's data
        //locationPicker.setValue("1");
        
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
        
        //needs to be faster and get more obj options !
        //less searching in db
        
        String id = FoundLuggageDetails.getInstance().currentLuggage().getRegistrationNr();
        System.out.println("iD: "+id);
            //            MyJDBC db = MainApp.connectToDatabase();
            ServiceDataFound detailsItem = new ServiceDataFound();
            ResultSet resultSet = detailsItem.getAllDetailsFound(id);
            
            
            
            while (resultSet.next()) {             
                int getRegistrationNr =     resultSet.getInt("F.registrationNr");
                String getDateFound =          resultSet.getString("F.dateFound");
                String getTimeFound =          resultSet.getString("F.timeFound");
                
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
                
                String getFlight =              resultSet.getString("F.arrivedWithFlight"); 
                String getLocationFound =       resultSet.getString("L.dutch");
                //String employeeId =         resultSet.getString("employeeId");
                //int matchedId =              resultSet.getInt("matchedId");

            // -> initialize current luggage's data
            colorPicker2.setValue(getSecondColor);
                
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
            
            locationFound.setText(getLocationFound);
            dateFound.setText(getDateFound);
            timeFound.setText(getTimeFound);
            flight.setText(getFlight);
            }

        
    }

    
    @FXML
    public void manualMatch() throws IOException{
        System.out.println("added to manual matching");
        FoundLuggage passObject =  FoundLuggageDetails.getInstance().currentLuggage();
        LuggageManualMatchFound.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        MainApp.refreshMatching = false;
        
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        
    }
    
    @FXML
    public void saveEditings() throws SQLException{
        //-------------------------
        //CHECK FIELDS + FEEDBACK !!   
        //-------------------------
       
        //will be changed 
        MyJDBC db = MainApp.connectToDatabase();
        db.executeUpdateQuery("UPDATE `foundluggage` SET "
                + "`dateFound`='"+dateFound.getText()+"', "
                + "`timeFound`='"+timeFound.getText()+"', "
                + "`luggageTag`='"+luggageTag.getText()+"', "
                + "`luggageType`='"+typePicker.getValue()+"', "
                + "`brand`='"+brand.getText()+"', "
                + "`mainColor`='"+colorPicker1.getValue()+"', "
                + "`secondColor`='"+colorPicker2.getValue()+"', "
                + "`size`='"+size.getText()+"', "
                + "`weight`='"+weight.getText()+"', "
                + "`otherCharacteristics`='"+signatures.getText()+"', "
                + "`arrivedWithFlight`='NULL', "
                + "`locationFound`='"+locationPicker.getValue()+"', "
                + "`passengerId`='"+ passangerId.getText()+"' "
                + "WHERE `registrationNr`='"+registrationNr.getText()+"';");
        
        
    }
}
