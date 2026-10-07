package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudyCollectionsAPIFacede {

	public static void main(String[] args) throws IOException {
		var numbers = new ArrayList<>(List.of(4,8,15,16,23,42));
		Collections.sort(numbers);
		Files.copy(Path.of("c:/tmp", "readme.txt"), Path.of("c:/tmp","readme.copy.txt"));
	}

}
