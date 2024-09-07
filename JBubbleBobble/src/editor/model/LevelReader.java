package editor.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The LevelReader class provides methods to retrieve levels from the file system.
 */
public class LevelReader {

    /**
     * Retrieves the list of levels by reading the filenames from the specified directory.
     * The method extracts numeric values from the filenames.
     *
     * @return a list of strings representing the levels.
     */
    public static List<String> getLevels() {
        List<String> levels = new ArrayList<>();

        File dir = new File(LevelManager.LEVELS_REL_PATH);

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
