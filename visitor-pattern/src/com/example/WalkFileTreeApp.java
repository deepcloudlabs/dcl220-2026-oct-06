package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

import com.example.util.PrintTree;

public class WalkFileTreeApp {

    @SuppressWarnings("unused")
	public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Usage: WalkFileTreeTest <starting directory>");
//            System.exit(-1);
//        }
        Path path = Paths.get("g:/My Drive");
        if (!Files.isDirectory(path)) {
            System.out.println(args[0] + " must be a directory!");
            System.exit(-1);
        }
        try {
            // Files.walkFileTree(path, new PrintTree());
        	ZonedDateTime fiveYearAgo = ZonedDateTime.now().minusYears(5);
        	Files.walkFileTree(path, new PrintTree());
        	//Files.walkFileTree(path, new OldFileVisitor(fiveYearAgo.toInstant()));
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}