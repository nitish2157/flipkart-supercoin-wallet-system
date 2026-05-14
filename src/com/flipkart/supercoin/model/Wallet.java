package com.flipkart.supercoin.model;

import java.util.LinkedList;
import java.util.Queue;

public class Wallet {

    private Queue<CoinBatch> coinBatches = new LinkedList<>();

    public Queue<CoinBatch> getCoinBatches() {
        return coinBatches;
    }
}