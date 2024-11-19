package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.LibrarianDAO;
import com.example.librarymanagementsystem.model.Librarian;

import java.util.List;
import java.util.Optional;

public class LibrarianService {
    private LibrarianDAO librarianDAO;

    public List<Librarian> getAllLibrarians() {
        return librarianDAO.getAllLibrarians();
    }

    public void addLibrarian(Librarian librarian) {
        librarianDAO.addLibrarian(librarian);
    }

    public Optional<Librarian> getLibrarianByUserId(int userId) {
        return librarianDAO.getLibrarianByUserId(userId);
    }

    public Optional<Librarian> getLibrarianByEmail(String email) {
        return librarianDAO.getLibrarianByEmail(email);
    }

    public boolean updateLibrarian(int userId, Librarian librarian) {
        return librarianDAO.updateLibrarian(userId, librarian);
    }

    public boolean deleteLibrarian(int userId) {
        return librarianDAO.deleteLibrarian(userId);
    }
}
