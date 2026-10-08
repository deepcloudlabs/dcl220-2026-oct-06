package com.example.factory;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service
public class ImageReaderFactory {
	private final Map<String,ImageReader> imageReaders;
			
	public ImageReaderFactory(Map<String, ImageReader> imageReaders) {
		this.imageReaders = imageReaders;
		System.out.println(imageReaders);
	}

	public Optional<ImageReader> getImageReader(String fileName) {
		String[] listOfString = fileName.split("\\.");
		String ext = listOfString[listOfString.length - 1].toLowerCase();
		for (var beanName : imageReaders.keySet()) {
			if (beanName.startsWith(ext))
				return Optional.of(imageReaders.get(beanName));						
		}
		return Optional.empty();
	}
}
