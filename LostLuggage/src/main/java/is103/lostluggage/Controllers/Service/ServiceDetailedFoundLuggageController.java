package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
        
  
    }   
    
    
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
                        "COALESCE(NULLIF(F.registrationNr,''), 'none') as `F.registrationNr`," +
                        "COALESCE(NULLIF(F.dateFound,''), 'unknown') as `F.dateFound`, " +
                        "COALESCE(NULLIF(F.timeFound,''), 'unknown') as `F.timeFound`, " +
                        "COALESCE(NULLIF(F.luggageTag,''), 'unknown') as `F.luggageTag`,  " +
                        "COALESCE(NULLIF(T.dutch,''), 'unknown') as `T.dutch`, " +
                        "COALESCE(NULLIF(F.brand,''), 'unknown') as `F.brand`," +
                        "COALESCE(NULLIF(C1.dutch,''), 'unknown') as `C1.dutch`,  " +
                        "COALESCE(NULLIF(C2.dutch,''), 'none') as `C2.dutch`," +
                        "COALESCE(NULLIF(F.size,''), 'unknown')	as `F.size`,  " +
                        "COALESCE(NULLIF(F.weight,''), 'unknown') as `F.weight`," +
                        "COALESCE(NULLIF(F.otherCharacteristics,''), 'none') as `F.otherCharacteristics`," +
                        "COALESCE(NULLIF(F.arrivedWithFlight,''), 'unknown') as `F.arrivedWithFlight`," +
                        "COALESCE(NULLIF(L.dutch ,''), 'unknown') as `L.dutch`," +
                        "COALESCE(NULLIF(F.passengerId,''), 'none') as `F.passengerId`," +
                        "COALESCE(NULLIF(P.name,''), 'unknown')  as `P.name`," +
                        "COALESCE(NULLIF(P.address,''), 'unknown') as `P.address`," +
                        "COALESCE(NULLIF(P.place,''), 'unknown') as `P.place`," +
                        "COALESCE(NULLIF(P.postalcode,''), 'unknown')  as `P.postalcode`," +
                        "COALESCE(NULLIF(P.country,''), 'unknown') as `P.country`," +
                        "COALESCE(NULLIF(P.email,''), 'unknown') as `P.email`," +
                        "COALESCE(NULLIF(P.phone,''), 'unknown') as `P.phone` " +
                            "FROM foundluggage AS F " +
                                "LEFT JOIN luggagetype AS T " +
                                "	ON F.luggageType = T.luggageTypeId " +
                                "LEFT JOIN color AS C1 " +
                                "	ON F.mainColor = C1.ralCode " +
                                "LEFT JOIN color AS C2 " +
                                "	ON F.secondColor = C2.ralCode " +
                                "LEFT JOIN location AS L " +
                                "	ON F.locationFound = L.locationId " +
                                "LEFT JOIN passenger AS P " +
                                "	ON (F.passengerId = P.passengerId) " +
                            "WHERE registrationNr='"+id+"';");
            
            
            
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
