/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers;

 
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mike
 */
public class HomeViewController implements Initializable {
    
    
    @FXML private Label label1;
    @FXML private Button button;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
//@FXML
//    protected void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label1.setText("Hello World!");
//        
//        if (button.isPressed()) {
//            System.out.println("You clicked me!");
//
//        }
//    }
    
    @FXML 
    protected void handleButtonAction(ActionEvent event) throws IOException {
        try { 
            Parent root1 = FXMLLoader.load(getClass().getResource("/Views/UserScene.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            System.out.println("Works");
        
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
