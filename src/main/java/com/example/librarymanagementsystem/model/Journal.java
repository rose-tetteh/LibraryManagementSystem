package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.Status;

import java.time.LocalDate;

/**
 * The type Journal.
 */
public class Journal extends LibraryResource{
    private final String genre;
    private final String author;
    private final int volumeNumber;
    private final LocalDate publicationDate;
    private final Status statusOfJournalAvailability;



    public static class JournalBuilder extends LibraryResource.Builder<JournalBuilder>{
        private String genre;
        private String author;
        private int volumeNumber;
        private LocalDate publicationDate;
        private Status statusOfJournalAvailability;

        public JournalBuilder() {}

        @Override
        public JournalBuilder getThis() {
            return null;
        }

        public JournalBuilder genre(String genre){
            this.genre = genre;
            return this;
        }

        public JournalBuilder author(String author){
            this.author = author;
            return getThis();
        }

        public JournalBuilder volumeNumber(int volumeNumber){
            this.volumeNumber = volumeNumber;
            return this;
        }

        public JournalBuilder publicationDate(LocalDate publicationDate){
            this.publicationDate = publicationDate;
            return this;
        }

        public JournalBuilder statusOfJournalAvailability(Status statusOfJournalAvailability){
            this.statusOfJournalAvailability = statusOfJournalAvailability;
            return this;
        }

        public Journal build(){
            return new Journal(this);
        }

    }

    /**
     * Instantiates a new Journal.
     *
     * @param builder the builder
     */
    public Journal(JournalBuilder builder) {
        super(builder);
        this.genre = builder.genre;
        this.author = builder.author;
        this.publicationDate = builder.publicationDate;
        this.volumeNumber = builder.volumeNumber;
        this.statusOfJournalAvailability = builder.statusOfJournalAvailability;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }
    public Status getStatusOfJournalAvailability() {
        return statusOfJournalAvailability;
    }
}
