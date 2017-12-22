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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author thijszijdel
 */
public class ServiceMoreDetails {
    //hight and with of the detail view (popup)
    private final int DETAILS_STAGE_H = 675;
    private final int DETAILS_STAGE_W = 415;

    private int matchLuggage = 0;

    //popup stages
    public Stage overlay = new Stage();
    public Stage popupStageFound = new Stage();   
    public Stage popupStageLost = new Stage(); 
    public Stage popupStageMatch = new Stage(); 
        
    /**  
     * Setting the details of the right object
     * This will happen by getting the item of the tables node 
     * This item will than be set in the right instance of ..LuggageDetailsInstance
     * 
     * @param type          type of luggage that needs to bet set
     * @param event         type of event this method is called
     * @param stageType     type of stage that needs to be viewed
     * @param stageLink     link to the FXML file 
     * @void 
     */
    public void setDetailsOfRow(String type, MouseEvent event, Stage stageType, String stageLink){
        //get the node of the given event
        Node node = ((Node) event.getTarget() ).getParent();
        
        //initialize a table row
        TableRow tableRowGet;

        //if the node is a instance of an table row
        if (node instanceof TableRow) {
            //cast the node to a table row   
            tableRowGet = (TableRow) node;
        } else {
               //possible is the text clicked
               //so: get table row from the node its parent
               tableRowGet = (TableRow) node.getParent();
        }
        
        //check wich type is given as a param
        if ("lost".equals(type)){
            //get the right found luggage object -> place this in getDetailObj
            LostLuggage getDetailObj = (LostLuggage) tableRowGet.getItem();
            
            //Detail object setten -> so it is posible to take this in the next fxml
            LostLuggage route = LostLuggageDetailsInstance.getInstance().currentLuggage();
            route.setRegistrationNr(getDetailObj.getRegistrationNr());
       
        }else if ("found".equals(type)){
            //get the right obj -> place this in getDet. repeat.. 
            FoundLuggage getDetailObj = (FoundLuggage) tableRowGet.getItem();
            
            //repeat.. 
            FoundLuggageDetailsInstance.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNr());
                
        } else if ("match".equals(type)){
            //      match to manual matching
            //  -   -   -   -   -   -   -   -   -
            MatchLuggage getDetailObj = (MatchLuggage) tableRowGet.getItem();
            FoundLuggageDetailsInstance.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNrFound());
            LostLuggageDetailsInstance.getInstance().currentLuggage().setRegistrationNr(getDetailObj.getRegistrationNrLost());
        } else {
            System.out.println("unsuported type given for the detail object");
        }
    }
    
    
    /**  
     * Setting the right details for calling the pop up details stage
     * 
     * @param popupKey      type of pop up wanted
     * @param stageType     type of stage needed
     * @param stageLink     link to the FXML file 
     * @void 
     */
    public void setAndOpenPopUpDetails(Stage stageType, String stageLink, String popupKey){
        //switchen to selection/ detailed view with: popup
        //check the popupkey
        if ("found".equals(popupKey) || "lost".equals(popupKey) || "match".equals(popupKey)){
            try {
                //try to call the popupstage
                popUpDetails(stageType, stageLink, popupKey);
            } catch (IOException ex) {
                Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**  
     * Call the pop up details stage
     * 
     * @throws IOException  trying to switch views 
     * @param type          type of pop up wanted
     * @param stage         type of stage needed
     * @param viewLink      link to the FXML file 
     * @void                only opening the pop up (detailed) view
     */
    public void popUpDetails(Stage stage, String viewLink, String type) throws IOException { 
        try { 
            //get popup fxml resource   
            Parent popup = FXMLLoader.load(getClass().getResource(viewLink));

            //set the right scene (popup) to the given stage
            stage.setScene(new Scene(popup));

            //setting the width
            stage.setWidth(DETAILS_STAGE_W);

            //if the type is match
            if("match".equals(type)){
                //set double width (found& lost next to each other)
                stage.setWidth(DETAILS_STAGE_W*2);
            } else {
                //set the normal width
                stage.setHeight(DETAILS_STAGE_H);
            }

            //no functies -> close / fullscreen/ topbar
            //stage.initStyle(StageStyle.TRANSPARENT); //off

            //stage always on top
            stage.setAlwaysOnTop(true);
            //stage not resizable 
            stage.setResizable(false);

            //if stage is already opened
            if (stage.isShowing()){
                //refresh (by closing and reopening it)
                stage.close();
                stage.show();
            } else {
                //Stage was closed so open it
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}