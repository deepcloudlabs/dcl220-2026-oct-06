package com.example.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise01 {

	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<>(List.of(1,2,3,4,5,6));
		var unmodifiebleList = Collections.unmodifiableList(numbers);
		System.out.println(unmodifiebleList.getClass());

	}

}
