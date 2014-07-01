package com.labyrinth.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LabyrinthUtils {
	private final static String CSV_SEPARATOR = ",";

	public static Map<String, String> loadScoresFromFile(String csvFilePath) {
		Map<String, String> result = new HashMap<>();
		String line;
		int linesRead = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
			while ((line = br.readLine()) != null) {

				if (linesRead > 0) {
					// use comma as separator
					String[] score = line.split(CSV_SEPARATOR);
					result.put(score[0], score[1]);
				}
				linesRead++;
			}

		} catch (IOException e) {
			Logger.getGlobal().warning("Scores file not present");
		}
		return result;
	}

	public static synchronized void saveScoreInFile(String csvFilePath,
			String user, String name) {
		try {
			boolean needsHeader = false;
			File file = new File(csvFilePath);
			if (!file.exists()) {
				file.createNewFile();
				needsHeader = true;
			}

			FileWriter fw = new FileWriter(file, true);
			BufferedWriter writer = new BufferedWriter(fw);
			if (needsHeader) {
				writer.write("Name,Score");
				writer.newLine();
			}
			writer.write(user + "," + name);
			writer.newLine();
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
