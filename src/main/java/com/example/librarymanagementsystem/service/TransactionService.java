package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.daos.TransactionDAO;
import com.example.librarymanagementsystem.model.Transaction;

import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO;

    public boolean addTransaction(Transaction transaction) {
        return transactionDAO.addTransaction(transaction);
    }

    public Transaction undoLastTransaction(){
        return transactionDAO.undoLastTransaction();
    }

    public boolean deleteTransaction(int transactionId){
        return transactionDAO.deleteTransaction(transactionId);
    }

    public List<Transaction> getTransactionsByPatronLibraryId(String patronLibraryId){
        return transactionDAO.getTransactionsByPatronLibraryId(patronLibraryId);
    }

    public List<Transaction> getAllTransactions(){
        return transactionDAO.getAllTransactions();
    }
}
