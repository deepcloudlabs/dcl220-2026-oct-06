package com.example.lottery.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AsyncStandardLotteryService {
	private static ExecutorService tp = Executors.newFixedThreadPool(100);
	// async
	public CompletableFuture<List<Integer>> draw(int max,int size){
		return CompletableFuture.supplyAsync(() ->{
			try{ TimeUnit.SECONDS.sleep(3); }catch(InterruptedException e) {}
			System.err.println("[%s][AsyncStandardLotteryService::draw]".formatted(Thread.currentThread().getName()));

			return ThreadLocalRandom.current().ints(1, max+1)
					.distinct()
					.limit(size)
					.sorted()
					.boxed()
					.collect(Collectors.toList());			
		},tp);
	}
}
