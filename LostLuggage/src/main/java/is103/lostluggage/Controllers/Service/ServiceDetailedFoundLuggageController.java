package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.getLanguage;
import is103.lostluggage.Model.FoundLuggage;
import is103.lostluggage.Model.FoundLuggageDetails;
import is103.lostluggage.Model.LuggageManualMatchFound;
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
 * FXML Controller class detailed found luggage
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDetailedFoundLuggageController implements Initializable {
   
        

    
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
        //try to load initialize methode
        try {
            initializeFoundFields();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDetailedFoundLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        checkFields();
  
    }   
    
    
         //Not closable & no borders
     ////   popupStageEditingView.initStyle(StageStyle.TRANSPARENT);
        //popUpDetails(popupStageMissed, "/Views/Service/ServiceDetailedMissedLuggageView.fxml", popupKey);
    @FXML
    public void openEditView() throws IOException{
        Stage stage = (Stage) registrationNr.getScene().getWindow();
        stage.close();
        MainApp.switchView("/Views/Service/ServiceEditFoundLuggageView.fxml");
    }
    
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        
        //needs to be faster and get more obj options !
        //less searching in db
        
        String id = FoundLuggageDetails.getInstance().currentLuggage().getRegistrationNr();
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
            String color = result_color.getString(getLanguage());
            mainColor.setText(color);
        }
        
    }
    @FXML
    private void setType(MyJDBC db, int luggageType) throws SQLException {
        ResultSet result_type = db.executeResultSetQuery("SELECT * FROM luggagetype WHERE luggageTypeId='"+luggageType+"'");
        while (result_type.next()) {    
            String typeGotten = result_type.getString(getLanguage());
            
            type.setText(typeGotten);
        }
     
    }
    @FXML
    private void setSecondColor(MyJDBC db, int secondColor2) throws SQLException {
        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+secondColor2+"'");
        while (result_second.next()) {    
            String color = result_second.getString(getLanguage());
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
            String location = result.getString(getLanguage());
            locationFound.setText(location);
        } 
       

    }
    
    @FXML
    public void checkFields(){
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
    protected void viewPotentials(ActionEvent event){
        MainApp.serviceChangeValue = 0; //temporary
    }
    
    @FXML
    protected void editLuggage(ActionEvent event){
        
    }
    
    @FXML
    protected void manualMatching(ActionEvent event){
        System.out.println("added to manual matching");
        FoundLuggage passObject =  FoundLuggageDetails.getInstance().currentLuggage();
        LuggageManualMatchFound.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        
        Stage stage = (Stage) registrationNr.getScene().getWindow();
        stage.close();
        
    }

    
    

    
}
