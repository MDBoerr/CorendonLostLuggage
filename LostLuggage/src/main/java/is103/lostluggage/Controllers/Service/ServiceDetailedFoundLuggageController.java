package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataFound;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Instance.Matching.FoundLuggageManualMatchingInstance;
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
import javafx.scene.Node;
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
    

      private String language = MainApp.getLanguage();  
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
        MainApp.switchView("/Views/Service/ServiceEditFoundLuggageView.fxml");
        closeStage();
    }
    
    
    @FXML
    private void initializeFoundFields() throws SQLException{
        String id = FoundLuggageDetailsInstance.getInstance().currentLuggage().getRegistrationNr();
            //            MyJDBC db = MainApp.connectToDatabase();
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

            
                
            registrationNr.setText( Integer.toString(getRegistrationNr) );  
            luggageTag.setText(getLuggageTag);
            
            type.setText(getLuggageType);
            brand.setText(getBrand);
             
            mainColor.setText(getMainColor);
            secondColor.setText(getSecondColor);
            size.setText(getSize);
            weight.setText(getWeight);
            signatures.setText(getOtherCharacteristics);

            passangerId.setText( getPassengerId );
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
    protected void viewPotentials(ActionEvent event) throws IOException{
        if (MainApp.isOnMatchingView()==false){
            MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        }
        closeStage();
        
        MainApp.serviceChangeValue = 0; //temporary
    }
    

    @FXML
    protected void manualMatching(ActionEvent event) throws IOException{
        if (MainApp.isOnMatchingView()==false){
            MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        }
        closeStage();
        FoundLuggage passObject =  FoundLuggageDetailsInstance.getInstance().currentLuggage();
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());        
    }
    
    public void closeStage(){
        Stage stage = (Stage) registrationNr.getScene().getWindow();
        stage.close();
    }

    
    

    
}
