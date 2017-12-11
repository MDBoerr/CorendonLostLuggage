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
            
            
            ResultSet resultSet = db.executeResultSetQuery(""
                    + "SELECT " +
                        "COALESCE(NULLIF(F.registrationNr,''), 'none') ," +
                        "COALESCE(NULLIF(F.dateFound,''), 'unknown') , " +
                        "COALESCE(NULLIF(F.timeFound,''), 'unknown') , " +
                        "COALESCE(NULLIF(F.luggageTag,''), 'unknown') ,  " +
                        "COALESCE(NULLIF(T.dutch,''), 'unknown') , " +
                        "COALESCE(NULLIF(F.brand,''), 'unknown') ," +
                        "COALESCE(NULLIF(C1.dutch,''), 'unknown') ,  " +
                        "COALESCE(NULLIF(C2.dutch,''), 'none') ," +
                        "COALESCE(NULLIF(F.size,''), 'unknown')	,  " +
                        "COALESCE(NULLIF(F.weight,''), 'unknown') ," +
                        "COALESCE(NULLIF(F.otherCharacteristics,''), 'none') ," +
                        "COALESCE(NULLIF(F.arrivedWithFlight,''), 'unknown') ," +
                        "COALESCE(NULLIF(L.dutch ,''), 'unknown') ," +
                        "COALESCE(NULLIF(F.passengerId,''), 'none') ," +
                        "COALESCE(NULLIF(P.name,''), 'unknown')  ," +
                        "COALESCE(NULLIF(P.address,''), 'unknown') ," +
                        "COALESCE(NULLIF(P.place,''), 'unknown') ," +
                        "COALESCE(NULLIF(P.postalcode,''), 'unknown')  ," +
                        "COALESCE(NULLIF(P.country,''), 'unknown') ," +
                        "COALESCE(NULLIF(P.email,''), 'unknown') ," +
                        "COALESCE(NULLIF(P.phone,''), 'unknown') " +
                            "FROM foundluggage AS F " +
                                "INNER JOIN luggagetype AS T " +
                                "	ON F.luggageType = T.luggageTypeId " +
                                "INNER JOIN color AS C1 " +
                                "	ON F.mainColor = C1.ralCode " +
                                "INNER JOIN color AS C2 " +
                                "	ON F.secondColor = C2.ralCode " +
                                "INNER JOIN location AS L " +
                                "	ON F.locationFound = L.locationId " +
                                "LEFT JOIN passenger AS P " +
                                "	ON (F.passengerId = P.passengerId) " +
                            "WHERE registrationNr=' "+id+" ';");
            
            
            
      //      ResultSet resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage WHERE registrationNr='"+id+"'");
//            "SELECT delivery, dateMatched, employee.firstname, foundluggage.registrationNr, passenger.name  FROM matched "
//                    + "INNER JOIN employee ON matched.employeeId = employee.employeeId INNER JOIN foundluggage ON matched.foundluggage = foundluggage.registrationNr INNER JOIN passenger ON foundluggage.passengerId = passenger.passengerId");
//    
            while (resultSet.next()) {             
                int getRegistrationNr =     resultSet.getInt("F.registrationNr");
                System.out.println("MADE");
                String getDateFound =          resultSet.getString("F.dateFound");
                System.out.println("it");
                String getTimeFound =          resultSet.getString("F.timeFound");
                
                String getLuggageTag =         resultSet.getString("F.luggageTag");
                String getLuggageType =        resultSet.getString("T.dutch");
                String getBrand =              resultSet.getString("F.brand");
                String getMainColor =          resultSet.getString("c1.dutch");
                String getSecondColor =        resultSet.getString("c2.dutch");
                int getSize =                  resultSet.getInt("F.size");
                int getWeight =                resultSet.getInt("F.weight");   
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

            
                
            registrationNr.setText( Integer.toString(getRegistrationNr) );  
            luggageTag.setText(getLuggageTag);
            
            //setType(db,getLuggageType);
            type.setText(getLuggageType);
            brand.setText(getBrand);
            
            //setColor(db, getMainColor); 
            mainColor.setText(getMainColor);
            //setSecondColor(db, getSecondColor);
            secondColor.setText(getSecondColor);
            
            String setSize = Integer.toString(getSize);
            size.setText(setSize);
            
            String setWeight = Integer.toString(getWeight);
            weight.setText(setWeight);
            
            signatures.setText(getOtherCharacteristics);
            
            //setPassenger(db, getPassengerId);
            passangerId.setText( Integer.toString(getPassengerId) );
            passangerName.setText(getName);
            address.setText(getAddress);
            place.setText(getPlace);
            postalCode.setText(getPostalcode);
            country.setText(getCountry);
            email.setText(getEmail);
            phone.setText(getPhone);
            
            
            
            
            
            //setLocation(db, getLocationFound);
            locationFound.setText(getLocationFound);
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
