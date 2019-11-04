package co.grandcircus;

import java.util.Arrays;
import java.util.Random;

public class Minefield {

	// Declare instance variables
	private int width;
	private int height;
	private int numBombs;
	private String[][] minefield;

	public Minefield() {
	}

	// Constructor takes in minefield width, height, and number of mines
	public Minefield(int width, int height, int numBombs) {
		super();
		this.width = width;
		this.height = height;
		this.numBombs = numBombs;

		// Declare variables to use while generating randomized minefield
		Random rand = new Random();
		int mineCount = 0;
		int mine;

		// Set minefield to String[][] of width and height provided with constructor
		minefield = new String[this.height][this.width];

		// Populate every array index in the minefield with a 0
		for (int h = 0; h < minefield.length; h++) {
			for (int k = 0; k < minefield[h].length; k++) {
				minefield[h][k] = "0";
			}
		}

		// Randomly sow array indices with mines (*) until the number of mines provided
		// with
		// constructor have been placed
		while (mineCount < numBombs) {
			int i = rand.nextInt(height);
			int j = rand.nextInt(width);
			if (!minefield[i][j].equals("*")) {
				minefield[i][j] = "*";
				mineCount++;
			}

		}

		// For every other array index in the minefield, check every adjacent array
		// index. If any of the indices contain a mine, increment the number displayed
		// in the original array index.
		//We had to separate each coordinate check into its own try-catch. Otherwise the
		//numbers on the outside of the minefield were not incrementing correctly due to 
		//OoB exceptions.
		for (int i = 0; i < minefield.length; i++) {
			for (int j = 0; j < minefield[i].length; j++) {
				if (minefield[i][j].equals("*")) {
					try {
						if (!minefield[i + 1][j].equals("*")) {
							minefield[i + 1][j] = String.valueOf((Integer.parseInt(minefield[i + 1][j]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i - 1][j].equals("*")) {
							minefield[i - 1][j] = String.valueOf((Integer.parseInt(minefield[i - 1][j]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i][j + 1].equals("*")) {
							minefield[i][j + 1] = String.valueOf((Integer.parseInt(minefield[i][j + 1]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i][j - 1].equals("*")) {
							minefield[i][j - 1] = String.valueOf((Integer.parseInt(minefield[i][j - 1]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i + 1][j + 1].equals("*")) {
							minefield[i + 1][j + 1] = String.valueOf((Integer.parseInt(minefield[i + 1][j + 1]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i + 1][j - 1].equals("*")) {
							minefield[i + 1][j - 1] = String.valueOf((Integer.parseInt(minefield[i + 1][j - 1]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i - 1][j + 1].equals("*")) {
							minefield[i - 1][j + 1] = String.valueOf((Integer.parseInt(minefield[i - 1][j + 1]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						if (!minefield[i - 1][j - 1].equals("*")) {
							minefield[i - 1][j - 1] = String.valueOf((Integer.parseInt(minefield[i - 1][j - 1]) + 1));
						}
					} catch (IndexOutOfBoundsException e) {
					}

				}
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNumBombs() {
		return numBombs;
	}

	public void setNumBombs(int numBombs) {
		this.numBombs = numBombs;
	}

	public String[][] getMinefield() {
		return minefield;
	}

	@Override
	public String toString() {
		return Arrays.toString(minefield);
	}

}
