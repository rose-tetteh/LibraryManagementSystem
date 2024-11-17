package com.example.librarymanagementsystem.dao;
import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements LibraryResourceDAO<Book> {
    private String query;
//    For queries where we are inserting values into different tables in one action
    private String query1;
    private ResultSet resultSet;
    private final Connection connection = DatabaseConnection.getConnection();
    private PreparedStatement preparedStatement;


    public List<Book> getAllResources(){
        List<Book> books = new ArrayList<>();
        query = """
                SELECT lr.resourceId, lr.title, b.author, b.genre, b.publicationDate, b.statusOfBookAvailability
                FROM Book b
                JOIN LibraryResource lr ON b.resourceId = lr.resourceId
                """;

        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    String id = resultSet.getString("resourceId");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String genre = resultSet.getString("genre");
                    LocalDate publicationDate = resultSet.getDate("publicationDate").toLocalDate();
                    Status status = Status.valueOf(resultSet.getString("statusOfBookAvailability"));
                    books.add(new Book.BookBuilder()
                            .resourceId(id)
                            .title(title)
                            .author(author)
                            .genre(genre)
                            .publicationDate(publicationDate)
                            .statusOfBookAvailability(status)
                            .build());
                }
            } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return books;
    }

    public void addResource(Book book){
        query = "INSERT INTO LibraryResource (resourceId, title, resourceType) VALUES (?, ?, ?)";
        query1 = "INSERT INTO Book (resourceId, author, genre, publicationDate, statusOfBookAvailability) VALUES (?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert into LibraryResource
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getResourceId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setObject(3, "book", Types.OTHER);
            preparedStatement.executeUpdate();

            // Insert into Book
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, book.getResourceId());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setDate(4, book.getPublicationDate());
            preparedStatement.setObject(5, "available", Types.OTHER);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
