package com.flipkart.supercoin;

import com.flipkart.supercoin.constant.Tier;
import com.flipkart.supercoin.model.User;
import com.flipkart.supercoin.repository.TransactionRepository;
import com.flipkart.supercoin.repository.UserRepository;
import com.flipkart.supercoin.service.AuthService;
import com.flipkart.supercoin.service.TransactionService;
import com.flipkart.supercoin.service.WalletService;

public class Driver {

    public static void main(String[] args) {

        UserRepository userRepository =
                new UserRepository();

        TransactionRepository transactionRepository =
                new TransactionRepository();

        AuthService authService =
                new AuthService(userRepository);

        TransactionService transactionService =
                new TransactionService(
                        transactionRepository
                );

        WalletService walletService =
                new WalletService(
                        authService,
                        transactionService
                );

        User u1 = new User("u1", Tier.REGULAR);
        User u2 = new User("u2", Tier.PLUS);

        userRepository.save(u1);
        userRepository.save(u2);

        // Day 1
        walletService.setCurrentDay(1);

        authService.login("u1");

        walletService.earnCoins("O-101", 3000);

        // Day 10
        walletService.setCurrentDay(10);

        walletService.earnCoins("O-102", 5000);

        // Day 15
        walletService.setCurrentDay(15);

        walletService.spendCoins("O-103", 30);

        // Day 35
        walletService.setCurrentDay(35);

        walletService.checkBalance();

        try {

            walletService.spendCoins("O-104", 100);

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

        walletService.spendCoins("O-105", 40);

        // Day 40
        walletService.setCurrentDay(40);

        walletService.cancelOrder("O-105");

        // Transaction History
        walletService.viewTransactionHistory();

        // Logout u1
        authService.logout();

        // Login u2
        authService.login("u2");

        // u2 earns coins
        walletService.earnCoins("O-201", 2000);

        // Check balance
        walletService.checkBalance();
    }
}
