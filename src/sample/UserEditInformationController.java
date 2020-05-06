package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserEditInformationController implements Initializable {

    LoginController lc = new LoginController();

    @FXML
    private TextField emailTextField;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = lc.getUser();
        emailTextField.setText(user.getEmail());
        firstnameTextField.setText(user.getFirstname());
        lastnameTextField.setText(user.getLastname());
        cityTextField.setText(user.getCity());
        addressTextField.setText(user.getAddress());
        zipTextField.setText(user.getZip());
        stateTextField.setText(user.getState());
        countryTextField.setText(user.getCountry());

    }

    @FXML
    public void changeInfo() {

        //change method from sql class
    }

}
