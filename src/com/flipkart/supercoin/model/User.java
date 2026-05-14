package com.flipkart.supercoin.model;

import com.flipkart.supercoin.constant.Tier;

public class User {

    private String name;
    private Tier tier;
    private Wallet wallet;
    private int lifetimeEarnedCoins;

    public User(String name, Tier tier) {
        this.name = name;
        this.tier = tier;
        this.wallet = new Wallet();
    }

    public String getName() {
        return name;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public int getLifetimeEarnedCoins() {
        return lifetimeEarnedCoins;
    }

    public void addLifetimeEarnedCoins(int coins) {
        this.lifetimeEarnedCoins += coins;
    }
}
