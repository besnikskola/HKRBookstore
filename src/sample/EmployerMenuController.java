package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Label remQuantityLabel;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private CheckBox remQuantityCheckBox;

    @FXML
    private TextArea bookListTextArea;

    @FXML
    private Button addBookBtn;

    @FXML
    private Button remBookBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < StoreController.arrListBooks.size(); i++) {
            bookListTextArea.insertText(0, StoreController.arrListBooks.get(i).toString() + "\n");
        }
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
    public void addBook(ActionEvent event) {
        Alert alert = new Alert(null);


        if (hasText(event)) {
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            String genre = genreTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());

            Book book = new Book(null, title, author, genre, quantity, price);
            int id = 0;

            for (int i = 0; i < StoreController.arrListBooks.size(); i++) {

                System.out.println("VALUE OF ID IS: " + id);

                if (id <= StoreController.arrListBooks.get(i).getId()) {
                    id = StoreController.arrListBooks.get(i).getId() + 1;
                    System.out.println("ID HAS BEEN CHANGED TO: " + id);
                }
            }

            book.setId(id);

            StoreController.arrListBooks.add(book);

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
            bookIdTextField.clear();
            bookListTextArea.clear();

            for (int i = 0; i < StoreController.arrListBooks.size(); i++) {
                bookListTextArea.insertText(0, StoreController.arrListBooks.get(i).toString() + "\n");
            }

        } else {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please make sure all of the text fields have text in them.");
            alert.show();
        }


    }

    @FXML
    public void removeBook(ActionEvent event) {
        Alert alert = new Alert(null);
        if (hasText(event)) {
            boolean removeEntireBook = true;

            if (remQuantityCheckBox.isSelected()) {
                removeEntireBook = false;
                remQuantityLabel.setVisible(true);
                remQuantityTextField.setVisible(true);
                System.out.println("Don't remove entire book.");
            }

            if (removeEntireBook) {
                try {
                    System.out.println("Remove entire book.");
                    int remId = Integer.parseInt(bookIdTextField.getText());

                    Book book = new Book(null, null, null, null, null, remId);
                    book.setId(remId);


                    if (!bookIdTextField.getText().isEmpty()) {
                        sql.removeBook(book);
                        for (int i = 0; i < StoreController.arrListBooks.size(); i++) {
                            if (StoreController.arrListBooks.get(i).getId() == remId) {
                                System.out.println("Book " + StoreController.arrListBooks.get(i).getTitle() + " with BookID: "
                                        + StoreController.arrListBooks.get(i).getId() + " has been removed from arrListBooks.");
                                StoreController.arrListBooks.remove(i);
                            }
                        }

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("The book with bookid " + bookIdTextField.getText() + " has been removed from the database!");
                        alert.show();
                        bookIdTextField.clear();
                        bookListTextArea.clear();

                        for (int i = 0; i < StoreController.arrListBooks.size(); i++) {
                            bookListTextArea.insertText(0, StoreController.arrListBooks.get(i).toString() + "\n");
                        }

                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please make sure all of the text fields have text in them.");
                        alert.show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Failure");
                    alert.setContentText("Please enter a number instead of text.");
                    alert.show();
                }
            } else {
                System.out.println("Remove only part.");
            }
        }
    }


    public boolean hasText(ActionEvent event) {
        if (event.getSource().toString().contains("addBookBtn")) {
            if (!titleTextField.getText().isEmpty() && !authorTextField.getText().isEmpty() && !genreTextField.getText().isEmpty() && !quantityTextField.getText().isEmpty() && !priceTextField.getText().isEmpty()) {

                System.out.println("All textfields have text.");
                return true;
            }
        } else if (event.getSource().toString().contains("remBookBtn")) {
            if (!bookIdTextField.getText().isEmpty()) {
                System.out.println("Book id field has text.");
                return true;
            }
        }

        System.out.println("They don't have text.");
        return false;
    }

}
