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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookEditInformationController implements Initializable {

    LoginController lc = new LoginController();
    SQLConnector sql = new SQLConnector();

    @FXML
    private TextField bookIdTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextArea bookListTextArea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < StoreController.arrListBooks.size(); i++) {
            bookListTextArea.insertText(0, StoreController.arrListBooks.get(i).toString() + "\n");
        }
    }

    public void updateFields(KeyEvent event) {

        try {
            Book book = null;
            for (int i = 0; i < StoreController.arrListBooks.size(); i++) {
                if (String.valueOf(StoreController.arrListBooks.get(i).getId()).equals(bookIdTextField.getText())) {
                    book = StoreController.arrListBooks.get(i);
                }
            }
            assert book != null;
            genreTextField.setText(book.getGenre());
            titleTextField.setText(book.getTitle());
            authorTextField.setText(book.getAuthor());
            quantityTextField.setText(String.valueOf(book.getQuantity()));
            priceTextField.setText(String.valueOf(book.getPrice()));
            
        } catch (NullPointerException e) {
            System.out.println("This bookID summons nullpointerexception.");
            genreTextField.clear();
            titleTextField.clear();
            authorTextField.clear();
            quantityTextField.clear();
            priceTextField.clear();
        }
    }

    public void changeBook(ActionEvent event) {

        Alert alert = new Alert(null);
        try {

            Integer id = Integer.valueOf(bookIdTextField.getText());
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            String genre = genreTextField.getText();
            Integer quantity = Integer.valueOf(quantityTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());

            Book changedBook = new Book(id, title, author, genre, quantity, price);
            sql.editBookInfo(changedBook);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            System.out.println("Changed.");
            alert.setTitle("Successfully changed information");
            alert.setContentText("Your information has successfully been changed..");
            alert.show();

            seeStore(event);
        } catch (NumberFormatException e) {

            System.out.println("Error.");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred");
            alert.setContentText("Please insert correct types for each textfield.");
            alert.show();

        } catch (IOException e) {
            System.out.println("Error.");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred");
            alert.setContentText("Please insert correct types for each textfield.");
            alert.show();
            e.printStackTrace();
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
