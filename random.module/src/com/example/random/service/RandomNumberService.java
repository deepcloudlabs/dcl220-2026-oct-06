package com.example.random.service;

import java.util.List;
import java.util.stream.IntStream;

public interface RandomNumberService {
	List<Integer> draw(int max, int size);

	default List<List<Integer>> draw(int max, int size, int column){
		return IntStream.range(0,column)
				        .mapToObj( _ -> draw(max,size))
				        .toList();
	}

}
