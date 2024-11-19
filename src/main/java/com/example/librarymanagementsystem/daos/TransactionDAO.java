package com.example.librarymanagementsystem.daos;

import com.example.librarymanagementsystem.enums.Status;
import com.example.librarymanagementsystem.model.Transaction;
import com.example.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TransactionDAO {
    private String query;
    private final Connection connection = DatabaseConnection.getConnection();
    private PreparedStatement preparedStatement;
    private final Stack<Transaction> transactionStack = new Stack<>();

    public boolean addTransaction(Transaction transaction) {
        query = "INSERT INTO transactions (transactionId, resourceId, patronLibraryId, transaction_date, due_date, transaction_status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, transaction.getTransactionId());
            preparedStatement.setInt(2, transaction.getResourceId());
            preparedStatement.setInt(3, transaction.getPatronLibraryId());
            preparedStatement.setDate(4, Date.valueOf(transaction.getTransactionDate()));
            preparedStatement.setDate(5, transaction.getDueDate());
            preparedStatement.setString(6, transaction.getTransactionStatus().name());
            if (preparedStatement.executeUpdate() > 0) {
                transactionStack.push(transaction);
                return true;
            }
            return false;
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Undo last transaction transaction.
     *
     * @return the transaction
     */
    public Transaction undoLastTransaction() {
        if (!transactionStack.isEmpty()) {
            Transaction lastTransaction = transactionStack.pop();
            try {
                query = "DELETE FROM transactions WHERE transactionId = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, lastTransaction.getTransactionId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
               throw new RuntimeException(e.getMessage());
            }
            return lastTransaction;
        }
        return null;
    }

    /**
     * Delete transaction boolean.
     *
     * @param transactionId the transaction id
     * @return the boolean
     */
    public boolean deleteTransaction(int transactionId) {
        query = "DELETE FROM transaction WHERE transactionId = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, transactionId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets transactions by patron library id.
     *
     * @param patronLibraryId the patron library id
     * @return the transactions by patron library id
     */
    public List<Transaction> getTransactionsByPatronLibraryId(String patronLibraryId) {
        query = "SELECT * FROM transactions WHERE patronLibraryId = ?";
        List<Transaction> transactions = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patronLibraryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapResultSetToTransaction(resultSet));
            }
        } catch (SQLException e) {
           throw new RuntimeException(e.getMessage());
        }
        return transactions;
    }

    private Transaction mapResultSetToTransaction(ResultSet resultSet) throws SQLException {
        return new Transaction.TransactionBuilder()
                .transactionId(resultSet.getInt("transactionId"))
                .resourceId(resultSet.getInt("resourceId"))
                .patronLibraryId(resultSet.getInt("patronLibraryId"))
                .transactionDate(resultSet.getDate("transaction_date").toLocalDate())
                .dueDate(resultSet.getDate("due_date").toLocalDate())
                .transactionStatus(Status.valueOf(resultSet.getString("transaction_status")))
                .build();
    }
}
