package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.HomeViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerHomeViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    @FXML 
    protected void rapportageView(ActionEvent event) throws IOException {
        try { 
            Parent root1 = FXMLLoader.load(getClass().getResource("/Views/ManagerRapportageView.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();

        
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML 
    protected void naarGevondenOverzichtScherm(ActionEvent event) throws IOException {
        try { 
            Parent root1 = FXMLLoader.load(getClass().getResource("/Views/ManagerGevondenView.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML 
    protected void naarVermisteOverzichtScherm(ActionEvent event) throws IOException {
        try { 
            Parent root1 = FXMLLoader.load(getClass().getResource("/Views/ManagerVerlorenView.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML 
    protected void naarTeruggebracht(ActionEvent event) throws IOException {
        try { 
            Parent root1 = FXMLLoader.load(getClass().getResource("/Views/ManagerTerugView.fxml"));
            //Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
