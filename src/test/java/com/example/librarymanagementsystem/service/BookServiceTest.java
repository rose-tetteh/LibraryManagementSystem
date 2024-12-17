package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.BookDAO;
import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static Connection connection;

    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    private BookService bookService;

    private Book testBook;

    @BeforeAll
    static void beforeAll() {
        DatabaseConnection.setTestMode(true);
        connection = DatabaseConnection.getConnection();
        DatabaseConnectionTest.setupSchema(connection);
    }

    @BeforeEach
    void setUp() {
        testBook = new Book.BookBuilder()
                .resourceId(1)
                .title("Test Book")
                .author("John Doe")
                .isbn("1234567890")
                .genre("Fiction")
                .numberOfCopies(5)
                .publicationDate(LocalDate.of(2023, 1, 1))
                .statusOfBookAvailability(Status.available)
                .build();
        bookService = new BookService(bookDAO);
    }

    @Test
    @DisplayName("Given existing books when getAllBooks is called then return book list")
    void givenExistingBooks_whenGetAllBooks_thenReturnBookList() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(testBook);
        when(bookDAO.getAllResources()).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.getAllBooks();

        // Assert
        assertNotNull(actualBooks);
        assertEquals(1, actualBooks.size());
        assertEquals(expectedBooks, actualBooks);
        verify(bookDAO).getAllResources();
    }

    @Test
    @DisplayName("Given a valid book when addBook is called then add book successfully")
    void givenValidBook_whenAddBook_thenAddBookSuccessfully() {
        // Arrange
        doNothing().when(bookDAO).addResource(testBook);

        // Act
        assertDoesNotThrow(() -> bookService.addBook(testBook));

        // Assert
        verify(bookDAO).addResource(testBook);
    }

    @ParameterizedTest
    @DisplayName("Given different book update scenarios")
    @CsvSource({
            "1, Test Book, true",
            "2, Another Book, false"
    })
    void givenBookToUpdate_whenUpdateBook_thenVerifyUpdateOperation(int resourceId, String title) {
        // Arrange
        Book bookToUpdate = new Book.BookBuilder()
                .resourceId(resourceId)
                .title(title)
                .build();
        doNothing().when(bookDAO).updateResource(bookToUpdate);

        // Act
        assertDoesNotThrow(() -> bookService.updateBook(bookToUpdate));

        // Assert
        verify(bookDAO).updateResource(bookToUpdate);
    }

    @Test
    @DisplayName("Given a book ID when getBookById is called then return book")
    void givenBookId_whenGetBookById_thenReturnBook() throws SQLException {
        // Arrange
        int bookId = 1;
        when(bookDAO.getResourceById(bookId)).thenReturn(testBook);

        // Act
        Book retrievedBook = bookService.getBookById(bookId);

        // Assert
        assertNotNull(retrievedBook);
        assertEquals(testBook, retrievedBook);
        verify(bookDAO).getResourceById(bookId);
    }

    @Test
    @DisplayName("Given a book ID when deleteBook is called then delete book successfully")
    void givenBookId_whenDeleteBook_thenDeleteBookSuccessfully() {
        // Arrange
        int bookId = 1;
        doNothing().when(bookDAO).deleteResource(bookId);

        // Act
        assertDoesNotThrow(() -> bookService.deleteBook(bookId));

        // Assert
        verify(bookDAO).deleteResource(bookId);
    }

    @Test
    @DisplayName("Given a non-existent book ID when getBookById is called then throw SQLException")
    void givenNonExistentBookId_whenGetBookById_thenThrowSQLException() throws SQLException {
        // Arrange
        int nonExistentBookId = 999;
        when(bookDAO.getResourceById(nonExistentBookId)).thenThrow(new SQLException("Book not found"));

        // Act & Assert
        assertThrows(SQLException.class, () -> bookService.getBookById(nonExistentBookId));
        verify(bookDAO).getResourceById(nonExistentBookId);
    }
}