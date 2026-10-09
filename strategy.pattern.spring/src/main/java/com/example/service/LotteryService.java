package com.example.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.annotation.CollectionSize;
import com.example.annotation.Strategy;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service
public class LotteryService {
	private List<Integer> numbers;
	@Strategy(size = CollectionSize.SMALL)
	@Autowired(required = true)
	@Qualifier(value = "HeapSorting")
	private Sorter<Integer> sorter;

	public void setSorter(Sorter<Integer> sorter) {
		this.sorter = sorter;
	}

	public void draw() {
		numbers = new Random().ints(1, 50).distinct().limit(6).boxed().collect(Collectors.toList());
		sorter.sort(numbers);
	}

	public void printNumbers() {
		System.out.println(numbers);
	}
}
