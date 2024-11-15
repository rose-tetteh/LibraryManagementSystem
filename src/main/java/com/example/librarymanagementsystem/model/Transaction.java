package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.enums.Status;

import java.sql.Date;
import java.time.LocalDate;

public class Transaction {

    private final int transactionId;
    private final int resourceId;
    private final int userId;
    private final LocalDate transactionDate;
    private final LocalDate dueDate;
    private final Status transactionStatus;

    /**
     * The type Transaction builder.
     */
    public static class TransactionBuilder{
        private int transactionId;
        private int resourceId;
        private int userId;
        private Status transactionStatus;
        private LocalDate transactionDate;
        private LocalDate dueDate;

        public TransactionBuilder transactionId(int transactionId){
            this.transactionId = transactionId;
            return this;
        }

        public TransactionBuilder resourceId(int resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public TransactionBuilder userId(int userId){
            this.userId = userId;
            return this;
        }

        public TransactionBuilder transactionStatus(Status transactionStatus){
            this.transactionStatus= transactionStatus;
            return this;
        }

        public TransactionBuilder transactionDate(LocalDate transactionDate){
            this.transactionDate = transactionDate;
            return this;
        }

        public TransactionBuilder dueDate(LocalDate dueDate){
            this.dueDate = dueDate;
            return this;
        }

        public Transaction build(){
            return new Transaction(this);
        }
    }

    /**
     * Instantiates a new Transaction.
     *
     * @param builder the builder
     */
    public Transaction(TransactionBuilder builder) {
        this.transactionId = builder.transactionId;
        this.resourceId = builder.resourceId;
        this.userId = builder.userId;
        this.transactionDate = builder.transactionDate;
        this.dueDate = builder.dueDate;
        this.transactionStatus = builder.transactionStatus;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Date getDueDate() {
        return Date.valueOf(dueDate);
    }

    public Status getTransactionStatus() {
        return transactionStatus;
    }
}
