package com.example.exercises;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise02 {

	public static void main(String[] args) {
		try (var tp = Executors.newSingleThreadExecutor(); Job job = new Job();) {

			var syncResult = job.fun();
			System.out.println("Sync Result is %d.".formatted(syncResult));
			job.gun().thenAcceptAsync(asyncResult -> {
				System.out.println("[%s] Async Result is %d.".formatted(Thread.currentThread().getName(), asyncResult));
			}, tp);
			for (var i = 0; i < 1_000; ++i) {
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (Exception e) {
				}
				System.out.println("Application is processing data [%d]".formatted(i));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}

class Job implements AutoCloseable {
	private ExecutorService tp = Executors.newFixedThreadPool(20);

	public int fun() { // sync -> blocking
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
		}
		return 42;
	}

	public CompletableFuture<Integer> gun() { // async -> non-blocking
		return CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
			}
			return 42;
		}, tp);
	}

	@Override
	public void close() throws Exception {
		tp.shutdown();

	}

}