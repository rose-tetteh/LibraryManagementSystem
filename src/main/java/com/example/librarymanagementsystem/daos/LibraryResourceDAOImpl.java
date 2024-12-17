package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.model.LibraryResource;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class LibraryResourceDAOImpl<T extends LibraryResource> implements LibraryResourceDAO<T> {
    private String query;
    private String query1;
    protected ResultSet resultSet = null;
    protected final Connection connection = DatabaseConnection.getConnection();
    protected PreparedStatement preparedStatement = null;

    @Override
    public List<T> getAllResources() {
        List<T> resources = new ArrayList<>();
        query = getSelectAllQuery();

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                resources.add(mapResultSetToResource(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            closeResources(null, resultSet);
        }
        return resources;
    }

    @Override
    public void addResource(T resource) {
        query = getInsertLibraryResourceQuery();
        query1 = getInsertSpecificResourceQuery();

        try {
            connection.setAutoCommit(false);

            // Insert into LibraryResource
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setLibraryResourceFields(preparedStatement, resource);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            // Insert into specific resource
            if (resultSet.next()) {
                int resourceId = resultSet.getInt(1);
                closeResources(preparedStatement, resultSet); // Close earlier before reuse

                preparedStatement = connection.prepareStatement(query1);
                setSpecificResourceFields(preparedStatement, resource, resourceId);
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Transaction failed and rollback also failed: " + rollbackException.getMessage());
            }
            throw new RuntimeException("Transaction failed, rolled back: " + e.getMessage());
        } finally {
            closeResources(preparedStatement, resultSet);
        }
    }

    @Override
    public void updateResource(T resource) {
        query = getUpdateLibraryResourceQuery();
        query1 = getUpdateSpecificResourceQuery();

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(query);
            setLibraryResourceFields(preparedStatement, resource);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query1);
            setSpecificResourceFields(preparedStatement, resource, resource.getResourceId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Transaction failed and rollback also failed: " + rollbackException.getMessage());
            }
            throw new RuntimeException("Transaction failed, rolled back: " + e.getMessage());
        } finally {
            closeResources(preparedStatement, resultSet);
        }
    }

    @Override
    public void deleteResource(int resourceId) {
        query = getDeleteSpecificResourceQuery();
        query1 = getDeleteLibraryResourceQuery();

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resourceId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, resourceId);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Transaction failed and rollback also failed: " + rollbackException.getMessage());
            }
            throw new RuntimeException("Transaction failed, rolled back: " + e.getMessage());
        } finally {
            closeResources(preparedStatement, resultSet);
        }
    }

    @Override
    public T getResourceById(int resourceId) {
        query = getSelectResourceByIdQuery();
        T resource = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resourceId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resource = mapResultSetToResource(resultSet);
            } else{
                throw new RuntimeException("Resource not found: " + resourceId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Resource not found: " + e.getMessage());
        } finally {
            closeResources(preparedStatement, resultSet);
        }

        return resource;
    }

    // Utility method to close resources
    private void closeResources(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close resources: " + e.getMessage());
        }
    }

    // Abstract methods for type-specific details
    protected abstract String getSelectAllQuery();

    protected abstract String getInsertLibraryResourceQuery();

    protected abstract String getInsertSpecificResourceQuery();

    protected abstract String getUpdateLibraryResourceQuery();

    protected abstract String getUpdateSpecificResourceQuery();

    protected abstract String getDeleteLibraryResourceQuery();

    protected abstract String getDeleteSpecificResourceQuery();

    protected abstract String getSelectResourceByIdQuery();

    protected abstract void setLibraryResourceFields(PreparedStatement preparedStatement, T resource) throws SQLException;

    protected abstract void setSpecificResourceFields(PreparedStatement preparedStatement, T resource, int resourceId) throws SQLException;

    protected abstract T mapResultSetToResource(ResultSet resultSet) throws SQLException;
}
