package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Journal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JournalDAO extends LibraryResourceDAOImpl<Journal> {

    @Override
    protected String getSelectAllQuery() {
        return """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, j.author, j.genre, j.volumeNumber, j.publicationDate, j.statusOfJournalAvailability
                FROM Journal j
                JOIN LibraryResource lr ON j.resourceId = lr.resourceId
                """;
    }

    @Override
    protected String getInsertLibraryResourceQuery() {
        return "INSERT INTO LibraryResource (title, resourceType, numberOfCopies) VALUES (?, ?, ?) RETURNING resourceId";
    }

    @Override
    protected String getInsertSpecificResourceQuery() {
        return "INSERT INTO Journal (resourceId, author, genre, volumeNumber, publicationDate, statusOfJournalAvailability) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateLibraryResourceQuery() {
        return "UPDATE LibraryResource SET title = ?, numberOfCopies = ? WHERE resourceId = ?";
    }

    @Override
    protected String getUpdateSpecificResourceQuery() {
        return "UPDATE Journal SET author = ?, genre = ?, volumeNumber = ?, publicationDate = ?, statusOfJournalAvailability = ? WHERE resourceId = ?";
    }

    @Override
    protected String getDeleteLibraryResourceQuery() {
        return "DELETE FROM LibraryResource WHERE resourceId = ?";
    }

    @Override
    protected String getDeleteSpecificResourceQuery() {
        return "DELETE FROM Journal WHERE resourceId = ?";
    }

    @Override
    protected String getSelectResourceByIdQuery() {
        return """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, j.author, j.genre, j.volumeNumber, j.publicationDate, j.statusOfJournalAvailability
                FROM Journal j
                JOIN LibraryResource lr ON j.resourceId = lr.resourceId
                WHERE lr.resourceId = ?
                """;
    }

    @Override
    protected void setLibraryResourceFields(PreparedStatement preparedStatement, Journal resource) throws SQLException {
        preparedStatement.setString(1, resource.getTitle());
        preparedStatement.setObject(2, "journal", Types.OTHER);
        preparedStatement.setInt(3, resource.getNumberOfCopies());
    }

    @Override
    protected void setSpecificResourceFields(PreparedStatement preparedStatement, Journal resource, int resourceId) throws SQLException {
        preparedStatement.setInt(1, resourceId);
        preparedStatement.setString(2, resource.getAuthor());
        preparedStatement.setString(3, resource.getGenre());
        preparedStatement.setInt(4, resource.getVolumeNumber());
        preparedStatement.setDate(5, resource.getPublicationDate());
        preparedStatement.setObject(6, resource.getStatusOfJournalAvailability().name(), Types.OTHER);
    }

    @Override
    protected Journal mapResultSetToResource(ResultSet resultSet) throws SQLException {
        return new Journal.JournalBuilder()
                .resourceId(resultSet.getInt("resourceId"))
                .title(resultSet.getString("title"))
                .numberOfCopies(resultSet.getInt("numberOfCopies"))
                .author(resultSet.getString("author"))
                .genre(resultSet.getString("genre"))
                .volumeNumber(resultSet.getInt("volumeNumber"))
                .publicationDate(resultSet.getDate("publicationDate").toLocalDate())
                .statusOfJournalAvailability(Status.valueOf(resultSet.getString("statusOfJournalAvailability")))
                .build();
    }
}
