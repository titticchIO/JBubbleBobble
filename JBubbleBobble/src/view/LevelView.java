package view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.level.Level;
import model.tiles.Tile;

public class LevelView implements Observer {
	private MovingEntityView playerView;
	private List<StaticEntityView> tiles;

	// Eventually
//	private List<MovingEntityView> movingEntities;

	public LevelView(Level level) {
		playerView = new MovingEntityView("player");
		level.getPlayer().addObserver(playerView);
		level.getPlayer().notifyPosition();
		tiles = new ArrayList<StaticEntityView>();
		for (Tile t : level.getTiles()) {
			StaticEntityView newTile = new StaticEntityView("tile");
			t.addObserver(newTile);
			t.notifyPosition();
			tiles.add(newTile);
		}
	}

	public MovingEntityView getPlayerView() {
		return playerView;
	}

	@Override
	public void update(Observable o, Object arg) {
		Level level = (Level) o;
		for (Tile t : level.getTiles()) {
		}
	}

	public void renderTiles(Graphics g) {
		for (StaticEntityView t : tiles) {
			t.render(g);
		}
	}
}
