package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.JournalDAO;
import com.example.librarymanagementsystem.model.Journal;

import java.sql.SQLException;
import java.util.List;

public class JournalService {
    private JournalDAO journalDAO;

    public JournalService(JournalDAO journalDAO) {
        this.journalDAO = journalDAO;
    }

    public List<Journal> getAllJournals() {
        return journalDAO.getAllResources();
    }

    public void addJournal(Journal journal) {
        journalDAO.addResource(journal);
    }

    public void updateJournal(Journal journal) {
        journalDAO.updateResource(journal);
    }

    public void deleteJournal(int resourceId) {
        journalDAO.deleteResource(resourceId);
    }

    public Journal getJournalById(int resourceId) throws SQLException {
        return journalDAO.getResourceById(resourceId);
    }
}
