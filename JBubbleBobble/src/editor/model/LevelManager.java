
package editor.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {

	public static final String LEVELS_REL_PATH = "resources/levels";
	public static final int ROWS = 24;
	public static final int COLS = 30;

	private static char[][] level;

	public LevelManager() {
		level =  new char[ROWS][COLS];
		emptyLevel();
	}

	public LevelManager(char wallTile) {
		level = new char[ROWS][COLS];

		setWalls(wallTile);
		emptyLevel();
	}

	public static void setLevel(char[][] levelData) {
		level = levelData;
	}

	public static void emptyLevel() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				level[i][j] = ' ';
			}
		}
	}

	public void setWalls(char tile) {
		try {
			for (int x = 0; x < COLS; x++) {
				level[0][x] = tile;
				level[ROWS - 1][x] = tile;
			}

			for (int y = 0; y < ROWS; y++) {
				level[y][0] = tile;
				level[y][COLS - 1] = tile;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Errore nell'impostazione dei muri: " + e.getMessage());
		}

	}

	public static void setTile(int y, int x, char tile) {
		if (y >= 0 && y < ROWS && x >= 0 && x < COLS) {
			level[y][x] = tile;
		} else {
			System.out.println(x + "|" + y);
			throw new IndexOutOfBoundsException("Posizione fuori dai limiti del livello");
		}
	}

	public static char[][] getLevel() {
		return level;
	}

	private static String matriceToString() {
		List<String> out = new ArrayList<>();

		for (int i = 0; i < ROWS; i++) {
			out.add(String.valueOf(level[i]));
		}

		return String.join("\n", out);
	}

	public static void saveLevelFile(int levelNum) {

		String filePath = LEVELS_REL_PATH + "/Livello" + levelNum + ".txt";

		try {
			File file = new File(filePath);

			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("New file created: " + filePath);
				} else {
					System.err.println("Error creating file: " + filePath);
					return; // Exit method if file creation fails
				}
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(matriceToString());
				System.out.println("Level " + levelNum + " saved successfully!");
			} catch (IOException e) {
				System.err.println("Error writing to file: " + e.getMessage());
			}
		} catch (IOException e) {
			System.err.println("Error accessing file:" + filePath + "|" + e.getMessage());
		}
	}

	public static void deleteLevelFile(int levelNum) {
		String filePath = LEVELS_REL_PATH + "/Livello" + levelNum + ".txt";

		try {
			File file = new File(filePath);

			if (file.exists()) {
				if (file.delete()) {
					System.out.println("File deleted: " + filePath);
				} else {
					System.err.println("Error deleting file: " + filePath);
					return; // Exit method if file creation fails
				}
			}

		} catch (Exception e) {
			System.err.println("Error deleting file:" + filePath + "|" + e.getMessage());
		}
	}
}