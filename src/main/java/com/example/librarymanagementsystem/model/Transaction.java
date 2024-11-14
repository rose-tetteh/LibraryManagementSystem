package com.example.librarymanagementsystem.model;

import java.time.LocalDate;

public class Transaction {

    private int transactionId;
    private int resourceId;
    private int userId;
    private LocalDate transactionDate;
    private LocalDate dueDate;
    private Status transactionStatus;


    /**
     * Instantiates a new Transaction.
     *
     * @param transactionId   the transaction id
     * @param resourceId      the resource id
     * @param userId          the user id
     * @param transactionDate the transaction date
     * @param dueDate         the due date
     */
    public Transaction(int transactionId, int resourceId, int userId, LocalDate transactionDate, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.resourceId = resourceId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
    }

    /**
     * Instantiates a new Transaction.
     *
     * @param resourceId        the resource id
     * @param userId            the user id
     * @param transactionDate   the transaction date
     * @param dueDate           the due date
     * @param transactionStatus the transaction status
     */
    public Transaction(int resourceId, int userId, LocalDate transactionDate, LocalDate dueDate, Status transactionStatus) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.dueDate = dueDate;
        this.transactionStatus = transactionStatus;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Status getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Status transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
