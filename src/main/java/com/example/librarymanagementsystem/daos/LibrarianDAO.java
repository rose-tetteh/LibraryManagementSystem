package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.model.Librarian;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The type Librarian dao.
 */
public class LibrarianDAO {
    private String query;
    private ResultSet resultSet;
    private final Connection connection = DatabaseConnection.getConnection();
    private PreparedStatement preparedStatement;

    /**
     * Gets all librarians.
     *
     * @return the all librarians
     */
    public List<Librarian> getAllLibrarians() {
        List<Librarian> librarians = new ArrayList<>();
        query = "SELECT * FROM librarian";

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                librarians.add(mapResultSetToLibrarian(resultSet));
            }
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
        return librarians;
    }

    /**
     * Add librarian.
     *
     * @param librarian the librarian
     */
    public void addLibrarian(Librarian librarian) {
       query = "INSERT INTO librarian (userId, username, email, phoneNumber, password)" +
                "VALUES (?, ?, ?, ?, ?)";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, librarian.getUserId());
            preparedStatement.setString(2, librarian.getUsername());
            preparedStatement.setString(3, librarian.getEmail());
            preparedStatement.setString(4, librarian.getPhoneNumber());
            preparedStatement.setString(5, librarian.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Gets librarian by user id.
     *
     * @param userId the user id
     * @return the librarian by user id
     */
    public Optional<Librarian> getLibrarianByUserId(int userId) {
       query = "SELECT * FROM librarian WHERE userId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToLibrarian(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Gets librarian by email.
     *
     * @param email the email
     * @return the librarian by email
     */
    public Optional<Librarian> getLibrarianByEmail(String email) {
       query = "SELECT * FROM librarian WHERE email = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
           resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToLibrarian(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Update librarian boolean.
     *
     * @param userId           the user id
     * @param updatedLibrarian the updated librarian
     * @return the boolean
     */
    public boolean updateLibrarian(int userId, Librarian updatedLibrarian) {
        query = "UPDATE librarian SET username = ?, email = ?, phoneNumber = ?, address = ? " +
                "WHERE userId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, updatedLibrarian.getUsername());
            preparedStatement.setString(2, updatedLibrarian.getEmail());
            preparedStatement.setString(3, updatedLibrarian.getPhoneNumber());
            preparedStatement.setInt(4, userId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete librarian boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean deleteLibrarian(int userId) {
        query = "DELETE FROM librarian WHERE librarianLibraryId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Librarian mapResultSetToLibrarian(ResultSet resultSet) throws SQLException {
        return new Librarian.LibrarianBuilder()
                .userId(resultSet.getInt("userId"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .phoneNumber(resultSet.getString("phoneNumber"))
                .build();
    }

}
