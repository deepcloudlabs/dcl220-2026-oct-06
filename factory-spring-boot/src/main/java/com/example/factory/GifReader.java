package com.example.factory;

import java.awt.Image;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service
@Scope("singleton")
public class GifReader implements ImageReader{

    public Image loadImage() {
        System.out.println("Reading Gif Image");
        return null;
    }

}
