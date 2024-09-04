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
import game.view.AnimationAndImagesLoader;
import game.view.ImageLoader;
import game.view.View;
import game.view.frames.GameFrame;
import game.view.frames.GameFrame.Screen;

public class LevelPanel extends JPanel {
	public static final long serialVersionUID = 1L;
	public final static float SCALE = 1.5f;
	private BufferedImage tilesImage;
	private GameFrame gameFrame;


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
		View.getInstance().getLevel().getEnemyManager().getEnemies().forEach(e -> renderEnemy(e, g2d));
		View.getInstance(gameFrame).getLevel().getEntities().forEach(e -> renderEntity(e, g2d));
	}

	private void renderPlayer(Player player, Graphics g) {
		Image playerImg;
		if (player.isStunned())
			playerImg = player.getDirection() == Direction.LEFT ? AnimationAndImagesLoader.getPlayerImage("stun_left")
					: AnimationAndImagesLoader.getPlayerImage("stun_right");
		else {
			playerImg = player.getDirection() == Direction.STATIC ? AnimationAndImagesLoader.getImage(Player.CODE, "static")
					: AnimationAndImagesLoader.getPlayerImage("walk_" + player.getDirection().name().toLowerCase());
		}
		g.drawImage(playerImg, (int) player.getX(), (int) player.getY(), (int) player.getWidth() + 1,
				(int) player.getHeight() + 1, null);
	}

	private void renderEnemy(Enemy enemy, Graphics g) {
		Image enemyImage;
		if (enemy.isDead()) {
			enemyImage = AnimationAndImagesLoader.loadDeadEnemyImage(enemy.getCode());
//			enemyImage= prendi l'immagine del nemico morto;
		} else {
			enemyImage = AnimationAndImagesLoader.loadEnemyImage(enemy.getCode(), enemy.getDirection(), enemy.getColorState());
		}
		g.drawImage(enemyImage, (int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth() + 1,
				(int) enemy.getHeight() + 1, null);
	}

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
		case Water water -> HelpMethods.isEntityGrounded(water) ? AnimationAndImagesLoader.getImage('_') : AnimationAndImagesLoader.getImage('|');
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