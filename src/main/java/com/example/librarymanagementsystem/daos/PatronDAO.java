package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.model.Patron;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.librarymanagementsystem.utils.DatabaseConnection.getConnection;

/**
 * The type Patron dao.
 */
public class PatronDAO {
    private String query;
    private ResultSet resultSet;
    private final Connection connection = DatabaseConnection.getConnection();
    private PreparedStatement preparedStatement;

    /**
     * Gets all patrons.
     *
     * @return the all patrons
     */
    public List<Patron> getAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        query = "SELECT * FROM patron";

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                patrons.add(mapResultSetToPatron(resultSet));
            }
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
        return patrons;
    }

    /**
     * Add patron.
     *
     * @param patron the patron
     */
    public void addPatron(Patron patron) {
       query = "INSERT INTO patrons (patronId, patronLibraryId, username, email, phoneNumber, address)" +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patron.getPatronId());
            preparedStatement.setString(2, patron.getPatronLibraryId());
            preparedStatement.setString(3, patron.getUsername());
            preparedStatement.setString(4, patron.getEmail());
            preparedStatement.setString(5, patron.getPhoneNumber());
            preparedStatement.setString(6, patron.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets patron by library id.
     *
     * @param patronLibraryId the patron library id
     * @return the patron by library id
     */
    public Optional<Patron> getPatronByLibraryId(String patronLibraryId) {
       query = "SELECT * FROM patrons WHERE patronLibraryId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patronLibraryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPatron(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Gets patron by email.
     *
     * @param email the email
     * @return the patron by email
     */
    public Optional<Patron> getPatronByEmail(String email) {
       query = "SELECT * FROM patrons WHERE email = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
           resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPatron(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Update patron boolean.
     *
     * @param patronLibraryId the patron library id
     * @param updatedPatron   the updated patron
     * @return the boolean
     */
    public boolean updatePatron(String patronLibraryId, Patron updatedPatron) {
        query = "UPDATE patrons SET username = ?, email = ?, phoneNumber = ?, address = ? " +
                "WHERE patronLibraryId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, updatedPatron.getUsername());
            preparedStatement.setString(2, updatedPatron.getEmail());
            preparedStatement.setString(3, updatedPatron.getPhoneNumber());
            preparedStatement.setString(4, updatedPatron.getAddress());
            preparedStatement.setString(5, patronLibraryId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete patron boolean.
     *
     * @param patronLibraryId the patron library id
     * @return the boolean
     */
// Delete a patron
    public boolean deletePatron(String patronLibraryId) {
        query = "DELETE FROM patrons WHERE patronLibraryId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patronLibraryId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Patron mapResultSetToPatron(ResultSet resultSet) throws SQLException {
        return new Patron.PatronBuilder()
                .patronId(resultSet.getInt("patronId"))
                .patronLibraryId(resultSet.getString("patronLibraryId"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .phoneNumber(resultSet.getString("phoneNumber"))
                .address(resultSet.getString("address"))
                .build();
    }

}
