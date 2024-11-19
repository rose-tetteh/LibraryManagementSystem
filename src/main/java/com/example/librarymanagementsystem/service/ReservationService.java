package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.ReservationDAO;
import com.example.librarymanagementsystem.model.Reservation;

import java.util.List;
import java.util.Queue;

public class ReservationService {
    private ReservationDAO reservationDAO;

    public boolean addReservation(Reservation reservation) {
        return reservationDAO.requestReservation(reservation);
    }

    public Queue<Reservation> getReservationByPatronLibraryId(String patronLibraryId) {
        return reservationDAO.getReservationsByPatronLibraryId(patronLibraryId);
    }

    public boolean deleteReservation(int reservationId){
        return reservationDAO.deleteReservation(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }
}
