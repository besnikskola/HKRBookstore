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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.html.ImageView;
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

    @FXML
    private Button bookid;

    @FXML
    private Button bookid2;

    @FXML
    private TextField addTextfield;

    @FXML
    private TextField removeTextfield;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button checkoutButton;

    @FXML
    private Button ShoppingList;

    @FXML
    private TextArea listTextarea;

    @FXML
    private TextArea top5Textarea;

    @FXML
    private Button sellingBooksBtn;

    static ArrayList<Book> arrListBooks = new ArrayList<Book>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookid.setVisible(false);
        bookid2.setVisible(false);
        addTextfield.setVisible(false);
        removeTextfield.setVisible(false);
        addButton.setVisible(false);
        removeButton.setVisible(false);
        checkoutButton.setVisible(false);
        ShoppingList.setVisible(false);
        listTextarea.setVisible(false);
        top5Textarea.setVisible(false);
        connect();
        arrListBooks = loadBooks();
        searchBookTextField.setOnKeyReleased(keyEvent -> relevantSearchMethod());
        if (LoginController.isLoggedIn) {
            System.out.println("Is logged in as: " + LoginController.user.toString());
            signUpBtn.setVisible(false);
            logInBtn.setVisible(false);
            top5Textarea.setVisible(false);
            sellingBooksBtn.setVisible(false);
            signOutBtn.setVisible(true);
            editInfoBtn.setVisible(true);
            bookid.setVisible(true);
            bookid2.setVisible(true);
            addTextfield.setVisible(true);
            removeTextfield.setVisible(true);
            addButton.setVisible(true);
            removeButton.setVisible(true);
            ShoppingList.setVisible(true);
            listTextarea.setVisible(true);

            if (LoginController.user.getIsEmployee()) {
                editBooksBtn.setVisible(true);
                addBookBtn.setVisible(true);
            }
        } else {
            System.out.println("Is not logged in.");
        }

    }

    SQLConnector sql = new SQLConnector();

    //for book search
    private void relevantSearchMethod() {
        try {

            if (searchBookTextField.getText().length() >= 1) {
                searchResultArea.clear();

                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT title, price ,bookid  FROM books " +
                        "WHERE title LIKE '" + searchBookTextField.getText() + "%' ORDER BY title DESC;");

                while (resultSet.next()) {
                    searchResultArea.appendText(resultSet.getString(1) + "    Price: " + resultSet.getDouble(2) +
                            "   ID: " + resultSet.getInt(3) + "\n");
                }
            } else
                searchResultArea.clear();

        } catch (SQLException sq) {
            sq.printStackTrace();
            System.out.println(sq.getMessage());
        }
    }


    @FXML
    public void AddToCart() throws SQLException {

        String bookid = addTextfield.getText();
        boolean validated = sql.BookAvailability(bookid);

        if (validated) {
            addTextfield.clear();

            listTextarea.clear();
            listTextarea.appendText(String.valueOf(cart));
            checkoutButton.setVisible(true);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Id not valid.");
            alert.setTitle("Error");
            alert.setContentText("Book not found. Please try again.");
            alert.show();
            addTextfield.clear();


        }
    }

    @FXML
    public void RemoveBooksFromCart() {


        String id = removeTextfield.getText();

        boolean validated = sql.RemoveFromCart(id);

        if (validated) {
            System.out.println("Book is in cart.");
            listTextarea.clear();
            listTextarea.appendText(String.valueOf(cart));
            removeTextfield.clear();
            if (cart.size()==0){
                checkoutButton.setVisible(false);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Id not valid.");
            alert.setTitle("Error");
            alert.setContentText("Book could not be removed. Please try again.");
            alert.show();
            removeTextfield.clear();


        }

    }
    @FXML
    private void TopSellers(){
        FamousBooks();

        do {
           PrintFamousBooks();
        }
        while (FamousBooks.size() > 0);
        top5Textarea.clear();
        top5Textarea.appendText(String.valueOf(PrintTop5));


        sellingBooksBtn.setVisible(false);
        top5Textarea.setVisible(true);

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
        } else if (event.getSource().toString().contains("checkoutButton")) {
            Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

        }

    }
}


