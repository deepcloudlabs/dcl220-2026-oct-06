package com.example.exercises;

import java.util.List;
import java.util.stream.Collectors;

public class Exercise01 {

	public static void main(String[] args) {
		var names = List.of("jack", "kate", "james", "jin", "ben", "sun"); // Collection API -> storage -> static
		var allNames = names.stream().sorted().collect(Collectors.joining(",")); // Stream API -> processing
		System.out.println(allNames);
		// Streaming Data: i) Storage ii) Processing
		// e-commerce: click data -> rec. sys.
		//        application log -> intrusion detection
		//        transaction log -> fraud detection
		//        market data     -> algorithmic trading/ hi-freq. trading
		
		// reactive programming -> non-blocking -> async. programming, event-driven -> observer pattern
		// i) reactive stream ii) functional programming 
	}

}
