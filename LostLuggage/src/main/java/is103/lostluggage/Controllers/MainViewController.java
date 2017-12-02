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
 * FXML Controller class
 *
 * @author gebruiker
 */
public class MainViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    
    //public static String header=  "Chooooooosseee rolee";
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image logo = new Image("Images/Logo.png");
        logoView.setImage(logo);
        topHBox.toFront();
        //title.setText("Chooooooosseee rolee");
//        try {
//            setTitle(header);
//        } catch (IOException ex) {
//            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        instance = this;
        

    }

    public static MainViewController getInstance() {
        return instance;
    }

    /**
     *
     * @param header
     */
    public static void getTitle(String header) throws IOException {
        //header = MainViewController.header;
        getInstance().setTitle(header);
    }
    
    private void setTitle(String header) {
        title.setText(header);
    }
  
    

    @FXML
    private void goBackToPreviousScene(ActionEvent event) throws IOException {

        if (previousView != null) {
            System.out.println("-Back: Previous view/scene");

            MainApp.switchView(previousView);

        } else {
            System.out.println("-Back: No previous view/scene");
        }

        //backButton.getScene().h
        //((Node)(event.getSource())).getScene().getWindow().hide();                      
    }

}
