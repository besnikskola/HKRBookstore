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

    public void changeBook(ActionEvent event) throws IOException {
        Integer id = Integer.valueOf(bookIdTextField.getText());
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        String genre = genreTextField.getText();
        Integer quantity = Integer.valueOf(quantityTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());

        Book changedBook = new Book(id, title, author, genre, quantity, price);
        sql.editBookInfo(changedBook);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        System.out.println("Changed.");
        alert.setTitle("Successfully changed information");
        alert.setContentText("Your information has successfully been changed..");
        alert.show();

        seeStore(event);

    }


    public void seeStore(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

}
