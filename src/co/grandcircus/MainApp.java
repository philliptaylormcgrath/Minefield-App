package co.grandcircus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainApp {

	public static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {

		String[][] minefield;
		Minefield minefieldObject;
		// displayMinefield(minefield);
		MineText.createMineDir();
		MineText.createFile();

		String cont = "yes";
		String result = "";

		System.out.println("*** WELCOME TO MINEFIELD ***");
		while (cont.equalsIgnoreCase("yes")) {
			System.out.println();
			int difficulty = Validator.getInt(scnr, "Pick your minefield:\n1. Easy\n2. Medium\n3. Hard\n4. Custom", 1,
					4);
			switch (difficulty) {
			case 1:
			case 2:
			case 3:
				minefieldObject = generateMinefield(difficulty);
				minefield = minefieldObject.getMinefield();
				MineText.writeToFile(minefieldObject);
				displayMinefield();
				/*for (String[] s : minefield) {
					System.out.println(Arrays.toString(s));
				}*/
				// System.out.println(Arrays.toString((Arrays.toString(minefield));
				break;
			case 4:
				minefieldObject = generateCustomMinefield();
				minefield = minefieldObject.getMinefield();
				/*for (String[] s : minefield) {
					System.out.println(Arrays.toString(s));
				}*/
				break;
			}
			while (result.equals("")) {

			}
			System.out.println("Do you want to continue? (yes/no)");
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
						System.out.printf("%-4s", " " + mineArrList.get(i-1).charAt(j-1) + " \n");
					} else {
						System.out.printf("%-4s", " " + mineArrList.get(i-1).charAt(j-1) + " ");
					}
				}
			}
		}
	}

	public static Minefield generateMinefield(int difficulty) {
		Minefield minefield = null;
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
		}
		return minefield;
	}

	public static Minefield generateCustomMinefield() {
		int width = Validator.getInt(scnr, "Enter minefield width:\n", 3, 99);
		int height = Validator.getInt(scnr, "Enter minefield height:\n", 3, 99);
		int numBombs = Validator.getInt(scnr, "Enter number of bombs:\n", 1, height * width - 1);
		Minefield minefield = new Minefield(width, height, numBombs);
		return minefield;
	}

	public static void playMinefield(String[][] minefield) {
		// 1. Ask uncover square or place flag
		// 2. Read text file
		// 3. If they enter a field that's already revealed, then let them know it was
		// invalid and take new input
		// a. If trying to put a flag on a flagged square, take new input
		// 4. If not revealed:
		// a. If trying to put flag, then put flag (F char)
		// b. if not trying to put flag, then read Minefield object
		// c. If square is a bomb, then explode and let them know they lost, display
		// revealed array
		// d. If square is not a bomb, reveal the square.
		// e. If revealed square is blank, then reveal all of the adjacent numbered or
		// blank squares
		// 5. Start over

		int actionChoice = Validator.getInt(scnr,
				"Pick your action (enter number):\n1. Uncover Square\n2. Flag Square\n", 1, 2);
		switch (actionChoice) {
		case 1:
			// if (!MineText.readSquareFromFile().equals"(□") {

			// }
			break;
		case 2:
			break;
		}
	}

}
