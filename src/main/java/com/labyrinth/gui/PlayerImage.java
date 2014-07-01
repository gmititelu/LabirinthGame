/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.labyrinth.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 * @author Gaby
 */

class PlayerImage extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int xPos;
	private int yPos;
	private int width = 16;
	private int height = 16;
	private Color color;
	private int matrixLine;
	private int matrixCol;
	private BufferedImage img;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public PlayerImage(Point point, int line, int col, Color color, String name) {

		this.userName = name;
		this.xPos = point.x;
		this.yPos = point.y;
		this.matrixLine = line;
		this.matrixCol = col;
		this.color = color;
	}

	public int getMatrixCol() {
		return matrixCol;
	}

	public void setMatrixCol(int matrixCol) {
		this.matrixCol = matrixCol;
	}

	public int getMatrixLine() {
		return matrixLine;
	}

	public void setMatrixLine(int matrixLine) {
		this.matrixLine = matrixLine;
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}

	public int getX() {
		return xPos;
	}

	public void setY(int yPos) {
		this.yPos = yPos;
	}

	public int getY() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void drawPoint(Graphics g) {

		if (img != null)
			g.drawImage(img, xPos + 2, yPos + 2, this);
		else {
			g.setColor(color);
			g.fillOval(xPos + 2, yPos + 2, width, height);
		}
	}

	public void setImage(String path) {

		try {
			img = ImageIO.read(Thread.currentThread().getContextClassLoader()
					.getResource("Icons/" + path));
		} catch (IOException ioEx) {
			Logger.getGlobal().warning(ioEx.getMessage());
		}

	}
}