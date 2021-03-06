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

public class UserEditInformationController implements Initializable {
    
    SQLConnector sql = new SQLConnector();

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
        User user = LoginController.user;

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
    public void changeInfo(ActionEvent event) throws IOException {
        String pTf = passwordTextField.getText();
        String rTf = repeatPasswordTextField.getText();

        boolean hasText = !pTf.isEmpty() && !rTf.isEmpty();
        boolean sameText = rTf.equals(pTf);

        if (hasText && sameText) {
            LoginController.user.setFirstname(firstnameTextField.getText());
            LoginController.user.setLastname(lastnameTextField.getText());
            LoginController.user.setCity(cityTextField.getText());
            LoginController.user.setAddress(addressTextField.getText());
            LoginController.user.setZip(zipTextField.getText());
            LoginController.user.setState(stateTextField.getText());
            LoginController.user.setCountry(countryTextField.getText());

            sql.changeUserInfo(LoginController.user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            System.out.println("Changed.");
            alert.setTitle("Successfully changed information");
            alert.setContentText("Your information has successfully been changed..");
            alert.show();

            seeStore(event);

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Not logged in.");
            alert.setTitle("Error");
            alert.setContentText("Invalid credentials. Please try again.");
            alert.show();

        }
    }

    public void seeStore(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

}
