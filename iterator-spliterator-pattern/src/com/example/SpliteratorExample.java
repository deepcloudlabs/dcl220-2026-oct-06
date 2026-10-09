package com.example;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SpliteratorExample {

	public static void main(String[] args) {
		List<Integer> numbers1 = IntStream.range(0, 10).boxed().toList();
		Spliterator<Integer> spliterator = numbers1.spliterator();

		// Spliterator is used for traversing sequences
		System.err.println("=======spliterator===============");
		while (spliterator.tryAdvance(System.err::println));

		// Spliterator is used for partitioning sequences
		System.err.println("=====spliterator estimate size====");
		List<Integer> numbers2 = IntStream.range(0, 10).boxed().toList();
		Spliterator<Integer> spliterator1 = numbers2.spliterator();
		Spliterator<Integer> spliterator2 = spliterator1.trySplit();
		System.err.println("spliterator1.estimateSize(): "+spliterator1.estimateSize());
		System.err.println("spliterator2.estimateSize(): "+spliterator2.estimateSize());
		System.err.println("=======spliterator1===============");
		while (spliterator1.tryAdvance(System.err::println));
		System.err.println("=======spliterator2================");
		while (spliterator2.tryAdvance(System.err::println));
		
		// Spliterator has characteristics
		System.err.println("=======spliterator3================");
		List<Integer> numbers3 = IntStream.range(0, 10).boxed().collect(Collectors.toList());
		Spliterator<Integer> spliterator3 = numbers3.spliterator();
		System.err.println("Characteristics: "+spliterator3.characteristics());
		// if it's capable of returning an exact number of elements with the estimateSize() method
		System.err.println("SIZED     :"+spliterator3.hasCharacteristics(Spliterator.SIZED));
		// if it's iterating through a sorted source 
		System.err.println("SORTED    :"+spliterator3.hasCharacteristics(Spliterator.SORTED));
		// if we split the instance using a trySplit() method and obtain Spliterators that are SIZED as well
		System.err.println("SUBSIZED  :"+spliterator3.hasCharacteristics(Spliterator.SUBSIZED));
		// if source can be safely modified concurrently
		System.err.println("CONCURRENT:"+spliterator3.hasCharacteristics(Spliterator.CONCURRENT));
		// if for each pair of encountered elements x, y, !x.equals(y)
		System.err.println("DISTINCT  :"+spliterator3.hasCharacteristics(Spliterator.DISTINCT));
		// if elements held by source can't be structurally modified
		System.err.println("IMMUTABLE :"+spliterator3.hasCharacteristics(Spliterator.IMMUTABLE));
		// if source holds nulls or not
		System.err.println("NONNULL   :"+spliterator3.hasCharacteristics(Spliterator.NONNULL));
		// if iterating over an ordered sequence
		System.err.println("ORDERED   :"+spliterator3.hasCharacteristics(Spliterator.ORDERED));
	}

}
