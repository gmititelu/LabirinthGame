package com.labyrinth.app;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.labyrinth.gui.JPanelLabyrinth;

public class ApplicationStart {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationStart window = new ApplicationStart();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationStart() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Labyrinth Game");
		JPanelLabyrinth labPanel = new JPanelLabyrinth();
		frame.setBounds(100, 100, 850, 700);
		frame.add(labPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
	}

}
