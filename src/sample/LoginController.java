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

    @FXML
    private Button logInBtn;

    SQLConnector sql = new SQLConnector();
    static User user;

    public static boolean isLoggedIn = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void CustomerAccount(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Customer1.fxml"));
    }

    public void logIn(ActionEvent event) {

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        boolean validated = sql.verifyLogin(email, password);
        if (validated) {
            //create new user object  based on the column
            user = sql.createUser(email, password);
            isLoggedIn = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            System.out.println("Logged in.");
            alert.setTitle("Successfully logged in");
            alert.setContentText("Welcome to the book store.");
            alert.show();
            emailTextField.clear();
            passwordTextField.clear();

            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = null;
                root = FXMLLoader.load(getClass().getResource("Store.fxml"));
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Not logged in.");
            alert.setTitle("Error");
            alert.setContentText("Invalid credentials. Please try again.");
            alert.show();
            emailTextField.clear();
            passwordTextField.clear();
        }


    }

    @FXML
    public void ToEmployerSite(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("Employer1.fxml"));


        stage.setScene(new Scene(root));
        stage.show();

    }

    public User getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

}
