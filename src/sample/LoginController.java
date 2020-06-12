package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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

    public void forgotPassword(MouseEvent event) {
        Alert alert = new Alert(null);

        if (!emailTextField.getText().isEmpty()) {
            if (containsValidMail(emailTextField.getText())) {
                if (!sql.userExists(emailTextField.getText())) {

                    System.out.println("Email text field is not empty, proceed to send password.");


                    final String username = "hkrbookstore@gmail.com";
                    final String password = "KristianstadBookStore123";

                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    String recoveredPass = sql.recoveredPassword(emailTextField.getText());
                    System.out.println("Password is: " + recoveredPass);


                    Session session = Session.getInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });

                    try {

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(emailTextField.getText()));
                        message.setSubject("HKR: Recover Password");
                        message.setText("Your password is: " + recoveredPass);

                        Transport.send(message);

                        System.out.println("Message has successfully been sent.");
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText("Password has been sent to your email!");
                        alert.show();

                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Email does not exist.");
                    alert.show();
                }

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Email is not written in a valid form.");
                alert.show();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Email textfield is empty.");
            alert.show();
        }

    }

    public boolean containsValidMail(String enteredMail) {
        boolean valid = false;
        if (enteredMail.contains("@") && enteredMail.contains(".")) {
            valid = true;
        }
        return valid;
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
