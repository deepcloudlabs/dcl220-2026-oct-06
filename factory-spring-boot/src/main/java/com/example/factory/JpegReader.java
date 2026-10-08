package com.example.factory;

import java.awt.Image;

import org.springframework.stereotype.Service;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service

public class JpegReader implements ImageReader {

	public Image loadImage() {
		System.out.println("Reading JPEG Image");
		return null;
	}

}
