package is103.lostluggage.Controllers.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import is103.lostluggage.Controllers.MainViewController;
import is103.lostluggage.Database.MyJDBC;
import is103.lostluggage.MainApp;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * LogInView Controller class
 *
 * @author Michael de Boer
 */
public class LogInViewController implements Initializable {

    private String header = "Log In";

    @FXML
    private JFXButton logInButton;

    @FXML
    private JFXTextField idTextField;

    @FXML
    private JFXPasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            MainViewController.getInstance().getTitle(header);
        } catch (IOException ex) {
            Logger.getLogger(OverviewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        logInButton.setOnAction(e -> {
            try {
                loggingIn();
            } catch (IOException ex) {
                Logger.getLogger(LogInViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void loggingIn() throws IOException {

        MyJDBC db = MainApp.connectToDatabase();
        String id = idTextField.getText();
        String password = passwordField.getText();
        String role = null;

        ResultSet resultSet;
        try {

            resultSet = db.executeLogInResultSetQuery(id, password);

            while (resultSet.next()) {
                role = resultSet.getString("Role");
                System.out.println("This isss thee role:  " + role);
                if (role != null) {
                    MainApp.checkLoggedInStatus(role);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LogInViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //PreparedStatement preparedStatement = db.executeResultSetQuery(sql);
    }

}
