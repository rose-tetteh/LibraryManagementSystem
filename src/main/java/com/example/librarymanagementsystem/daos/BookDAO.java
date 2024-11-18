package com.example.librarymanagementsystem.daos;
import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * query1 = For queries where we are inserting values into different tables in one action
 * The type Book dao.
 */
public class BookDAO implements LibraryResourceDAO<Book> {
    private String query;
    private String query1;
    private ResultSet resultSet;
    private final Connection connection = DatabaseConnection.getConnection();
    private PreparedStatement preparedStatement;


    public List<Book> getAllResources(){
        List<Book> books = new ArrayList<>();
        query = """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, b.author, b.genre, b.isbn, b.publicationDate, b.statusOfBookAvailability
                FROM Book b
                JOIN LibraryResource lr ON b.resourceId = lr.resourceId
                """;

        try(Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(query);
                while(resultSet.next()){
                    books.add(mapResultSetToBook(resultSet));
                }
            } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return books;
    }

    public void addResource(Book book){
        query = "INSERT INTO LibraryResource (title, resourceType, numberOfCopies) VALUES (?, ?, ?) RETURNING resourceId";
        query1 = "INSERT INTO Book (resourceId, author, genre, isbn, publicationDate, statusOfBookAvailability) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert into LibraryResource
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, "book", Types.OTHER);
            preparedStatement.setInt(3, book.getNumberOfCopies());
            resultSet = preparedStatement.executeQuery();

            // Insert into Book
            if (resultSet.next()){
                int resourceId = resultSet.getInt("resourceId");

                preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setInt(1, resourceId);
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setString(3, book.getGenre());
                preparedStatement.setString(4, book.getIsbn());
                preparedStatement.setDate(5, book.getPublicationDate());
                preparedStatement.setObject(6, "available", Types.OTHER);
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateResource(Book book){
        query = "UPDATE LibraryResource SET title = ?, numberOfCopies = ? WHERE resourceId = ?";
        query1 = "UPDATE Book SET author = ?, genre = ?, isbn = ?, publicationDate = ?, statusOfBookAvailability = ? WHERE resourceId = ?";

        try{
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getNumberOfCopies());
            preparedStatement.setInt(3, book.getResourceId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getGenre());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setDate(4, book.getPublicationDate());
            preparedStatement.setObject(5, "available", Types.OTHER);
            preparedStatement.setInt(6, book.getResourceId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteResource(int resourceId){
        query = "DELETE FROM Book WHERE resourceId = ?";
        query1 = "DELETE FROM LibraryResource WHERE resourceId = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resourceId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, resourceId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Book with Id: " + resourceId +" does not exist." + e.getMessage());
        }
    }

    @Override
    public Book getResourceById(int resourceId){
        query = """
                SELECT lr.resourceId, lr.title, lr.numberOfCopies, b.author, b.genre, b.isbn, b.publicationDate, b.statusOfBookAvailability
                FROM Book b
                JOIN LibraryResource lr ON b.resourceId = lr.resourceId
                WHERE lr.resourceId = ?
                """;
        Book book = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resourceId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                book = mapResultSetToBook(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Resource not found" + e.getMessage());
        }
        return book;
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException{
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
