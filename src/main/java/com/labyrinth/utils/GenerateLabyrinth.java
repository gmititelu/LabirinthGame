package com.labyrinth.utils;

import java.awt.Point;
import java.util.Stack;

/**
 * 
 * Class that generates a labyrinth of size NxN. Basically it constructs the
 * 2d Graph in which for every cell the direction in which it can go is established by a prime number.
 * So Initially all cells are assumed unvisited. Then starting from a random position on the grid, 
 * a randomized DFS is performed. Once a cell is visited the wall is removed between the current and that cell.
 * 
 *  At the end we would have the DFS tree generated which guarantees exactly 1 path from start to end.
 * 
 * @author Gaby
 * 
 */
public class GenerateLabyrinth {
	private int matrix[][];
	private int N;

	public int[][] getMatrix() {
		return matrix;
	}

	public GenerateLabyrinth(int nr) {
		N = nr;
		matrix = new int[N + 1][N + 1];
		populateMatrix();
		createMaze();
		matrix[0][0] = matrix[0][0] / 5;
		matrix[N - 1][N - 1] = matrix[N - 1][N - 1] / 2;

		// addBorders();
	}

	public int getN() {
		return N;
	}

	public int getElementAt(int x, int y) {
		if ((x >= 0) && (x < N) && (y >= 0) && (y < N))
			return matrix[x][y];
		else
			return 11;
	}

	public void populateMatrix() {
		int i, j;
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++) {
				matrix[i][j] = 2 * 3 * 5 * 7;
			}
	}

	public void addBorders() {
		int i;
		matrix[0][0] = matrix[0][0] * 35;
		matrix[0][N - 1] = matrix[0][N - 1] * 15;
		matrix[N - 1][0] = matrix[N - 1][0] * 14;
		matrix[N - 1][N - 1] = matrix[N - 1][N - 1] * 6;
		for (i = 1; i < N - 1; i++) {
			matrix[0][i] = matrix[0][i] * 10;
			matrix[N - 1][i] = matrix[N - 1][i] * 10;
			matrix[i][0] = matrix[i][0] * 21;
			matrix[i][N - 1] = matrix[i][N - 1] * 21;
		}
	}

	public int generateDivideNumber() {
		int nr = (int) Math.round(3 * Math.random());
		if (nr == 0)
			return 2;
		if (nr == 1)
			return 3;
		if (nr == 2)
			return 5;
		return 7;
	}

	public void changeNeighbour(int i, int j, int val) {
		if (val == 2)
			matrix[i + 1][j] = matrix[i + 1][j] / 5;
		if (val == 3)
			matrix[i][j + 1] = matrix[i][j + 1] / 7;
		if (val == 5)
			matrix[i - 1][j] = matrix[i - 1][j] / 2;
		if (val == 7)
			matrix[i][j - 1] = matrix[i][j - 1] / 3;
	}

	public int[] genPermutare() {
		int[] a;
		int[] val;
		a = new int[4];
		val = new int[5];
		int i;
		int nr;
		for (i = 0; i < 4; i++) {
			do {
				nr = (int) Math.round(3 * Math.random());
			} while (val[nr] != 0);
			a[i] = nr;
			val[nr] = 1;
		}
		return a;
	}

	public int getDirection(int nr) {
		if (nr == 0)
			return 2;
		if (nr == 1)
			return 3;
		if (nr == 2)
			return 5;
		return 7;
	}

	public void createMaze() {
		Stack<Point> stiva = new Stack<Point>();
		Stack<Integer> vecini = new Stack<Integer>();
		int totalCells = N * N;
		Point currentCell;
		int visitedCells = 1;
		int xi = (int) Math.round((N - 1) * Math.random());
		int yi = (int) Math.round((N - 1) * Math.random());
		currentCell = new Point(xi, yi);
		int dx[] = { 1, 0, -1, 0 };
		int dy[] = { 0, 1, 0, -1 };
		int i, newi, newj;
		while (visitedCells < totalCells) {
			vecini.clear();
			for (i = 0; i < 4; i++) {
				newi = currentCell.x + dx[i];
				newj = currentCell.y + dy[i];

				if (((newi >= 0) && (newi < N) && (newj >= 0) && (newj < N))
						&& (matrix[newi][newj] == 210)) {
					vecini.push(i);
				}
			}

			if (vecini.size() >= 1) {
				int chosen = (int) Math.floor(vecini.size() * Math.random());
				int value = vecini.get(chosen);
				matrix[currentCell.x][currentCell.y] = matrix[currentCell.x][currentCell.y]
						/ getDirection(value);
				changeNeighbour(currentCell.x, currentCell.y,
						getDirection(value));
				newi = currentCell.x + dx[value];
				newj = currentCell.y + dy[value];
				// System.out.print("From "+
				// currentCell.x+" "+currentCell.y+" passes in: ");
				stiva.add(currentCell);
				currentCell = new Point(newi, newj);
				// System.out.println(currentCell.x+" "+currentCell.y);
				visitedCells++;
				// afisare();
				// System.out.println();
			} else {
				currentCell = stiva.pop();
			}
		}
	}
}
