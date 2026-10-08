package com.example;

import java.util.List;

public class ObjectCachingExample3 {
	// -XX:AutoBoxCacheMax=1024
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		Integer x = Integer.valueOf(42);
		Integer y = 42;
		Integer u = 549;
		Integer v = 549;
		// 12B + 4B = 16B
		//       4B
		int z = 42; // 4B
		List<Integer> numbers; // will work like List<int> numbers; in the future!
		System.err.println("x==y: "+(x==y));
		System.err.println("u==v: "+(u==v));
		
	}

}
