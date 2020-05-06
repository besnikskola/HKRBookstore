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
        Alert alert = new Alert(null);


        if (!BookId.getText().isEmpty()) {
                int bookId = Integer.parseInt(BookId.getText());
                int quantityOfBook=Integer.parseInt(QuantityOfBook.getText());
                //sql.removeBook(bookId*quantityOfBook);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Added to cart");
                alert.setContentText("Book: " + BookId.getText() + " has been added to the cart ");
                alert.show();
                BookId.clear();


            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please make sure all of the text fields have text in them.");
                alert.show();

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
