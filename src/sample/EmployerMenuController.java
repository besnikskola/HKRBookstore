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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployerMenuController implements Initializable {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField bookIdTextField;

    @FXML
    private TextField remQuantityTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField genreTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    SQLConnector sql = new SQLConnector();


    @FXML
    public void LogOut1(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void addBook() {
        Alert alert = new Alert(null);

        if (hasText()) {
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            String genre = genreTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());

            Book book = new Book (null, title, author, genre, quantity, price);


            sql.addBook(book);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("The book " + titleTextField.getText() + " has been added to the database!");
            alert.show();

            titleTextField.clear();
            authorTextField.clear();
            genreTextField.clear();
            quantityTextField.clear();
            priceTextField.clear();

        } else {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please make sure all of the text fields have text in them.");
            alert.show();
        }


    }

    public void removeBook() {
        int remId = Integer.parseInt(bookIdTextField.getText());
        Alert alert = new Alert(null);
        Book book = new Book(null, null, null, null, null, remId);
        book.setId(remId);

        if (!bookIdTextField.getText().isEmpty()) {
            sql.removeBook(book);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("The book with bookid " + bookIdTextField.getText() + " has been removed from the database!");
            alert.show();
            bookIdTextField.clear();


        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please make sure all of the text fields have text in them.");
            alert.show();
        }
    }


    public boolean hasText() {
        if (!titleTextField.getText().isEmpty() && !authorTextField.getText().isEmpty() && !genreTextField.getText().isEmpty() && !quantityTextField.getText().isEmpty() && !priceTextField.getText().isEmpty()) {

            System.out.println("All textfields have text.");
            return true;
        }

        System.out.println("They don't have text.");
        return false;
    }

}
