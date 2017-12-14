/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Controllers;

import is103.lostluggage.Controllers.Service.ServiceEditFoundLuggageViewController;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataDetails;
import is103.lostluggage.Model.Service.Data.ServiceDataMore;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Poek Ligthart
 */
public class AddNewDataViewController implements Initializable {
    @FXML private TableView<String[]> colorTable;
    @FXML private TableColumn<String[],String> ralCode;
    @FXML private TableColumn<String[],String> english;
    @FXML private TableColumn<String[],String> dutch;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceDataDetails colors = new ServiceDataDetails("color", "*", null);
        try {
            ObservableList<String> colorsStringList = colors.getStringList();
            //colorTable.setItems(colorsStringList);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEditFoundLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    

}
