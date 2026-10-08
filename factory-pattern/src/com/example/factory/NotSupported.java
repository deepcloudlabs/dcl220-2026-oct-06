package com.example.factory;

import java.awt.Image;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class NotSupported implements ImageReader {

	public Image loadImage() {
		throw new IllegalArgumentException("This format is not supported yet!");
	}

}
