package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.model.LibraryResource;

import java.sql.SQLException;
import java.util.List;

public interface LibraryResourceDAO<T extends LibraryResource> {
    void addResource(T resource) throws SQLException;
    void updateResource(T resource) throws SQLException;
    void deleteResource(int resourceId) throws SQLException;
    T getResourceById(int resourceId) throws SQLException;
    List<T> getAllResources() throws SQLException;
}
