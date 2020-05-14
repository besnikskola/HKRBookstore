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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    private ArrayList<Book> abcBookList = new ArrayList<>(arrListBooks);

    private void filter(){
        abcBookList.sort(Comparator.comparing(Book::getAuthor));
    }

    private ObservableList<Book> obsBooks(){

        ObservableList<Book> books = FXCollections.observableArrayList();
        for (Book b: abcBookList) {
            books.add(new Book(null ,b.getTitle(), b.getAuthor(),b.getGenre(), b.getQuantity(), b.getPrice()));
        }
        return books;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filter();

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTable.setItems(obsBooks());

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