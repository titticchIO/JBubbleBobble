package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.model.entities.Entity;
import game.model.entities.Player;
import game.model.HelpMethods;
import game.model.bubbles.PlayerBubble;
import game.model.bubbles.WaterBubble;
import game.model.bubbles.special_effects.Water;
import game.model.enemies.*;
import game.model.level.Level;
import game.model.powerups.OrangeParasol;
import game.model.powerups.PurpleParasol;
import game.model.powerups.RedParasol;
import game.model.tiles.Tile;

public class LevelPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public final static float SCALE = 1.5f;
	private BufferedImage tilesImage;
	private GameFrame gameFrame;

//	private MenuPanel menuPanel

//	NON AGGIUNGERE IL PATTERN SINGLETON!!!!!

	public LevelPanel(GameFrame gameFrame) {
		setPanelSize();
		this.gameFrame = gameFrame;
	}

	private void setPanelSize() {
		Dimension size = new Dimension((int) (Level.GAME_WIDTH * SCALE), (int) (Level.GAME_HEIGHT * SCALE));
		setPreferredSize(size);
	}

	public void renderTilesOnce() {
		tilesImage = new BufferedImage(Level.GAME_WIDTH, Level.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tilesImage.getGraphics();
		View.getInstance(gameFrame).getLevel().getTiles().forEach(t -> renderEntity(t, g));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(SCALE, SCALE);
		g2d.scale(SCALE, SCALE);
		super.paintComponent(g2d);

		g2d.drawImage(tilesImage, 0, 0, this);
		View.getInstance(gameFrame).getLevel().getEntities().forEach(e -> renderEntity(e, g2d));
	}

	public void renderEntity(Entity entity, Graphics g) {
		Image img;
		img = switch (entity) {
		case Tile tile -> Images.getImage(tile.getCode());
		case Laser laser -> Images.getImage("I", "red");
		case Enemy enemy -> AnimationLoader.loadEnemyImage(enemy.getCode(), enemy.getDirection(), enemy.getColor());
		case OrangeParasol orangeParasol -> Images.getImage("@", "orange");
		case RedParasol redParasol -> Images.getImage("@", "red");
		case PurpleParasol purpleParasol -> Images.getImage("@", "purple");
		case Player player -> switch (player.getDirection()) {
		case RIGHT -> AnimationLoader.getPlayerImage("walk_right");
		case LEFT -> AnimationLoader.getPlayerImage("walk_left");
		case STATIC -> Images.getImage("P", "static");
		default -> throw new IllegalArgumentException("Unexpected value: " + player.getDirection());
		};
		case PlayerBubble playerBubble -> {
			if (playerBubble.hasEnemy()) {
				yield AnimationLoader.loadBubbleEnemyImage(playerBubble.getEnemy().getCode(),
						playerBubble.getEnemy().getColor());
			} else {
				yield Images.getImage(playerBubble.getCode());
			}
		}
		case WaterBubble waterBubble -> AnimationLoader.loadEntityImage("/bubbles/waterBubble.gif");
		case Water water -> HelpMethods.isEntityGrounded(water) ? Images.getImage("_") : Images.getImage("|");
		default -> Images.getImage(entity.getCode());
		};
		if (entity instanceof Tile) {
			g.drawImage(img, (int) entity.getX(), (int) entity.getY(), (int) entity.getWidth(),
					(int) entity.getHeight(), null);
		} else
			g.drawImage(img, (int) entity.getX(), (int) entity.getY(), (int) entity.getWidth() + 1,
					(int) entity.getHeight() + 1, null);
	}

}