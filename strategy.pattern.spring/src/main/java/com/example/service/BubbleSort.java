package com.example.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Component(value = "BubbleSorting")
public class BubbleSort<T extends Comparable<? super T>> implements Sorter<T> {

	public List<T> sort(List<T> list) {
		System.out.println("Sorting using Bubble Sort Algorithm...");
		Collections.sort(list);
		return list;
	}

}
