package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.SQLConnector.Email;


public class RegisterController implements Initializable {


    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField repeatPasswordTextField;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField stateTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField emailTextField;

    SQLConnector sql = new SQLConnector();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void AccountCreated(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    public void registerUser(ActionEvent event) throws IOException {
        String pTf = passwordTextField.getText();
        String rTf = repeatPasswordTextField.getText();

        boolean hasText = !addressTextField.getText().isEmpty() && !emailTextField.getText().isEmpty() && !firstnameTextField.getText().isEmpty() &&
                !lastnameTextField.getText().isEmpty() && !cityTextField.getText().isEmpty() && !zipTextField.getText().isEmpty() && !countryTextField.getText().isEmpty() &&
                !stateTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty() && !repeatPasswordTextField.getText().isEmpty();
        boolean sameText = rTf.equals(pTf);

        if (hasText && sameText) {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            String firstname = firstnameTextField.getText();
            String lastname = lastnameTextField.getText();
            String city = cityTextField.getText();
            String address = addressTextField.getText();
            String zip = zipTextField.getText();
            String country = countryTextField.getText();
            String state = stateTextField.getText();

            User user = new User(email, password, firstname, lastname, address, city, zip, state, country, false);
            sql.createUser(user);
            LoginController.user = user;
            LoginController.isLoggedIn = true;
            Email.add(emailTextField.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            System.out.println("Logged in.");
            alert.setTitle("Successfully logged in");
            alert.setContentText("Welcome to the book store.");
            alert.show();
            emailTextField.clear();
            passwordTextField.clear();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

            stage.setScene(new Scene(root));
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Not logged in.");
            alert.setTitle("Error");
            alert.setContentText("Invalid credentials. Please try again.");
            alert.show();

        }


    }

}
