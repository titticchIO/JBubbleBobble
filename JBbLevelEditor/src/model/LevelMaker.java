package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelMaker {
	public static final int ROWS = 25;
	public static final int COLS = 30;
	private static String[][] level;

	public LevelMaker() {
		//this("#1");
		level = new String[ROWS][COLS];
		emptyLevel();
	}

	public LevelMaker(String wallTile) {
		level = new String[ROWS][COLS];

		setWalls(wallTile);
		emptyLevel();
	}

	public void emptyLevel() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				level[i][j] = " ";
			}
		}
	}

	public void setWalls(String tile) {
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

	public static void setTile(int y, int x, String tile) {
		if (y >= 0 && y < ROWS && x >= 0 && x < COLS) {
			level[y][x] = tile;
		} else {
			throw new IndexOutOfBoundsException("Posizione fuori dai limiti del livello");
		}
	}

	private static String matriceToString() {
		List<String> out = new ArrayList<>();

		for (int i = 0; i < ROWS; i++) {
			out.add("|" + String.join("|", level[i]));
		}

		return String.join("\n", out);
	}

	public static  void saveLevelToFile(int levelNum) {

		//String filePath = "/levels/Livello" + levelNum + ".txt";
		String filePath = "C:\\Users\\Università\\Documents\\livello5.txt";



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
			System.err.println("Error accessing file: " + e.getMessage());
		}

//		String filePath = "JBubbleBobbleProg/JBubbleBobble/resources/levels/Livello" + levelNum + ".txt";
//
//		
//		try {
//			File file = new File(filePath);
//			
//			if (!file.exists())
//				file.createNewFile();
//			
//			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//				bw.write(toString());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/*
	public static void main(String[] args) {
		LevelMaker l1 = new LevelMaker();

		l1.setTile(1, 1, "#1");
		l1.setTile(3, 3, "#3");
		l1.setTile(15, 15, "#15");

		l1.saveLevelToFile(2);

	}
	*/
}