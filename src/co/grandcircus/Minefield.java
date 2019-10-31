package co.grandcircus;

import java.util.Random;
import java.util.Arrays;

public class Minefield {
	public static Random rand = new Random();

	private int width;
	private int height;
	private int numBombs;
	private char[][] minefield;

	public Minefield() {
	}

	public Minefield(int width, int height, int numBombs) {
		super();
		this.width = width;
		this.height = height;
		this.numBombs = numBombs;
		int mineCount = 0;
		int mine;
		this.minefield = new char[width][height];
		for (int i = 0; i < minefield.length; i++) {
			for (int j = 0; j < minefield[i].length; j++) {
				mine = rand.nextInt(2);;
				if (mineCount != numBombs) {
					if (mine == 0) {
						minefield[i][j] = '*';
						mineCount++;
					} else {
						minefield[i][j] = '0';
					}
				}
			}
		}
		for (int i = 0; i < minefield.length; i++) {
			for (int j = 0; j < minefield[i].length; j++) {
				if (minefield[i][j] == '*') {
					try {
					if (minefield[i + 1][j] != '*') {
						minefield[i + 1][j] = (char) (Character.getNumericValue(minefield[i + 1][j]) + 1);
					}
					if (minefield[i - 1][j] != '*') {
						minefield[i - 1][j] = (char) (Character.getNumericValue(minefield[i - 1][j]) + 1);
					}
					if (minefield[i][j + 1] != '*') {
						minefield[i][j + 1] = (char) (Character.getNumericValue(minefield[i][j + 1]) + 1);
					}
					if (minefield[i][j - 1] != '*') {
						minefield[i][j - 1] = (char) (Character.getNumericValue(minefield[i][j - 1]) + 1);
					}
					if (minefield[i + 1][j + 1] != '*') {
						minefield[i + 1][j + 1] = (char) (Character.getNumericValue(minefield[i + 1][j + 1]) + 1);
					}
					if (minefield[i + 1][j - 1] != '*') {
						minefield[i + 1][j - 1] = (char) (Character.getNumericValue(minefield[i + 1][j - 1]) + 1);
					}
					if (minefield[i - 1][j + 1] != '*') {
						minefield[i - 1][j + 1] = (char) (Character.getNumericValue(minefield[i - 1][j + 1]) + 1);
					}
					if (minefield[i - 1][j - 1] != '*') {
						minefield[i - 1][j - 1] = (char) (Character.getNumericValue(minefield[i - 1][j - 1]) + 1);
					} } catch (IndexOutOfBoundsException e) {
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
	
	public char[][] getMinefield() {
		return minefield;
	}

	@Override
	public String toString() {
		return Arrays.toString(minefield);
	}
	
	

}
