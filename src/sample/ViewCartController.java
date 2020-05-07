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

public class ViewCartController implements Initializable {

    @FXML
    private TextField BookId2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    SQLConnector sql = new SQLConnector();



    @FXML
    public void CheckOutAgain(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void BackToShopping(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Customer2.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }
}