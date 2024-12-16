package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Patron dao test.
 */
class PatronDAOTest {
    private static PatronDAO patronDAO;
    private static Patron patron;

    /**
     * Sets up.
     * Enable test mode
     * Get test connection
     * Initialize DAO
     * Setup test database schema
     * Add patron to database for tests
     */
    @BeforeAll
    static void setUp() {
        DatabaseConnection.setTestMode(true);
        Connection connection = DatabaseConnection.getConnection();

        patronDAO = new PatronDAO();
        DatabaseConnectionTest.setupSchema(connection);

        patron = new Patron.PatronBuilder()
                .patronLibraryId("LIB-12345678-42")
                .username("johndoe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .address("123 New Street")
                .build();
    }

    /**
     * Given patron when add patron then return patron.
     */
    @Test
    @DisplayName("Test Add Patron")
    void givenPatron_whenAddPatron_thenReturnPatron(){
        assertEquals(patron, patronDAO.addPatron(patron));
    }

    /**
     * Test add and retrieve patron.
     */
    @Test
    @DisplayName("Add Patron and Retrieve")
    void testRetrieveAddedPatron() {
//        patronDAO.addPatron(patron);
        Optional<Patron> retrievedPatron = patronDAO.getPatronByLibraryId("LIB-12345678-42");

        assertTrue(retrievedPatron.isPresent());
        assertEquals("LIB-12345678-42", retrievedPatron.get().getPatronLibraryId());
        assertEquals("johnupdated", retrievedPatron.get().getUsername());
    }

    @Test
    void givenPatronWithSomeNullAttributes_whenAddPatron_thenThrowRuntimeException (){
        Patron newPatron = new Patron.PatronBuilder()
                .patronLibraryId("LIB-12345678-50")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .build();
        assertThrows(RuntimeException.class, () -> patronDAO.addPatron(newPatron));
    }

    /**
     * Test add multiple patrons.
     *
     * @param libraryId   the library id
     * @param username    the username
     * @param email       the email
     * @param phoneNumber the phone number
     * @param address     the address
     */
    @ParameterizedTest
    @DisplayName("Add Multiple Patrons")
    @CsvSource({
            "LIB-12345678-42, janeDoe, jane@example.com, 0987654321, 456 Mock Avenue"
    })
    void givenNewPatronWithAnAlreadyExisting_whenAddPatron_returnEntityAlreadyExistsException(String libraryId, String username, String email,
                                String phoneNumber, String address) {
//        patronDAO.addPatron(patron);
        Patron newPatron = new Patron.PatronBuilder()
                .patronLibraryId(libraryId)
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();

        assertThrows(RuntimeException.class, () -> patronDAO.addPatron(newPatron));

    }

    /**
     * Test get all patrons.
     */
    @Test
    @DisplayName("Get All Patrons")
    void testGetAllPatrons() {
        patronDAO.addPatron(patron);
        Patron newPatron = new Patron.PatronBuilder()
                .patronLibraryId("LIB-37485920-91")
                .username("janetdoe")
                .email("janet@example.com")
                .phoneNumber("0987654321")
                .address("456 Mock Avenue")
                .build();

        patronDAO.addPatron(newPatron);

        // Retrieve all patrons
        List<Patron> patrons = patronDAO.getAllPatrons();

        // Assertions
        assertEquals(2, patrons.size());
    }

    /**
     * Test update patron.
     */
    @Test
    @DisplayName("Update Patron")
    void testUpdatePatron() {
//        patronDAO.addPatron(patron);
        Patron updatedPatron = new Patron.PatronBuilder()
                .username("johnupdated")
                .email("john.updated@example.com")
                .phoneNumber("9876543210")
                .address("789 Updated Street")
                .build();

        boolean updateResult = patronDAO.updatePatron("LIB-12345678-42", updatedPatron);

        Optional<Patron> retrievedPatron = patronDAO.getPatronByLibraryId("LIB-12345678-42");

        assertTrue(updateResult);
        assertTrue(retrievedPatron.isPresent());
        assertEquals("johnupdated", retrievedPatron.get().getUsername());
        assertEquals("john.updated@example.com", retrievedPatron.get().getEmail());
    }

    /**
     * Test delete patron.
     */
    @Test
    @DisplayName("Delete Patron")
    void testDeletePatron() {
//        patronDAO.addPatron(patron);
        boolean deleteResult = patronDAO.deletePatron("LIB-12345678-42");

        Optional<Patron> retrievedPatron = patronDAO.getPatronByLibraryId("LIB-12345678-42");

        assertTrue(deleteResult);
        assertFalse(retrievedPatron.isPresent());
    }
}