package co.grandcircus;

import java.util.Arrays;
import java.util.Random;

public class Minefield {
	public static Random rand = new Random();

	private int width;
	private int height;
	private int numBombs;
	private String[][] minefield;

	public Minefield() {
	}

	public Minefield(int width, int height, int numBombs) {
		super();
		this.width = width;
		this.height = height;
		this.numBombs = numBombs;
		int mineCount = 0;
		int mine;
		minefield = new String[this.width][this.height];
		for (int h = 0; h < minefield.length; h++) {
			for (int k = 0; k < minefield[h].length; k++) {
				minefield[h][k] = "0";
			}
		}

		while (mineCount < numBombs) {
			int i = rand.nextInt(width);
			int j = rand.nextInt(height);
			if (!minefield[i][j].equals("*")) {
				minefield[i][j] = "*";
				mineCount++;
			}

		}

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
