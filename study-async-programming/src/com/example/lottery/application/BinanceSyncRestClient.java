package com.example.lottery.application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BinanceSyncRestClient {
	private static final String BINANCE_REST_API_URL = 
			"https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";

	public static void main(String[] args) throws IOException, InterruptedException {
		// Networking API (Java 11)-> Flow API, Domain-Specific Language, Method Chaining -> jQuery
		// DateTime API (Java 8)
		var client = HttpClient.newHttpClient(); // Factory Method Pattern
		var request = HttpRequest.newBuilder() // Builder Pattern
				                 .uri(URI.create(BINANCE_REST_API_URL))
				                 .header("Accept","application/json")
				                 .build();
		var start = System.currentTimeMillis();
        for (var i=0;i<10;++i) {
        	var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        	System.err.println(response);
        } 
        var stop = System.currentTimeMillis();
        System.err.println("Total Duration: "+(stop-start)+" msec.");
        System.err.println("Average Duration: "+(stop-start)/10.0+" msec.");
	}

}
