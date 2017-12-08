package is103.lostluggage.Controllers.Service;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Data.ServiceDataFound;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Thijs Zijdel - 500782165
 * @author 2
 */
public class ServiceHomeViewController implements Initializable {

    
    //view title
    private final String title = "Service Home";
   
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene
        MainViewController.previousView = "/fxml/SelectUserRoleView.fxml";
        
        //reset refreshing to auto
        MainApp.refreshMatching = true;
        
        //titel boven de pagina zetten
        try {
            MainViewController.getInstance().getTitle(title);
        } catch (IOException ex) {
            Logger.getLogger(ServiceHomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        

        
        
        
        
        
        
//        ServiceDataFound dataListFoundMain;
//        try {
//            dataListFoundMain = new ServiceDataFound();
//        } catch (SQLException ex) {
//            dataListFound = (ServiceDataFound) ServiceDataFound.getFoundLuggage();
//            Logger.getLogger(ServiceMatchingViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    
    /*-------------------------------------/
    /*            switch views            */
    @FXML
    protected void logOut(ActionEvent event) throws IOException {
        MainApp.switchView("/fxml/SelectUserRoleView.fxml");
    }

    @FXML
    protected void toInputView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceInputLuggageView.fxml");
    }

    @FXML
    protected void toFoundLuggageView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceFoundOverviewView.fxml");
    }

    @FXML
    protected void toMissedLuggageView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMissedOverviewView.fxml");
    }

    @FXML
    protected void toMatchingView(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/Service/ServiceMatchingView.fxml");
    }

}
