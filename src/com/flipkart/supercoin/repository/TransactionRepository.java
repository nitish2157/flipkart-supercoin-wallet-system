package com.flipkart.supercoin.repository;

import com.flipkart.supercoin.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }
}
