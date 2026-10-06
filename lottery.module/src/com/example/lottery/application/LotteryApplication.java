package com.example.lottery.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ServiceLoader;

import com.example.random.service.NoSuchImplementationException;
import com.example.random.service.Qualifier;
import com.example.random.service.QualityOfService;
import com.example.random.service.RandomNumberService;

public class LotteryApplication {

	public static void main(String[] args) {
		try {
			var randomNumberService = loadRandomNumberService();
			randomNumberService.draw(60, 6, 10).forEach(System.out::println);			
		} catch (NoSuchImplementationException e) {
			System.out.println(e.getMessage());
		}
	}

	public static RandomNumberService loadRandomNumberService() throws NoSuchImplementationException {
		var properties = new Properties();
		var quality = QualityOfService.FAST;
		try {
			properties.load(new FileInputStream(new File("src","application.properties")));
			quality = QualityOfService.valueOf(properties.getProperty("quality"));
		} catch (IOException e) {
			System.err.println("Cannot read file: application.properties. Assuming quality is FAST.");
		}
		var randomNumberServices = ServiceLoader.load(RandomNumberService.class);
		for (var randomService : randomNumberServices) {
			var clazz = randomService.getClass();
			if (clazz.isAnnotationPresent(Qualifier.class)) {
				var qualifier = clazz.getAnnotation(Qualifier.class);
				if (qualifier.value() == quality)
					return randomService;
			}
		}
		throw new NoSuchImplementationException("No such implementation is found for the value: %s.".formatted(quality.name()));
	}
}
