package sample;


import java.sql.*;
import java.util.ArrayList;


public class SQLConnector {

    /*TODO WARNING***********************************************************************
      TODO MUST GET SQL JAR OR SOMETHING TO CONNECT TO DATABASE INTO LIBRARY
      TODO********************************************************************************************
     */
    private String url = "jdbc:mysql://den1.mysql6.gear.host:3306/hkrbookstore?user=hkrbookstore&password=Ed5!_3nNLzwI&serverTimezone=UTC";

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

    public void sqlQuery() {
        try {
            statement = connection.createStatement();

            resultSet = statement.executeQuery(""); // enter the SQL query here

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)); //int is example, specify here what type to get
            }


        } catch (SQLException sq) {
            sq.printStackTrace();
            System.out.println(sq.getMessage());
        }
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
                Book book = new Book(null, title, author, genre, quantity, price);
                bookHold.add(book);
                System.out.print("BOOK ADDED: [ID " + id);
                System.out.print(", TITLE " + title);
                System.out.print(", AUTHOR " + author);
                System.out.print(", GENRE " + genre);
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

    public void RemoveBookFromCart(int bookId) {
        connect();
        try {
            String sql = "INSERT INTO `books` (`bookid`, `title`, `author`, `genre`, `quantity`, `price`) VALUES (NULL, '" + bookId + "');";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }


    public void removeBook(Book book) {
        connect();

        try {
            String sql = "DELETE FROM `books` WHERE `books`.`bookid` = " + book.getId();
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public void changeUserInfo(User user) {
        connect();
        try {

            String sql = "UPDATE users SET firstname = '" + user.getFirstname() + "', lastname = ' " + user.getLastname() + "', address = ' + " + user.getAddress() + "', city = '" + user.getCity() + "', zip = '" + user.getZip() + "', state = '" + user.getState() + "', country = '" + user.getCountry() + "' WHERE email = '+" + user.getEmail() + "';";

            statement = connection.createStatement();
            statement.executeUpdate(sql);


            System.out.println("Information successfully edited.");

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

}
