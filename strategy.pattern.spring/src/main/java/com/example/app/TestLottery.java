package com.example.app;

import com.example.injector.StrategyInjector;
import com.example.service.LotteryService;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestLottery {
    public static void main(String[] args) {
        LotteryService lottery= new LotteryService();
        StrategyInjector.inject(lottery);
        lottery.draw();
        lottery.printNumbers();
    }
}
