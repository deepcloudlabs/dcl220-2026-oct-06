package com.example.lottery.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StandardLotteryService {
	public List<Integer> draw(int max,int size){
		try{ TimeUnit.SECONDS.sleep(3); }catch(InterruptedException e) {}
		return ThreadLocalRandom.current().ints(1, max+1)
				          .distinct()
				          .limit(size)
				          .sorted()
				          .boxed()
				          .collect(Collectors.toList());
	}
}
