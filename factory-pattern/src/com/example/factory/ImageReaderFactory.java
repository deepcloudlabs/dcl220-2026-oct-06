package com.example.factory;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class ImageReaderFactory {
	public static ImageReader createImageReader(String fileName) {
		String[] listOfString = fileName.split("\\.");
		String ext = listOfString[listOfString.length - 1].toLowerCase();
		if (ext.equals("jpg") || ext.equals("jpeg"))
			return new JpegReader();
		else if (ext.equals("gif"))
			return new GifReader();
		else if (ext.equals("png"))
			return new PngReader();
		else if (ext.equals("j2k") || ext.equals("jpeg2k"))
			return new Jpeg2000Reader();

		return new NotSupported();
	}
}
