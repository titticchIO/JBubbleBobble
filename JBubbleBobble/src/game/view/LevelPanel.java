package game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.model.entities.Entity;
import game.model.entities.Player;
import game.model.enemies.Invader;
import game.model.enemies.Monsta;
import game.model.enemies.SkelMonsta;
import game.model.enemies.ZenChan;
import game.model.level.Level;
import game.model.tiles.Tile;

public class LevelPanel extends JPanel {
	public final static float SCALE = 1.5f;
	private BufferedImage tilesImage;

//	private MenuPanel menuPanel

//	NON AGGIUNGERE IL PATTERN SINGLETON!!!!!

	public LevelPanel() {
		setPanelSize();
	}

	private void setPanelSize() {
		Dimension size = new Dimension((int) (Level.GAME_WIDTH * SCALE), (int) (Level.GAME_HEIGHT * SCALE));
		setPreferredSize(size);
	}

	public void renderTilesOnce() {
		tilesImage = new BufferedImage(Level.GAME_WIDTH, Level.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tilesImage.getGraphics();
		View.getInstance().getLevel().getTiles().forEach(t -> renderEntity(t, g));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(SCALE, SCALE);
		g2d.scale(SCALE, SCALE);
		super.paintComponent(g2d);

		g2d.drawImage(tilesImage, 0, 0, this);
		View.getInstance().getLevel().getEntities().forEach(e -> renderEntity(e, g2d));
	}

	public void renderEntity(Entity entity, Graphics g) {
		Image img;
		img = switch (entity) {
		case Tile tile -> Images.getImage(tile.getCode());
		case Invader invader -> AnimationLoader.loadEntityImage("enemies/invader/default.gif");
		case Monsta monsta-> AnimationLoader.loadEntityImage("enemies/monsta/"+monsta.getDirection().name().toLowerCase()+".gif");
		case SkelMonsta skelmonsta -> AnimationLoader.loadEntityImage("enemies/skelmonsta/"+skelmonsta.getDirection().name().toLowerCase()+".gif");
		case ZenChan zen_Chan -> AnimationLoader.loadEntityImage("enemies/zenchan/"+zen_Chan.getDirection().name().toLowerCase()+".gif");
		
		case Player player -> switch (player.getDirection()) {
			case RIGHT -> AnimationLoader.getPlayerImage("walk_right");
			case LEFT -> AnimationLoader.getPlayerImage("walk_left");
			case STATIC -> Images.getImage("P", "static");
			default -> throw new IllegalArgumentException("Unexpected value: " + player.getDirection());
		};
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