package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController extends SQLConnector implements Initializable {


    @FXML
    private TextArea ViewCart;
    @FXML
    private TextArea OrderEmail;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        ViewCart.setVisible(false);
        OrderEmail.setVisible(false);

    }


    @FXML
    public void ViewList() {
        ViewCart.clear();
        ViewCart.appendText(String.valueOf(cart));
        OrderEmail.appendText("Place an order on: " + Email.get(0));

        ViewCart.setVisible(true);
        OrderEmail.setVisible(true);
    }

    @FXML
    public void CheckOut(ActionEvent event) throws IOException {

        if (cart.size() > 0) {

            do {
                ReduceQuantity();
            }
            while (bookID.size() > 0);

            OrderOnEmail();
            GetOrderId();

            do {
                checkOut();
            }
            while (IdBook.size() > 0);

            ViewCart.clear();
            ViewCart.appendText(String.valueOf(cart));
            cart.clear();


            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

            stage.setScene(new Scene(root));
            stage.show();


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Nothing in cart");
            alert.setTitle("Cart empty");
            alert.setContentText("No book in cart");
            alert.show();
        }
    }

    @FXML
    public void ContinueShopping(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("Store.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }
}

