package com.example.lottery.application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BinanceAsyncRestClient {
	private static final String BINANCE_REST_API_URL = 
			"https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";
	private static AtomicInteger counter = new AtomicInteger(0);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder()
				                 .uri(URI.create(BINANCE_REST_API_URL))
				                 .header("Accept","application/json")
				                 .build();
		var start = System.currentTimeMillis();
        for (var i=0;i<10;++i) {
        	client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        	      .thenAcceptAsync(response->{
        	    	  System.err.println(response.body());
        	    	  if (counter.incrementAndGet() == 10) {
        	    		  var stop = System.currentTimeMillis();
        	    		  System.err.println("Total Duration: "+(stop-start)+" msec.");
        	    		  System.err.println("Average Duration: "+(stop-start)/10.0+" msec.");        	    		  
        	    	  }
        	      });
        } 
		TimeUnit.SECONDS.sleep(10);

	}

}
