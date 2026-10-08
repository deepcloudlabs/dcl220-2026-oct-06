package com.example.app;

import com.example.factory.*;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class TestImageReaderFactory {
	public static void main(String[] args) {
		ImageReader reader = ImageReaderFactory.createImageReader("my.secret.jpeg");
		System.out.println(reader.getClass().getName());
		reader.loadImage();
	}
}
