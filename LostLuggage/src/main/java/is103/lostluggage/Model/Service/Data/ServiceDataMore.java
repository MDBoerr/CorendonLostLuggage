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
    
        private final int DETAILS_STAGE_H = 675;
        private final int DETAILS_STAGE_W = 400;
        
        
        private int matchLuggage = 0;
        
        //popup stage
        public Stage overlay = new Stage();
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
            LostLuggage route = LostLuggageDetailsInstance.getInstance().currentLuggage();
            route.setRegistrationNr(getDetailObj.getRegistrationNr());
       
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
        //switchen to selection/ detailed view with: popup
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
                
                
                //Possible fix for out of screen on windows 
                //final int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
                //final int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
                
                
                Rectangle2D mainScreenBounds = Screen.getPrimary().getVisualBounds();

                if (null!=type)switch (type) {
                    case "found":stage.setX(mainScreenBounds.getMinX() + mainScreenBounds.getWidth() - DETAILS_STAGE_W);
                        break;
                    case "lost":stage.setX(mainScreenBounds.getMinX() - mainScreenBounds.getWidth() - DETAILS_STAGE_W);
                        break;
                    case "match":
                        
                            //overlay testing
                            if (overlay.isShowing() == false){
                                overlay.setWidth(mainScreenBounds.getMaxX());
                                overlay.setHeight(mainScreenBounds.getMaxY());
                                //overlay.show();
                            }
                            
                            if (matchLuggage == 0){
                                //popup stage for lost luggage
                                stage = popupStageLost;
                                stage.close();
                                
                                //set Stage boundaries to the left side  of the visible bounds of the users (main) screen
                                stage.setX(mainScreenBounds.getMinX() - mainScreenBounds.getWidth() - DETAILS_STAGE_W);
                                matchLuggage ++;
                            } else {
                                //popup stage for found luggage
                                stage = popupStageFound;
                                stage.close();
                                
                                //set Stage boundaries to the right side  of the visible bounds of the users (main) screen
                                stage.setX(mainScreenBounds.getMinX() + mainScreenBounds.getWidth() - DETAILS_STAGE_W);
                                matchLuggage--;
                            }
                        break;
                    default: //top - middle of screen
                        break;
                }
                
                //set Stage boundaries to the top  of the visible bounds of the users (main) screen
                stage.setY(mainScreenBounds.getMinY() - mainScreenBounds.getHeight() - DETAILS_STAGE_H);
                
                stage.setWidth(DETAILS_STAGE_W);
                stage.setHeight(DETAILS_STAGE_H);
                
                
                
                //no functies -> close / fullscreen/ topbar
                //stage.initStyle(StageStyle.TRANSPARENT); //off

                //stage altijd on top
                stage.setAlwaysOnTop(true);
                //stage not resizable 
                stage.setResizable(false);

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