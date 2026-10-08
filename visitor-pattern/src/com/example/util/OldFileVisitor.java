package com.example.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

import static java.nio.file.FileVisitResult.*;

public class OldFileVisitor implements FileVisitor<Path> {
	private final Instant threshold;
	
	
    public OldFileVisitor(Instant threshold) {
		this.threshold = threshold;
	}

	//Print information about the root path
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
        return CONTINUE;
    }

    //Print information about each type of file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
    	Instant lastAccessTime = attr.lastAccessTime().toInstant();
    	if(lastAccessTime.isBefore(threshold)) {
    		System.out.println("%s - Last access: %s".formatted(file,lastAccessTime));
    	}
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.print("vistiFileFailed: %s".formatted(exc.getMessage()));
        return CONTINUE;
    }
}
