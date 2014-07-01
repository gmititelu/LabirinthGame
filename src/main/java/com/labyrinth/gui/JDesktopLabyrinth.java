package com.labyrinth.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import com.labyrinth.utils.GenerateLabyrinth;

/**
 * 
 * @author Gaby
 */

public class JDesktopLabyrinth extends JDesktopPane implements KeyListener {

	private static final long serialVersionUID = 1L;
	private final JLabyrinthTablePanel timePanel;
	private BufferedImage img;
	private Point[][] points;
	private int[][] lab;
	private int N;
	private int xCoord, yCoord;
	private PlayerImage playerImg;
	private long startTime;
	private long finishTime;

	private boolean started;

	public JDesktopLabyrinth(int n, String userName,
			final JLabyrinthTablePanel panel) {

		this.timePanel = panel;
		this.N = n;
		setFocusable(true);
		addKeyListener(this);
		try {

			img = ImageIO.read(Thread.currentThread().getContextClassLoader()
					.getResource("Labyrinth2.jpg"));
		} catch (IOException ioEx) {
			Logger.getGlobal().warning(ioEx.getMessage());
		}
		setSize(img.getWidth(), img.getHeight());
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		setMinimumSize(new Dimension(img.getWidth(), img.getHeight()));
		setOpaque(false);
		points = new Point[N + 1][N + 1];
		generatePoints();
		playerImg = new PlayerImage(points[N - 1][N - 1], N - 1, N - 1,
				Color.ORANGE, userName);
	}

	public void startGame() {

		// game starts
		started = true;
		lab = new GenerateLabyrinth(N).getMatrix();

		// reposition player
		playerImg.setMatrixCol(N - 1);
		playerImg.setMatrixLine(N - 1);
		moveSquare((int) points[N - 1][N - 1].getX(),
				(int) points[N - 1][N - 1].getY(), playerImg);

		startTime = System.currentTimeMillis();
		finishTime = 0;

		timePanel.setLabelTime(getStringFromTime("Your time is: ", 0));
		repaint();
		this.requestFocus();
		this.requestFocusInWindow();
		this.setFocusable(true);
	}

	private void generatePoints() {

		int i;
		int j;
		for (i = 0; i <= N; i++)
			for (j = 0; j <= N; j++)
				points[i][j] = new Point(170 + j * 20 + xCoord, 55 + i * 20
						+ yCoord);
	}

	private void drawLabyrinth(Graphics g) {

		int i, j;
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++) {
				int val = lab[i][j];

				if (val % 2 == 0)
					drawThickLine(g, points[i + 1][j].x, points[i + 1][j].y,
							points[i + 1][j + 1].x, points[i + 1][j + 1].y, 4,
							Color.WHITE);
				if (val % 3 == 0)
					drawThickLine(g, points[i + 1][j + 1].x,
							points[i + 1][j + 1].y, points[i][j + 1].x,
							points[i][j + 1].y, 4, Color.WHITE);
				if (val % 5 == 0)
					drawThickLine(g, points[i][j].x, points[i][j].y,
							points[i][j + 1].x, points[i][j + 1].y, 4,
							Color.WHITE);
				if (val % 7 == 0)
					drawThickLine(g, points[i][j].x, points[i][j].y,
							points[i + 1][j].x, points[i + 1][j].y, 4,
							Color.WHITE);
			}

	}

	@Override
	public void paintComponent(Graphics g) {

		xCoord = (int) (this.getWidth() - img.getWidth()) / 2;
		yCoord = (int) (this.getHeight() - img.getHeight()) / 2;
		g.drawImage(img, xCoord, yCoord, this);
		if (lab != null) {
			generatePoints();
			drawLabyrinth(g);
			moveSquare(
					points[playerImg.getMatrixLine()][playerImg.getMatrixCol()].x,
					points[playerImg.getMatrixLine()][playerImg.getMatrixCol()].y,
					playerImg);
			playerImg.drawPoint(g);
		}
		super.paintComponent(g);

	}

	private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2,
			int thickness, Color c) {

		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double) (thickness) / (2 * lineLength);

		// The x,y increments from an endpoint needed to create a rectangle...
		double ddx = -scale * (double) dY;
		double ddy = scale * (double) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx;
		yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx;
		yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx;
		yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx;
		yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);

	}

	private void moveSquare(int x, int y, PlayerImage playerImg) {

		// Current square state, stored as final variables
		// to avoid repeat invocations of the same methods.
		final int CURR_X = playerImg.getX();
		final int CURR_Y = playerImg.getY();
		final int CURR_W = playerImg.getWidth();
		final int CURR_H = playerImg.getHeight();
		final int OFFSET = 2;

		if ((CURR_X != x) || (CURR_Y != y)) {

			// The square is moving, repaint background
			// over the old square location.
			repaint(CURR_X, CURR_Y, CURR_W + OFFSET, CURR_H + OFFSET);

			// Update coordinates.
			playerImg.setX(x);
			playerImg.setY(y);

			// Repaint the square at the new location.
			repaint(playerImg.getX(), playerImg.getY(), playerImg.getWidth()
					+ OFFSET, playerImg.getHeight() + OFFSET);
		}

	}

	private String getStringFromTime(String prefix, long timePlayed) {

		int sec, min, hour, day;
		sec = (int) (timePlayed / 1000);
		min = sec / 60;
		sec = sec % 60;
		// Get elapsed time in minutes
		min = min + (int) (timePlayed / (60 * 1000));
		hour = min / 60;
		min = min % 60;
		// Get elapsed time in hours
		hour = hour + (int) (timePlayed / (60 * 60 * 1000));
		day = hour / 24;
		hour = hour % 24;
		// Get elapsed time in days
		day = day + (int) (timePlayed / (24 * 60 * 60 * 1000));
		String message;

		if (day != 0) {
			message = prefix + day + " days," + hour + " hours, " + min
					+ " minutes, " + sec + " seconds!";
		} else {
			if (hour != 0) {
				message = prefix + hour + " hours, " + min + " minutes, " + sec
						+ " seconds!";
			} else {
				if (min != 0) {
					message = prefix + min + " minutes, " + sec + " seconds!";
				} else {
					message = prefix + sec + " seconds!";
				}
			}
		}
		return message;
	}

	private boolean isValid(int newLine, int newCol, int dir) {

		if ((newCol < 0) || (newCol >= N) || (newLine < 0) || (newLine >= N))
			return false;
		int currentCol, currentLine;
		currentCol = playerImg.getMatrixCol();
		currentLine = playerImg.getMatrixLine();
		int val = lab[currentLine][currentCol];

		if ((val % 2 == 0) && (dir == 0))
			return false;
		if ((val % 3 == 0) && (dir == 1))
			return false;
		if ((val % 5 == 0) && (dir == 2))
			return false;
		if ((val % 7 == 0) && (dir == 3))
			return false;
		return true;

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (!started)
			return;
		int keyCode = e.getKeyCode();
		int currentCol, currentLine;
		currentCol = playerImg.getMatrixCol();
		currentLine = playerImg.getMatrixLine();

		if ((currentCol == 0) && (currentLine == 0)) {

			return;

		} else {

			long timePlayed = System.currentTimeMillis() - startTime;
			timePanel.setLabelTime(getStringFromTime("Your time is:",
					timePlayed));

			switch (keyCode) {
			case 39:// right
			{
				if (isValid(currentLine, currentCol + 1, 1)) {
					currentCol = currentCol + 1;
					playerImg.setMatrixCol(currentCol);
					moveSquare(points[currentLine][currentCol].x,
							points[currentLine][currentCol].y, playerImg);
				}
			}
				break;

			case 38:// up
			{
				if (isValid(currentLine - 1, currentCol, 2)) {
					currentLine = currentLine - 1;
					playerImg.setMatrixLine(currentLine);
					moveSquare(points[currentLine][currentCol].x,
							points[currentLine][currentCol].y, playerImg);
				}
			}
				break;

			case 37:// left
			{
				if (isValid(currentLine, currentCol - 1, 3)) {
					currentCol = currentCol - 1;
					playerImg.setMatrixCol(currentCol);
					moveSquare(points[currentLine][currentCol].x,
							points[currentLine][currentCol].y, playerImg);
				}
			}
				break;

			case 40:// down
			{
				if (isValid(currentLine + 1, currentCol, 0)) {
					currentLine = currentLine + 1;
					playerImg.setMatrixLine(currentLine);
					moveSquare(points[currentLine][currentCol].x,
							points[currentLine][currentCol].y, playerImg);
				}
			}
				break;
			}
		}

		if ((currentCol == 0) && (currentLine == 0)) {

			moveSquare(points[currentLine][currentCol].x - 50,
					points[currentLine][currentCol].y, playerImg);
			finishTime = System.currentTimeMillis();
			long totalTime = finishTime - startTime;
			JOptionPane.showMessageDialog(
					JDesktopLabyrinth.this,
					getStringFromTime("You finished in: ", totalTime),
					"Congratulations!!",
					JOptionPane.INFORMATION_MESSAGE,

					new ImageIcon(Thread.currentThread()
							.getContextClassLoader()
							.getResource("labyrinth.jpg")));
			timePanel.setLabelTime(getStringFromTime("Your total time is:",
					totalTime));
			this.timePanel.addNewRow(playerImg.getUserName(),
					String.valueOf(totalTime));

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public PlayerImage getPlayerImg() {
		return playerImg;
	}

	public void setPlayerImg(PlayerImage playerImg) {
		this.playerImg = playerImg;
	}

}
