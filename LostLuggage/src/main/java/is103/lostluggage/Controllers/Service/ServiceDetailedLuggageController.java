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
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
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
    private static Label brand_field;
    
    @FXML
    private static Label type_field;

    @FXML  
    private JFXTextField searchField;
    
    
    @FXML
    //Field that contains the employeeId
    private JFXTextField employeeIdField;
    
    
    @FXML
    private JFXTextField kofferField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("switched!!");
        
//        kofferField.setText(getDetailObj.getObj_address());
        zet();

    }   
    
    
    public static void setDetailedInfoFoundLuggage(FoundLuggage getDetailObj){
        
        System.out.println("a: "+getDetailObj);
        System.out.println("Adress of object: "+ getDetailObj.getObj_address());
        System.out.println("Label of object: "+ getDetailObj.getObj_labelnumber());
        System.out.println("type of object: "+ getDetailObj.getObj_type());
        
        System.out.println("yes: labe number");
        
        
        ServiceDetailedLuggageController method = new ServiceDetailedLuggageController();
        method.showDetails(getDetailObj);
        
        
        System.out.println(getDetailObj.getObj_labelnumber());
        
        
//        id_field.setText(getDetailObj.getObj_labelnumber());
//        
//        brand_field.setText(getDetailObj.getObj_address());
//        
//        type_field.setText(getDetailObj.getObj_type());
        
        
    }
    
    @FXML
    private void showDetails(FoundLuggage getDetailObj) {
        System.out.println("-------------------------- show details");
        System.out.println("yess good methode");
        System.out.println(getDetailObj.getObj_type());
            id_field = new Label();
            
            
            //--> niet de bedoeling maar werkt wel, althans geen error meer
            //kofferField = new JFXTextField();
            
            
            
            // --> facking bug 
            //kofferField.setText("test :D ----");
            
            
            
            System.out.println("-------------- show details");
            
            //kofferField.setText(getDetailObj.getObj_address());
    }
    
    
    private void zet(){
        kofferField.setText("  ddd   ");
    }
    
}
