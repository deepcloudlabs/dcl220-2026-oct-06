package com.example;

import java.util.concurrent.ThreadLocalRandom;

public class Exercise03 {

	public static void main(String[] args) {
		// Functional Programming since Java SE 8 
		ThreadLocalRandom.current()
		                 .ints(1, 1000)
		                 .distinct()
		                 .filter(value -> value%2 == 0)
		                 .limit(10)
		                 .sorted()
		                 .boxed()
		                 .toList()
		                 .forEach(System.out::println);
	}

}
