package sample;


import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class SQLConnector {

    /*TODO WARNING***********************************************************************
      TODO MUST GET SQL JAR OR SOMETHING TO CONNECT TO DATABASE INTO LIBRARY
      TODO********************************************************************************************
     */
    private String url = "jdbc:mysql://den1.mysql6.gear.host:3306/hkrbookstore?user=hkrbookstore&password=Ed5!_3nNLzwI&serverTimezone=UTC";
    static ArrayList<String> cart = new ArrayList<>();
    static ArrayList<String> bookID = new ArrayList<>();
    static ArrayList<Integer> Quantity = new ArrayList<>();
    static ArrayList<String> Email = new ArrayList<>();
    static ArrayList<String> IdBook = new ArrayList<>();
    static ArrayList<String> OrderId = new ArrayList<>();
    static ArrayList<String> FamousBooks = new ArrayList<>();


    int NewQuantity;


    Connection connection;
    Statement statement;
    ResultSet resultSet;
    PreparedStatement preparedStatement;

    //use this to connect to the database
    public void connect() {
        try {
            System.out.println("Connection successfully established..");
            connection = DriverManager.getConnection(url);

        } catch (SQLException sq) {
            sq.printStackTrace();
            System.out.println(sq.getMessage());
        }
    }

    public boolean validCreatedUser(User user) {
        boolean isValid = false;
        try {
            connect();
            String sql = "SELECT email FROM users WHERE email = '" + user.getEmail() + "';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            String email = null;
            while (resultSet.next()) {
                email = resultSet.getString("email");
            }
            if (email == null) {
                isValid = true;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception.");
        }
        return isValid;
    }


    public void createUser(User user) {

        try {
            connect();
            String sql = "INSERT INTO `users` (`email`, `password`, `firstname`, `lastname`, `address`, `city`, `zip`, `state`, `country`, `isEmployee`) VALUES " +
                    "('" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getFirstname() + "', '" + user.getLastname() + "', '" + user.getAddress() + "', " +
                    "'" + user.getCity() + "', '" + user.getZip() + "', '" + user.getState() + "', '" + user.getCountry() + "', '0');";
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            System.out.println("User created: " + user.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean verifyLogin(String userIn, String pwIn) {
        boolean isVerified = false;
        try {
            connect();
            String sql = "SELECT email, password FROM users WHERE email = '" + userIn + "' AND password = '" + pwIn + "';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);


            while (resultSet.next()) {
                if (resultSet.getString("email").equals(userIn) && resultSet.getString("password").equals(pwIn)) {
                    System.out.println(resultSet.getString("email") + " is email");
                    System.out.println(resultSet.getString("password") + " is password");
                    isVerified = true;
                    Email.add(resultSet.getString("email"));
                }
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isVerified;
    }

    public User createUser(String email, String password) {
        User user = null;

        try {
            connect();
            String sql = "SELECT email, password, firstname, lastname, address, city, zip, state, country, isEmployee FROM users WHERE email = '" + email + "' AND password = '" + password + "';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString("email").equals(email) && resultSet.getString("password").equals(password)) {
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    String zip = resultSet.getString("zip");
                    String state = resultSet.getString("state");
                    String country = resultSet.getString("country");
                    boolean isEmployee = resultSet.getBoolean("isEmployee");
                    System.out.print("USER ADDED: [FIRSTNAME: " + firstName);
                    System.out.print(", LASTNAME: " + lastName);
                    System.out.print(", ADDRESS: " + address);
                    System.out.print(", CITY: " + city);
                    System.out.print(", ZIP: " + zip);
                    System.out.print(", STATE: " + state);
                    System.out.print(", COUNTRY: " + country);
                    System.out.println(", ISEMPLOYEE: " + isEmployee + "]");

                    user = new User(email, password, firstName, lastName, address, city, zip, state, country, isEmployee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<Book> loadBooks() {
        ArrayList<Book> bookHold = new ArrayList<>();
        try {
            connect();
            String sql = "SELECT * from BOOKS;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                int id = resultSet.getInt("bookid");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                Book book = new Book(id, title, author, genre, quantity, price);
                bookHold.add(book);
                System.out.print("BOOK ADDED: [ID: " + id);
                System.out.print(", TITLE: " + title);
                System.out.print(", AUTHOR: " + author);
                System.out.print(", GENRE: " + genre);
                System.out.print(", QUANTITY: " + quantity);
                System.out.println(" PRICE: " + price + "]");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookHold;
    }

    //simple way to see what books are currently in the database
    public void listBooks() {

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT title FROM books;");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
            System.out.println(sq.getMessage());
        }

    }


    public void addBook(Book book) {
        connect();

        try {
            String sql = "INSERT INTO `books` (`title`, `author`, `genre`, `quantity`, `price`) VALUES ('" + book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getGenre() + "', '" + book.getQuantity() + "', '" + book.getPrice() + "');";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }


    public void removeBook(boolean entireBook, Book book, int quantity) {
        try {
            connect();

            System.out.println("SQL: Remove entire book: " + entireBook);

            if (entireBook) {
                String sql = "DELETE FROM `books` WHERE `books`.`bookid` = " + book.getId();
                statement = connection.createStatement();
                statement.executeUpdate(sql);
                EmployerMenuController.removeSuccess = true;
                System.out.println("Status of removeSuccess: " + EmployerMenuController.removeSuccess);

            } else {
                String checkSql = "SELECT quantity FROM books WHERE bookid = " + book.getId() + ";";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(checkSql);

                int checker = 0;
                while (resultSet.next()) {
                    checker = resultSet.getInt("quantity");
                    checker = checker - quantity;
                    System.out.println("Value of checker: " + checker);
                }

                boolean moreThanZero = false;

                if (checker >= 0) {
                    moreThanZero = true;
                    System.out.println("Result is more than or equal to zero.");
                } else {
                    System.out.println("Result is NOT more than zero!");
                }

                if (moreThanZero) {
                    String removeSql = "UPDATE `books` SET `quantity` = `quantity` - " + quantity + " WHERE `bookid` = " + book.getId() + ";";
                    statement = connection.createStatement();
                    statement.executeUpdate(removeSql);

                    EmployerMenuController.removeSuccess = true;
                    System.out.println("Book quantity has been removed.");
                } else {
                    System.out.println("Result is not more than zero and thus you have to decrease by a different number.");
                }

            }
            disconnect();
        } catch (SQLIntegrityConstraintViolationException e) {
            EmployerMenuController.removeSuccess = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exists in another table");
            alert.setContentText("This book id exists in another table as a foreign key. It cannot be entirely removed from the database. Choose remove quantity for this book instead.");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserInfo(User user) {
        connect();
        try {

            preparedStatement = connection.prepareStatement("UPDATE users SET firstname = ?, lastname = ?, address = ?, city = ?, zip = ?, state = ?, country = ? WHERE email = ?;");
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getZip());
            preparedStatement.setString(6, user.getState());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setString(8, user.getEmail());

            int rowAffected = preparedStatement.executeUpdate();

            System.out.println("Customer information successfully updated. Total of rows changed: " + rowAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

    }

    public void editBookInfo(Book book) {
        connect();
        try {

            preparedStatement = connection.prepareStatement("UPDATE books SET title = ?, author = ?, genre = ?, quantity = ?, price = ? WHERE bookid = ?;");
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getQuantity());
            preparedStatement.setDouble(5, book.getPrice());
            preparedStatement.setInt(6, book.getId());

            int rowAffected = preparedStatement.executeUpdate();

            System.out.println("Book information successfully edited. Total of rows changed: " + rowAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

    }


    //disconnect from the database
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public boolean BookAvailability(String bookid) {
        boolean Available = false;
        try {
            connect();
            String sql = "SELECT bookid, quantity, title, price, author FROM books WHERE bookid = '" + bookid + "';";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Integer count1 = Collections.frequency(cart, "Book id: " + resultSet.getString("bookid") + "  '" + resultSet.getString("title") + "'  AUTHOR:" + resultSet.getString("author") + "  PRICE:" + resultSet.getDouble("price") + " sek" + "\n");
                NewQuantity = resultSet.getInt("quantity") - count1;
                System.out.println("Quantity left: " + NewQuantity);
                if (resultSet.getString("bookid").equals(bookid) && NewQuantity > 0) {
                    resultSet.getString("bookid");
                    resultSet.getString("title");
                    resultSet.getString("author");
                    resultSet.getDouble("price");


                    IdBook.add(bookid);
                    bookID.add(bookid);
                    cart.add("Book id: " + resultSet.getString("bookid") + "  '" + resultSet.getString("title") + "'  AUTHOR:" + resultSet.getString("author") + "  PRICE:" + resultSet.getDouble("price") + " sek" + "\n");
                    System.out.println(cart.contains("Book id: " + resultSet.getString("bookid") + "  '" + resultSet.getString("title") + "'  AUTHOR:" + resultSet.getString("author") + "  PRICE:" + resultSet.getDouble("price") + " sek" + "\n"));

                    for (int j = 0; j < cart.size(); j++)
                        System.out.println("Item " + j + ": " + cart.get(j));


                    Integer countA = Collections.frequency(cart, "Book id: " + resultSet.getString("bookid") + "  '" + resultSet.getString("title") + "'  AUTHOR:" + resultSet.getString("author") + "  PRICE:" + resultSet.getDouble("price") + " sek" + "\n");
                    System.out.println("Quantity: " + countA + ", added of the same book");

                    System.out.println("Quantity: " + resultSet.getInt("quantity"));
                    NewQuantity = resultSet.getInt("quantity") - countA;
                    System.out.println("New quantity: " + NewQuantity);
                    Quantity.add(NewQuantity);


                    Available = true;

                }
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Available;
    }


    public boolean RemoveFromCart(String bookid) {

        boolean InCart = false;
        try {
            connect();
            String sql = "SELECT bookid, title, quantity, price, author FROM books WHERE bookid = '" + bookid + "';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);


            while (resultSet.next()) {
                if (cart.contains("Book id: " + resultSet.getString("bookid") + "  '" + resultSet.getString("title") + "'  AUTHOR:" + resultSet.getString("author") + "  PRICE:" + resultSet.getDouble("price") + " sek" + "\n")) {
                    resultSet.getString("bookid");
                    resultSet.getString("title");
                    resultSet.getString("author");
                    resultSet.getDouble("price");


                    System.out.println("yes");
                    InCart = true;
                    bookID.add(bookid);
                    IdBook.remove(bookid);
                    cart.remove("Book id: " + resultSet.getString("bookid") + "  '" + resultSet.getString("title") + "'  AUTHOR:" + resultSet.getString("author") + "  PRICE:" + resultSet.getDouble("price") + " sek" + "\n");
                    System.out.println("new size: " + cart.size());
                    for (int j = 0; j < cart.size(); j++)
                        System.out.println("element " + j + ": " + cart.get(j));

                    NewQuantity = NewQuantity + 1;
                    System.out.println("New quantity: " + NewQuantity);
                    Quantity.add(NewQuantity);


                }
            }
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return InCart;
    }


    public void ReduceQuantity() {

        String BookId = bookID.get(0);
        int quantity = Quantity.get(0);
        String ConvertQuantity = Integer.toString(quantity);

        try {
            connect();
            String sql = "UPDATE books SET quantity='" + ConvertQuantity + "' WHERE bookid='" + BookId + "';";
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            bookID.remove(0);
            Quantity.remove(0);


            System.out.println(bookID);

            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void OrderOnEmail() {
        String email = Email.get(0);

        try {
            connect();
            String sql = "INSERT INTO `orders` (`email`) VALUES ('" + email + "');";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Order books on " + email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }


    public void GetOrderId() {
        String email = Email.get(0);

        try {
            connect();
            String sql = "SELECT * FROM orders WHERE orderid=(SELECT MAX(orderid) FROM orders WHERE email ='" + email + "');";

            // String sql = "SELECT orderid FROM orders WHERE email='" + email + "';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                resultSet.getString("orderid");
                OrderId.add(resultSet.getString("orderid"));
                System.out.println(resultSet.getString("orderid"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }


    public void checkOut() {
        String oderId = OrderId.get(0);
        String bookId = IdBook.get(0);

        try {
            connect();
            String sql = "INSERT INTO `orders_has_books` (`orderid`,`bookid`) VALUES ('" + oderId + "','" + bookId + "');";
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            IdBook.remove(0);
            System.out.println(IdBook);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();
    }


    public void FamousBooks() {

        try {
            connect();
            String sql = "SELECT OBH.bookid, B.title, count(*) BookQuantity FROM orders_has_books OBH JOIN Books B on OBH.bookid = B.bookid group by OBH.bookid order by BookQuantity desc, OBH.bookid asc limit 5;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                resultSet.getString("B.title");
                System.out.println(resultSet.getString("B.title"));
                FamousBooks.add(resultSet.getString("B.title") + "\n");
            }
            // System.out.println(FamousBooks);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String recoveredPassword(String email) {
        String pw = null;
        try {
            connect();
            String sql = "SELECT password FROM users WHERE email = '" + email + "';";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                pw = resultSet.getString("password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pw;
    }
}





