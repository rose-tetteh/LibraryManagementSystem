package com.example.librarymanagementsystem.model;

import java.time.LocalDate;

/**
 * The type Journal.
 */
public class Journal extends LibraryResource{
    private String genre;
    private int volumeNumber;
    private LocalDate publicationDate;
    private Status statusOfJournalAvailability;


    /**
     * Instantiates a new Journal.
     *
     * @param title           the title
     * @param author          the author
     * @param genre           the genre
     * @param publicationDate the publication date
     * @param volumeNumber    the volume number
     */
    public Journal(String title, String author, String genre, LocalDate publicationDate, int volumeNumber) {
        super(title, author);
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.volumeNumber = volumeNumber;
    }

    /**
     * Instantiates a new Journal.
     *
     * @param resourceId      the resource id
     * @param title           the title
     * @param author          the author
     * @param genre           the genre
     * @param publicationDate the publication date
     * @param volumeNumber    the volume number
     */
    public Journal(int resourceId, String title, String author, String genre, LocalDate publicationDate, int volumeNumber) {
        super(resourceId, title, author);
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.volumeNumber = volumeNumber;
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

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public Status getStatusOfJournalAvailability() {
        return statusOfJournalAvailability;
    }

    public void setStatusOfJournalAvailability(Status statusOfJournalAvailability) {
        this.statusOfJournalAvailability = statusOfJournalAvailability;
    }
}
