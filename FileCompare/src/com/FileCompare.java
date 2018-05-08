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
import org.apache.log4j.Logger;
import java.util.stream.Stream;

import org.apache.log4j.PropertyConfigurator;

public class FileCompare {
	static String directory1 = "/Users/MY PC/Desktop/sarika/";
	static String directory2 = "/Users/MY PC/Desktop/modifier data/";
	static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	static final Logger logger = Logger.getLogger(FileCompare.class.getName());

	public static void main(String[] args) {
		PropertyConfigurator.configure("Logging.properties");
		listAllTheFiles(directory1);
		String newFile = "my data.txt";
		boolean status=createFile(newFile);
		if(status) {
			logger.info("file with name "+newFile+" created");
		}
		String fileName = "my data.txt";
		openFile(fileName);
		String appendFile = "my data.txt";
		boolean status1=appendFile(appendFile);
		if(status1) {
			logger.info("file with name "+appendFile+" got edited");
		}
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

	private static boolean createFile(String newFile) {
		boolean status=false;
		String path = directory1 + newFile;
		try {
			String content = "This is the content to write into create file";
			File file = new File(path);
			String path2 = directory2 + newFile;
			File file1 = new File(path2);

			if (!file.exists()) {
				file.createNewFile();
				file1.createNewFile();
				status=true;
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
		return status;
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

	private static boolean appendFile(String appendFile) {
		boolean status=false;
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
				status=true;
				output.close();
				PrintWriter output2 = new PrintWriter(new FileWriter(pathName2, true));
				String historyContent = sdf.format(file.lastModified()) + "  new line added as " + appendData;
				output2.printf("%s\r\n", historyContent);
				
				output2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("file with name " + appendFile + " does not exists in the directory ");
		}
		return status;

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
