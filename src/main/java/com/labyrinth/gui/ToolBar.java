/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.labyrinth.gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * 
 * @author Gaby
 */
public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	private final JPanelLabyrinth parent;

	private JButton start;
	private JToggleButton results;
	private JComboBox<ImageIcon> simpleIconsCombo;
	private JComboBox<ImageIcon> smileyIconsCombo;

	public ToolBar(final JPanelLabyrinth parent) {

		this.parent = parent;
		this.setFloatable(false);
		this.setOrientation(JToolBar.HORIZONTAL);
		this.setFocusable(false);
		this.setMargin(new Insets(10, 10, 10, 10));

		this.start = new JButton(new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("Icons/start.png")));
		this.start.setToolTipText("Enter new labyrinth");
		this.start.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		Vector<ImageIcon> simpleIcons = new Vector<ImageIcon>();
		for (int i = 1; i < 5; i++)

			simpleIcons
					.add(new ImageIcon(Thread.currentThread()
							.getContextClassLoader()
							.getResource("Icons/" + i + ".png")));
		this.simpleIconsCombo = new JComboBox<ImageIcon>(simpleIcons);
		this.simpleIconsCombo.setRenderer(new IconsCellRenderer());
		this.simpleIconsCombo.setMaximumSize(new Dimension(50, 25));
		this.simpleIconsCombo.setPreferredSize(new Dimension(50, 25));
		this.simpleIconsCombo.setToolTipText("Choose character");
		this.simpleIconsCombo.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));

		Vector<ImageIcon> smileyIcons = new Vector<ImageIcon>();
		for (int i = 1; i < 10; i++)
			smileyIcons.add(new ImageIcon(Thread.currentThread()
					.getContextClassLoader()
					.getResource("Icons/s" + i + ".png")));
		this.smileyIconsCombo = new JComboBox<ImageIcon>(smileyIcons);
		this.smileyIconsCombo.setRenderer(new IconsCellRenderer());
		this.smileyIconsCombo.setMaximumSize(new Dimension(50, 25));
		this.smileyIconsCombo.setPreferredSize(new Dimension(50, 25));
		this.smileyIconsCombo.setToolTipText("Choose smiley character");
		this.smileyIconsCombo.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.results = new JToggleButton(new ImageIcon(Thread.currentThread()
				.getContextClassLoader().getResource("Icons/table.png")));
		this.results.setToolTipText("View labyrinth results");
		this.results.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.add(this.start);
		this.add(this.results);
		this.addSeparator();
		this.add(this.simpleIconsCombo);
		this.addSeparator();
		this.add(this.smileyIconsCombo);
		this.addListeners();

	}

	class IconsCellRenderer extends DefaultListCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList<?> list,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// Get the default cell renderer
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);

			// Create a font based on the item value
			return label;
		}
	}

	private void addListeners() {

		this.start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				parent.getTable().getTable().reset();
				parent.getDesktopLabyrinth().startGame();
				parent.repaint();
				parent.getDesktopLabyrinth().requestFocus();
				parent.getDesktopLabyrinth().requestFocusInWindow();
				parent.getDesktopLabyrinth().setFocusable(true);

			}

		});

		this.start.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				parent.getDesktopLabyrinth().requestFocus();
				parent.getDesktopLabyrinth().requestFocusInWindow();
				parent.getDesktopLabyrinth().setFocusable(true);
				e.consume();

			}

		});

		this.simpleIconsCombo.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				e.consume();

			}

		});

		this.simpleIconsCombo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				
				parent.getDesktopLabyrinth().requestFocus();
				parent.getDesktopLabyrinth().requestFocusInWindow();
				parent.getDesktopLabyrinth().setFocusable(true);
				parent.getDesktopLabyrinth()
						.getPlayerImg()
						.setImage(
								(simpleIconsCombo.getSelectedIndex() + 1)
										+ ".png");
				parent.repaint();

			}

		});

		this.smileyIconsCombo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				parent.getDesktopLabyrinth().requestFocus();
				parent.getDesktopLabyrinth().requestFocusInWindow();
				parent.getDesktopLabyrinth().setFocusable(true);
				parent.getDesktopLabyrinth()
						.getPlayerImg()
						.setImage(
								"s" + (smileyIconsCombo.getSelectedIndex() + 1)
										+ ".png");
				parent.repaint();

			}

		});

		this.smileyIconsCombo.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				e.consume();

			}

		});

		this.results.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) parent.getAux().getLayout();
				if (results.isSelected()) {
					cl.show(parent.getAux(), "t");
				} else {
					cl.show(parent.getAux(), "dl");
					parent.getDesktopLabyrinth().requestFocus();
					parent.getDesktopLabyrinth().requestFocusInWindow();
					parent.getDesktopLabyrinth().setFocusable(true);
				}
			}
		});
	}
}
