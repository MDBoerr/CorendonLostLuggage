package is103.lostluggage;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.Model.Service.Data.ServiceDataMatch;
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


/**
 * Main class
 *
 * @author Michael de Boer
 *
 */

public class MainApp extends Application {

    private static BorderPane root;

    public static String user = null;
    
    public static boolean onMatchingView = false;
    public static int serviceChangeValue = 99;
    public static boolean resetMatching = true; //true= refresh       -> get's alternated in program
                                                  //false= dont refresh
                                                  //for: manual matching
    //Match object for getting the same data in service pages
    private static ServiceDataMatch matchData = new ServiceDataMatch();
    
    //Database instance
    private static MyJDBC db;
    
    //Name of the database
    final private static String DB_NAME = "lostluggage";
        
    public static String language = "english";

    @Override
    public void start(Stage stage) throws Exception {

        //Method to set the db property
        setDatabase();
        //db.executeUpdateQuery("RENAME TABLE missingluggage TO lostluggage;");
        
        //set root
        root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

        //switchView("/fxml/SelectUserRoleView.fxml");
        checkLoggedInStatus(user);

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

    }

    //methode voor het switchen van schermen
    public static void switchView(String view) throws IOException {
        //parent vanuit MainApp laden
        Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(view));

        //scene zetten ( in Center van BorderPane )
        //fxmlView.
        root.setCenter(fxmlView);

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

        MainApp.db = db;
    }
    
    //method to connect to the database
    public static MyJDBC getDatabase() {
        return MainApp.db;
    }
    
    public static String getLanguage() {
        return language;
    }
    
    public static ServiceDataMatch getMatchData() {
        return matchData;
    }
    private static boolean potentialMatchesReSet = false;
    
    public static void setPotentialResetStatus(boolean b) {
        MainApp.potentialMatchesReSet = b;
    }
    public static boolean getPotentialResetStatus(){
        return MainApp.potentialMatchesReSet;
    }
    

    public static void checkLoggedInStatus(String user) throws IOException {

        if (user != null) {
            System.out.println(user);
            if (user.equals("Adminstrator")) {
                switchView("/Views/Admin/HomeUserView.fxml");
                System.out.println(user);

            }
            if (user.equals("Manager")) {
                switchView("/Views/ManagerHomeView.fxml");

            }
            if (user.equals("Service")) {
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
