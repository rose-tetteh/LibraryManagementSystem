package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.BookDAO;
import com.example.librarymanagementsystem.model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private BookDAO bookDAO;

    public List<Book> getAllBooks() {
        return bookDAO.getAllResources();
    }

    public void addBook(Book book) {
        bookDAO.addResource(book);
    }

    public void updateBook(Book book) {
        bookDAO.updateResource(book);
    }

    public void deleteBook(int resourceId) {
        bookDAO.deleteResource(resourceId);
    }

    public Book getBookById(int resourceId) throws SQLException {
        return bookDAO.getResourceById(resourceId);
    }
}
