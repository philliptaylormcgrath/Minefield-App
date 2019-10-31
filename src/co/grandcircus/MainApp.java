package co.grandcircus;

import java.util.Arrays;
import java.util.Scanner;

public class MainApp {

	public static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {

		char[][] minefield;
		//displayMinefield(minefield);

		String cont = "yes";
		String result = "";

		System.out.println("*** WELCOME TO MINEFIELD ***");
		while (cont.equalsIgnoreCase("yes")) {
			System.out.println();
			int difficulty = Validator.getInt(scnr, "Pick your minefield:\n1. Easy\n2. Medium\n3. Hard\n4. Custom",
					1, 4);
			switch (difficulty) {
			case 1:
			case 2:
			case 3:
				minefield = generateMinefield(difficulty).getMinefield();
				for (char[] c: minefield) {
					System.out.println(Arrays.toString(c));
				}
				//System.out.println(Arrays.toString((Arrays.toString(minefield));
				break;
			case 4:
				generateCustomMinefield();
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

	public static void displayMinefield(char[][] minefield) {
		for (int i = 0; i < minefield.length; i++) {
			for (int j = 0; j < minefield[i].length; j++) {
				System.out.print(minefield[i][j]);
			}
			System.out.print("\n");
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

	public static void playMinefield(char[][] minefield) {
		// 1. Ask uncover square or place flag
		// 2. Read text file
		// 3. If they enter a field that's already revealed, then let them know it was
		// invalid and take new input
		// a. If trying to put a flag on a flagged square, take new input
		// 4. If not revealed:
		// a. If trying to put flag, then put flag
		// b. if not trying to put flag, then read Minefield object
		// c. If square is a bomb, then explode and let them know they lost, display revealed array
		// d. If square is not a bomb, reveal the square.
		// e. If revealed square is blank, then reveal all of the adjacent numbered or blank squares
		// 5. Start over
	}

}
