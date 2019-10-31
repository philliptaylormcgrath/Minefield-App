package co.grandcircus;

public class Minefield {

	private int width;
	private int height;
	private int numBombs;

	public Minefield() {
	}

	public Minefield(int width, int height, int numBombs) {
		super();
		this.width = width;
		this.height = height;
		this.numBombs = numBombs;
		int mineCount = 0;
		char[][] minefield = new char[width][height];
		for (int i = 0; i < minefield.length; i++) {
			for (int j = 0; j < minefield[i].length; j++) {
				int mine = (int) (Math.random() * ((1 - 0) + 1)) + 0;
				if (mineCount != numBombs) {
					if (mine == 0) {
						minefield[i][j] = '*';
					}
				}
			} 
		}
		for (int i = 0; i < minefield.length; i++) {
			int counter = 0;
			for (int j = 0; j <minefield[i].length;j++) {
				if (minefield[i][j] == '*') {
					
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

}
