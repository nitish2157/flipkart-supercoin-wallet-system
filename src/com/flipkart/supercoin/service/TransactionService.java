package com.flipkart.supercoin.service;

import com.flipkart.supercoin.constant.TransactionStatus;
import com.flipkart.supercoin.constant.TransactionType;
import com.flipkart.supercoin.model.Transaction;
import com.flipkart.supercoin.repository.TransactionRepository;

import java.util.List;
import java.util.UUID;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(TransactionType type,
                                         TransactionStatus status,
                                         int amount,
                                         int day,
                                         String orderId,
                                         String userName) {

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                type,
                status,
                amount,
                day,
                orderId,
                userName
        );

        transactionRepository.save(transaction);

        return transaction;
    }

    public void printTransactions(String userName) {

        List<Transaction> transactions =
                transactionRepository.getAllTransactions();

        for (Transaction transaction : transactions) {

            if (transaction.getUserName().equals(userName)) {

                System.out.println(transaction);
            }
        }
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }
}
