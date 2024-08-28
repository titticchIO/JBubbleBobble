package game.view;

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
import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.bubbles.WaterBubble;
import game.model.bubbles.special_effects.Water;
import game.model.enemies.*;
import game.model.level.Level;
import game.model.powerups.OrangeParasol;
import game.model.powerups.Parasol;
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
		setBackground(Color.BLACK);
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

	public void startLevelTransition(int levelNumber) {
		// Cattura l'immagine attuale del livello
		BufferedImage currentLevelImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = currentLevelImage.getGraphics();
		paintComponent(g); // Disegna lo stato corrente del livello su currentLevelImage
		g.dispose();

		// Passa l'immagine catturata al transitionPanel
		TransitionPanel transitionPanel = View.getInstance().getTransitionPanel();
		transitionPanel.startTransition(currentLevelImage,
				ImageLoader.importImg("/levelsimg/Livello" + levelNumber + ".png"));
		gameFrame.showState(GameFrame.Screen.TRANSITION);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(SCALE, SCALE);
		g2d.scale(SCALE, SCALE);
		super.paintComponent(g2d);

		g2d.drawImage(tilesImage, 0, 0, this);
		renderPlayer(View.getInstance().getLevel().getPlayer(), g2d);
		View.getInstance(gameFrame).getLevel().getEntities().forEach(e -> renderEntity(e, g2d));
	}

	public void renderPlayer(Player player, Graphics g) {
		Image playerImg = null;
		if (player.isStunned())
			playerImg = player.getDirection() == Direction.LEFT ? AnimationLoader.getPlayerImage("stun_left")
					: AnimationLoader.getPlayerImage("stun_right");
		else {
			playerImg = player.getDirection() == Direction.STATIC ? Images.getImage(Player.CODE, "static")
					: AnimationLoader.getPlayerImage("walk_" + player.getDirection().name().toLowerCase());
		}
		g.drawImage(playerImg, (int) player.getX(), (int) player.getY(), (int) player.getWidth() + 1,
				(int) player.getHeight() + 1, null);
	}

	public void renderEntity(Entity entity, Graphics g) {
		Image img;
		img = switch (entity) {
		case Tile tile -> Images.getImage(tile.getCode());
		case Laser laser -> Images.getImage(Laser.CODE);
		case Enemy enemy ->
			AnimationLoader.loadEnemyImage(enemy.getCode(), enemy.getDirection(), enemy.getColorState());
		case OrangeParasol orangeParasol -> Images.getImage(Parasol.CODE, "orange");
		case RedParasol redParasol -> Images.getImage(Parasol.CODE, "red");
		case PurpleParasol purpleParasol -> Images.getImage(Parasol.CODE, "purple");
		case PlayerBubble playerBubble -> {
			if (playerBubble.getLifeSpan() <= 500) {
				yield AnimationLoader.loadBubblePoppingImage();
			} else if (playerBubble.hasEnemy()) {
				yield AnimationLoader.loadBubbleEnemyImage(playerBubble.getEnemy().getCode());
			} else {
				yield Images.getImage(playerBubble.getCode());
			}
		}
		case WaterBubble waterBubble -> AnimationLoader.loadEntityImage("/bubbles/water_bubble.gif");
		case Water water -> HelpMethods.isEntityGrounded(water) ? Images.getImage('_') : Images.getImage('|');
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