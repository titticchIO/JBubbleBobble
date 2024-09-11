package editor.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the level data for the game, allowing levels to be created, modified, and saved to files.
 */
public class LevelManager {

    // Public static constants
    public static final String LEVELS_REL_PATH = "resources/levels";
    public static final int ROWS = 24;
    public static final int COLS = 30;

    // Private static field
    private static char[][] level;

    /**
     * Constructs a new LevelManager and initializes an empty level.
     */
    public LevelManager() {
        level = new char[ROWS][COLS];
        emptyLevel();
    }
    
    /**
     * Sets the current level data.
     *
     * @param levelData The new level data.
     */
    public static void setLevel(char[][] levelData) {
        level = levelData;
    }

    /**
     * Clears the current level, setting all tiles to empty (' ').
     */
    public static void emptyLevel() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                level[i][j] = ' ';
    }

    /**
     * Sets a specific tile in the level.
     *
     * @param y    The y-coordinate of the tile.
     * @param x    The x-coordinate of the tile.
     * @param tile The character representing the tile.
     */
    public static void setTile(int y, int x, char tile) {
        if (y >= 0 && y < ROWS && x >= 0 && x < COLS) 
            LevelManager.level[y][x] = tile;
        else {
            throw new IndexOutOfBoundsException("Position out of level bounds");
        }
    }

    /**
     * Converts the level matrix to a string.
     *
     * @return The string representation of the level matrix.
     */
    private static String matriceToString() {
        List<String> out = new ArrayList<>();

        for (int i = 0; i < ROWS; i++) {
            out.add(String.valueOf(level[i]));
        }

        return String.join("\n", out);
    }

    /**
     * Saves the current level to a file.
     *
     * @param levelNum The number identifying the level.
     */
    public static void saveLevelFile(int levelNum) {

        String filePath = LEVELS_REL_PATH + "/Livello" + levelNum + ".txt";

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                if (file.createNewFile()) 
                    System.out.println("New file created: " + filePath);
                else {
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

    /**
     * Deletes the level file with the specified number.
     *
     * @param levelNum The number identifying the level.
     */
    public static void deleteLevelFile(int levelNum) {
        String filePath = LEVELS_REL_PATH + "/Livello" + levelNum + ".txt";

        try {
            File file = new File(filePath);

            if (file.exists()) {
                if (file.delete()) 
                    System.out.println("File deleted: " + filePath);
                else {
                    System.err.println("Error deleting file: " + filePath);
                    return; // Exit method if file creation fails
                }
            }

        } catch (Exception e) {
            System.err.println("Error deleting file:" + filePath + "|" + e.getMessage());
        }
    }

	/**
	 * Retrieves the list of levels by reading the filenames from the specified directory.
	 * The method extracts numeric values from the filenames.
	 *
	 * @return a list of strings representing the levels.
	 */
	public static List<String> getLevels() {
	    List<String> levels = new ArrayList<>();
	
	    File dir = new File(LEVELS_REL_PATH);
	
	    for (File file : dir.listFiles())
	        levels.add(file.getName().replaceAll("[^0-9]", ""));
	
	    Collections.sort(levels, new Comparator<String>() {
	        @Override
	        public int compare(String s1, String s2) {
	            return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
	        }
	    });
	
	    
	    return levels;
	}
}
