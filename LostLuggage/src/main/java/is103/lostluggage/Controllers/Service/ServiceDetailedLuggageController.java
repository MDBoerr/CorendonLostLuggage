/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Service;

import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Model.FoundLuggage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;






import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import static is103.lostluggage.MainApp.connectToDatabase;
import is103.lostluggage.Model.LuggageDetails;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;




/**
 * FXML Controller class
 *
 * @author thijszijdel
 */
public class ServiceDetailedLuggageController implements Initializable {
    
    @FXML
    public Label id_field;
    
    @FXML
    private JFXTextField idField;
    
    @FXML
    private JFXTextField typeField;

    @FXML  
    private JFXTextField brandField;
    
    @FXML  
    private JFXTextField colorField;
    
    @FXML  
    private JFXTextArea signaturesField;
    
    @FXML
    //Field that contains the employeeId
    private JFXTextField employeeIdField;
    
    

    
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
            Logger.getLogger(ServiceDetailedLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }   
    
    

    
    
    
    private void initializeFoundFields() throws SQLException{
        String id = LuggageDetails.getInstance().currentLuggage().getRegistrationNr();
        System.out.println("iD: "+id);
            MyJDBC db = MainApp.connectToDatabase();
            
            ResultSet resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage WHERE registrationNr='"+id+"'");
                
            while (resultSet.next()) {
                //String registrationNr =     resultSet.getString("registrationNr");
                //String dateFound =          resultSet.getString("dateFound");
                //String timeFound =          resultSet.getString("timeFound");
                
                //String luggageTag =         resultSet.getString("luggageTag");
                int luggageType =           resultSet.getInt("luggageType");
                String brand =              resultSet.getString("brand");
                int mainColor =             resultSet.getInt("mainColor");
                int secondColor =           resultSet.getInt("secondColor");
                //int size =                  resultSet.getInt("size");
                //int weight =                resultSet.getInt("weight");   
                String otherCharacteristics=resultSet.getString("otherCharacteristics");
                int passengerId =           resultSet.getInt("passengerId");
                
                //String arrivedWithFlight =  resultSet.getString("arrivedWithFlight"); 
                int locationFound =         resultSet.getInt("locationFound");
                //String employeeId =         resultSet.getString("employeeId");
                //int matchedId =              resultSet.getInt("matchedId");
 
            idField.setText(id);  
//            setType(db,luggageType);
            brandField.setText(brand);
            
            
            
//            setColor(db, mainColor); 
//            setSecondColor(db, secondColor);
            
            signaturesField.setText(otherCharacteristics);
            
//            setPassenger(db, passengerId);
//            
//            setLocation(db, locationFound);
            }
        
    }
    
    private void setColor(MyJDBC db, int colorD) throws SQLException {
        ResultSet result_color = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+colorD+"'");
        while (result_color.next()) {    
            String color = result_color.getString("english");
            colorField.setText(color);
        }
    }
    private void setType(MyJDBC db, int luggageType) throws SQLException {
        ResultSet result_type = db.executeResultSetQuery("SELECT * FROM luggagetype WHERE luggageTypeId='"+luggageType+"'");
        while (result_type.next()) {    
            String type = result_type.getString("english");
            typeField.setText(type);
        }
    }
    
    private void setSecondColor(MyJDBC db, int secondColor) {
//        ResultSet result_second = db.executeResultSetQuery("SELECT * FROM color WHERE ralCode='"+secondColor+"'");
//        while (result_second.next()) {    
//            String color = result_second.getString("english");
//            colorField2.setText(color);
//        }
    }     

    private void setPassenger(MyJDBC db, int passengerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setLocation(MyJDBC db, int locationFound) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    @FXML
    protected void saveLuggageChanges(ActionEvent event) throws SQLException {
        String luggageId = idField.getText();
        String luggageType = typeField.getText();
        String luggageBrand = brandField.getText();
        String luggageColor = colorField.getText();
        String luggageSignatures = signaturesField.getText();
        
        
        MyJDBC db = MainApp.connectToDatabase();
        ResultSet resultSet;
        resultSet = db.executeResultSetQuery("SELECT * FROM foundLuggage WHERE idfoundLuggage='"+luggageId+"'");
        System.out.println("result is:"+resultSet);
        if (    luggageType == null || "".equals(luggageType) ||
                luggageBrand == null || "".equals(luggageBrand) ||
                luggageColor == null || "".equals(luggageColor) ||
                luggageSignatures == null || "".equals(luggageSignatures)
                ) {
            System.out.println("Een van de velden is leeg of null");
        } else {
            db.executeUpdateQuery("UPDATE `LostLuggage`.`foundLuggage` SET `type`='"+luggageType+"', `brand`='"+luggageBrand+"', `color`='"+luggageColor+"', `signatures`='"+luggageSignatures+"' WHERE `idfoundLuggage`='"+luggageId+"'");
            System.out.println("DB row is updated!");
        }
   
    }

    
}
