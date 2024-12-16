package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.LibrarianDAO;
import com.example.librarymanagementsystem.model.Librarian;
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
 * The type Librarian service test.
 */
@ExtendWith(MockitoExtension.class)
class LibrarianServiceTest {
    private static Connection connection;

    @Mock
    private LibrarianDAO librarianDAO;

    @InjectMocks
    private LibrarianService librarianService;

    private Librarian testLibrarian;

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
        testLibrarian = new Librarian.LibrarianBuilder()
                .userId(1)
                .username("johndoe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .password("123yes")
                .build();
        librarianService = new LibrarianService(librarianDAO);
    }

    /**
     * Given existing librarians when get all librarians then return librarian list.
     */
    @Test
    @DisplayName("Given existing librarians when getAllLibrarians is called then return list of librarians")
    void givenExistingLibrarians_whenGetAllLibrarians_thenReturnLibrarianList() {
        // Arrange
        List<Librarian> expectedLibrarians = Arrays.asList(testLibrarian);
        when(librarianDAO.getAllLibrarians()).thenReturn(expectedLibrarians);

        // Act
        List<Librarian> actualLibrarians = librarianService.getAllLibrarians();

        // Assert
        assertNotNull(actualLibrarians);
        assertEquals(1, actualLibrarians.size());
        assertEquals(expectedLibrarians, actualLibrarians);
        verify(librarianDAO).getAllLibrarians();
    }

    /**
     * Given valid librarian when add librarian then add successfully.
     */
    @Test
    @DisplayName("Given a valid librarian when addLibrarian is called then add successfully")
    void givenValidLibrarian_whenAddLibrarian_thenAddSuccessfully() {
        // Arrange
        when(librarianDAO.addLibrarian(testLibrarian)).thenReturn(testLibrarian);

        // Act
        Librarian addedLibrarian = librarianService.addLibrarian(testLibrarian);

        // Assert
        assertNotNull(addedLibrarian);
        assertEquals(testLibrarian, addedLibrarian);
        verify(librarianDAO).addLibrarian(testLibrarian);
    }

    /**
     * Given librarian user id when get librarian by id then return librarian.
     */
    @Test
    @DisplayName("Given a librarian user ID when getLibrarianByUserId is called then return librarian")
    void givenLibrarianUserId_whenGetLibrarianByUserId_thenReturnLibrarian() {
        // Arrange
        when(librarianDAO.getLibrarianByUserId(1))
                .thenReturn(Optional.of(testLibrarian));

        // Act
        Optional<Librarian> retrievedLibrarian = librarianService.getLibrarianByUserId(1);

        // Assert
        assertTrue(retrievedLibrarian.isPresent());
        assertEquals(testLibrarian, retrievedLibrarian.get());
        verify(librarianDAO).getLibrarianByUserId(1);
    }

    /**
     * Given librarian email when get librarian by email then return librarian.
     */
    @Test
    @DisplayName("Given a librarian email when getLibrarianByEmail is called then return librarian")
    void givenLibrarianEmail_whenGetLibrarianByEmail_thenReturnLibrarian() {
        // Arrange
        when(librarianDAO.getLibrarianByEmail("john@example.com"))
                .thenReturn(Optional.of(testLibrarian));

        // Act
        Optional<Librarian> retrievedLibrarian = librarianService.getLibrarianByEmail("john@example.com");

        // Assert
        assertTrue(retrievedLibrarian.isPresent());
        assertEquals(testLibrarian, retrievedLibrarian.get());
        verify(librarianDAO).getLibrarianByEmail("john@example.com");
    }

    /**
     * Given librarian user id when update librarian then return update status.
     *
     * @param userId         the user id
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @DisplayName("Given different librarian update scenarios")
    @CsvSource({
            "1, true",
            "99, false"
    })
    void givenLibrarianUserId_whenUpdateLibrarian_thenReturnUpdateStatus(int userId, boolean expectedResult) {
        // Arrange
        Librarian updatedLibrarian = new Librarian.LibrarianBuilder()
                .username("updatedName")
                .email("updated@example.com")
                .build();

        when(librarianDAO.updateLibrarian(eq(userId), eq(updatedLibrarian)))
                .thenReturn(expectedResult);

        // Act
        boolean updateResult = librarianService.updateLibrarian(userId, updatedLibrarian);

        // Assert
        assertEquals(expectedResult, updateResult);
        verify(librarianDAO).updateLibrarian(userId, updatedLibrarian);
    }

    /**
     * Given librarian user id when delete librarian then return deletion status.
     */
    @Test
    @DisplayName("Given a librarian user ID when deleteLibrarian is called then return deletion status")
    void givenLibrarianUserId_whenDeleteLibrarian_thenReturnDeletionStatus() {
        // Arrange
        int userId = 1;
        when(librarianDAO.deleteLibrarian(userId)).thenReturn(true);

        // Act
        boolean deletionResult = librarianService.deleteLibrarian(userId);

        // Assert
        assertTrue(deletionResult);
        verify(librarianDAO).deleteLibrarian(userId);
    }

    /**
     * Given non-existent librarian id when get librarian by id then return empty optional.
     */
    @Test
    @DisplayName("Given a non-existent librarian user ID when getLibrarianByUserId is called then return empty optional")
    void givenNonExistentLibrarianId_whenGetLibrarianByUserId_thenReturnEmptyOptional() {
        // Arrange
        when(librarianDAO.getLibrarianByUserId(99))
                .thenReturn(Optional.empty());

        // Act
        Optional<Librarian> retrievedLibrarian = librarianService.getLibrarianByUserId(99);

        // Assert
        assertFalse(retrievedLibrarian.isPresent());
        verify(librarianDAO).getLibrarianByUserId(99);
    }
}