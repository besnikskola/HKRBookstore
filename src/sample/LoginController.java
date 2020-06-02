package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    SQLConnector sql = new SQLConnector();
    static User user;

    public static boolean isLoggedIn = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logIn(ActionEvent event) {

        try {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            Alert alert = new Alert(null);

            if (!emailTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
                boolean validated = sql.verifyLogin(email, password);
                if (validated) {
                    //create new user object  based on the column
                    user = sql.createUser(email, password);
                    isLoggedIn = true;
                    System.out.println("Logged in.");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successfully logged in");
                    alert.setContentText("Welcome to the book store.");
                    alert.show();
                    emailTextField.clear();
                    passwordTextField.clear();

                    changeScene(event);

                } else {
                    System.out.println("Not logged in.");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid credentials. Please try again.");
                    alert.show();
                    emailTextField.clear();
                    passwordTextField.clear();
                }
            } else {
                System.out.println("Please enter text.");
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter text on the textfields.");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    public void changeScene(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
