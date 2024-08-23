package game.model.bubbles;

import game.model.HelpMethods;
import game.model.Model;
import game.model.entities.MovingEntity;
import game.model.tiles.Tile;

public class FireBall extends MovingEntity {

	public FireBall(float x, float y) {
		super(x, y, "#");
		airSpeed = 1.0f;
	}

	@Override
	protected void updateYPos() {
		setY(y + airSpeed);
	}

	public void expand() {
		String[][] lvlData = Model.getInstance().getCurrentLevel().getLvlData();
		int xPos = (int) (x / Tile.TILE_SIZE);
		int yPos = (int) (y / Tile.TILE_SIZE);
		System.out.println(lvlData[yPos-1][xPos]);
	}

	@Override
	public void updateEntity() {
		updateYPos();
		if (HelpMethods.isEntityGrounded(this))
			expand();
	}

}
