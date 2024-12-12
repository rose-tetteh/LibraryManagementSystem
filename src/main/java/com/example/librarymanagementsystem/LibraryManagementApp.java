package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.daos.BookDAO;
import com.example.librarymanagementsystem.daos.PatronDAO;
import com.example.librarymanagementsystem.enums.ResourceType;
import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Patron;

import java.time.LocalDate;

public class LibraryManagementApp {
    public static void main(String[] args) {
        PatronDAO patronDAO = new PatronDAO();
//        BookDAO bookDAO = new BookDAO();
//        try {
//            // Add a new book to the database
//            Book newBook = new Book.BookBuilder()
//                    .resourceId(7)
//                    .title("Commit Code") // Inherited from LibraryResource.Builder
//                    .resourceType(ResourceType.book) // Inherited from LibraryResource.Builder
//                    .author("Robert C. Mills")
//                    .genre("Computer Engineering")
//                    .publicationDate(LocalDate.of(2008, 8, 1))
//                    .statusOfBookAvailability(Status.available)
//                    .numberOfCopies(7)
//                    .isbn("978-3-16-148410-0")
//                    .build();
//
//            bookDAO.addResource(newBook);
//
//            System.out.println("Book added successfully!");
//
////             Fetch and print all books from the database
////            int resourceId = 6;
////            bookDAO.deleteResource(resourceId);
////            Book book = bookDAO.getResourceById(resourceId);
//            System.out.println("List of all books in the database:");
//            for (Book book : bookDAO.getAllResources()) {
//                System.out.println("ID: " + book.getResourceId() +
//                        ", Title: " + book.getTitle() +
//                        ", Author: " + book.getAuthor() +
//                        ", Genre: " + book.getGenre() +
//                        ", Publication Date: " + book.getPublicationDate() +
//                        ", Status: " + book.getStatusOfBookAvailability());
//            }
//        } catch (RuntimeException e) {
//           throw new RuntimeException(e.getMessage());
//        }
//
//        try {
//            int bookId = 7;
//
//            Book findbook = bookDAO.getResourceById(bookId);
//
//            if (findbook != null) {
//                Book updateBook = new Book.BookBuilder()
//                        .resourceId(findbook.getResourceId())
//                        .title(findbook.getTitle())
//                        .author(findbook.getAuthor())
//                        .genre(findbook.getGenre())
//                        .publicationDate(findbook.getPublicationDate().toLocalDate())
//                        .statusOfBookAvailability(findbook.getStatusOfBookAvailability())
//                        .numberOfCopies(7)
//                        .isbn("978-3-16-148410-0")
//                        .build();
//
//                bookDAO.updateResource(updateBook);
//                System.out.println("Book updated");
//            } else {
//                System.out.println("Book not found");
//            }
//
//            System.out.println("List of all books in the database:");
//            for (Book book : bookDAO.getAllResources()) {
//                System.out.println("ID: " + book.getResourceId() +
//                        ", Title: " + book.getTitle() +
//                        ", NumberOfCopies: " + book.getNumberOfCopies() +
//                        ", Author: " + book.getAuthor() +
//                        ", Genre: " + book.getGenre() +
//                        ", ISBN: " + book.getIsbn() +
//                        ", Publication Date: " + book.getPublicationDate() +
//                        ", Status: " + book.getStatusOfBookAvailability());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }

        try {
//            Patron newPatron = new Patron.PatronBuilder()
//                    .patronLibraryId("LIB-00045678-32")
//                    .username("mike")
//                    .email("mike@example.com")
//                    .phoneNumber("+233124456789")
//                    .address("567 New Street, Accra")
//                    .build();
//
//            patronDAO.addPatron(newPatron);
            Patron patron1 = patronDAO.getPatronByLibraryId("LIB-12345678-31").get();
            System.out.println(patron1);
//            patron1 = patronDAO.getPatronByLibraryId("LIB-12345678-31");

            System.out.println("Patrons");
            // Displaying the details of the created patron
           for (Patron patron : patronDAO.getAllPatrons()) {
               System.out.println("ID: " + patron.getPatronId() +
                       " Library ID: " + patron.getPatronLibraryId() +
                       " Username: " + patron.getUsername() +
                       " Email: " + patron.getEmail() +
                       " Phone Number: " + patron.getPhoneNumber() +
                       " Address: " + patron.getAddress());
           }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
