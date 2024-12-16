package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Librarian dao test.
 */
class LibrarianDAOTest {
    private static LibrarianDAO librarianDAO;
    private static Librarian librarian, addedLibrarian;

    /**
     * Sets up.
     * Enable test mode
     * Get test connection
     * Initialize DAO
     * Setup test database schema
     * Add librarian to database for tests
     */
    @BeforeAll
    static void setUp() {
        DatabaseConnection.setTestMode(true);
        Connection connection = DatabaseConnection.getConnection();

        librarianDAO = new LibrarianDAO();
        DatabaseConnectionTest.setupSchema(connection);

        librarian = new Librarian.LibrarianBuilder()
                .userId(1)
                .username("johndoe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .password("123yes")
                .build();
        addedLibrarian = librarianDAO.addLibrarian(librarian);
    }

    /**
     * Given librarian when add librarian then return librarian.
     */
    @Test
    @DisplayName("Test Add Librarian")
    void givenLibrarian_whenAddLibrarian_thenAddSuccessfully() {
        assertEquals(librarian, addedLibrarian);
    }

    /**
     * Test add and retrieve librarian.
     */
    @Test
    @DisplayName("Add Librarian and Retrieve")
    void testRetrieveAddedLibrarian() {
        Optional<Librarian> retrievedLibrarian = librarianDAO.getLibrarianByUserId(1);

        assertTrue(retrievedLibrarian.isPresent());
        assertEquals(1, retrievedLibrarian.get().getUserId());
        assertEquals("johndoe", retrievedLibrarian.get().getUsername());
    }

    /**
     * Given librarian with some null attributes when add librarian then throw runtime exception.
     */
    @Test
    @DisplayName("Add Librarian with some null values")
    void givenLibrarianWithSomeNullAttributes_whenAddLibrarian_thenThrowRuntimeException (){
        Librarian newLibrarian = new Librarian.LibrarianBuilder()
                .userId(3)
                .email("johnny@example.com")
                .phoneNumber("1233757890")
                .build();
        assertThrows(RuntimeException.class, () -> librarianDAO.addLibrarian(newLibrarian));
    }

    /**
     * Given new librarian with an already existing when add librarian return entity already exists exception.
     *
     * @param userId      the user id
     * @param username    the username
     * @param email       the email
     * @param phoneNumber the phone number
     * @param password    the password
     */
    @ParameterizedTest
    @DisplayName("Add Librarian with an ID already in use")
    @CsvSource({
            "1, janeDoe, jane@example.com, 0987654321, 456-Mock"
    })
    void givenNewLibrarianWithAnAlreadyExisting_whenAddLibrarian_returnEntityAlreadyExistsException(int userId, String username, String email,
                                                                                              String phoneNumber, String password) {
        Librarian newLibrarian = new Librarian.LibrarianBuilder()
                .userId(userId)
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        assertThrows(RuntimeException.class, () -> librarianDAO.addLibrarian(newLibrarian));

    }

    /**
     * Test get librarian by email.
     */
    @Test
    @DisplayName("Get Librarian by Email")
    void testGetLibrarianByEmail() {
        Optional<Librarian> retrievedLibrarian = librarianDAO.getLibrarianByEmail("john@example.com");

        assertTrue(retrievedLibrarian.isPresent());
        assertEquals("john@example.com", retrievedLibrarian.get().getEmail());
    }

    /**
     * Test get all librarians.
     */
    @Test
    @DisplayName("Get All Librarians")
    void testGetAllLibrarians() {
        Librarian newLibrarian = new Librarian.LibrarianBuilder()
                .userId(2)
                .username("janedoe")
                .email("jane@example.com")
                .phoneNumber("0987654321")
                .password("1245no")
                .build();

        librarianDAO.addLibrarian(newLibrarian);

        // Retrieve all librarians
        List<Librarian> librarians = librarianDAO.getAllLibrarians();

        // Assertions
        assertTrue(librarians.size() >= 2);
    }

    /**
     * Test update librarian.
     */
    @Test
    @DisplayName("Update Librarian")
    void testUpdateLibrarian() {
        Librarian updatedLibrarian = new Librarian.LibrarianBuilder()
                .username("johnupdated")
                .email("john.updated@example.com")
                .phoneNumber("9876543210")
                .build();

        boolean updateResult = librarianDAO.updateLibrarian(2, updatedLibrarian);

        Optional<Librarian> retrievedLibrarian = librarianDAO.getLibrarianByUserId(2);

        assertTrue(updateResult);
        assertTrue(retrievedLibrarian.isPresent());
        assertEquals("johnupdated", retrievedLibrarian.get().getUsername());
        assertEquals("john.updated@example.com", retrievedLibrarian.get().getEmail());
    }

    /**
     * Test delete librarian.
     */
    @Test
    @DisplayName("Delete Librarian")
    void testDeleteLibrarian() {
        boolean deleteResult = librarianDAO.deleteLibrarian(1);

        Optional<Librarian> retrievedLibrarian = librarianDAO.getLibrarianByUserId(1);

        assertTrue(deleteResult);
        assertFalse(retrievedLibrarian.isPresent());
    }
}