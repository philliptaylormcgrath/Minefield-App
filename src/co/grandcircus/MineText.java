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
			System.out.println("IOException, something's amiss. File was not read");
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
			System.out.println("IOException, something's amiss. File was not read");
		}
		return fileReadout;

	}

	public static void writeToFile(Minefield minefield) {
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		File file = path.toFile();
		PrintWriter output;

		output = null;
		// This method writes the blank minefield into the txt document.
		try {
			output = new PrintWriter(new FileOutputStream(file));
			for (int i = 0; i < minefield.getWidth(); i++) {
				for (int j = 0; j < minefield.getHeight(); j++) {
					if (j == minefield.getHeight() - 1) {
						output.println("@");
					} else {
						output.print("@");
					}
				}
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
				// System.out.println("File successfully created");
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
		String readString = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			for (int i = 0; i < (yAxis - 1); i++) {
				/*
				 * This for loop will continue reading the txt document line by line until it
				 * reaches the row that the user selected with their coordinate input. This is
				 * half of the equation that allows us to locate a specific spot in the txt file
				 */
				readString = br.readLine();
			}
			br.skip(xAxis - 1);
			/*
			 * This line initiates at the yAxis coordinate and skips characters until it
			 * gets to the xAxis input
			 * 
			 */
			txtChar = br.read();
			// Now that the reader is progressed to the coordinate input,
			// It will read the next character alone

			readString = Character.toString((char) txtChar);
			// Unfortunately, it can only read that character as a unicode decimal!
			// So we have to convert it to its character state
			// And finally convert it back into a String (the minefield is an array of
			// Strings)
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} catch (IOException e) {
			System.out.println("IOException, something's amiss. File was not read");
		}

		return readString;

	}

	public static void createTempFile(String tempName) {
		String tempFileName = tempName;
		Path path = Paths.get("src", "co", "grandcircus", tempFileName);
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
				// System.out.println("File successfully created");
			} catch (IOException e) {
				System.out.println("Problem creating file. File not created");
			}
		} else {
			System.out.println("The file already exists");
		}
	}

	// Method to take user coordinates, find that element in the array, then
	// write that element into the txt file at the specified coordinates.
	// If it's a space, it will look for spaces and numbers to display. Loop for
	// spaces. If it's a number, just reveal.
	public static void writeInput(Minefield minefield, int xAxis, int yAxis) {

		String[][] minefieldArr = minefield.getMinefield();
		String arraySpot = minefieldArr[yAxis - 1][xAxis - 1];
		String fileName = "Minefield_Display.txt";
		// String test = "test";
		Path readFilePath = Paths.get("src", "co", "grandcircus", fileName);
		File readFile = readFilePath.toFile();
		String tempFileName = "temp.txt";
		createTempFile(tempFileName);
		Path tempFilePath = Paths.get("src", "co", "grandcircus", tempFileName);
		File tempFile = tempFilePath.toFile();
		PrintWriter output = null;
		BufferedReader br = null;

		/*
		 * What's happening in the below loop is we're reading through the existing txt
		 * document, writing it to a new temp txt until we get to the user coordinate,
		 * then dropping the 2D array element into that coordinate, then reading and
		 * writing the rest of the display to the temp txt file. The old file is deleted
		 * and the temp file is renamed to replace the old file.
		 */
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
					for (int i2 = yAxis; i2 < minefield.getMinefield().length; i2++) {
						output.println(line);
						line = br.readLine();
					}
				}
			}
			readFile.delete();
			tempFile.renameTo(readFile);
			output.close();

			/*
			 * Below, we're using method recursion to utilize readInputTxt within itself.
			 * Like the logic that incremented adjacent numbers during minefield creation,
			 * each one of these trycatch blocks is scanning a spot adjacent to the user
			 * input, but only if the user input coordinate is a 0 in the minefield. If that
			 * spot is still hidden in the txt AND if the corresponding minefield location
			 * is not a bomb, then that spot is revealed. This logic is repeated for all
			 * surrounding coordinates.
			 */
			if (arraySpot.equals("0")) {
				try {
					if (readInputTxt(xAxis, yAxis - 1).equals("@")) {

						if (!minefieldArr[yAxis - 2][xAxis - 1].equals("*")) {
							MineText.writeInput(minefield, xAxis, yAxis - 1);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis - 1, yAxis - 1).equals("@")) {
						if (!minefieldArr[yAxis - 2][xAxis - 2].equals("*")) {
							MineText.writeInput(minefield, xAxis - 1, yAxis - 1);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis + 1, yAxis - 1).equals("@")) {
						if (!minefieldArr[yAxis - 2][xAxis].equals("*")) {
							MineText.writeInput(minefield, xAxis + 1, yAxis - 1);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis, yAxis + 1).equals("@")) {
						if (!minefieldArr[yAxis][xAxis - 1].equals("*")) {
							MineText.writeInput(minefield, xAxis, yAxis + 1);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis + 1, yAxis + 1).equals("@")) {

						if (!minefieldArr[yAxis][xAxis].equals("*")) {
							MineText.writeInput(minefield, xAxis + 1, yAxis + 1);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis - 1, yAxis + 1).equals("@")) {

						if (!minefieldArr[yAxis][xAxis - 2].equals("*")) {
							MineText.writeInput(minefield, xAxis - 1, yAxis + 1);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis + 1, yAxis).equals("@")) {
						if (!minefieldArr[yAxis - 1][xAxis].equals("*")) {
							MineText.writeInput(minefield, xAxis + 1, yAxis);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
				try {
					if (readInputTxt(xAxis - 1, yAxis).equals("@")) {
						if (!minefieldArr[yAxis - 1][xAxis - 2].equals("*")) {
							MineText.writeInput(minefield, xAxis - 1, yAxis);
						}
					}
				} catch (IndexOutOfBoundsException | IllegalArgumentException e) {
				}
			}
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
		Path readFilePath = Paths.get("src", "co", "grandcircus", fileName);
		File readFile = readFilePath.toFile();
		String tempFileName = "temp.txt";
		createTempFile(tempFileName);
		Path tempFilePath = Paths.get("src", "co", "grandcircus", tempFileName);
		File tempFile = tempFilePath.toFile();
		PrintWriter output = null;
		BufferedReader br = null;

		try {
			output = new PrintWriter(new FileOutputStream(tempFile));
			br = new BufferedReader(new FileReader(readFile));
			String line = br.readLine();
			while (line != null) {
				for (int i = 0; i < (yAxis - 1); i++) {
					output.println(line);
					line = br.readLine();
				} // Logic here}
				for (int i = 0; i < minefield.getWidth(); i++) {
					if (i == (xAxis - 1)) {
						if (i == (minefield.getWidth() - 1)) {
							if (readInputTxt(xAxis, yAxis).equals("F")) {
								output.println("@");
								// Uses our readInputTxt method to find this coordinate
								// If the user picks a coordinate to Flag and it
								// is ALREADY a flag, it will revert back to its
								// original form (combining flag/deflag)
							} else {
								output.println("F");
								// If the coordinate is displayed as blank,
								// a flag is placed at that coordinate.
							}
							line = br.readLine();
						} else {
							System.out.println(readInputTxt(xAxis, yAxis));
							if (readInputTxt(xAxis, yAxis).equals("F")) {
								output.print("@");
							} else {
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

	public static void revealMinefield(Minefield minefield) {
		String[][] minefieldArr = minefield.getMinefield();
		String fileName = "Minefield_Display.txt";
		Path path = Paths.get("src", "co", "grandcircus", fileName);
		File file = path.toFile();
		PrintWriter output;

		output = null;
		// This method writes the blank minefield into the txt document.
		try {
			output = new PrintWriter(new FileOutputStream(file));
			for (int i = 0; i < minefield.getHeight(); i++) {
				for (int j = 0; j < minefield.getWidth(); j++) {
					if (j == minefield.getWidth() - 1) {
						output.println(minefieldArr[i][j]);
					} else {
						output.print(minefieldArr[i][j]);
					}
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("404 File not found");
		} finally {
			output.close();
		}
	}
}