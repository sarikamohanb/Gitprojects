package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class FileCompare {
	static String directory1 = "/Users/MY PC/Desktop/sarika/";
	static String directory2 = "/Users/MY PC/Desktop/modifier data/";
	static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	public static void main(String[] args) {
		listAllTheFiles(directory1);
		String newFile = "my data.txt";
		createFile(newFile);
		String fileName = "my data.txt";
		openFile(fileName);
		String appendFile = "my data.txt";
		appendFile(appendFile);
		String historyFile = "my data.txt";
		historyOfEdits(historyFile);

	}

	private static void listAllTheFiles(String directoryName) {
		Path source = Paths.get(directoryName);
		try {
			System.out.println("List of files in the directory");
			Files.walk(source).filter(Files::isRegularFile).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void createFile(String newFile) {
		String path = directory1 + newFile;
		try {
			String content = "This is the content to write into create file";
			File file = new File(path);
			String path2 = directory2 + newFile;
			File file1 = new File(path2);

			if (!file.exists()) {
				file.createNewFile();
				file1.createNewFile();
				System.out.println("\nnew file with name " + path + " created");
			} else {
				System.out.println("\nfile cannot be created: " + path + " already exists");
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			String historyContent = sdf.format(file.lastModified()) + "  file created";
			bw1.write(historyContent);
			bw1.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static void openFile(String fileName) {
		String pathName = directory1 + fileName;
		try (Stream<String> stream = Files.lines(Paths.get(pathName))) {
			System.out.println("Contents in the file " + fileName);
			stream.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("file does not exist in the location");

		}

	}

	private static void appendFile(String appendFile) {
		String pathName = directory1 + appendFile;
		String pathName2 = directory2 + appendFile;
		String appendData = "";
		File file = new File(pathName);
		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("\nEnter the new line to be added ");
			try {
				appendData = reader.readLine();
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try (PrintWriter output = new PrintWriter(new FileWriter(pathName, true))) {
				output.printf("%s\r\n", appendData);
				PrintWriter output2 = new PrintWriter(new FileWriter(pathName2, true));
				String historyContent = sdf.format(file.lastModified()) + "  new line added as " + appendData;
				output2.printf("%s\r\n", historyContent);
				output.close();
				output2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("file with name " + appendFile + " does not exists in the directory ");
		}

	}

	private static void historyOfEdits(String historyFile) {
		String pathName = directory2 + historyFile;
		try (Stream<String> stream = Files.lines(Paths.get(pathName))) {
			System.out.println("\nHistory of Changes of file " + historyFile + ":");
			stream.forEach(System.out::println);
		} catch (IOException e) {
			System.out.println("file does not exist in the location");

		}

	}

}
