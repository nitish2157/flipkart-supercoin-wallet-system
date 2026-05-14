package com.flipkart.supercoin.service;

import com.flipkart.supercoin.constant.Tier;
import com.flipkart.supercoin.constant.TransactionStatus;
import com.flipkart.supercoin.constant.TransactionType;
import com.flipkart.supercoin.exception.InsufficientBalanceException;
import com.flipkart.supercoin.exception.UserNotLoggedInException;
import com.flipkart.supercoin.model.CoinBatch;
import com.flipkart.supercoin.model.Transaction;
import com.flipkart.supercoin.model.User;

import java.util.LinkedList;
import java.util.Queue;

public class WalletService {

    private final AuthService authService;
    private final TransactionService transactionService;

    private int currentDay;

    public WalletService(AuthService authService,
                         TransactionService transactionService) {

        this.authService = authService;
        this.transactionService = transactionService;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public void earnCoins(String orderId, int orderAmount) {

        User user = validateUser();

        removeExpiredCoins(user);

        int coins;

        if (user.getTier() == Tier.REGULAR) {

            coins = (orderAmount / 100) * 2;
            coins = Math.min(coins, 50);

        } else {

            coins = (orderAmount / 100) * 4;
            coins = Math.min(coins, 100);
        }

        CoinBatch batch = new CoinBatch(
                coins,
                currentDay,
                currentDay + 30
        );

        user.getWallet().getCoinBatches().offer(batch);

        user.addLifetimeEarnedCoins(coins);

        autoUpgradeTier(user);

        transactionService.createTransaction(
                TransactionType.CREDIT,
                TransactionStatus.COMPLETED,
                coins,
                currentDay,
                orderId,
                user.getName()
        );

        System.out.println("Earned " + coins + " coins");
    }

    public void spendCoins(String orderId, int coinsToSpend) {

        User user = validateUser();

        removeExpiredCoins(user);

        int balance = getBalance(user);

        if (balance < coinsToSpend) {

            transactionService.createTransaction(
                    TransactionType.DEBIT,
                    TransactionStatus.REJECTED,
                    coinsToSpend,
                    currentDay,
                    orderId,
                    user.getName()
            );

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        int originalCoinsToSpend = coinsToSpend;

        Queue<CoinBatch> queue =
                user.getWallet().getCoinBatches();

        while (coinsToSpend > 0) {

            CoinBatch batch = queue.peek();

            if (batch.getCoins() <= coinsToSpend) {

                coinsToSpend -= batch.getCoins();

                queue.poll();

            } else {

                batch.setCoins(
                        batch.getCoins() - coinsToSpend
                );

                coinsToSpend = 0;
            }
        }

        transactionService.createTransaction(
                TransactionType.DEBIT,
                TransactionStatus.COMPLETED,
                originalCoinsToSpend,
                currentDay,
                orderId,
                user.getName()
        );

        System.out.println("Coins spent successfully");
    }

    public void cancelOrder(String orderId) {

        User user = validateUser();

        int refundCoins = 0;

        for (Transaction transaction :
                transactionService.getAllTransactions()) {

            if (transaction.getOrderId() != null
                    && transaction.getOrderId().equals(orderId)
                    && transaction.getTransactionType()
                    == TransactionType.DEBIT
                    && transaction.getTransactionStatus()
                    == TransactionStatus.COMPLETED) {

                refundCoins = transaction.getAmount();

                break;
            }
        }

        if (refundCoins == 0) {

            System.out.println(
                    "No transaction found for refund"
            );

            return;
        }

        CoinBatch refundBatch = new CoinBatch(
                refundCoins,
                currentDay,
                currentDay + 30
        );

        user.getWallet()
                .getCoinBatches()
                .offer(refundBatch);

        transactionService.createTransaction(
                TransactionType.REFUND,
                TransactionStatus.COMPLETED,
                refundCoins,
                currentDay,
                orderId,
                user.getName()
        );

        System.out.println("Refund successful");
    }

    public void checkBalance() {

        User user = validateUser();

        removeExpiredCoins(user);

        int balance = getBalance(user);

        System.out.println(
                "Current Balance : " + balance
        );

        System.out.println(
                "Current Tier : " + user.getTier()
        );
    }

    public void viewTransactionHistory() {

        User user = validateUser();

        transactionService.printTransactions(
                user.getName()
        );
    }

    private User validateUser() {

        User user = authService.getCurrentUser();

        if (user == null) {

            throw new UserNotLoggedInException(
                    "No user logged in"
            );
        }

        return user;
    }

    private int getBalance(User user) {

        int total = 0;

        for (CoinBatch batch :
                user.getWallet().getCoinBatches()) {

            total += batch.getCoins();
        }

        return total;
    }

    private void removeExpiredCoins(User user) {

        Queue<CoinBatch> queue =
                user.getWallet().getCoinBatches();

        Queue<CoinBatch> activeQueue =
                new LinkedList<>();

        while (!queue.isEmpty()) {

            CoinBatch batch = queue.poll();

            if (currentDay > batch.getExpiryDay()) {

                transactionService.createTransaction(
                        TransactionType.EXPIRY,
                        TransactionStatus.COMPLETED,
                        batch.getCoins(),
                        currentDay,
                        null,
                        user.getName()
                );

            } else {

                activeQueue.offer(batch);
            }
        }

        user.getWallet()
                .getCoinBatches()
                .addAll(activeQueue);
    }

    private void autoUpgradeTier(User user) {

        if (user.getTier() == Tier.REGULAR
                && user.getLifetimeEarnedCoins() >= 300) {

            user.setTier(Tier.PLUS);

            System.out.println(
                    "User upgraded to PLUS tier"
            );
        }
    }
}