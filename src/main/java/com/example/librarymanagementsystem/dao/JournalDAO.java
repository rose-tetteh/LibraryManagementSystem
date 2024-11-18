package com.example.librarymanagementsystem.dao;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Journal;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JournalDAO implements LibraryResourceDAO<Journal> {
    private String query;
    private String query1;
    private ResultSet resultSet;
    private final Connection connection = DatabaseConnection.getConnection();
    private PreparedStatement preparedStatement;

    public List<Journal> getAllResources(){
        List<Journal> allJournals = new ArrayList<>();
        query = """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, j.author, j.genre, j.volumeNumber, j.publicationDate, j.statusOfJournalAvailability
                FROM Journal b
                JOIN LibraryResource lr ON j.resourceId = lr.resourceId
                """;

        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                int id = resultSet.getInt("resourceId");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int numberOfCopies = resultSet.getInt("numberOfCopies");
                int volumeNumber = resultSet.getInt("volumeNumber");
                LocalDate publicationDate = resultSet.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(resultSet.getString("statusOfJournalAvailability"));
                allJournals.add(new Journal.JournalBuilder()
                        .resourceId(id)
                        .title(title)
                        .author(author)
                        .genre(genre)
                        .numberOfCopies(numberOfCopies)
                        .volumeNumber(volumeNumber)
                        .publicationDate(publicationDate)
                        .statusOfJournalAvailability(status)
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return allJournals;
    }

    @Override
    public void addResource(Journal journal)  {
        query = "INSERT INTO LibraryResource (title, resourceType, numberOfCopies) VALUES (?, ?, ?) RETURNING resourceId";
        query1 = "INSERT INTO Journal (resourceId, author, genre, volumeNumber, publicationDate, statusOfJournalAvailability) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert into LibraryResource
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, journal.getTitle());
            preparedStatement.setObject(2, "journal", Types.OTHER);
            preparedStatement.setInt(3, journal.getNumberOfCopies());
            resultSet = preparedStatement.executeQuery();

            // Insert into Journal
            if (resultSet.next()){
                int resourceId = resultSet.getInt("resourceId");

                preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setInt(1, resourceId);
                preparedStatement.setString(2, journal.getAuthor());
                preparedStatement.setString(3, journal.getGenre());
                preparedStatement.setInt(4, journal.getVolumeNumber());
                preparedStatement.setDate(5, journal.getPublicationDate());
                preparedStatement.setObject(6, "available", Types.OTHER);
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void updateResource(Journal journal){
        query = "UPDATE LibraryResource SET title = ?, numberOfCopies = ? WHERE resourceId = ?";
        query1 = "UPDATE Journal SET author = ?, genre = ?, volumeNumber = ?, publicationDate = ?, statusOfJournalAvailability = ? WHERE resourceId = ?";

        try{
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, journal.getTitle());
            preparedStatement.setInt(2, journal.getNumberOfCopies());
            preparedStatement.setInt(3, journal.getResourceId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, journal.getAuthor());
            preparedStatement.setString(2, journal.getGenre());
            preparedStatement.setInt(3, journal.getVolumeNumber());
            preparedStatement.setDate(4, journal.getPublicationDate());
            preparedStatement.setObject(5, "available", Types.OTHER);
            preparedStatement.setInt(6, journal.getResourceId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void deleteResource(int resourceId)  {
        query = "DELETE FROM Journal WHERE resourceId = ?";
        query1 = "DELETE FROM LibraryResource WHERE resourceId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resourceId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, resourceId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Journal with Id: " + resourceId +" does not exist." + e.getMessage());
        }
    }

    @Override
    public Journal getResourceById(int resourceId)  {
        query = """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, j.author, j.genre, j.volumeNumber, j.publicationDate, j.statusOfJournalAvailability
                FROM Journal b
                JOIN LibraryResource lr ON j.resourceId = lr.resourceId
                WHERE lr.resourceId = ?
                """;
        Journal journal = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resourceId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("resourceId");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int numberOfCopies = resultSet.getInt("numberOfCopies");
                int volumeNumber = resultSet.getInt("volumeNumber");
                LocalDate publicationDate = resultSet.getDate("publicationDate").toLocalDate();
                Status status = Status.valueOf(resultSet.getString("statusOfJournalAvailability"));
                journal = new Journal.JournalBuilder()
                        .resourceId(id)
                        .title(title)
                        .author(author)
                        .genre(genre)
                        .numberOfCopies(numberOfCopies)
                        .volumeNumber(volumeNumber)
                        .publicationDate(publicationDate)
                        .statusOfJournalAvailability(status)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Resource not found" + e.getMessage());
        }
        return journal;
    }
}
