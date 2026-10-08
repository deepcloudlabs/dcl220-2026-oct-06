package com.example.domain;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

record Size (int width,int height) {}

interface Image {
	byte[] getImageData();
	Size getSize();
}

abstract class ImageLoader {

    protected ImageLoader next = null;

    public ImageLoader next(ImageLoader next) {
    	this.next = next;
    	return next;
    }

    abstract public Image loadImage(String fileName); 
    
    
}

@Loader(order=-1)
class JpegImageLoader extends ImageLoader {

	@Override
	public Image loadImage(String fileName) {
		if (canReadImage(fileName)) {
			return new JpegImage();
		}
		if (Objects.nonNull(next))
			return next.loadImage(fileName);
		throw new IllegalStateException("Cannot handle image: %s".formatted(fileName));
	}

	private boolean canReadImage(String fileName) {
		return ThreadLocalRandom.current().nextInt(10) < 2;
	}

	public static class JpegImage implements Image {

		@Override
		public byte[] getImageData() {
			return null;
		}

		@Override
		public Size getSize() {
			return new Size(1024,1024);
		}
		
	}
}

@Loader(order=2)
class Jpeg2000ImageLoader extends ImageLoader {

	@Override
	public Image loadImage(String fileName) {
		if (canReadImage(fileName)) {
			return new JpegImage2000();
		}
		if (Objects.nonNull(next))
			return next.loadImage(fileName);
		throw new IllegalStateException("Cannot handle image: %s".formatted(fileName));
	}

	private boolean canReadImage(String fileName) {
		return ThreadLocalRandom.current().nextInt(10) < 2;
	}

	public static class JpegImage2000 implements Image {

		@Override
		public byte[] getImageData() {
			return null;
		}

		@Override
		public Size getSize() {
			return new Size(1024,1024);
		}
		
	}
}


@Loader(order=1)
class PngImageLoader extends ImageLoader {
	
	@Override
	public Image loadImage(String fileName) {
		if (canReadImage(fileName)) {
			return new PngImage();
		}
		if (Objects.nonNull(next))
			return next.loadImage(fileName);
		throw new IllegalStateException("Cannot handle image: %s".formatted(fileName));
	}
	
	private boolean canReadImage(String fileName) {
		return ThreadLocalRandom.current().nextInt(10) < 2;
	}
	
	public static class PngImage implements Image {
		
		@Override
		public byte[] getImageData() {
			return null;
		}
		
		@Override
		public Size getSize() {
			return new Size(1024,1024);
		}
		
	}
}

@Loader(order=5)
class TiffImageLoader extends ImageLoader {
	
	@Override
	public Image loadImage(String fileName) {
		if (canReadImage(fileName)) {
			return new TiffImage();
		}
		if (Objects.nonNull(next))
			return next.loadImage(fileName);
		throw new IllegalStateException("Cannot handle image: %s".formatted(fileName));
	}
	
	private boolean canReadImage(String fileName) {
		return ThreadLocalRandom.current().nextInt(10) < 2;
	}
	
	public static class TiffImage implements Image {
		
		@Override
		public byte[] getImageData() {
			return null;
		}
		
		@Override
		public Size getSize() {
			return new Size(1024,1024);
		}
		
	}
}

@Loader(order=-10)
class GifImageLoader extends ImageLoader {
	
	@Override
	public Image loadImage(String fileName) {
		if (canReadImage(fileName)) {
			return new GifImage();
		}
		if (Objects.nonNull(next))
			return next.loadImage(fileName);
		throw new IllegalStateException("Cannot handle image: %s".formatted(fileName));
	}
	
	private boolean canReadImage(String fileName) {
		return ThreadLocalRandom.current().nextInt(10) < 2;
	}
	
	public static class GifImage implements Image {
		
		@Override
		public byte[] getImageData() {
			return null;
		}
		
		@Override
		public Size getSize() {
			return new Size(1024,1024);
		}
		
	}
}

public class ChainOfResponsibilityDemo2 {

    public static void main(String[] args) {
        var imageLoader = makeImageLoader();
        Image image = imageLoader.loadImage("image01");
        System.out.println("%s: %s".formatted(image.getClass().getSimpleName(),image.getSize()));
    }

	private static ImageLoader makeImageLoader() {
		var jpegImageLoader = new JpegImageLoader();
		jpegImageLoader.next(new PngImageLoader())
		               .next(new TiffImageLoader())
		               .next(new GifImageLoader());
		return jpegImageLoader;
	}
}
