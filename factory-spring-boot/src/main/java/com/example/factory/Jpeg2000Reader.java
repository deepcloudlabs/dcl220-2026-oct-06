package com.example.factory;

import java.awt.Image;

import org.springframework.stereotype.Service;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service

public class Jpeg2000Reader implements ImageReader {

	public Image loadImage() {
		System.out.println("Reading JPEG2000 Image");
		return null;
	}

}
