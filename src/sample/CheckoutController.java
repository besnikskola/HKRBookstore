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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        ViewCart.setVisible(false);

    }


@FXML
public void ViewList() {
        ViewCart.clear();
    ViewCart.appendText(String.valueOf(cart));

    ViewCart.setVisible(true);
}

    @FXML
    public void CheckOut() {

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


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Nothing in cart");
            alert.setTitle("Cart empty");
            alert.setContentText("No book in cart");
            alert.show();
        }
    }
}

