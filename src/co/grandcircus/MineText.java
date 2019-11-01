package co.grandcircus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// Assigned to Phil currently
public class MineText {
	public static void readFromFile() {
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		File file = path.toFile();

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} catch (IOException e) {
			System.out.println("IOException, something's fucked. File was not read");
		}

	}

	public static ArrayList<String> readEntireFile() {
		ArrayList<String> fileReadout = new ArrayList<>();
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		File file = path.toFile();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while (line != null) {
				fileReadout.add(line);
				line = br.readLine();
			}
			br.close();
			return fileReadout;
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} catch (IOException e) {
			System.out.println("IOException, something's fucked. File was not read");
		}
		return fileReadout;

	}

	/*
	 * public static String userSelects(String[][] minefield, int xAxis, int yAxis)
	 * { String indexConts = minefield[xAxis - 1][yAxis - 1]; }
	 */
	public static void writeToFile(Minefield minefield) {
		String[][] minefieldArr = minefield.getMinefield();
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		String test = "test";
		File file = path.toFile();
		PrintWriter output; // These two lines could be one line declaring and initializing. Putting it
							// outside of the trycatch allows us to close the output in the 'finally' logic
		output = null;
		// This method will take the input from the reader (A,3 for instance) and then
		// scan the (array?txt?) and determine whether or not to reveal things.
		try {
			output = new PrintWriter(new FileOutputStream(file));
			for (int i = 0; i < minefield.getWidth(); i++) {
				for (int j = 0; j < minefield.getHeight(); j++) {
					if (j == minefield.getHeight() - 1) {
						output.println("@");
					} else {
						output.print("@");
					}
					/*
					 * if (i == 0 && j == 0) { output.print(" "); } else if (i == 0 && j != 0) {
					 * 
					 * if (j == minefield.getHeight()) { output.println("x" + j); } else {
					 * output.print("x" + j); } } else if (i != 0 && j == 0) {
					 * 
					 * output.print("y" + i); } else if (i != 0 & j != 0) { output.print("□"); if (j
					 * == minefield.getHeight()) { output.println("□"); } }
					 */
					// output.print("□");
				}
				// output.print("\b");
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} finally {
			output.close();
		}

	}

	public static void createFile() {
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
				System.out.println("File successfully created");
			} catch (IOException e) {
				System.out.println("Problem creating file. File not created");
			}
		} else {
			System.out.println("The file already exists");
		}
	}

	/*
	 * public static void createMineDir() { // Create a string representing the name
	 * of the folder we want to create, or // verify that it already exists String
	 * dirPath = "Minefield_Folder"; // Creates a 'resources' folder // next line is
	 * turning our string reference above into a Path to use with the // other File
	 * methods Path folder = Paths.get(dirPath);
	 * 
	 * if (Files.notExists(folder)) { // this Files.notExists() method is checking
	 * to make sure that the folder is not // already there try {
	 * Files.createDirectories(folder); // this method will create a folder for us
	 * with the associated name we // passed in // as a parameter
	 * System.out.println("The file was created successfully"); } catch (IOException
	 * e) {
	 * 
	 * System.out.println("An error has occurred, the folder was not created");
	 * 
	 * } } else { System.out.println("The folder already exists"); // Can now
	 * comment any of the above out, after folder // creation } }
	 */
	public static String readInputTxt(int xAxis, int yAxis) {
		// This method will find the character in the TXT document that corresponds with
		// the user's input coordinates
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		File file = path.toFile();
		int txtChar = 0;
		String readString = "0";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			for (int i = 0; i < (yAxis - 1); i++) {
				br.readLine();

			}

			br.skip(xAxis - 1);

			txtChar = br.read();
			readString = Character.toString((char) txtChar);
			System.out.println(readString);

			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} catch (IOException e) {
			System.out.println("IOException, something's fucked. File was not read");
		}

		return readString;

	}

	public static void createTempFile(String tempName) {
		String tempFileName = tempName;
		Path path = Paths.get("src", "co", "grandcircus", tempFileName);
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
				System.out.println("File successfully created");
			} catch (IOException e) {
				System.out.println("Problem creating file. File not created");
			}
		} else {
			System.out.println("The file already exists");
		}
	}

	// Create method to take user coordinates, find that element in the array, then
	// write that element into the txt file.
	// If it's a space, it will look for spaces and numbers to display. Loop for
	// spaces. If it's a number, just reveal.
	public static void writeInput(Minefield minefield, int xAxis, int yAxis) {

		String[][] minefieldArr = minefield.getMinefield();
		String arraySpot = minefieldArr[yAxis - 1][xAxis - 1];
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		// String test = "test";
		Path readFilePath = Paths.get("src", "co", "grandcircus", fileName);
		File readFile = readFilePath.toFile();
		String tempFileName = "temp.txt";
		createTempFile(tempFileName);
		Path tempFilePath = Paths.get("src", "co", "grandcircus", tempFileName);
		File tempFile = tempFilePath.toFile();
		PrintWriter output = null;
		BufferedReader br = null;
		// File file = path.toFile();

		// This method will take the input from the reader (A,3 for instance) and then
		// scan the array and determine whether or not to reveal things.
		try {
			output = new PrintWriter(new FileOutputStream(tempFile));
			br = new BufferedReader(new FileReader(readFile));
			String line = br.readLine();
			while (line != null) {
				for (int i = 0; i < (yAxis - 1); i++) {
					output.println(line);
					line = br.readLine();
				}
				for (int i = 0; i < minefield.getWidth(); i++) {
					if (i == (xAxis - 1)) {
						if (i == (minefield.getWidth() - 1)) {
							output.println(arraySpot);
							line = br.readLine();
						} else {
							output.print(arraySpot);
						}
					} else {
						if (i == (minefield.getWidth() - 1)) {
							output.println(line.charAt(i));
							line = br.readLine();
						} else {
							output.print(line.charAt(i));
						}
					}
				}
				if (yAxis != minefield.getHeight()) {
					for (int i = yAxis; i < minefield.getMinefield().length; i++) {
						output.println(line);
						line = br.readLine();
					}
				}
			}
			readFile.delete();
			tempFile.renameTo(readFile);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} catch (IOException e) {
			System.out.println("Problem creating file. File not created");
		} finally {
			output.close();
		}
	}

	public static void writeFlag(Minefield minefield, int xAxis, int yAxis) {

		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		// String test = "test";
		Path readFilePath = Paths.get("src", "co", "grandcircus", fileName);
		File readFile = readFilePath.toFile();
		String tempFileName = "temp.txt";
		createTempFile(tempFileName);
		Path tempFilePath = Paths.get("src", "co", "grandcircus", tempFileName);
		File tempFile = tempFilePath.toFile();
		PrintWriter output = null;
		BufferedReader br = null;

		// This method will take the input from the reader (A,3 for instance) and then
		// scan the array and determine whether or not to reveal things.
		try {
			output = new PrintWriter(new FileOutputStream(tempFile));
			br = new BufferedReader(new FileReader(readFile));
			String line = br.readLine();
			while (line != null) {
				for (int i = 0; i < (yAxis - 1); i++) {
					output.println(line);
					line = br.readLine();
				}
				for (int i = 0; i < minefield.getWidth(); i++) {
					if (i == (xAxis - 1)) {
						if (i == (minefield.getWidth() - 1)) {
							if (readInputTxt(xAxis, yAxis).equals("F")) {
								output.println("@");
							}
							else {
								output.println("F");
							}
							line = br.readLine();
						} else {
							System.out.println(readInputTxt(xAxis, yAxis));
							if (readInputTxt(xAxis, yAxis).equals("F")) {
								output.print("@");
							}
							else {
								output.print("F");
							}
						}
					} else {
						if (i == (minefield.getWidth() - 1)) {
							output.println(line.charAt(i));
							line = br.readLine();
						} else {
							output.print(line.charAt(i));
						}
					}
				}
				if (yAxis != minefield.getHeight()) {
					for (int i = yAxis; i < minefield.getMinefield().length; i++) {
						output.println(line);
						line = br.readLine();
					}
				}
			}
			readFile.delete();
			tempFile.renameTo(readFile);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} catch (IOException e) {
			System.out.println("Problem creating file. File not created");
		}

		finally {
			output.close();
		}
	}
}