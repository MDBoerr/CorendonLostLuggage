package is103.lostluggage;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.Model.Service.Data.ServiceDataMatch;
import is103.lostluggage.Model.User;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;


/**
 * Main class
 *
 * @author Michael de Boer
 *
 */

public class MainApp extends Application {

    private static BorderPane root;
    
    public static boolean onMatchingView = false;
    public static int serviceChangeValue = 99;
    public static boolean resetMatching = true; //true= refresh       -> get's alternated in program
                                                  //false= dont refresh
                                                  //for: manual matching
    
    
    //Database instance
    private static MyJDBC DB;
    
    //Name of the database
    final private static String DB_NAME = "CorendonLostLuggage";
        
    public static String language = "english";
    
    public static User currentUser = null;
    
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {

        //Method to set the db property
        setDatabase();
        //db.executeUpdateQuery("RENAME TABLE missingluggage TO lostluggage;");
        
        //set root
        root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

        //switchView("/fxml/SelectUserRoleView.fxml");
        checkLoggedInStatus(currentUser);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        //scene.getStylesheets().add("/styles/MaterialDesign.css");

        stage.setTitle("Corendon Lost Luggage");
        stage.setScene(scene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.setMinWidth(1000);
        stage.setMinHeight(700);

        Image logo = new Image("Images/Stage logo.png");
        //Image applicationIcon = new Image(getClass().getResourceAsStream("Images/Logo.png"));
        stage.getIcons().add(logo);

        stage.show();
        
        //Set the mainstage as a property
        MainApp.mainStage = stage;
        
    }

    //methode voor het switchen van schermen
    public static void switchView(String view) throws IOException {
        //parent vanuit MainApp laden
        Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(view));

        //scene zetten ( in Center van BorderPane )
        //fxmlView.
        root.setCenter(fxmlView);

    }
    
    public static File selectFileToSave(String defaultFileName){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Specify filename");

        //todo: provide the file selection dialog to the user
        File file = fileChooser.showSaveDialog(mainStage);

        //File selected? return the file, else return null
        if(file != null){
            return file;
        }else{
            return null;
        }
    }

    public static boolean isOnMatchingView(){
        return onMatchingView;
    }
    public static void setOnMatchingView(boolean value){
        MainApp.onMatchingView = value;
    }
    
    //set the database instance
    public static void setDatabase(){
        
        MyJDBC db = new MyJDBC(MainApp.DB_NAME);

        MainApp.DB = db;
    }
    
    //method to connect to the database
    public static MyJDBC getDatabase() {
        return MainApp.DB;
    }
    
    public static String getLanguage() {
        return language;
    }
    
    
    //Match object for getting the same data in service pages
    private static final ServiceDataMatch MATCH_DATA = new ServiceDataMatch().getInstance();
    
    public static ServiceDataMatch getMATCH_DATA() {
        return MATCH_DATA;
    }
    private static boolean potentialMatchesReSet = false;
    
    public static void setPotentialResetStatus(boolean b) {
        MainApp.potentialMatchesReSet = b;
    }
    public static boolean getPotentialResetStatus(){
        return MainApp.potentialMatchesReSet;
    }
    

    public static void checkLoggedInStatus(User user) throws IOException {
       
        if (user != null) {
            currentUser = user;
            System.out.println(user);
            if (user.getRole().equals("Administrator")) {
                switchView("/Views/Admin/HomeUserView.fxml");
                System.out.println("The correct user role is selected: " + user);

            }
            if (user.getRole().equals("Manager")) {
                switchView("/Views/ManagerHomeView.fxml");

            }
            if (user.getRole().equals("Service")) {
                switchView("/Views/Service/ServiceHomeView.fxml");

            }

        } else {
            switchView("/Views/Admin/LogInView.fxml");

        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
