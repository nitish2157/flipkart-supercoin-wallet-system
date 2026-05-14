package com.flipkart.supercoin.model;

public class CoinBatch {

    private int coins;
    private int earnedDay;
    private int expiryDay;

    public CoinBatch(int coins, int earnedDay, int expiryDay) {
        this.coins = coins;
        this.earnedDay = earnedDay;
        this.expiryDay = expiryDay;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getEarnedDay() {
        return earnedDay;
    }

    public int getExpiryDay() {
        return expiryDay;
    }

    @Override
    public String toString() {
        return "CoinBatch{" +
                "coins=" + coins +
                ", expiryDay=" + expiryDay +
                '}';
    }
}
