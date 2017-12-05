package is103.lostluggage.Controllers.Manager;

import is103.lostluggage.Controllers.Admin.OverviewUserController;
import is103.lostluggage.Controllers.Admin.HomeUserViewController;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daron
 */
public class ManagerReportViewController implements Initializable {
//    public String myArray[] = {"jan","feb","mar","apr"};

//    public ArrayList<String> name = new ArrayList<String>(); // Member
//
//    public ArrayList<String> getNameformarray() {
//        name.add("jan");
//        name.add("feb");
//        
//        
//        
//        name.add("mar");
//        return name;
//    }
    /**
     * Initializes the controller class.
     */
    public String mon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To Previous Scene

        MainViewController.previousView = "/Views/ManagerHomeView.fxml";
        XYChart.Series<String, Number> seriesfound = new XYChart.Series<>();
        XYChart.Series<String, Number> serieslost = new XYChart.Series<>();

        try {

            MyJDBC db = MainApp.connectToDatabase();

            ResultSet resultSet;
            ResultSet lostSet;

            resultSet = db.executeResultSetQuery("SELECT count(dateFound) as quantityfound, extract(month FROM dateFound) as months from foundluggage  GROUP BY extract(month from dateFound)");
            lostSet = db.executeResultSetQuery("SELECT count(dateLost) as quantitylost, extract(month FROM dateLost) as months from lostluggage  GROUP BY extract(month from dateLost)");

            while (resultSet.next()) {
                int countfound = resultSet.getInt("quantityfound");
                int month = resultSet.getInt("months");

                switch (month) {
                    case 1:
                        mon = "januari";
                        break;
                    case 2:
                        mon = "februari";
                        break;
                    case 3:
                        mon = "maart";
                        break;
                    case 4:
                        mon = "april";
                        break;
                    case 5:
                        mon = "Mei";
                        break;
                    case 6:
                        mon = "juni";
                        break;
                    case 7:
                        mon = "juli";
                        break;
                    case 8:
                        mon = "augustus";
                        break;
                    case 9:
                        mon = "september";
                        break;
                    case 10:
                        mon = "oktober";
                        break;
                    case 11:
                        mon = "november";
                        break;
                    case 12:
                        mon = "december";

                }
                seriesfound.getData().add(new XYChart.Data<>(mon, countfound));

            }

            while (lostSet.next()) {

                int month = lostSet.getInt("months");
                int countlost = lostSet.getInt("quantitylost");

                switch (month) {
                    case 1:
                        mon = "januari";
                        break;
                    case 2:
                        mon = "februari";
                        break;
                    case 3:
                        mon = "maart";
                        break;
                    case 4:
                        mon = "april";
                        break;
                    case 5:
                        mon = "Mei";
                        break;
                    case 6:
                        mon = "juni";
                        break;
                    case 7:
                        mon = "juli";
                        break;
                    case 8:
                        mon = "augustus";
                        break;
                    case 9:
                        mon = "september";
                        break;
                    case 10:
                        mon = "oktober";
                        break;
                    case 11:
                        mon = "november";
                        break;
                    case 12:
                        mon = "december";

                }
                
                serieslost.getData().add(new XYChart.Data<>(mon, countlost));
            }

        } catch (SQLException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        seriesfound.setName("Found Luggage");
        serieslost.setName("Lost Luggage");
        lineChart.getData().add(seriesfound);
        lineChart.getData().add(serieslost);

//
//          lineChart.getData().clear();
//          XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
//          series.getData().add(new XYChart.Data<String, Number>("jan", 20));
//          series.getData().add(new XYChart.Data<String, Number>("feb", 60));
//          series.getData().add(new XYChart.Data<String, Number>("mar", 14));
//          series.getData().add(new XYChart.Data<String, Number>("apr", 26));
//          series.getData().add(new XYChart.Data<String, Number>("mei", 7));
//          lineChart.getData().add(series);
//
//          
//          XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
//          series2.getData().add(new XYChart.Data<String, Number>("jan", 50));
//          series2.getData().add(new XYChart.Data<String, Number>("feb", 66));
//          series2.getData().add(new XYChart.Data<String, Number>("mar", 77));
//          series2.getData().add(new XYChart.Data<String, Number>("apr", 88));
//          series2.getData().add(new XYChart.Data<String, Number>("mei", 3));
//          lineChart.getData().add(series2);
    }

    @FXML
    LineChart<String, Number> lineChart;

    @FXML
    protected void naarVermisteOverzichtScherm(ActionEvent event) throws IOException {
        MainApp.switchView("/Views/ManagerHomeView.fxml");
    }

}
