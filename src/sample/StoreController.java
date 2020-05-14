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
    private Button addBookBtn;

    @FXML
    private TextField searchBookTextField;

    @FXML
    private TextArea searchResultArea;

    static ArrayList<Book> arrListBooks = new ArrayList<Book>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        connect();
        arrListBooks = loadBooks();
        searchBookTextField.setOnKeyReleased(keyEvent -> relevantSearchMethod());
        if (LoginController.isLoggedIn) {
            System.out.println("Is logged in as: " + LoginController.user.toString());
            signUpBtn.setVisible(false);
            logInBtn.setVisible(false);
            signOutBtn.setVisible(true);
            editInfoBtn.setVisible(true);
            if (LoginController.user.getIsEmployee()) {
                editBooksBtn.setVisible(true);
                addBookBtn.setVisible(true);
            }
        } else {
            System.out.println("Is not logged in.");
        }

    }

    //for book search
    private void relevantSearchMethod() {
        try {

            if (searchBookTextField.getText().length() >= 1) {
                searchResultArea.clear();

                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT title FROM books " +
                        "WHERE title LIKE '" + searchBookTextField.getText() + "%' ORDER BY title DESC;");

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
    public void signOut(ActionEvent event) throws IOException {
        LoginController.isLoggedIn = false;
        LoginController.user = null;
        System.out.println("You have successfully signed out.");
        changeScene(event);
    }

    @FXML
    public void changeScene(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        if (event.getSource().toString().contains("logInBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("signUpBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("infoBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("info.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("optionBtn") || event.getSource().toString().contains("optionBtn2") || event.getSource().toString().contains("optionBtn3")) {
            Parent root = FXMLLoader.load(getClass().getResource("changeOptionSample.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("editInfoBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("UserEditInformation.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("editBooksBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("BookEditInformation.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("viewBookListBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("BooklistA-ZController.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("addBookBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("EmployerMenu.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        } else if (event.getSource().toString().contains("signOutBtn")) {
            Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

}


