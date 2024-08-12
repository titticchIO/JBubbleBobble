package editor.model;

import java.io.File;
import java.util.*;

public class LevelReader {

	public static List<String> getLevels() {
		List<String> levels = new ArrayList<String>();

		File dir = new File(LevelManager.LEVELS_REL_PATH);

		for (File file : dir.listFiles()) {
			levels.add(file.getName().replaceAll("[^0-9]", ""));
		}

		return levels;
	}
}
