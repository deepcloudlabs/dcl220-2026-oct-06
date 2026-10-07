package com.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BridgePatternThreadPool {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Application is just started.");
		// Executors.newXYZ() --> ExecutorService -- submit --> Future
		//                  bridge                    bridge
		ExecutorService es = Executors.newFixedThreadPool(8);		
		System.out.println(es.getClass());
		Future<Integer> result = es.submit(() -> {
			TimeUnit.SECONDS.sleep(5);
			return 42;
		});	
		System.out.println(result.getClass());
		System.out.println(result.get());
		es.shutdown();
		System.out.println("Application is stoped.");
		// ExecutorService -----> Future
		//     submit               get
	}

}
