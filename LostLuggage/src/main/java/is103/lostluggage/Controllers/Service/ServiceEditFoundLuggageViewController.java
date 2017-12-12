package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataDetails;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.getLanguage;
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
        
        //will be removed
        checkFields();
    }    
    
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        
        //needs to be faster and get more obj options !
        //less searching in db
        
        String id = FoundLuggageDetails.getInstance().currentLuggage().getRegistrationNr();
        System.out.println("iD: "+id);
            MyJDBC db = MainApp.connectToDatabase();
            
            //new query needed here
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

        
    }
    
    @FXML
    private void setColor(MyJDBC db, int colorD) throws SQLException {
        //i will change this with inner joints (sql query)
        ResultSet result_color = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+colorD+"'");
        while (result_color.next()) {    
            String color = result_color.getString(getLanguage());
            mainColor.setText(color);
        }
        
    }
    @FXML
    private void setType(MyJDBC db, int luggageType) throws SQLException {
        //i will change this with inner joints (sql query)
        ResultSet result_type = db.executeResultSetQuery("SELECT * FROM luggagetype WHERE luggageTypeId='"+luggageType+"'");
        while (result_type.next()) {    
            String typeGotten = result_type.getString(getLanguage());
            
            type.setText(typeGotten);
        }
     
    }
    @FXML
    private void setSecondColor(MyJDBC db, int secondColor2) throws SQLException {
        //i will change this with inner joints (sql query)
        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+secondColor2+"'");
        while (result_second.next()) {    
            String color = result_second.getString(getLanguage());
            secondColor.setText(color);
        }
       
    }     
    @FXML
    private void setPassenger(MyJDBC db, int passengerIdG) throws SQLException {
        //i will change this with inner joints (sql query)
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
        //i will change this with inner joints (sql query)
        String locationId = Integer.toString(locationFoundG);
        ResultSet result = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+locationId+"'");
        while (result.next()) {    
            String location = result.getString(getLanguage());
            locationFound.setText(location);
        } 
       

    }
    
    @FXML
    public void checkFields(){
        //i will change this with is null (sql query)
        System.out.println("type.getText(): "+type.getText() );
        
        if (type.getText() == null){type.setText("Unknown");}
        if (luggageTag.getText() == null){luggageTag.setText("Unknown");}
        if (brand.getText() == null){brand.setText("Unknown");}
        if (signatures.getText() == null){signatures.setText("None");}
        if (flight.getText() == null){flight.setText("Unknown");}
            
        if (mainColor.getText() == null){mainColor.setText("Unknown");}
        if (secondColor.getText() == null){secondColor.setText("Unknown");}
        
        if (passangerId.getText() == null){passangerId.setText("");}
        if (passangerName.getText() == null){passangerName.setText("Unknown");}
        if (address.getText() == null){address.setText("Unknown");}
        if (place.getText() == null){place.setText("Unknown");}
        if (postalCode.getText() == null){postalCode.setText("Unknown");}
        if (country.getText() == null){country.setText("Unknown");}
        if (email.getText() == null){email.setText("Unknown");}
        if (phone.getText() == null){phone.setText("Unknown");}
        
        if (locationFound.getText() == null){locationFound.setText("Unknown");}
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
