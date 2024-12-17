package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.utils.DatabaseConnection;
import com.example.librarymanagementsystem.utils.DatabaseConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDAOTest {
    private static ReservationDAO reservationDAO;
    private Reservation existingReservation;
    private Reservation newReservation;
    private static Connection connection;

    @BeforeAll
    static void setUp() {
        DatabaseConnection.setTestMode(true);
        connection = DatabaseConnection.getConnection();
        reservationDAO = new ReservationDAO();
        DatabaseConnectionTest.setupSchema(connection);
        DatabaseConnectionTest.testData(connection);

    }

    @BeforeEach
    void prepareTestData() {
        // Regression Test Data Setup
        existingReservation = new Reservation.ReservationBuilder()
                .reservationId(1)
                .resourceId(1)
                .patronLibraryId("LIB-12345678-42")
                .reservationStatus(Status.pending)
                .reservationDate(LocalDate.now())
                .queuePosition(1)
                .build();
    }

    /**
     * Regression Test: Verify Existing Reservation Workflow
     * - Test if existing reservation process remains consistent
     */
    @Test
    @DisplayName("Regression: Reservation Workflow Consistency")
    void verifyExistingReservationWorkflow() {
        // Verify reservation can be requested
        assertTrue(reservationDAO.requestReservation(existingReservation),
                "Existing reservation workflow should remain functional");

        // Verify reservation can be retrieved
        Queue<Reservation> reservations = reservationDAO.getReservationsByPatronLibraryId("LIB-12345678-42");
        assertFalse(reservations.isEmpty(), "Retrieved reservations should not be empty");
        assertEquals(2, reservations.size(), "Number of reservations should match expected");

        // Verify reservation details remain consistent
        Reservation retrievedReservation = reservations.peek();
        assertNotNull(retrievedReservation);
        assertEquals(existingReservation.getResourceId(), retrievedReservation.getResourceId(),
                "Resource ID should remain consistent");
        assertEquals(existingReservation.getReservationStatus(), retrievedReservation.getReservationStatus(),
                "Reservation status should remain consistent");
    }

    /**
     * Regression Test: Multiple Reservation Handling
     * - Verify system can handle multiple reservations without breaking existing functionality
     */
    @Test
    @DisplayName("Regression: Multiple Reservation Handling")
    void verifyMultipleReservationHandling() {
        newReservation = new Reservation.ReservationBuilder()
                .reservationId(2)
                .resourceId(2)
                .patronLibraryId("LIB-12345678-45")
                .reservationStatus(Status.pending)
                .reservationDate(LocalDate.now())
                .queuePosition(2)
                .build();
        // Add existing reservation
        reservationDAO.requestReservation(existingReservation);

        // Add new reservation
        assertTrue(reservationDAO.requestReservation(newReservation),
                "New reservation should be added without disrupting existing workflow");

        // Verify total reservations
        List<Reservation> allReservations = reservationDAO.getAllReservations();
        assertEquals(2, allReservations.size(), "Total reservations should match expected count");
    }

    /**
     * Regression Test: Reservation Deletion Integrity
     * - Verify deletion process maintains system integrity
     */
    @Test
    @DisplayName("Regression: Reservation Deletion Integrity")
    void verifyReservationDeletionIntegrity() {
        newReservation = new Reservation.ReservationBuilder()
                .reservationId(2)
                .resourceId(2)
                .patronLibraryId("LIB-12345678-45")
                .reservationStatus(Status.pending)
                .reservationDate(LocalDate.now())
                .queuePosition(2)
                .build();
        // Precondition: Add reservations
        reservationDAO.requestReservation(existingReservation);
        reservationDAO.requestReservation(newReservation);

        // Delete existing reservation
        assertTrue(reservationDAO.deleteReservation(1), "Reservation deletion should succeed");

        // Verify remaining reservations
        List<Reservation> remainingReservations = reservationDAO.getAllReservations();
        assertEquals(3, remainingReservations.size(), "Remaining reservations should match after deletion");
        assertEquals(newReservation.getReservationId(), remainingReservations.get(0).getReservationId(),
                "Remaining reservation should be the one not deleted");
    }

    /**
     * Regression Test: Error Scenario Handling
     * - Verify system's response to potential error scenarios remains consistent
     */
    @Test
    @DisplayName("Regression: Error Scenario Handling")
    void verifyErrorScenarioHandling() {
        // Attempt to delete non-existent reservation
        assertFalse(reservationDAO.deleteReservation(9999),
                "Deleting non-existent reservation should return false");

        // Verify system state remains unchanged
        List<Reservation> reservations = reservationDAO.getAllReservations();
        assertFalse(reservations.isEmpty(), "System state should remain unaffected by error scenario");
    }
}