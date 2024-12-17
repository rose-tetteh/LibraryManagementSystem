package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.daos.BookDAO;
import com.example.librarymanagementsystem.daos.JournalDAO;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Journal;
import com.example.librarymanagementsystem.model.LibraryResource;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.JournalService;
import com.example.librarymanagementsystem.utils.ViewLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class BookControllerView1 {
    @FXML
    public Button transactionView;
    @FXML
    public Button reservationView;
    @FXML
    public Circle profileImage;
    @FXML
    private HBox dashBox;

    @FXML
    public VBox sidebar;

    @FXML
    public Button bookView; // For Books button

    @FXML
    private ComboBox<String> bookOrJournalCombo; // Dropdown for selecting Book/Journal

    @FXML
    private TextField searchField; // Search bar for titles

    @FXML
    private TableView<Object> bookTable; // TableView for displaying books/journals

    @FXML
    private TableColumn<Object, String> idColumn, titleColumn, authorColumn, genreColumn, isbnColumn, publicationDateColumn, copiesColumn, statusColumn;

    private BookDAO bookDAO;
    private JournalDAO journalDAO;
    private final BookService bookService = new BookService(bookDAO);
    private final JournalService journalService = new JournalService(journalDAO);
    private final ObservableList<Object> data = FXCollections.observableArrayList();

    public BookControllerView1(BookDAO bookDAO, JournalDAO journalDAO) {
        this.bookDAO = bookDAO;
        this.journalDAO = journalDAO;
    }

    @FXML
    public void initialize() {
        // Set up the ComboBox and TableView columns
        bookOrJournalCombo.setValue("Books");
        setUpTableColumns();

        // Load books by default
        loadBooks();

        // Add a listener for the ComboBox selection
        bookOrJournalCombo.valueProperty().addListener((observable, oldValue, newValue) -> handleCategorySelection());

        // Add a listener to the search field
        searchField.setOnKeyReleased(this::handleSearch);
    }


    @FXML
    private void handleDashboardView() {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-dashboard-view.fxml","Patron Dashboard", stage);
        } catch (Exception e) {
            showError("Error loading Dashboard view", e);
        }
    }

    @FXML
    private void handleBookView() {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-book-view.fxml","Books", stage);
        } catch (Exception e) {
            showError("Error loading Book view", e);
            e.printStackTrace(); // Add this to see more detailed error information
        }
    }

    @FXML
    private void handleTransactionView() {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-transaction-view.fxml","Approved Transactions", stage);
        } catch (Exception e) {
            showError("Error loading Transaction view", e);
        }
    }

    @FXML
    private void handleReservationView() {
        try {
            Stage stage = (Stage) dashBox.getScene().getWindow();
            ViewLoader.load("patron-reservation-view.fxml","Reservations", stage);
        } catch (Exception e) {
            showError("Error loading Reservation view", e);
        }
    }

    private void setUpTableColumns() {
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getClass().getSimpleName() + "-" + ((LibraryResource) param.getValue()).getResourceId()));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        statusColumn.setCellValueFactory(param -> {
            String status = param.getValue() instanceof Book
                    ? ((Book) param.getValue()).getNumberOfCopies() > 0 ? "Available" : "Unavailable"
                    : ((Journal) param.getValue()).getNumberOfCopies() > 0 ? "Available" : "Unavailable";

            return Bindings.createStringBinding(() -> status);
        });
    }

    @FXML
    private void handleCategorySelection() {
        String selectedCategory = bookOrJournalCombo.getValue();

        if ("Books".equals(selectedCategory)) {
            loadBooks();
        } else if ("Journals".equals(selectedCategory)) {
            loadJournals();
        }
    }

    private void loadBooks() {
        List<Book> books = bookService.getAllBooks();  // Assuming this method fetches all books from the DB
        data.clear();
        data.addAll(books);
        bookTable.setItems(data);
    }

    private void loadJournals() {
        List<Journal> journals = journalService.getAllJournals();  // Assuming this method fetches all journals from the DB
        data.clear();
        data.addAll(journals);
        bookTable.setItems(data);
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String searchQuery = searchField.getText().toLowerCase();
        ObservableList<Object> filteredData = FXCollections.observableArrayList();

        // Filter books or journals based on the search query
        if ("Books".equals(bookOrJournalCombo.getValue())) {
            filteredData.addAll(data.stream()
                    .filter(item -> ((Book) item).getTitle().toLowerCase().contains(searchQuery))
                    .toList());
        } else {
            filteredData.addAll(data.stream()
                    .filter(item -> ((Journal) item).getTitle().toLowerCase().contains(searchQuery))
                    .toList());
        }

        bookTable.setItems(filteredData);
    }

    private void showError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }
}
