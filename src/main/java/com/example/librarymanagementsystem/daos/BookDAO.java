package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BookDAO extends LibraryResourceDAOImpl<Book> {

    @Override
    protected String getSelectAllQuery() {
        return """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, b.author, b.genre, b.isbn, b.publicationDate, b.statusOfBookAvailability
                FROM Book b
                JOIN LibraryResource lr ON b.resourceId = lr.resourceId
                """;
    }

    @Override
    protected String getInsertLibraryResourceQuery() {
        return "INSERT INTO LibraryResource (title, resourceType, numberOfCopies) VALUES (?, ?, ?)";
    }

    @Override
    protected String getInsertSpecificResourceQuery() {
        return "INSERT INTO Book (resourceId, author, genre, isbn, publicationDate, statusOfBookAvailability) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateLibraryResourceQuery() {
        return "UPDATE LibraryResource SET title = ?, numberOfCopies = ? WHERE resourceId = ?";
    }

    @Override
    protected String getUpdateSpecificResourceQuery() {
        return "UPDATE Book SET author = ?, genre = ?, isbn = ?, publicationDate = ?, statusOfBookAvailability = ? WHERE resourceId = ?";
    }

    @Override
    protected String getDeleteLibraryResourceQuery() {
        return "DELETE FROM LibraryResource WHERE resourceId = ?";
    }

    @Override
    protected String getDeleteSpecificResourceQuery() {
        return "DELETE FROM Book WHERE resourceId = ?";
    }

    @Override
    protected String getSelectResourceByIdQuery() {
        return """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, b.author, b.genre, b.isbn, b.publicationDate, b.statusOfBookAvailability
                FROM Book b
                JOIN LibraryResource lr ON b.resourceId = lr.resourceId
                WHERE lr.resourceId = ?
                """;
    }

    @Override
    protected void setLibraryResourceFields(PreparedStatement preparedStatement, Book book) throws SQLException {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setObject(2, "book", Types.OTHER);
        preparedStatement.setInt(3, book.getNumberOfCopies());
    }

    @Override
    protected void setSpecificResourceFields(PreparedStatement preparedStatement, Book book, int resourceId) throws SQLException {
        preparedStatement.setInt(1, resourceId);
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getGenre());
        preparedStatement.setString(4, book.getIsbn());
        preparedStatement.setDate(5, book.getPublicationDate());
        preparedStatement.setObject(6, book.getStatusOfBookAvailability().name(), Types.OTHER);
    }

    @Override
    protected Book mapResultSetToResource(ResultSet resultSet) throws SQLException {
        return new Book.BookBuilder()
                .resourceId(resultSet.getInt("resourceId"))
                .title(resultSet.getString("title"))
                .numberOfCopies(resultSet.getInt("numberOfCopies"))
                .author(resultSet.getString("author"))
                .genre(resultSet.getString("genre"))
                .isbn(resultSet.getString("isbn"))
                .publicationDate(resultSet.getDate("publicationDate").toLocalDate())
                .statusOfBookAvailability(Status.valueOf(resultSet.getString("statusOfBookAvailability")))
                .build();
    }
}
