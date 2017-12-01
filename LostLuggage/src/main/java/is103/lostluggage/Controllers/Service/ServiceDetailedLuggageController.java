/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Model.FoundLuggage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author thijszijdel
 */
public class ServiceDetailedLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("switched!!");
    }   
    
    
    
    
    public static void setDetailedInfoFoundLuggage(FoundLuggage getDetailObj){
        System.out.println("a: "+getDetailObj);
        System.out.println("Adress of object: "+ getDetailObj.getObj_address());
        System.out.println("Label of object: "+ getDetailObj.getObj_labelnumber());
        System.out.println("type of object: "+ getDetailObj.getObj_type());
    }
    
}
