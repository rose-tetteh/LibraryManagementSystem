package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.Status;
import java.time.LocalDate;

public class Reservation {

    private final int reservationId;
    private final int resourceId;
    private final int patronLibraryId;
    private final Status reservationStatus;
    private final LocalDate reservationDate;
    private final int queuePosition;

    /**
     * The type Reservation builder.
     */
    public static class ReservationBuilder{

        private int reservationId;
        private int resourceId;
        private int patronLibraryId;
        private Status reservationStatus;
        private LocalDate reservationDate;
        private int queuePosition;

        public ReservationBuilder reservationId(int reservationId){
            this.reservationId = reservationId;
            return this;
        }

        public ReservationBuilder resourceId(int resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public ReservationBuilder patronLibraryId(int patronLibraryId){
            this.patronLibraryId = patronLibraryId;
            return this;
        }

        public ReservationBuilder reservationStatus(Status reservationStatus){
            this.reservationStatus= reservationStatus;
            return this;
        }

        public ReservationBuilder reservationDate(LocalDate reservationDate){
            this.reservationDate = reservationDate;
            return this;
        }

        public ReservationBuilder queuePosition(int queuePosition){
            this.queuePosition = queuePosition;
            return this;
        }

        public Reservation build(){
            return new Reservation(this);
        }
    }

    /**
     * Instantiates a new Reservation.
     *
     * @param builder the builder
     */
    public Reservation(ReservationBuilder builder) {
        this.reservationId = builder.reservationId;
        this.resourceId = builder.resourceId;
        this.patronLibraryId = builder.patronLibraryId;
        this.reservationStatus = builder.reservationStatus;
        this.reservationDate = builder.reservationDate;
        this.queuePosition = builder.queuePosition;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getPatronLibraryId() {
        return patronLibraryId;
    }

    public Status getReservationStatus() {
        return reservationStatus;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

}
