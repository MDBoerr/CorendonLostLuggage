package is103.lostluggage;

import is103.lostluggage.Controllers.MainViewController;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainApp extends Application {

    private static BorderPane root;

    @Override
    public void start(Stage stage) throws Exception {
        //Oude manier:
        //Parent root = FXMLLoader.load(getClass().getResource("/Views/HomeView.fxml"));        //Admin
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/ServiceHomeView.fxml"));  //Service medewerker
        //Parent root = FXMLLoader.load(getClass().getResource("/Views/ManagerHomeView.fxml"));   //Manager 

        //MyJDBC.createTestDatabase("AirlineDemo");
        //set root
        root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        //root.setTop(headerFxml);          -> later nog een header invoeren!
        //Na laden 'main view'   tijdelijke switch naar -> ServiceHomeView
        //switchView("/fxml/ServiceHomeView.fxml");
        switchView("/fxml/SelectUserRoleView.fxml");

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Corendon Lost Luggage");
        stage.setScene(scene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.show();

    }

    //methode voor het switchen van schermen
    public static void switchView(String view) throws IOException {
        //parent vanuit MainApp laden
        Parent fxmlView = FXMLLoader.load(MainApp.class.getResource(view));

        //scene zetten ( in Center van BorderPane )
        //fxmlView.
        root.setCenter(fxmlView);
        
        //Tried to bring top to front and center to Back.
        //root.toBack();
        Node center = root.getCenter();
      // --->  center.toBack();
        Node top = root.getTop();
        //top.toFront();

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
