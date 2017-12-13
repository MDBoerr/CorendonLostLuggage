/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is103.lostluggage.Model.Service.Data;

import is103.lostluggage.Controllers.Service.ServiceMatchingViewController;
import is103.lostluggage.Model.Service.Instance.Details.FoundLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Instance.Details.LostLuggageDetailsInstance;
import is103.lostluggage.Model.Service.Model.FoundLuggage;
import is103.lostluggage.Model.Service.Model.LostLuggage;
import is103.lostluggage.Model.Service.Model.MatchLuggage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author thijszijdel
 */
public class ServiceDataMore {
    
    
        //popup stage
        public Stage popupStageFound = new Stage();   
        public Stage popupStageLost = new Stage(); 
        
        public void setDetailsOfRow(String type, MouseEvent event, Stage stageType, String stageLink, String popupKey){
        
             Node node = ((Node) event.getTarget() ).getParent();
             TableRow tableRowGet;
             
             if (node instanceof TableRow) {
                    tableRowGet = (TableRow) node;
            } else {
                    // if text is clicked -> get parent
                    tableRowGet = (TableRow) node.getParent();
            }
             
        if ("lost".equals(type)){
            //get the right found luggage object -> place this in getDetailObj
            LostLuggage getDetailObj = (LostLuggage) tableRowGet.getItem();
            
            //Detail object setten -> so it is posible to take this in the next fxml
            
            //LostLuggageDetails.getInstance().currentLuggage() .setRegistrationNr(getDetailObj.getRegistrationNr());
            LostLuggage route = LostLuggageDetailsInstance.getInstance().currentLuggage();
            route.setRegistrationNr(getDetailObj.getRegistrationNr());
            
            //----
            //Momenteel alleen de id en met execute query alle gegevens verkrijgen
            //Nog kijken wat efficienter is
            //----
        } 
        
        if ("found".equals(type)){
            //get the right obj -> place this in getDet. repeat.. 
            FoundLuggage getDetailObj = (FoundLuggage) tableRowGet.getItem();
            
            //repeat.. 
            FoundLuggageDetailsInstance.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
                
        } 
        
         if ("match".equals(type)){
            //      match to manual matching
            //  -   -   -   -   -   -   -   -   -
            MatchLuggage getDetailObj = (MatchLuggage) tableRowGet.getItem();
            FoundLuggageDetailsInstance.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNrFound());
            LostLuggageDetailsInstance.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNrLost());

        }
    }
    
    
    
    
    public void setAndOpenPopUpDetails(String type, Stage stageType, String stageLink, String popupKey){
        //switchen naar detailed view dmv: popup
        if ("found".equals(type) || "lost".equals(type)){
            try {
                popUpDetails(stageType, stageLink, popupKey);
            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("match".equals(type)){
            try {
                popUpDetails(popupStageLost, "/Views/Service/ServiceDetailedLostLuggageView.fxml", popupKey);
                popUpDetails(popupStageFound, "/Views/Service/ServiceDetailedFoundLuggageView.fxml", popupKey);
            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void popUpDetails(Stage stage, String viewLink, String type) throws IOException { 
            try { 
                //get popup fxml resource   
                Parent popup = FXMLLoader.load(getClass().getResource(viewLink));
                stage.setScene(new Scene(popup));
                
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                
                
                //set location of pop ups
                if ("found".equals(type)){
                    //stage.setX(screenBounds.getMinX() + screenBounds.getWidth() - 10);
                    stage.setX(+600);
                } else if ("lost".equals(type)) {
                    //stage.setX(screenBounds.getMaxX() - screenBounds.getWidth() - 10);
                    stage.setX(-100);
                } else if ("match".equals(type)){
                    //if popup details are being used in the coming changes
                    //*don't forget to set right position!
                }
                
 //               stage.setY(screenBounds.getMaxY() - screenBounds.getHeight() - 10);
                stage.setY(-100);
                //no functies -> close / fullscreen/ topbar
                //stage.initStyle(StageStyle.TRANSPARENT); //off

                //stage altijd on top
                stage.setAlwaysOnTop(true);

                if (stage.isShowing()){
                    //Stage was open -> refresh
                    stage.close();
                    stage.show();
                } else {
                    //Stage was gesloten -> alleen openen
                    stage.show();
                    System.out.println("Popup opend");
                }


            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}