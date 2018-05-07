package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

public class FileCompare {

	public static void main(String[] args) {
		String directoryName = "/Users/MY PC/Desktop/sarika";
		listAllTheFiles(directoryName);
		String fileName = "/Users/MY PC/Desktop/sarika/my data.txt";
		openFile(fileName);
		String newFile = "/Users/MY PC/Desktop/sarika/hiii.txt";
		createFile(newFile);

	}

	private static void listAllTheFiles(String directoryName) {
		Path source = Paths.get(directoryName);
		try {
			Files.walk(source).filter(Files::isRegularFile).forEach(System.out::println);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static void openFile(String fileName) {
		

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
stream.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("file does not exist in the location");
		}
		

	}

	private static void createFile(String path) {
		try {
			String content = "This is the content to write into create file";
			File file = new File(path);

			if (!file.exists()) {
				file.createNewFile();
				System.out.println("new file with name " + path + " created");
			} else {
				System.out.println("file with name " + path + " already exists");
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
