package com.example.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Component(value = "HeapSorting")
public class HeapSort<T extends Comparable<? super T>> implements Sorter<T> {

	public List<T> sort(List<T> list) {
		System.out.println("Sorting using Heap Sort Algorithm...");
		Collections.sort(list);
		return list;
	}

}
