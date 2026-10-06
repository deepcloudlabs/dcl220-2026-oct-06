package com.example.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CachingHandler implements InvocationHandler{
	private final Object target;
	private final static Map<String,Map<Integer,Object>> cache = new ConcurrentHashMap<>();
	
	public CachingHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		var resultMap = cache.get(method.getName());
		if (Objects.isNull(resultMap)) {
			var result = method.invoke(target, args);
			resultMap = new ConcurrentHashMap<Integer,Object>();
			resultMap.put(Arrays.hashCode(args), result);
			cache.put(method.getName(), resultMap);
			return result;
		} else {
			var result = resultMap.get(Arrays.hashCode(args));
			if (Objects.isNull(result)) {
				result = method.invoke(target, args);
				resultMap.put(Arrays.hashCode(args), result);				
			}
			return result;
		}
	}

}
