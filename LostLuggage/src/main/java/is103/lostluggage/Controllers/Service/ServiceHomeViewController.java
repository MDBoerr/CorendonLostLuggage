package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.MainApp;
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
 * @author 2
 */
public class ServiceHomeViewController implements Initializable {

    //views title
    private final String title = "Service Home";
   
    
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
        MainApp.resetMatching = true;
        
        //set the view's title
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(ServiceHomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        //set screen status
        MainApp.setOnMatchingView(false);
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
