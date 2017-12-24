package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thijszijdel
 */
public class ServiceConfirmedMatchLuggageViewController implements Initializable {
    //al the lost fields
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
    
    
    //Al the found fields
    //Note: 1 -> so they are alternated 
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
    
    
    
    @FXML private JFXTextField detailsMatcheId;
    @FXML private JFXTextField detailsName;
    @FXML private JFXTextField detailsPhone;
    @FXML private JFXTextField detailsEmail;
    @FXML private JFXTextField detailsAddress;
    @FXML private JFXTextField detailsPlace;
    @FXML private JFXTextField detailsCode;
    @FXML private JFXTextField detailsCountry;
    
    @FXML private JFXComboBox detailsDeliverer;
    
    

    private final String LANGUAGE = MainApp.getLanguage();  
    
    private final String TITLE = "Matched luggage";
    
    //conection to the main database
    private final MyJDBC DB = MainApp.getDatabase();
    
    
    //current date 
    private String currentDate;
    
    private int matcheID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainViewController.getInstance().getTitle(TITLE);
        } catch (IOException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //try to load initialize methode
        try {
            setLostFields(getManualLostLuggageResultSet());
            setFoundFields(getManualFoundLuggageResultSet());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDetailedFoundLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("date");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        currentDate = dtf.format(localDate);
        try {
            generateMatcheId();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceConfirmedMatchLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setDetails();
        try {
            setMatched();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceConfirmedMatchLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generateMatcheId() throws SQLException{
        ResultSet resultSet = DB.executeResultSetQuery("SELECT COUNT(*) AS 'count' FROM matched;");
        while (resultSet.next()){
            int count = resultSet.getInt("count");
            
            matcheID = count++; 
        }
        do {
            matcheID++;
        } while (!checkMatcheId());
        
    }
    
    private boolean checkMatcheId() throws SQLException{
        ResultSet resultSetCheck = DB.executeResultSetQuery("SELECT COUNT(*) AS 'check' FROM matched WHERE matchedid = '"+matcheID+"';");
        while (resultSetCheck.next()){
            int check = resultSetCheck.getInt("check");
            
            return check == 0;
        }
        return false;
    }
    
    private void setDetails() {
        detailsName.setText(passangerName.getText());
        detailsPhone.setText(phone.getText());
        detailsEmail.setText(email.getText());
        detailsAddress.setText(address.getText());
        detailsPlace.setText(place.getText());
        detailsCode.setText(postalCode.getText());
        detailsCountry.setText(country.getText());
        
        detailsMatcheId.setText(Integer.toString(matcheID));
//        ObservableList<String> colorsStringList = colors.getStringList();
//            colorPicker1.getItems().addAll(colorsStringList);
        ObservableList<String> deliverers = FXCollections.observableArrayList();
        deliverers.add("DHL");
        deliverers.add("Post NL");
        deliverers.add("Correndon");
        deliverers.add("Self service");
        
        detailsDeliverer.getItems().addAll(deliverers);
    }
    private void setMatched() throws SQLException {
        DB.executeUpdateQuery("INSERT INTO `matched` "
                + " (`matchedId`, `foundluggage`, `lostluggage`, `employeeId`, `dateMatched`, `delivery`) "
                + "VALUES ('"+matcheID+"', '"+registrationNr1.getText()+"', '"+registrationNr.getText()+"', 'TZ', '"+currentDate+"', '');");
        DB.executeUpdateQuery("UPDATE `lostluggage` SET `matchedId`='"+matcheID+"' WHERE `registrationNr`='"+registrationNr.getText()+"';");
        DB.executeUpdateQuery("UPDATE `foundluggage` SET `matchedId`='"+matcheID+"' WHERE `registrationNr`='"+registrationNr1.getText()+"';");
    }
    @FXML
    protected void confirmDeliverer() throws SQLException {
        DB.executeUpdateQuery("UPDATE `matched` SET "
                + " `delivery`='"+detailsDeliverer.getValue().toString()+"' "
                        + "WHERE `matchedId`='"+detailsMatcheId.getText()+"';");
       
    }
    
    /**  
     * Here will the resultSet of the lost manual matching luggage be get 
     * For getting the right resultSet the correct instance id will be passed
     * 
     * @return resultSet     resultSet for the right luggage
     */  
    private ResultSet getManualLostLuggageResultSet() throws SQLException{
        String id = LostLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();

        ServiceDataLost detailsItem = new ServiceDataLost();
        ResultSet resultSet = detailsItem.getAllDetailsLost(id);
        return resultSet;
    }

    /**  
     * Here are all the detail fields been set with the right data from:
     * The resultSet given
     * 
     * @param resultSet         this will be converted to temp strings and integers
     * @void no direct          the fields will be set within this method
     */       
    @FXML
    private void setLostFields(ResultSet resultSet) throws SQLException{
        //loop trough all the luggages in the resultSet
        //Note: there will be only one 
        while (resultSet.next()) {             
            int getRegistrationNr =        resultSet.getInt("F.registrationNr");
            String getDateLost =           resultSet.getString("F.dateLost");
            String getTimeLost =           resultSet.getString("F.timeLost");

            String getLuggageTag =         resultSet.getString("F.luggageTag");
            String getLuggageType =        resultSet.getString("T."+LANGUAGE+"");
            String getBrand =              resultSet.getString("F.brand");
            String getMainColor =          resultSet.getString("c1."+LANGUAGE+"");
            String getSecondColor =        resultSet.getString("c2."+LANGUAGE+"");
            String getSize =               resultSet.getString("F.size");
            String getWeight =             resultSet.getString("F.weight");   
            String getOtherCharacteristics=resultSet.getString("F.otherCharacteristics");

            int getPassengerId =           resultSet.getInt("F.passengerId");
            String getName =               resultSet.getString("P.name");
            String getAddress =            resultSet.getString("P.address");
            String getPlace =              resultSet.getString("P.place");
            String getPostalcode =         resultSet.getString("P.postalcode");
            String getCountry =            resultSet.getString("P.country");
            String getEmail =              resultSet.getString("P.email");
            String getPhone =              resultSet.getString("P.phone");

            String getFlight =             resultSet.getString("F.Flight"); 

        //set al the fields
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
    
    
    
    /**  
     * Here will the resultSet of the found manual matching luggage be get 
     * For getting the right resultSet the correct instance id will be passed
     * 
     * @return resultSet     resultSet for the right luggage
     */  
    private ResultSet getManualFoundLuggageResultSet() throws SQLException{
        String id = FoundLuggageManualMatchingInstance.getInstance().currentLuggage().getRegistrationNr();
        ServiceDataFound detailsItem = new ServiceDataFound();
        ResultSet resultSet = detailsItem.getAllDetailsFound(id);
            
        return resultSet;
    }
    
    /**  
     * Here are all the detail fields been set with the right data from:
     * The resultSet given
     * 
     * @param resultSet         this will be converted to temp strings and integers
     * @void no direct          the fields will be set within this method
     */    
    @FXML
    private void setFoundFields(ResultSet resultSet) throws SQLException{ 
        //loop trough all the luggages in the resultSet
        //Note: there will be only one
        while (resultSet.next()) {             
            int getRegistrationNr =        resultSet.getInt("F.registrationNr");
            String getDateFound =          resultSet.getString("F.dateFound");
            String getTimeFound =          resultSet.getString("F.timeFound");

            String getLuggageTag =         resultSet.getString("F.luggageTag");
            String getLuggageType =        resultSet.getString("T."+LANGUAGE+"");
            String getBrand =              resultSet.getString("F.brand");
            String getMainColor =          resultSet.getString("c1."+LANGUAGE+"");
            String getSecondColor =        resultSet.getString("c2."+LANGUAGE+"");
            String getSize =               resultSet.getString("F.size");
            String getWeight =             resultSet.getString("F.weight");   
            String getOtherCharacteristics=resultSet.getString("F.otherCharacteristics");

            String getPassengerId =        resultSet.getString("F.passengerId");

            String getName =               resultSet.getString("P.name");
            String getAddress =            resultSet.getString("P.address");
            String getPlace =              resultSet.getString("P.place");
            String getPostalcode =         resultSet.getString("P.postalcode");
            String getCountry =            resultSet.getString("P.country");
            String getEmail =              resultSet.getString("P.email");
            String getPhone =              resultSet.getString("P.phone");

            String getFlight =             resultSet.getString("F.arrivedWithFlight"); 
            String getLocationFound =      resultSet.getString("L."+LANGUAGE+"");
        
        //set al the fields
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
    
    
    /**  
     * When the 'manual match ' button is clicked the instances will be set
     * And the stage will be closed
     * 
     * @param event             when the button is clicked 
     */   
    @FXML
    protected void manualMatching(ActionEvent event) throws IOException{
        //if the user is not on the matching view, switch to that view
        if (MainApp.isOnMatchingView()==false){
            MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
        }
        
        //initialize the instances with the right luggage
        FoundLuggage passFoundLuggage =  FoundLuggageDetailsInstance.getInstance().currentLuggage();
        FoundLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passFoundLuggage.getRegistrationNr());   
        
        
        LostLuggage passLostLuggage =  LostLuggageDetailsInstance.getInstance().currentLuggage();
        LostLuggageManualMatchingInstance.getInstance().currentLuggage().setRegistrationNr(passLostLuggage.getRegistrationNr());
        
        //close this stage
        closeStage();
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
    
    @FXML 
    public void viewDetails(){
        //this is already the detailed view..
    }
    
    /**  
     * Close the current stage by getting the window of a fields scene's
     * And casting this to a stage, and close this stage
     * 
     */ 
    public void closeStage(){
        Stage stage = (Stage) registrationNr.getScene().getWindow();
        stage.close();
    }
    
    
    
}



//          FIX that luggage is added to :
/*          matched 
with:       matchedId
            foundluggage
            lostluggage
            employeeId
            dateMatched
            delivery
*/