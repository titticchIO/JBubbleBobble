package editor.model;

import java.io.*;
import java.util.ArrayList;
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
            System.out.println(x + "|" + y);
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

        String filePath = LEVELS_REL_PATH + "/Level" + levelNum + ".txt";

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
        String filePath = LEVELS_REL_PATH + "/Level" + levelNum + ".txt";

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
     * Sets the outer walls of the level to the specified tile.
     *
     * @param tile The character representing the wall tile.
     */
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
            System.err.println("Error setting walls: " + e.getMessage());
        }
    }
}
