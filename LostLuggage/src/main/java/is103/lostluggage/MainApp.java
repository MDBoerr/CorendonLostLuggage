package is103.lostluggage;

import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
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

public class MainApp extends Application {

    private static BorderPane root;

    public static String user = null;
    
    public static int serviceChangeValue = 99;
    public static boolean refreshMatching = true; //true= refresh       -> get's alternated in program
                                                  //false= dont refresh
                                                  //for: manual matching

    private static String dbName = "CorendonLostLuggage";
    
    public static String language = "English";

    @Override
    public void start(Stage stage) throws Exception {

        //Uncomment line below to create a Database on local SQL Server -> See READ ME for help 
        //MyJDBC.createLostLuggageDatabase(dbName);
        
        
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

    public static MyJDBC connectToDatabase() {

        MyJDBC db = new MyJDBC(dbName);

        return db;
    }
    
    public static String getLanguage() {
        return language;
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
