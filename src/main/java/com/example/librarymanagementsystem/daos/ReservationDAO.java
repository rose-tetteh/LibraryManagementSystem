package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Reservation;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReservationDAO {
    private String query;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private final Connection connection = DatabaseConnection.getConnection();
    private final Queue<Reservation> reservationQueue = new LinkedList<>();

    /**
     * Request reservation boolean.
     *
     * @param reservation the reservation
     * @return the boolean
     */
    public boolean requestReservation(Reservation reservation) {
        query = "INSERT INTO reservations (reservationId, resourceId, patronLibraryId, reservation_status, reservation_date, queue_position) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservation.getReservationId());
            preparedStatement.setInt(2, reservation.getResourceId());
            preparedStatement.setInt(3, reservation.getPatronLibraryId());
            preparedStatement.setObject(4, "pending", Types.OTHER);
            preparedStatement.setDate(5, Date.valueOf(reservation.getReservationDate()));
            preparedStatement.setInt(6, reservationQueue.size() + 1);
            if (preparedStatement.executeUpdate() > 0) {
                reservationQueue.add(reservation);
                return true;
            }
            return false;
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets reservations by patron library id.
     *
     * @param patronLibraryId the patron library id
     * @return the reservations by patron library id
     */
    public Queue<Reservation> getReservationsByPatronLibraryId(String patronLibraryId) {
        query = "SELECT * FROM reservations WHERE patronLibraryId = ?";
        Queue<Reservation> patronReservations = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patronLibraryId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patronReservations.add(mapResultSetToReservation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return patronReservations;
    }

    /**
     * Delete reservation boolean.
     *
     * @param reservationId the reservation id
     * @return the boolean
     */
    public boolean deleteReservation(int reservationId) {
        query = "DELETE FROM reservation WHERE reservationId = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reservationId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Reservation> getAllReservations() {
        query = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservations.add(mapResultSetToReservation(resultSet));
            }
        } catch (SQLException e) {
          throw new RuntimeException(e.getMessage());
        }
        return reservations;
    }


    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        return new Reservation.ReservationBuilder()
                .reservationId(resultSet.getInt("reservationId"))
                .resourceId(resultSet.getInt("resourceId"))
                .patronLibraryId(resultSet.getInt("patronLibraryId"))
                .reservationStatus(Status.valueOf(resultSet.getString("reservation_status")))
                .reservationDate(resultSet.getDate("reservation_date").toLocalDate())
                .queuePosition(resultSet.getInt("queue_position"))
                .build();
    }

}
