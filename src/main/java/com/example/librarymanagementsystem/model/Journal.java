package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.Status;

import java.time.LocalDate;

/**
 * The type Journal.
 */
public class Journal extends LibraryResource{
    private final String genre;
    private final int volumeNumber;
    private final LocalDate publicationDate;
    private final Status statusOfJournalAvailability;


    public static abstract class JournalBuilder extends LibraryResource.Builder<JournalBuilder>{
        private String genre;
        private int volumeNumber;
        private LocalDate publicationDate;
        private Status statusOfJournalAvailability;

        public JournalBuilder() {}

        public JournalBuilder genre(String genre){
            this.genre = genre;
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
        this.publicationDate = builder.publicationDate;
        this.volumeNumber = builder.volumeNumber;
        this.statusOfJournalAvailability = builder.statusOfJournalAvailability;
    }

    public String getGenre() {
        return genre;
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
