package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Customer2Controller extends SQLConnector implements Initializable {
   // private CartArrayList shoppingList = new CartArrayList();

    ArrayList<String> cart = new ArrayList<>();

    @FXML
    private TextField searchBookTextField;
    @FXML
    private TextArea searchResultArea;
    @FXML
    private TextField BookId;
    @FXML
    private TextField Remove;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        searchBookTextField.setOnKeyReleased(keyEvent -> relevantSearchMethod());

    }
    SQLConnector sql = new SQLConnector();

    //for book search
    private void relevantSearchMethod() {
        try {

            if (searchBookTextField.getText().length() >= 2) {
                searchResultArea.clear();

                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT title, bookid FROM books " +
                        "WHERE title LIKE '%" + searchBookTextField.getText() + "%';");

                while (resultSet.next()) {
                    searchResultArea.appendText(resultSet.getString(1) + "         ID: " + resultSet.getInt(2) + "\n");
                }
            } else
                searchResultArea.clear();

        } catch (SQLException sq) {
            sq.printStackTrace();
            System.out.println(sq.getMessage());
        }
    }


    @FXML
    public void LogOut(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    public void CheckOut(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void Info2(ActionEvent event) throws IOException {
        disconnect();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("info2.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML

    public void AddToCart() {

        String bookid = BookId.getText();
        boolean validated = sql.BookAvailability(bookid);

        if (validated) {
            BookId.clear();
            //System.out.println("Id valid.");
            //  System.out.println(bookid);

        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Id not valid.");
            alert.setTitle("Error");
            alert.setContentText("Book not found. Please try again.");
            alert.show();
            BookId.clear();


        }


    }

    @FXML
    public void RemoveBooksFromCart() {


        String id = Remove.getText();

        boolean validated = sql.RemoveFromCart(id);

        if (validated) {
            BookId.clear();
            System.out.println("Book is in cart.");

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Id not valid.");
            alert.setTitle("Error");
            alert.setContentText("Book could not be removed. Please try again.");
            alert.show();
            BookId.clear();


        }
    }
    @FXML
    public void Check(){

        for (int j = 0; j < bookID.size(); j++)
            for (int i = 0; i < bookID.size(); i++)

                CheckOut();

    }



    @FXML
    public void ViewCart(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ViewCart.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }
}
