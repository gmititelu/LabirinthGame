package com.labyrinth.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * @author Gaby
 */
public class JPanelLabyrinth extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int LABYRINTH_SIZE = 23;
	private ToolBar toolBar;
	private JDesktopLabyrinth desktopLabyrinth;
	private JLabyrinthTablePanel table;
	private JPanel aux;

	public String generateString() {
		return String.valueOf(Math.round(Math.random() * 10000));
	}

	public JPanelLabyrinth() {

		this.setLayout(new BorderLayout());
		this.setFocusable(false);
		String user = generateString();

		this.table = new JLabyrinthTablePanel(user);
		this.desktopLabyrinth = new JDesktopLabyrinth(LABYRINTH_SIZE, user,
				this.table);
		this.toolBar = new ToolBar(this);

		this.add(this.toolBar, BorderLayout.SOUTH);
		aux = new JPanel();
		aux.setLayout(new CardLayout());
		aux.add(new JScrollPane(desktopLabyrinth), "dl");
		aux.add(new JScrollPane(table), "t");

		CardLayout cl = (CardLayout) aux.getLayout();
		cl.show(aux, "dl");
		this.add(aux, BorderLayout.CENTER);
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public JDesktopLabyrinth getDesktopLabyrinth() {
		return desktopLabyrinth;
	}

	public void setDesktopLabyrinth(JDesktopLabyrinth desktopLabyrinth) {
		this.desktopLabyrinth = desktopLabyrinth;
	}

	public JLabyrinthTablePanel getTable() {
		return table;
	}

	public void setTable(JLabyrinthTablePanel table) {
		this.table = table;
	}

	public JPanel getAux() {
		return aux;
	}

	public void setAux(JPanel aux) {
		this.aux = aux;
	}

}
