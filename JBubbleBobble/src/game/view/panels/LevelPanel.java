package game.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.model.entities.Entity;
import game.model.entities.MovingEntity.Direction;
import game.model.entities.Player;
import game.model.HelpMethods;
import game.model.Tile;
import game.model.bubbles.PlayerBubble;
import game.model.bubbles.WaterBubble;
import game.model.bubbles.special_effects.Water;
import game.model.enemies.*;
import game.model.level.Level;
import game.model.powerups.OrangeParasol;
import game.model.powerups.Parasol;
import game.model.powerups.PurpleParasol;
import game.model.powerups.RedParasol;
import game.view.AnimationAndImagesLoader;
import game.view.ImageLoader;
import game.view.View;
import game.view.frames.GameFrame;

/**
 * The {@code LevelPanel} class represents the panel where the game level is
 * rendered. It manages the drawing of tiles, entities (such as enemies,
 * power-ups, bubbles, etc.), and the player character on the screen. It also
 * handles scaling and starting level transitions.
 */
public class LevelPanel extends JPanel {
	public static final long serialVersionUID = 1L;
	public final static float SCALE = 1.5f;
	private BufferedImage tilesImage; // the image of the level's tiles, rendered once at the start of each level

	/**
	 * Constructs the {@code LevelPanel}, setting the initial panel size and
	 * background color.
	 */
	public LevelPanel() {
		setPanelSize();
		setBackground(Color.BLACK);
	}

	/**
	 * Sets the preferred size of the panel based on the game's width, height, and
	 * scale.
	 */
	private void setPanelSize() {
		Dimension size = new Dimension((int) (Level.GAME_WIDTH * SCALE), (int) (Level.GAME_HEIGHT * SCALE));
		setPreferredSize(size);
	}

	/**
	 * Renders the tiles of the level once and stores them in a buffered image for
	 * efficient rendering.
	 * 
	 * @param gameFrame the {@code GameFrame} instance representing the main game
	 *                  window.
	 */
	public void renderTilesOnce(GameFrame gameFrame) {
		tilesImage = new BufferedImage(Level.GAME_WIDTH, Level.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tilesImage.getGraphics();
		View.getInstance(gameFrame).getLevel().getTiles().forEach(t -> renderEntity(t, g));
	}

	/**
	 * Starts the transition between levels. Captures the current level's image and
	 * passes it to the transition panel for smooth transitioning between levels.
	 *
	 * @param levelNumber the number of the level that the game is transitioning to.
	 */
	public void startLevelTransition(int levelNumber) {
		BufferedImage currentLevelImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentLevelImage.getGraphics();
		paintComponent(g); // Capture the current state of the level
		g.dispose();
		TransitionPanel transitionPanel = View.getInstance().getTransitionPanel();
		transitionPanel.startTransition(currentLevelImage,
				ImageLoader.importImg("/levelsimg/Livello" + levelNumber + ".png"));
		View.getInstance().getGameFrame().showState(GameFrame.Screen.TRANSITION);
	}

	/**
	 * Paints the level by rendering the tiles, player, enemies, and other entities
	 * on the screen. It uses scaling and translation to adjust for the game's scale
	 * factor.
	 * 
	 * @param g the {@code Graphics} object used for rendering.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(SCALE, SCALE);
		g2d.scale(SCALE, SCALE);
		super.paintComponent(g2d);

		g2d.drawImage(tilesImage, 0, 0, this);
		renderPlayer(View.getInstance().getLevel().getPlayer(), g2d);
		View.getInstance().getLevel().getEnemyManager().getEnemies().forEach(e -> renderEnemy(e, g2d));
		View.getInstance().getLevel().getEntities().forEach(e -> renderEntity(e, g2d));
	}

	/**
	 * Renders the player on the screen. The player's state determines which image
	 * is drawn (e.g., stunned, walking, shooting bubbles).
	 *
	 * @param player the {@code Player} entity representing the player.
	 * @param g      the {@code Graphics} object used for rendering.
	 */
	private void renderPlayer(Player player, Graphics g) {
		Image playerImg;
		if (player.isStunned())
			playerImg = player.getDirection() == Direction.LEFT ? AnimationAndImagesLoader.getPlayerImage("stun_left")
					: AnimationAndImagesLoader.getPlayerImage("stun_right");
		else if (!player.canShoot()) {
			playerImg = player.getBubbleDirection() == Direction.LEFT
					? AnimationAndImagesLoader.getPlayerImage("bubble_left")
					: AnimationAndImagesLoader.getPlayerImage("bubble_right");
		} else {
			playerImg = player.getDirection() == Direction.STATIC
					? AnimationAndImagesLoader.getImage(Player.CODE, "static")
					: AnimationAndImagesLoader.getPlayerImage("walk_" + player.getDirection().name().toLowerCase());
		}
		g.drawImage(playerImg, (int) player.getX(), (int) player.getY(), (int) player.getWidth() + 1,
				(int) player.getHeight() + 1, null);
	}

	/**
	 * Renders an enemy on the screen. The enemy's state (alive or dead) determines
	 * which image is drawn.
	 *
	 * @param enemy the {@code Enemy} entity to render.
	 * @param g     the {@code Graphics} object used for rendering.
	 */
	private void renderEnemy(Enemy enemy, Graphics g) {
		Image enemyImage;
		if (enemy.isDead()) {
			enemyImage = AnimationAndImagesLoader.loadDeadEnemyImage(enemy.getCode());
		} else {
			enemyImage = AnimationAndImagesLoader.loadEnemyImage(enemy.getCode(), enemy.getDirection(),
					enemy.getColorState());
		}
		g.drawImage(enemyImage, (int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth() + 1,
				(int) enemy.getHeight() + 1, null);
	}

	/**
	 * Renders a generic entity on the screen. Different entities (e.g., tiles,
	 * bubbles, parasols) have different images based on their type and state.
	 *
	 * @param entity the {@code Entity} to render.
	 * @param g      the {@code Graphics} object used for rendering.
	 */
	private void renderEntity(Entity entity, Graphics g) {
		Image img;
		img = switch (entity) {
		case Tile tile -> AnimationAndImagesLoader.getImage(tile.getCode());
		case Laser laser -> AnimationAndImagesLoader.getImage(Laser.CODE);
		case OrangeParasol orangeParasol -> AnimationAndImagesLoader.getImage(Parasol.CODE, "orange");
		case RedParasol redParasol -> AnimationAndImagesLoader.getImage(Parasol.CODE, "red");
		case PurpleParasol purpleParasol -> AnimationAndImagesLoader.getImage(Parasol.CODE, "purple");
		case PlayerBubble playerBubble -> {
			if (playerBubble.isPopped()) {
				yield AnimationAndImagesLoader.loadBubblePoppingImage();
			} else if (playerBubble.hasEnemy()) {
				yield AnimationAndImagesLoader.loadBubbleEnemyImage(playerBubble.getEnemy().getCode());
			} else {
				yield AnimationAndImagesLoader.getImage(playerBubble.getCode());
			}
		}
		case WaterBubble waterBubble -> AnimationAndImagesLoader.loadEntityImage("/bubbles/water_bubble.gif");
		case Water water -> HelpMethods.isEntityGrounded(water) ? AnimationAndImagesLoader.getImage('_')
				: AnimationAndImagesLoader.getImage('|');
		default -> AnimationAndImagesLoader.getImage(entity.getCode());
		};
		if (entity instanceof Tile) {
			g.drawImage(img, (int) entity.getX(), (int) entity.getY(), (int) entity.getWidth(),
					(int) entity.getHeight(), null);
		} else
			g.drawImage(img, (int) entity.getX(), (int) entity.getY(), (int) entity.getWidth() + 1,
					(int) entity.getHeight() + 1, null);
	}

}