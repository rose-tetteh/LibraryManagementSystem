package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.PatronDAO;
import com.example.librarymanagementsystem.model.Patron;

import java.util.List;
import java.util.Optional;

public class PatronService {
    private final PatronDAO patronDAO;

    public PatronService() {
        this.patronDAO = new PatronDAO();
    }

    public List<Patron> getAllPatrons() {
        return patronDAO.getAllPatrons();
    }

    public Patron addPatron(Patron patron) {

        patronDAO.addPatron(patron);
        return patron;
    }

    public boolean updatePatron(String patronLibraryId,Patron patron) {
        return patronDAO.updatePatron(patronLibraryId, patron);
    }

    public boolean deletePatron(String patronLibraryId) {
        return patronDAO.deletePatron(patronLibraryId);
    }

    public Optional<Patron> getPatronById(String patronLibraryId) {
        return patronDAO.getPatronByLibraryId(patronLibraryId);
    }

    public Optional<Patron> getPatronByEmail(String email) {
        return patronDAO.getPatronByEmail(email);
    }
}
