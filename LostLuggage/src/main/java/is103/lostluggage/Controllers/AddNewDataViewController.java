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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Poek Ligthart
 */
public class AddNewDataViewController implements Initializable {
    public static ObservableList<Data> colorList;
    
    @FXML private TableView<Data> colorTable;
    @FXML private TableColumn<Data, String> ralCode;
    @FXML private TableColumn<Data, String> english;
    @FXML private TableColumn<Data, String> dutch;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServiceDataDetails colors = new ServiceDataDetails("color", "*", null);
        try {
            ResultSet colorResultSet = colors.getServiceDetailsResultSet();
            while (colorResultSet.next()){
                int ralCode = colorResultSet.getInt("ralCode");
                String english = colorResultSet.getString("english");
                String dutch = colorResultSet.getString("dutch");
                
                String ralCodeS = Integer.toString(ralCode);
                colorList.add(new Data(
                        ralCode,
                        english,
                        dutch
                )); 
                
            } //end while loop
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEditFoundLuggageViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initializeColorTable();
    }    
    
        public void initializeColorTable(){
        ralCode.setCellValueFactory(      new PropertyValueFactory<>("ralCode"));
        english.setCellValueFactory(            new PropertyValueFactory<>("english"));  //-> lost
        dutch.setCellValueFactory(            new PropertyValueFactory<>("dutch"));
        
       
        colorTable.setItems(colorList);
    }
    

}
