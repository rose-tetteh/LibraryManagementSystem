package com.example.librarymanagementsystem.model;

import java.time.LocalDate;

public class Book extends LibraryResource{
    private String genre;
    private LocalDate publicationDate;
    private Status statusOfBookAvailability;


    /**
     * Instantiates a new Book.
     *
     * @param title           the title
     * @param author          the author
     * @param genre           the genre
     * @param publicationDate the publication date
     */
    public Book(String title, String author, String genre, LocalDate publicationDate) {
        super(title, author);
        this.genre = genre;
        this.publicationDate = publicationDate;
    }

    /**
     * Instantiates a new Book.
     *
     * @param resourceId      the resource id
     * @param title           the title
     * @param author          the author
     * @param genre           the genre
     * @param publicationDate the publication date
     */
    public Book(int resourceId, String title, String author, String genre, LocalDate publicationDate) {
        super(resourceId, title, author);
        this.genre = genre;
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
