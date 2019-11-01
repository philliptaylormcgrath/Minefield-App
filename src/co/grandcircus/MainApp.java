package co.grandcircus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainApp {

	public static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {
		Minefield minefield = null;
		//MineText.createMineDir();
		MineText.createFile();
		String cont = "yes";
		System.out.println("*** WELCOME TO MINEFIELD ***");
		while (cont.equalsIgnoreCase("yes")) {
			System.out.println();
			minefield = generateMinefield();
			
			MineText.writeToFile(minefield);
			
			playMinefield(minefield);
			
			System.out.println("Do you want to play again? (yes/no)");
			cont = scnr.nextLine();
		}
		System.out.println("Bye!");
		scnr.close();
	}

	public static void displayMinefield() {
		ArrayList<String> mineArrList = MineText.readEntireFile();
		int rows = mineArrList.size();
		int columns = mineArrList.get(0).length();
		for (int i = 0; i <= rows; i++) {
			for (int j = 0; j <= columns; j++) {
				if (i == 0 && j == 0) {
					System.out.print("___|");
				} else if (i == 0 && j != 0) {
					if (j == columns) {
						System.out.printf("%-3s", "x" + j + "\n");
					} else {
						System.out.printf("%-3s|", "x" + j);
					}
				} else if (i != 0 && j == 0) {
					System.out.printf("%-3s|", "y" + i);
				} else if (i != 0 & j != 0) {
					if (j == columns) {
						System.out.printf("%-4s", " " + mineArrList.get(i - 1).charAt(j - 1) + " \n");
					} else {
						System.out.printf("%-4s", " " + mineArrList.get(i - 1).charAt(j - 1) + " ");
					}
				}
			}
		}
	}

	public static Minefield generateMinefield() {
		Minefield minefield = null;
		int difficulty = Validator.getInt(scnr, "Pick your minefield:\n1. Easy\n2. Medium\n3. Hard\n4. Custom\n", 1, 4);
		switch (difficulty) {
		case 1:
			minefield = new Minefield(9, 9, 10);
			break;
		case 2:
			minefield = new Minefield(16, 16, 40);
			break;
		case 3:
			minefield = new Minefield(16, 30, 99);
			break;
		case 4:
			int width = Validator.getInt(scnr, "Enter minefield width:\n", 3, 99);
			int height = Validator.getInt(scnr, "Enter minefield height:\n", 3, 99);
			int numBombs = Validator.getInt(scnr, "Enter number of bombs:\n", 1, height * width - 1);
			minefield = new Minefield(width, height, numBombs);
			return minefield;
		}
		return minefield;
	}

	public static void playMinefield(Minefield minefield) {
		if (checkMinefield(minefield).equals("win")) {
			System.out.println("YOU WIN!");
			displayMinefield();
		} else {
			displayMinefield();
			int actionChoice = Validator.getInt(scnr,
					"Pick your action (enter number):\n1. Uncover Square\n2. Flag/Unflag Square\n", 1, 2);
			switch (actionChoice) {
			case 1:
				uncoverSquare(minefield);
				playMinefield(minefield);
				break;
			case 2:
				flagSquare(minefield);
				playMinefield(minefield);
				break;
			}
		}
	}

	public static void uncoverSquare(Minefield minefield) {
		int xAxis = Validator.getInt(scnr, "Enter the x coordinate:", 1, minefield.getHeight());
		int yAxis = Validator.getInt(scnr, "Enter the y coordinate:", 1, minefield.getWidth());
		String selection = MineText.readInputTxt(xAxis, yAxis);
		if (selection.equals("@")) {
			System.out.println("It's a @");
		}
		while (!selection.equals("@") && !selection.equals("F")) {
			System.out.println("Invalid selection. Try again:");
			xAxis = Validator.getInt(scnr, "Enter the x coordinate:", 1, minefield.getHeight());
			yAxis = Validator.getInt(scnr, "Enter the y coordinate:", 1, minefield.getWidth());
			selection = MineText.readInputTxt(xAxis, yAxis);
		}
		if (!minefield.getMinefield()[yAxis-1][xAxis-1].equals("*")) {
			MineText.writeInput(minefield,xAxis, yAxis);
			playMinefield(minefield);
		} else {
			System.out.println("BOOM! YOU LOSE :(");
			displayMinefield(); // Need to make some method that prints the actual minefield array to the
								// Minefield_Display.txt file
		}
	}

	public static void flagSquare(Minefield minefield) {
		int xAxis = Validator.getInt(scnr, "Enter the x coordinate:", 1, minefield.getHeight());
		int yAxis = Validator.getInt(scnr, "Enter the y coordinate:", 1, minefield.getWidth());
		String selection = MineText.readInputTxt(xAxis, yAxis);
		while (!selection.equals("@") && !selection.equals("F")) {
			System.out.println("Invalid selection. Try again:");
			xAxis = Validator.getInt(scnr, "Enter the x coordinate:", 1, minefield.getHeight());
			yAxis = Validator.getInt(scnr, "Enter the y coordinate:", 1, minefield.getWidth());
			selection = MineText.readInputTxt(xAxis, yAxis);
		}
		//if (selection.equals("@")) {
			MineText.writeFlag(minefield, xAxis, yAxis);
			playMinefield(minefield);
		//} else if (selection.equals("F")) {
		//	MineText.writeFlag(minefield, xAxis, yAxis);
		//	playMinefield(minefield);
		//}
	}

	public static String checkMinefield(Minefield minefield) {
		ArrayList<String> minefieldArr = MineText.readEntireFile();
		int j = 0;
		for (String s : minefieldArr) {
			for (int i = 0; i < s.length(); i++) {
				if (!minefield.getMinefield()[j][i].equals("*") && Character.toString(s.charAt(i)).equals("@")) {
					return "";
				}
			}
			j++;
		}
		return "win";
	}

}
