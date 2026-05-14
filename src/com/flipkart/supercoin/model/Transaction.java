package com.flipkart.supercoin.model;

import com.flipkart.supercoin.constant.TransactionStatus;
import com.flipkart.supercoin.constant.TransactionType;

public class Transaction {

    private String transactionId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private int amount;
    private int day;
    private String orderId;
    private String userName;

    public Transaction(String transactionId,
                       TransactionType transactionType,
                       TransactionStatus transactionStatus,
                       int amount,
                       int day,
                       String orderId,
                       String userName) {

        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.day = day;
        this.orderId = orderId;
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public int getAmount() {
        return amount;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {

        if (orderId == null) {

            return "[Day " + day + "] " +
                    transactionType + " " +
                    amount + " - " +
                    transactionStatus;
        }

        return "[Day " + day + "] " +
                transactionType + " " +
                amount + " (" + orderId + ") - " +
                transactionStatus;
    }
}
