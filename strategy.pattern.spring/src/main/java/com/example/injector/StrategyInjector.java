package com.example.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.annotation.CollectionSize;
import com.example.annotation.Strategy;
import com.example.service.BubbleSort;
import com.example.service.HeapSort;
import com.example.service.InsertionSort;
import com.example.service.Sorter;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class StrategyInjector {

	public static Object inject(Object obj) {
		// Reflection API is used...
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Strategy.class)) {
				Strategy st = field.getAnnotation(Strategy.class);
				CollectionSize cs = st.size();
				Sorter<Integer> sorter = null;
				switch (cs) {
				case SMALL:
					sorter = new InsertionSort<Integer>();
					break;
				case BIG:
					sorter = new BubbleSort<Integer>();
					break;
				case LARGE:
					sorter = new HeapSort<Integer>();
					break;
				}
				String setterName = "set" + field.getName().toUpperCase().charAt(0) + field.getName().substring(1);
				for (Method method : obj.getClass().getMethods()) {
					if (method.getName().equals(setterName)) {
						try {
							method.invoke(obj, sorter); // setter injection
						} catch (Exception ex) {
							Logger.getLogger(StrategyInjector.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			}
		}
		return obj;
	}
}
