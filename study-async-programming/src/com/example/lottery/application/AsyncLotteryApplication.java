package com.example.lottery.application;

import java.util.concurrent.TimeUnit;

import com.example.lottery.service.AsyncStandardLotteryService;

public class AsyncLotteryApplication {
	// Throughput: 33 lottery numbers per second
	public static void main(String[] args) throws InterruptedException {
		var lotteryService = new AsyncStandardLotteryService();
		System.err.println("Application is started!");
		for (var i = 0; i < 100; ++i) {
			lotteryService.draw(60, 6)
			              .thenAcceptAsync(numbers -> System.err.println("[%s] %s".formatted(Thread.currentThread().getName(),numbers)));
			System.err.println("[%s] i: %d".formatted(Thread.currentThread().getName(),i));
		}
		System.err.println("Application ends!");
		TimeUnit.SECONDS.sleep(60);
	}

}
