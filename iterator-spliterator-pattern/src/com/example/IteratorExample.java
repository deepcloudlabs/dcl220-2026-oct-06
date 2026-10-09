package com.example;

import java.util.Iterator;
import java.util.Set;

public class IteratorExample {

	public static void main(String[] args) {
		var numbers = Set.of(4,8,23,42,15,16);
		
		// outer loop #1
		for (Iterator<Integer> iter = numbers.iterator(); iter.hasNext();) {
			int x = iter.next();
			System.out.println("x="+x);
		}
		
		// outer loop #2
		for (var x : numbers) {
			System.out.println("x="+x);			
		}
		
		// inner loop #1
		numbers.forEach(System.out::println);
		
		// inner loop #2
		numbers.stream().forEach(System.out::println);
		
		// inner loop #3
		numbers.parallelStream().forEach(System.err::println);
	}

}
