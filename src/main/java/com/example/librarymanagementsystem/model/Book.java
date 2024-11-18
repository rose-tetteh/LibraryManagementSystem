package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.Status;

import java.sql.Date;
import java.time.LocalDate;

/**
 * isbn - international standard book number
 * The type Book.
 */
public class Book extends LibraryResource{
    private final String genre;
    private final String author;
    private final String isbn;
    private final LocalDate publicationDate;
    private final Status statusOfBookAvailability;

    /**
     * The type Book builder.
     */
    public static class BookBuilder extends LibraryResource.Builder<BookBuilder>{
        private String genre;
        private String author;
        private String isbn;
        private LocalDate publicationDate;
        private Status statusOfBookAvailability;

        public BookBuilder() {}

        @Override
        public BookBuilder getThis() {
            return this;
        }

        public BookBuilder genre(String genre){
            this.genre = genre;
            return this;
        }

        public BookBuilder author(String author){
            this.author = author;
            return getThis();
        }

        public BookBuilder isbn(String isbn){
            this.isbn = isbn;
            return getThis();
        }

        public BookBuilder publicationDate(LocalDate publicationDate){
            this.publicationDate = publicationDate;
            return this;
        }

        public BookBuilder statusOfBookAvailability(Status statusOfBookAvailability){
            this.statusOfBookAvailability = statusOfBookAvailability;
            return this;
        }

        public Book build(){
            return new Book(this);
        }

    }

    /**
     * Instantiates a new Book.
     *
     * @param builder the builder
     */
    public Book(BookBuilder builder) {
        super(builder);
        this.genre = builder.genre;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.publicationDate = builder.publicationDate;
        this.statusOfBookAvailability = builder.statusOfBookAvailability;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public Date getPublicationDate() {

        return Date.valueOf(publicationDate);
    }

    public String getStatusOfBookAvailability() {

        return String.valueOf(statusOfBookAvailability);
    }

    public String getIsbn() {
        return isbn;
    }
}
