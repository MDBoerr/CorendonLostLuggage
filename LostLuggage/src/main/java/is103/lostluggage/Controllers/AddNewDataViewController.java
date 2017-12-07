/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers;

import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Poek Ligthart
 */
public class AddNewDataViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "";
    }    
    
//Add new data to the database.
    @FXML
    protected void AddNewColor(ActionEvent event) throws IOException {
        
    }

    @FXML
    protected void AddNewLuggageType(ActionEvent event) throws IOException {
        
    }
    
    @FXML
    protected void AddNewDestination(ActionEvent event) throws IOException {
        
    }
    
    @FXML
    protected void AddNewFlight(ActionEvent event) throws IOException {
        
    }
    @FXML
    protected void FinishAndClose(ActionEvent event) throws IOException {
        
    }
}
