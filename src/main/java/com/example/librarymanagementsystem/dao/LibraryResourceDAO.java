package com.example.librarymanagementsystem.dao;

import com.example.librarymanagementsystem.model.LibraryResource;

import java.sql.SQLException;
import java.util.List;

public interface LibraryResourceDAO<T extends LibraryResource> {
    void addResource(T resource) throws SQLException;
    List<T> getAllResources() throws SQLException;
}
