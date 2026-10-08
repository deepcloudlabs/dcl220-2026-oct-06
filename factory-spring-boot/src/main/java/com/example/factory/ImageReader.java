package com.example.factory;

import java.awt.Image;

import org.springframework.stereotype.Service;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@Service
public interface ImageReader {
      Image loadImage();
}
