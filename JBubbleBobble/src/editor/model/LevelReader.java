package editor.model;

import java.io.File;
import java.util.*;

public class LevelReader {

	public static final String LEVELS_REL_PATH = "resources/levels";
	private List<String> levels;

	public LevelReader() {
		levels = new ArrayList<String>();
		
		File dir = new File(LEVELS_REL_PATH);
		
		for (File file : dir.listFiles()) {
			levels.add(file.getName().replaceAll("[^0-9]", ""));
		}
	}
	
	public List<String> getLevels() {
		return levels;
	}
	
	public void addFileNumber(String FileNumber) {
		levels.add(FileNumber);
	}
}
