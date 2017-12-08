package is103.lostluggage.Controllers;

import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * MainView Controller class
 *
 * @author Michael de Boer
 */
public class MainViewController implements Initializable {

    //Create instance
    public static MainViewController instance = null;
    public MainViewController(){}

    @FXML
    private Button backButton;

    @FXML
    private ImageView logoView;
    
    @FXML
    private HBox topHBox;

    public static String previousView;

    @FXML
    private Label title;
        

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image logo = new Image("Images/Logo.png");
        logoView.setImage(logo);
        topHBox.toFront();

        instance = this;
        
        

    }
    //Get instance
    public static MainViewController getInstance() {
        return instance;
    }

    /**
     * Get the static string header and use instance to pass header to setTitle
     * @param header
     */
    public static void getTitle(String header) throws IOException {
        //header = MainViewController.header;
        getInstance().setTitle(header);
    }
    //Set non-static header as Title
    private void setTitle(String header) {
        title.setText(header);
    }
  
    

    @FXML
    private void goBackToPreviousScene(ActionEvent event) throws IOException {

        if (previousView != null) {
            System.out.println("-Back: Previous view= " + previousView);

            MainApp.switchView(previousView);

        } else {
            System.out.println("-Back: No previous view/scene");
        }

        //backButton.getScene().h
        //((Node)(event.getSource())).getScene().getWindow().hide();                      
    }

}
