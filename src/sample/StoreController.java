package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StoreController extends SQLConnector implements Initializable {

    @FXML
    private Button signUpBtn;

    @FXML
    private Button logInBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private Button editInfoBtn;

    @FXML
    private Button editBooksBtn;

    @FXML
    private TextField searchBookTextField;

    @FXML
    private TextArea searchResultArea;

    static ArrayList<Book> arrListBooks = new ArrayList<Book>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editBooksBtn.setVisible(true);
            connect();
            arrListBooks = loadBooks();
            searchBookTextField.setOnKeyReleased(keyEvent -> relevantSearchMethod());
            if (LoginController.isLoggedIn) {
                System.out.println("Is logged in as: " + LoginController.user.toString());
                signUpBtn.setVisible(false);
                logInBtn.setVisible(false);
                signOutBtn.setVisible(true);
                editInfoBtn.setVisible(true);
                editBooksBtn.setVisible(true);

            } else {
                System.out.println("Is not logged in.");
            }

    }

    //for book search
    private void relevantSearchMethod() {
        try {

            if (searchBookTextField.getText().length() >= 2) {
                searchResultArea.clear();

                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT title FROM books " +
                        "WHERE title LIKE '%" + searchBookTextField.getText() + "%';");

                while (resultSet.next()) {
                    searchResultArea.appendText(resultSet.getString(1) + "\n");
                }
            } else
                searchResultArea.clear();

        } catch (SQLException sq) {
            sq.printStackTrace();
            System.out.println(sq.getMessage());
        }
    }

    @FXML
    public void signUp(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void logIn(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));


        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    public void info(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("info.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void options(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("changeOptionSample.fxml"));

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    public void seeEditInfo(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("UserEditInformation.fxml"));

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    public void seeList(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("BooklistA-ZController.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void seeEditBookInfo(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("BookEditInformation.fxml"));

        stage.setScene(new Scene(root));
        stage.show();

    }

}


