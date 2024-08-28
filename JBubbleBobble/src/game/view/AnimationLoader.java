package game.view;

import javax.swing.ImageIcon;

import game.model.EnemiesPath;
import game.model.enemies.Enemy.ColorState;
import game.model.entities.MovingEntity.Direction;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AnimationLoader {

	// Mappa per contenere le animazioni del player caricate staticamente
	private static final Map<String, ImageIcon> playerAnimations = new HashMap<>();

	// Percorsi delle animazioni del player
	private static final String PLAYER_WALK_LEFT = "player/left_walk.gif";
	private static final String PLAYER_WALK_RIGHT = "player/right_walk.gif";
//	private static final String PLAYER_JUMP = "player/jump.gif";
	// Aggiungi altri percorsi delle animazioni del player qui

	static {
		// Carica le animazioni del player staticamente
		playerAnimations.put("walk_left", loadImageIcon(PLAYER_WALK_LEFT));
		playerAnimations.put("walk_right", loadImageIcon(PLAYER_WALK_RIGHT));
//		playerAnimations.put("jump", loadImageIcon(PLAYER_JUMP));
		// Carica altre animazioni del player
	}

	// Metodo per caricare un'ImageIcon dato un percorso
	private static ImageIcon loadImageIcon(String relativePath) {
		String absolutePath = getAbsolutePath(relativePath);
		return new ImageIcon(absolutePath);
	}

	// Metodo per ottenere un'animazione del player
	public static ImageIcon getPlayerAnimation(String animationKey) {
		return playerAnimations.get(animationKey);
	}

	// Metodo per ottenere un'immagine del player
	public static Image getPlayerImage(String animationKey) {
		ImageIcon icon = getPlayerAnimation(animationKey);
		return icon != null ? icon.getImage() : null;
	}

	// Metodo per caricare dinamicamente animazioni di altre entità
	public static ImageIcon loadEntityAnimation(String relativePath) {
		return loadImageIcon(relativePath);
	}

	// Metodo per ottenere l'immagine di un'entità
	public static Image loadEntityImage(String relativePath) {
		ImageIcon icon = loadEntityAnimation(relativePath);
		return icon != null ? icon.getImage() : null;
	}

	public static String getAbsolutePath(String relativePath) {
		File file = new File("resources/" + relativePath);
		if (file.exists()) {
			return file.getAbsolutePath();
		} else {
			throw new IllegalArgumentException("File non trovato: " + relativePath);
		}
	}
	
	public static Image loadBubblePoppingImage() {
		return loadEntityImage("/bubbles/bubble_pops.gif");
	}
	
	public static Image loadBubbleEnemyImage(char code) {
		return loadEntityImage(EnemiesPath.getPath(code) + "bubbled.gif");
	}
	
	public static Image loadEnemyImage(char code, Direction direction, ColorState color) {
		if (code == 'I' || code == 'U') 
			return loadEntityImage(EnemiesPath.getPath(code) + "default-" + color.name().toLowerCase() + ".gif");
		return loadEntityImage(EnemiesPath.getPath(code) + direction.name().toLowerCase() + "-" + color.name().toLowerCase() + ".gif");
	}
}
