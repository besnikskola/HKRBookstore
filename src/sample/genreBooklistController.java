package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class genreBooklistController extends StoreController implements Initializable {

    @FXML private ListView<String> genreView;
    @FXML private TextArea textAreaGenre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreView.getItems().addAll("Comedy",
                "Fantasy",
                "History",
                "Novel",
                "Philosophy",
                "Poetry",
                "Tragedy");

        genreView.setOnMouseClicked( event -> getGenres());

    }

    private void getGenres(){
        textAreaGenre.clear();
        String genre = genreView.getSelectionModel().getSelectedItem();
        System.out.println(genre);

        for (Book b:arrListBooks){
            if (b.getGenre().equals(genre)) {
                textAreaGenre.appendText(b.getTitle() + " " + b.getAuthor() + "\n");
            }
        }


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