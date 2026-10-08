package com.example;

import java.util.LinkedList;
import java.util.List;

public class ObjectCachingExample2 {

	// -Xms128m -Xmx128m -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+PrintStringDeduplicationStatistics

	private static final List<String> LOTS_OF_STRINGS = new LinkedList<>();

	public static void main(String[] args) throws Exception {
		int iteration = 0;
		while (true) {
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 1000; j++) {
					LOTS_OF_STRINGS.add(new String("String " + i));
				}
			}
			iteration++;
			System.out.println("Survived Iteration: " + iteration);
			Thread.sleep(10);
		}
	}

}
