package com.example.factory;

import java.awt.Image;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class GifReader implements ImageReader{

    public Image loadImage() {
        System.out.println("Reading Gif Image");
        return null;
    }

}
