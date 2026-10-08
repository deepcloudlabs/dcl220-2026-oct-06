package com.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.factory.ImageReader;
import com.example.factory.ImageReaderFactory;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner{
	private final ImageReaderFactory imageReaderFactory;
	

	public DemoApplication(ImageReaderFactory imageReaderFactory) {
		this.imageReaderFactory = imageReaderFactory;
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		ImageReader reader = imageReaderFactory.getImageReader("my.secret.tiff")
                         .orElseThrow(() -> new IllegalStateException("Cannot find a image reader"));
        System.out.println(reader.getClass().getName());
        reader.loadImage();		
	}

}
