package com.example.lottery.application;

import com.example.lottery.service.StandardLotteryService;

public class LotteryApplication {
	// Throughput: 0.33 lottery numbers per second
	public static void main(String[] args) {
		var lotteryService = new StandardLotteryService();
		System.err.println("Application is started!");
		for (var i=0;i<10;++i) {
			var numbers = lotteryService.draw(60, 6); // sync
			System.err.println(numbers);			
		}
		System.err.println("Application ends!");
	}

}
