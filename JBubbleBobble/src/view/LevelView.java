package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.level.Level;
import model.tiles.Tile;
import view.tiles.TileView;

public class LevelView implements Observer {
	private PlayerView playerView;
	private List<TileView> tiles;

	public LevelView() {
		tiles = new ArrayList<TileView>();
	}

	@Override
	public void update(Observable o, Object arg) {
		Level level = (Level) o;
		for (Tile t: level.getTiles()) {
			
		}
		
	}

}
