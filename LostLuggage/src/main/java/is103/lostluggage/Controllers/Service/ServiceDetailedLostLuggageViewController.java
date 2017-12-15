package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.MainApp;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceDetailedLostLuggageViewController implements Initializable {
    
    @FXML private AnchorPane popupPain;
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
    
    public Stage popupStageEditingView = new Stage(); 
    
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
            Logger.getLogger(ServiceDetailedFoundLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

  
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
    protected void viewPotentials(ActionEvent event){
        closeStage();
        //methode starten
        MainApp.serviceChangeValue = 0;
    }
    
    
    @FXML
    protected void manualMatching(ActionEvent event){
        LostLuggage passObject =  LostLuggageDetailsInstance.getInstance().currentLuggage();
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passObject.getRegistrationNr());
        
        closeStage();
        
    }
    
    
    
    @FXML
    public void openEditView() throws IOException{
        closeStage();
        MainApp.switchView("/Views/Service/ServiceEditLostLuggageView.fxml");
    }
    
    
    public void closeStage(){
        Stage stage = (Stage) registrationNr.getScene().getWindow();
        stage.close();
    }

    
    

    
}
