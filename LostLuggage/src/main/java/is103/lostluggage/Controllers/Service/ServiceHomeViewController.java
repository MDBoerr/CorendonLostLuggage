package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
import is103.lostluggage.Model.Service.Data.ServiceDataMatch;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 */
public class ServiceHomeViewController implements Initializable {

    //set the onMatchingView to false, the current page isn't the matching view
    public static boolean onMatchingView = false;
    
    //set the potentialMatchesRest to false, it isn't needed to reset now
    private static boolean potentialMatchesReSet = false;
    
    
    //Match object for getting the same data in service pages
    private static final ServiceDataMatch MATCH_DATA = ServiceDataMatch.getInstance();
    
    
    public static boolean resetMatching = true; //true= refresh       -> get's alternated in program
                                                //false= dont refresh
                                                //for: manual matching

    /**
     * This method is for getting the one and only instance of the class match 
     * 
     * @return ServiceDataMatch the instance of this class
     */
    public static ServiceDataMatch getMATCH_DATA() {
        return ServiceHomeViewController.MATCH_DATA;
    }
    
    /**
     * This is for setting the onMatchingView boolean
     * Note: used for example by the detailed views.
     * 
     * @param value         always false, only when on matching view = true
     */
    public static void setOnMatchingView(boolean value) {
        ServiceHomeViewController.onMatchingView = value;
    }

    /**
     * Getting the state of the boolean on matching view
     * 
     * @return boolean the state of this variable
     */
    public static boolean isOnMatchingView() {
        return ServiceHomeViewController.onMatchingView;
    }

    /**
     * This is for setting the status of the potential matching resetting table
     * This will be checked when opening the matching view 
     * 
     * @param b     true: if the potentialMatching needs to be reset
     */
    public static void setPotentialResetStatus(boolean b) {
        ServiceHomeViewController.potentialMatchesReSet = b;
    }
    /**
     * Get the status of there is a reset needed for the potential matching
     * For clearing the list.
     * 
     * @return boolean of the status
     */
    public static boolean getPotentialResetStatus() {
        return ServiceHomeViewController.potentialMatchesReSet;
    }

    //view title
    private final String TITLE = "Service Home";
   
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set switchable to prev view. (this)
        MainViewController.previousView = "/Views/Service/ServiceHomeView.fxml";
        
        //reset refreshing to auto
        resetMatching = true;
        
        //set the view's title
        try {
            MainViewController.getInstance().getTitle(TITLE);
        } catch (IOException ex) {
            Logger.getLogger(ServiceHomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        //set screen status
        setOnMatchingView(false);
    }
    
    
    /*-------------------------------------/
    /*            switch views            */
    /*------------------------------------*/
    @FXML
    protected void logOut(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/SelectUserRoleView.fxml");
        //logout methode is needed here!
    }

    @FXML
    protected void toInputView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInputLuggageView.fxml");
    }

    @FXML
    protected void toFoundLuggageView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceOverviewFoundView.fxml");
    }

    @FXML
    protected void toMissedLuggageView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceOverviewLostView.fxml");
    }

    @FXML
    protected void toMatchingView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
    }

}
