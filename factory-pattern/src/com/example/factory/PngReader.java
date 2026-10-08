package com.example.factory;

import java.awt.Image;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class PngReader implements ImageReader {

	public Image loadImage() {
		System.out.println("Reading PNG Image");
		return null;
	}

}
