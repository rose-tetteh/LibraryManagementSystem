package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Book dao test.
 */
class BookDAOTest {
    private static BookDAO bookDAO;
    private static Book testBook;

    /**
     * Sets up.
     */
    @BeforeAll
    static void setUp() {
        DatabaseConnection.setTestMode(true);
        Connection connection = DatabaseConnection.getConnection();
        bookDAO = new BookDAO();
        DatabaseConnectionTest.setupSchema(connection);
    }

    /**
     * Sets up test data.
     */
    @BeforeEach
    void setUpTestData() {
        testBook = new Book.BookBuilder()
                .title("Test Book")
                .numberOfCopies(5)
                .author("Test Author")
                .genre("Fiction")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2023, 1, 1))
                .statusOfBookAvailability(Status.available)
                .build();
    }

    /**
     * Given valid book when add book then book is added successfully.
     * Verify the book was added by searching for it
     */
    @Test
    @DisplayName("Test Add Book")
    void given_ValidBook_when_AddBook_then_BookIsAddedSuccessfully() {
        assertDoesNotThrow(() -> bookDAO.addResource(testBook));

        List<Book> books = bookDAO.getAllResources();
        boolean bookFound = books.stream()
                .anyMatch(b -> b.getTitle().equals(testBook.getTitle()) &&
                        b.getAuthor().equals(testBook.getAuthor()) &&
                        b.getIsbn().equals(testBook.getIsbn()));
        assertTrue(bookFound);
    }

    /**
     * Given book with maximum title length when add book then handle successfully.
     * Verify the book was added with correct title
     */
    @Test
    @DisplayName("Test Add Book With Maximum Title Length")
    void givenBookWithMaximumTitleLength_whenAddBook_thenHandleSuccessfully() {
        String longTitle = "A".repeat(255);
        Book bookWithLongTitle = new Book.BookBuilder()
                .title(longTitle)
                .numberOfCopies(1)
                .author("Test Author")
                .genre("Fiction")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2023, 1, 1))
                .statusOfBookAvailability(Status.available)
                .build();

        assertDoesNotThrow(() -> bookDAO.addResource(bookWithLongTitle));

        List<Book> books = bookDAO.getAllResources();
        boolean bookFound = books.stream()
                .anyMatch(b -> b.getTitle().equals(longTitle));
        assertTrue(bookFound);
    }

    /**
     * Given existing book id when get resource by id then return correct book.
     */
    @Test
    @DisplayName("Test Get Book By Id")
    void givenExistingBookId_whenGetResourceById_thenReturnCorrectBook() {
        // Add book and get its ID from the list of all books
        bookDAO.addResource(testBook);
        List<Book> books = bookDAO.getAllResources();
        Book addedBook = books.stream()
                .filter(b -> b.getTitle().equals(testBook.getTitle()) &&
                        b.getAuthor().equals(testBook.getAuthor()) &&
                        b.getIsbn().equals(testBook.getIsbn()))
                .findFirst()
                .orElseThrow();

        Book retrievedBook = bookDAO.getResourceById(addedBook.getResourceId());

        assertNotNull(retrievedBook);
        assertEquals(addedBook.getResourceId(), retrievedBook.getResourceId());
        assertEquals(addedBook.getTitle(), retrievedBook.getTitle());
    }

    /**
     * Given multiple books when get all resources then return all books.
     */
    @Test
    @DisplayName("Test Get All Books")
    void given_MultipleBooks_when_GetAllResources_then_ReturnAllBooks() {
        Book secondBook = new Book.BookBuilder()
                .title("Second Test Book")
                .numberOfCopies(3)
                .author("Another Author")
                .genre("Non-Fiction")
                .isbn("0987654321")
                .publicationDate(LocalDate.of(2023, 2, 1))
                .statusOfBookAvailability(Status.available)
                .build();

        bookDAO.addResource(testBook);
        bookDAO.addResource(secondBook);

        List<Book> books = bookDAO.getAllResources();
        assertFalse(books.isEmpty());
        assertTrue(books.size() >= 2);
    }


    /**
     * Given existing book when delete resource then book is deleted successfully.
     */
    @Test
    @DisplayName("Test Delete Book")
    void givenExistingBook_whenDeleteResource_thenBookIsDeletedSuccessfully() {
        // Add book and get its ID
        bookDAO.addResource(testBook);
        List<Book> books = bookDAO.getAllResources();
        Book addedBook = books.stream()
                .filter(b -> b.getTitle().equals(testBook.getTitle()))
                .findFirst()
                .orElseThrow();

        assertDoesNotThrow(() -> bookDAO.deleteResource(addedBook.getResourceId()));
        assertThrows(RuntimeException.class, () -> bookDAO.getResourceById(addedBook.getResourceId()));
    }

    /**
     * Given non-existent book id when get resource by id then throw exception.
     */
    @Test
    @DisplayName("Test Get Non-existent Book")
    void givenNonExistentBookId_whenGetResourceById_thenThrowException() {
        assertThrows(RuntimeException.class, () -> bookDAO.getResourceById(-1));
    }

    /**
     * Given book with special characters when add book then handle successfully.
     */
    @Test
    @DisplayName("Test Add Book With Special Characters")
    void givenBookWithSpecialCharacters_whenAddBook_thenHandleSuccessfully() {
        Book bookWithSpecialChars = new Book.BookBuilder()
                .title("Test Book #1 & Special Characters !@#$%")
                .numberOfCopies(1)
                .author("Author's Name-With-Hyphens")
                .genre("Fiction/Fantasy & Sci-Fi")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2023, 1, 1))
                .statusOfBookAvailability(Status.available)
                .build();

        assertDoesNotThrow(() -> bookDAO.addResource(bookWithSpecialChars));

        List<Book> books = bookDAO.getAllResources();
        boolean bookFound = books.stream()
                .anyMatch(b -> b.getTitle().equals(bookWithSpecialChars.getTitle()) &&
                        b.getAuthor().equals(bookWithSpecialChars.getAuthor()));
        assertTrue(bookFound);
    }

    /**
     * Given book with duplicate isbn when add book then throw exception.
     */
    @Test
    @DisplayName("Test Add Book With Duplicate ISBN")
    void given_BookWithDuplicateISBN_when_AddBook_then_ThrowException() {
        bookDAO.addResource(testBook);

        Book duplicateIsbnBook = new Book.BookBuilder()
                .title("Different Title")
                .numberOfCopies(1)
                .author("Different Author")
                .genre("Fiction")
                .isbn(testBook.getIsbn())
                .publicationDate(LocalDate.of(2023, 1, 1))
                .statusOfBookAvailability(Status.available)
                .build();

        assertThrows(RuntimeException.class, () -> bookDAO.addResource(duplicateIsbnBook));
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        try {
            List<Book> books = bookDAO.getAllResources();
            for (Book book : books) {
                bookDAO.deleteResource(book.getResourceId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}