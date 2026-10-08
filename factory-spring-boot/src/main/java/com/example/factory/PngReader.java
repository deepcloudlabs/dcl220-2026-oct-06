package com.example.factory;

import java.awt.Image;

import org.springframework.stereotype.Service;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service

public class PngReader implements ImageReader {

	public Image loadImage() {
		System.out.println("Reading PNG Image");
		return null;
	}

}
