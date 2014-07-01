package com.labyrinth.gui;

import java.awt.Component;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * @author Gaby
 */
public class JLabyrinthTable extends JTable {

	private static final long serialVersionUID = 1L;
	String[] columnNames = { "Username", "Time(ms)" };
	Vector<Vector<String>> lines;

	/** Creates a new instance of JLabyrinthTable */
	public JLabyrinthTable() {

		this.lines = new Vector<>();
		this.setModel(new AbstractTableModel() {

			private static final long serialVersionUID = 1L;

			public String getColumnName(int col) {

				return columnNames[col].toString();

			}

			public int getRowCount() {

				return lines.size();

			}

			public int getColumnCount() {

				return columnNames.length;

			}

			public Class<? extends Object> getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			public Object getValueAt(int row, int col) {

				return ((Vector<?>) lines.elementAt(row)).elementAt(col);

			}

			public boolean isCellEditable(int row, int col) {

				return false;

			}

		});
		this.setDefaultRenderer(String.class,
				new MyTableCellRenderer(this.getDefaultRenderer(String.class)));
	}

	public void addRow(String username, String time) {

		for (int i = 0; i < lines.size(); i++)
			if (this.getValueAt(i, 0).equals(username))
				return;
		Vector<String> row = new Vector<>();
		row.add(username);
		row.add(time);
		this.lines.add(row);
		((AbstractTableModel) this.getModel()).fireTableDataChanged();

	}

	public void reset() {

		this.lines.removeAllElements();
		((AbstractTableModel) this.getModel()).fireTableDataChanged();

	}

	public class MyTableCellRenderer implements TableCellRenderer {
		private TableCellRenderer defaultRenderer;

		public MyTableCellRenderer(TableCellRenderer renderer) {
			defaultRenderer = renderer;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			Component comp = defaultRenderer.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column);

			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				label.setHorizontalAlignment(JLabel.CENTER);
			}
			return comp;
		}
	}

}
