package com.example.exercises;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Exercise01 {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try(ObjectInputStream ois = new ObjectInputStream(
				                         new BufferedInputStream(
				                        		new FileInputStream(
				                        			new File("c:/tmp","image.jpg"))))){
			try {
				ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		var fis = new FileInputStream(new File("c:/tmp","image.jpg"));
		fis.read();
		var bfis = new BufferedInputStream(fis);
		bfis.read();
		var obfis = new ObjectInputStream(bfis);
		var o = obfis.readObject();
	}

}
