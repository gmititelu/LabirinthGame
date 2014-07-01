package com.labyrinth.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.labyrinth.utils.LabyrinthUtils;

/**
 * 
 * @author Gaby
 */
public class JLabyrinthTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JLabyrinthTable table;
	private String fileScoresPath;
	private JLabel time;

	/** Creates a new instance of JLabyrinthTablePanel */
	public JLabyrinthTablePanel(String user) {

		this.setLayout(new BorderLayout());
		JPanel aux = new JPanel();
		aux.setLayout(new BorderLayout());
		JLabel userLabel = new JLabel("Labyrinth results");
		userLabel.setHorizontalAlignment(JLabel.CENTER);
		aux.add(userLabel, BorderLayout.CENTER);
		aux.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		this.add(aux, BorderLayout.NORTH);

		this.table = new JLabyrinthTable();
		this.add(new JScrollPane(table), BorderLayout.CENTER);

		this.time = new JLabel();
		time.setHorizontalAlignment(JLabel.CENTER);
		aux = new JPanel();
		aux.setLayout(new BorderLayout());
		aux.add(time, BorderLayout.CENTER);
		aux.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		this.add(aux, BorderLayout.SOUTH);
		time.setText("Time");
		fileScoresPath = "scores.csv";

		initializeValues(fileScoresPath);

	}

	private void initializeValues(String path) {
		Map<String, String> scores = LabyrinthUtils.loadScoresFromFile(path);
		for (Map.Entry<String, String> entry : scores.entrySet()) {
			this.addRow(entry.getKey(), entry.getValue());
		}
	}

	public void setLabelTime(String time) {
		this.time.setText(time);
	}

	public void addRow(String id, String time) {
		this.table.addRow(id, time);
	}

	public void addNewRow(String userName, String time) {
		LabyrinthUtils.saveScoreInFile(fileScoresPath, userName, time);
		this.addRow(userName, time);
	}

	public JLabyrinthTable getTable() {
		return table;
	}

}
