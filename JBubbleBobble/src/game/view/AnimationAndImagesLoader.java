package game.view;

import javax.swing.ImageIcon;

import game.model.Paths;
import game.model.enemies.Enemy.ColorState;
import game.model.entities.MovingEntity.Direction;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AnimationAndImagesLoader {

	// Mappa per contenere le animazioni del player caricate staticamente
	private static final Map<String, ImageIcon> playerAnimations = new HashMap<>();

	// Percorsi delle animazioni del player
	private static final String PLAYER_WALK_LEFT = "player/left_walk.gif";
	private static final String PLAYER_WALK_RIGHT = "player/right_walk.gif";
	private static final String PLAYER_STUN_LEFT = "player/stun_left.gif";
	private static final String PLAYER_STUN_RIGHT = "player/stun_right.gif";
	// Aggiungi altri percorsi delle animazioni del player qui

	static {
		// Carica le animazioni del player staticamente
		playerAnimations.put("walk_left", loadImageIcon(PLAYER_WALK_LEFT));
		playerAnimations.put("walk_right", loadImageIcon(PLAYER_WALK_RIGHT));
		playerAnimations.put("stun_left", loadImageIcon(PLAYER_STUN_LEFT));
		playerAnimations.put("stun_right", loadImageIcon(PLAYER_STUN_RIGHT));
		// Carica altre animazioni del player
	}

	// Metodo per caricare un'ImageIcon dato un percorso
	private static ImageIcon loadImageIcon(String relativePath) {
		String absolutePath = Paths.getAbsolutePath(relativePath);
		return new ImageIcon(absolutePath);
	}

	// Metodo per ottenere un'animazione del player
	private static ImageIcon getPlayerAnimation(String animationKey) {
		return playerAnimations.get(animationKey);
	}

	// Metodo per ottenere un'immagine del player
	public static Image getPlayerImage(String animationKey) {
		ImageIcon icon = getPlayerAnimation(animationKey);
		return icon != null ? icon.getImage() : null;
	}

	// Metodo per caricare dinamicamente animazioni di altre entità
	private static ImageIcon loadEntityAnimation(String relativePath) {
		return loadImageIcon(relativePath);
	}

	// Metodo per ottenere l'immagine di un'entità
	public static Image loadEntityImage(String relativePath) {
		ImageIcon icon = loadEntityAnimation(relativePath);
		return icon != null ? icon.getImage() : null;
	}

	
	public static Image loadBubblePoppingImage() {
		return loadEntityImage("/bubbles/bubble_pops.gif");
	}
	
	public static Image loadBubbleEnemyImage(char code) {
		return loadEntityImage(Paths.getPath(code) + "bubbled.gif");
	}
	
	public static Image loadEnemyImage(char code, Direction direction, ColorState color) {
		if (code == 'I' || code == 'U') 
			return loadEntityImage(Paths.getPath(code) + "default-" + color.name().toLowerCase() + ".gif");
		return loadEntityImage(Paths.getPath(code) + direction.name().toLowerCase() + "-" + color.name().toLowerCase() + ".gif");
	}
	
	public static Image loadDeadEnemyImage(char code) {
		return loadEntityImage(Paths.getPath(code) + "dead.gif");
	}
	

	public static BufferedImage getImage(char imageCode) {
		BufferedImage img;
		if (Character.isAlphabetic(imageCode))
			img = ImageLoader.importImg(Paths.getPath(imageCode) + "image.png");
		else
			img = ImageLoader.importImg(Paths.getPath(imageCode) + ".png");
		return img;
	}
	
	public static BufferedImage getImage(Character type, String position) {
		return ImageLoader.importImg(Paths.getPath(type) + position + ".png");
	}


}
