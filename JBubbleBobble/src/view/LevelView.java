package view;

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

	public LevelView() {
		tiles = new ArrayList<StaticEntityView>();
	}

	@Override
	public void update(Observable o, Object arg) {
		Level level = (Level) o;
		for (Tile t : level.getTiles()) {

		}

	}

}
