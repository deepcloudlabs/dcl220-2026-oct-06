package com.example.factory;

import java.awt.Image;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class JpegReader implements ImageReader {

	public Image loadImage() {
		System.out.println("Reading JPEG Image");
		return null;
	}

}
