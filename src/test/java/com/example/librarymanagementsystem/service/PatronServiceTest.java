package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.PatronDAO;
import com.example.librarymanagementsystem.model.Patron;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Patron service test.
 */
@ExtendWith(MockitoExtension.class)
class PatronServiceTest {
    private static Connection connection;

    @Mock
    private PatronDAO patronDAO;

    @InjectMocks
    private PatronService patronService;

    private Patron testPatron;

    /**
     * Before all.
     */
    @BeforeAll
    static void beforeAll() {
        DatabaseConnection.setTestMode(true);
        connection = DatabaseConnection.getConnection();

        DatabaseConnectionTest.setupSchema(connection);
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        testPatron = new Patron.PatronBuilder()
                .patronLibraryId("LIB-12345678-42")
                .username("johndoe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .address("123 Test Street")
                .build();
        patronService = new PatronService(patronDAO);
    }

    /**
     * Given existing patrons when get all patrons then return patron list.
     */
    @Test
    @DisplayName("Given existing patrons when getAllPatrons is called then return list of patrons")
    void givenExistingPatrons_whenGetAllPatrons_thenReturnPatronList() {
        // Arrange
        List<Patron> expectedPatrons = Arrays.asList(testPatron);
        when(patronDAO.getAllPatrons()).thenReturn(expectedPatrons);

        // Act
        List<Patron> actualPatrons = patronService.getAllPatrons();

        // Assert
        assertNotNull(actualPatrons);
        assertEquals(1, actualPatrons.size());
        assertEquals(expectedPatrons, actualPatrons);
        verify(patronDAO).getAllPatrons();
    }

    /**
     * Given valid patron when add patron then return added patron.
     */
    @Test
    @DisplayName("Given a valid patron when addPatron is called then return added patron")
    void givenValidPatron_whenAddPatron_thenReturnAddedPatron() {
        // Arrange
        when(patronDAO.addPatron(testPatron)).thenReturn(testPatron);

        // Act
        Patron addedPatron = patronService.addPatron(testPatron);

        // Assert
        assertNotNull(addedPatron);
        assertEquals(testPatron, addedPatron);
        verify(patronDAO).addPatron(testPatron);
    }

    /**
     * Given patron library id when get patron by id then return patron.
     */
    @Test
    @DisplayName("Given a patron library ID when getPatronById is called then return patron")
    void givenPatronLibraryId_whenGetPatronById_thenReturnPatron() {
        // Arrange
        when(patronDAO.getPatronByLibraryId("LIB-12345678-42"))
                .thenReturn(Optional.of(testPatron));

        // Act
        Optional<Patron> retrievedPatron = patronService.getPatronById("LIB-12345678-42");

        // Assert
        assertTrue(retrievedPatron.isPresent());
        assertEquals(testPatron, retrievedPatron.get());
        verify(patronDAO).getPatronByLibraryId("LIB-12345678-42");
    }

    /**
     * Given patron email when get patron by email then return patron.
     */
    @Test
    @DisplayName("Given a patron email when getPatronByEmail is called then return patron")
    void givenPatronEmail_whenGetPatronByEmail_thenReturnPatron() {
        // Arrange
        when(patronDAO.getPatronByEmail("john@example.com"))
                .thenReturn(Optional.of(testPatron));

        // Act
        Optional<Patron> retrievedPatron = patronService.getPatronByEmail("john@example.com");

        // Assert
        assertTrue(retrievedPatron.isPresent());
        assertEquals(testPatron, retrievedPatron.get());
        verify(patronDAO).getPatronByEmail("john@example.com");
    }

    /**
     * Given patron library id when update patron then return update status.
     *
     * @param libraryId      the library id
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @DisplayName("Given different patron update scenarios")
    @CsvSource({
            "LIB-12345678-42, true",
            "LIB-87654321-99, false"
    })
    void givenPatronLibraryId_whenUpdatePatron_thenReturnUpdateStatus(String libraryId, boolean expectedResult) {
        // Arrange
        Patron updatedPatron = new Patron.PatronBuilder()
                .username("updatedName")
                .email("updated@example.com")
                .build();

        when(patronDAO.updatePatron(eq(libraryId), eq(updatedPatron)))
                .thenReturn(expectedResult);

        // Act
        boolean updateResult = patronService.updatePatron(libraryId, updatedPatron);

        // Assert
        assertEquals(expectedResult, updateResult);
        verify(patronDAO).updatePatron(libraryId, updatedPatron);
    }

    /**
     * Given patron library id when delete patron then return deletion status.
     */
    @Test
    @DisplayName("Given a patron library ID when deletePatron is called then return deletion status")
    void givenPatronLibraryId_whenDeletePatron_thenReturnDeletionStatus() {
        // Arrange
        String libraryId = "LIB-12345678-42";
        when(patronDAO.deletePatron(libraryId)).thenReturn(true);

        // Act
        boolean deletionResult = patronService.deletePatron(libraryId);

        // Assert
        assertTrue(deletionResult);
        verify(patronDAO).deletePatron(libraryId);
    }

    /**
     * Given non-existent patron id when get patron by id then return empty optional.
     */
    @Test
    @DisplayName("Given a non-existent patron library ID when getPatronById is called then return empty optional")
    void givenNonExistentPatronId_whenGetPatronById_thenReturnEmptyOptional() {
        // Arrange
        when(patronDAO.getPatronByLibraryId("LIB-99999999-99"))
                .thenReturn(Optional.empty());

        // Act
        Optional<Patron> retrievedPatron = patronService.getPatronById("LIB-99999999-99");

        // Assert
        assertFalse(retrievedPatron.isPresent());
        verify(patronDAO).getPatronByLibraryId("LIB-99999999-99");
    }
}