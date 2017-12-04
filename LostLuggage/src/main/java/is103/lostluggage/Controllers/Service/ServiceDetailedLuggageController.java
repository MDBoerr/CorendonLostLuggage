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
        
//        kofferField.setText(getDetailObj.getObj_address());
        //initializeFoundFields();
  
    }   
    
    

    
    @FXML
    private void showDetails(FoundLuggage getDetailObj) {
        System.out.println("-------------------------- show details");
        System.out.println("yess good methode");
        //System.out.println(getDetailObj.getObj_type());
            id_field = new Label();
            
            
            //--> niet de bedoeling maar werkt wel, althans geen error meer
            //kofferField = new JFXTextField();
            
            
            
            // --> facking bug 
            //kofferField.setText("test :D ----");
            
            
            
            System.out.println("-------------- show details");
            
            //kofferField.setText(getDetailObj.getObj_address());
    }
    
    
    private void initializeFoundFields(){
//        String id = LuggageDetails.getInstance().currentLuggage().getIdfoundLuggage();
//        String type = LuggageDetails.getInstance().currentLuggage().getObj_type();
//        String brand = LuggageDetails.getInstance().currentLuggage().getObj_brand();
//        String color = LuggageDetails.getInstance().currentLuggage().getObj_color();
//        String signatures = LuggageDetails.getInstance().currentLuggage().getObj_signatures();
//        
//        idField.setText(id);
//        typeField.setText(type);
//        brandField.setText(brand);
//        colorField.setText(color);
//        signaturesField.setText(signatures);
//      
    }
    
    @FXML
    protected void saveLuggageChanges(ActionEvent event) throws SQLException {
        String luggageId = idField.getText();
        String luggageType = typeField.getText();
        String luggageBrand = brandField.getText();
        String luggageColor = colorField.getText();
        String luggageSignatures = signaturesField.getText();
        
        
        MyJDBC db = new MyJDBC("LostLuggage");
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
