/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanager.ui.listbook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import librarymanager.Database.DatabaseHandler;
import librarymanager.ui.addbook.AddbookController;

/**
 * FXML Controller class
 *
 * @author Kwesi-Welbred
 */
public class DisplaybooksController implements Initializable {
    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        initCol();
        
        loadData();
    }    
//initializing the table
    private void initCol() {
          titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
          idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
          authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
          publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
          availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }
//loading the database into the tableview
    private void loadData() {
        DatabaseHandler handler = new DatabaseHandler();
         String qu = "select title form BOOK";
        ResultSet rs = handler.execQuery(qu);
        try {
            while(rs.next()){
                String Title = rs.getString("title");  //the getString arguments should correspond the database fields name
                String id = rs.getString("id");  
                String author = rs.getString("author");  
                String publisher = rs.getString("publisher");
                Boolean Availability = rs.getBoolean("isAvail");
        //loading... the data into the list        
                list.add(new Book(Title, id, author, publisher, Availability));
                
            }   } catch (SQLException ex) {
            Logger.getLogger(AddbookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Associate the table list by passing the data into the tableView
        tableView.getItems().setAll(list);
    }
    //the class book

    /**
     *
     */
   public static class Book{
    private final SimpleStringProperty title;
    private final SimpleStringProperty id;
    private final SimpleStringProperty author;
    private final SimpleStringProperty publisher;
    private final SimpleBooleanProperty availability;
    //constructor
    Book(String title, String id, String author, String pub, Boolean avail ){
    this.title = new SimpleStringProperty(title);
    this.id = new SimpleStringProperty(id);
    this.author = new SimpleStringProperty(author);
    this.publisher = new SimpleStringProperty(pub);
    this.availability = new SimpleBooleanProperty(avail);
    
    }
    //Getters
        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailability() {
            return availability.get();
        }
    
    
    
    }
    
}
