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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class genreBooklistController extends StoreController implements Initializable {

    @FXML private TableView<Book> genreBookTable;
    @FXML private TableColumn<Book, String> genreTitleCol = new TableColumn<>();
    @FXML private TableColumn<Book, String> genreAuthorCol = new TableColumn<>();
    @FXML private TableColumn<Book, Integer> genreQuantityCol = new TableColumn<>();
    @FXML private TableColumn<Book, Double> genrePriceCol = new TableColumn<>();
    @FXML private TableColumn<Book, Integer> genreIdCol = new TableColumn<>();

    private ArrayList <Book> genreBookArray = new ArrayList<>(arrListBooks);

    @FXML private ListView<String> genreView;

    private void genreFilter(){
        genreBookArray.sort(Comparator.comparing(Book::getTitle));
    }

    private ObservableList<Book> obsBooks () {
        ObservableList <Book> books =FXCollections.observableArrayList();

        String genre = genreView.getSelectionModel().getSelectedItem();
        System.out.println(genre);

        for (Book b:genreBookArray) {
            if (b.getGenre().equals(genre))
            books.add(new Book(b.getId() ,b.getTitle(), b.getAuthor(),b.getGenre(), b.getQuantity(), b.getPrice()));
        }

        return books;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        genreFilter();

        genreView.getItems().addAll("Comedy",
                "Fantasy",
                "History",
                "Novel",
                "Philosophy",
                "Poetry",
                "Tragedy");

        genreView.setOnMouseClicked( event -> {
            genreTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            genreAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
            genreQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            genrePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            genreIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            genreBookTable.setItems(obsBooks());
        });


    }


    @FXML
    public void AZ(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("BooklistA-ZController.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void Back1(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Genre.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }
}