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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Customer2Controller extends SQLConnector implements Initializable {
    private CartArrayList shoppingList = new CartArrayList();

    @FXML
    private TextField searchBookTextField;
    @FXML
    private TextArea searchResultArea;
    @FXML
    private TextField BookId;
    @FXML
    private TextField QuantityOfBook;

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
            //create new user object  based on the column
            BookId.clear();
            System.out.println("Id valid.");

            System.out.println(bookid);
            shoppingList.add(bookid);

            System.out.println(shoppingList.toString());

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Id not valid.");
            alert.setTitle("Error");
            alert.setContentText("Invalid credentials. Please try again.");
            alert.show();
            BookId.clear();

        }


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
