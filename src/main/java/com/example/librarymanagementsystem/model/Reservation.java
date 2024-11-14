package com.example.librarymanagementsystem.model;

import java.time.LocalDate;

public class Reservation {

    private int reservationId;
    private int resourceId;
    private int userId;
    private Status reservationStatus;
    private LocalDate reservationDate;
    private int queuePosition;

    /**
     * Instantiates a new Reservation.
     *
     * @param reservationId     the reservation id
     * @param resourceId            the resource id
     * @param userId            the user id
     * @param reservationStatus the reservation status
     * @param reservationDate   the reservation date
     * @param queuePosition     the queue position
     */
    public Reservation(int reservationId, int resourceId, int userId, Status reservationStatus, LocalDate reservationDate, int queuePosition) {
        this.reservationId = reservationId;
        this.resourceId = resourceId;
        this.userId = userId;
        this.reservationStatus = reservationStatus;
        this.reservationDate = reservationDate;
        this.queuePosition = queuePosition;
    }

    /**
     * Instantiates a new Reservation.
     *
     * @param resourceId            the resource id
     * @param userId            the user id
     * @param reservationStatus the reservation status
     * @param reservationDate   the reservation date
     * @param queuePosition     the queue position
     */
    public Reservation(int resourceId, int userId, Status reservationStatus, LocalDate reservationDate, int queuePosition) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.reservationStatus = reservationStatus;
        this.reservationDate = reservationDate;
        this.queuePosition = queuePosition;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Status getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Status reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }
}
