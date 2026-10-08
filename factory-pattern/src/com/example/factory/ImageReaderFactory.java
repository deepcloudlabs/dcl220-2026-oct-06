package com.example.factory;

import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class ImageReaderFactory {
	private static Map<String,ImageReader> IMAGE_READERS = Map.of(
		"jpg", new JpegReader(),
		"jpeg", new JpegReader(),
		"gif", new GifReader(),
		"png", new PngReader(),
		"j2k", new Jpeg2000Reader(),
		"jpeg2k", new Jpeg2000Reader()			
	);
			
	public static Optional<ImageReader> createImageReader(String fileName) {
		String[] listOfString = fileName.split("\\.");
		String ext = listOfString[listOfString.length - 1].toLowerCase();
		return Optional.ofNullable(IMAGE_READERS.get(ext));
	}
}
