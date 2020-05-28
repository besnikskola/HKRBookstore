package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class BooklistController extends StoreController implements Initializable {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> titleCol = new TableColumn<>();
    @FXML private TableColumn<Book, String> authorCol = new TableColumn<>();
    @FXML private TableColumn<Book, String> genreCol = new TableColumn<>();
    @FXML private TableColumn<Book, Integer> quantityCol = new TableColumn<>();
    @FXML private TableColumn<Book, Double> priceCol = new TableColumn<>();
    @FXML private TableColumn<Book, Integer> idCol = new TableColumn<>();
    @FXML private TextField idField;
    @FXML private Button id;
    @FXML private Button add;

    private ArrayList<Book> abcBookList = new ArrayList<>(arrListBooks);

    private void filter(){
        abcBookList.sort(Comparator.comparing(Book::getTitle));
    }

    private ObservableList<Book> obsBooks(){

        ObservableList<Book> books = FXCollections.observableArrayList();
        for (Book b: abcBookList) {
            books.add(new Book(b.getId() ,b.getTitle(), b.getAuthor(),b.getGenre(), b.getQuantity(), b.getPrice()));
        }
        return books;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setVisible(false);
        id.setVisible(false);
        add.setVisible(false);

        filter();

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        bookTable.setItems(obsBooks());


        if (LoginController.isLoggedIn) {
            idField.setVisible(true);
            id.setVisible(true);
            add.setVisible(true);
        }
    }
    @FXML
    public void jTableMouseClicked(){

        TablePosition pos = (TablePosition) bookTable.getSelectionModel().getSelectedCells().get(0);
        int index = pos.getRow();
        String selected = bookTable.getItems().get(index).toString();
        selected = selected.substring(9, selected.indexOf(","));
        System.out.println(selected);

        idField.setText(selected);



    }
    @FXML
    public void AddByAlphabeticallyOrder() throws SQLException {

        String bookid = idField.getText();
        boolean validated = sql.BookAvailability(bookid);

        if (validated) {
            idField.clear();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Id not valid.");
            alert.setTitle("Error");
            alert.setContentText("Book not found. Please try again.");
            alert.show();
            idField.clear();
        }


    }

    @FXML
    public void GenreList(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GenreBooklist.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void Back(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }
}