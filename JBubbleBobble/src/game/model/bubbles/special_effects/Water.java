package game.model.bubbles.special_effects;

import static game.model.tiles.Tile.TILE_SIZE;
import game.model.HelpMethods;
import game.model.Model;
import game.model.level.Level;
import game.model.entities.MovingEntity;
import static game.model.entities.MovingEntity.Direction.*;

public class Water extends MovingEntity {

	public static final char CODE = '_';
	private int watersToSpawn;
	private int lifeSpan;

	public Water(float x, float y, int watersToSpawn) {
		super(x, y, CODE);
		setDirection(RIGHT);
		this.watersToSpawn = watersToSpawn;
		lifeSpan = 70;
	}

	private void delete() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeWater(this);
		System.out.println("deleted");
	}

	private void updatePosition() {
		if (watersToSpawn > 0) {
			Model.getInstance().getCurrentLevel().getBubbleManager().addWater(new Water(x, y, watersToSpawn - 1));
			watersToSpawn = 0;
		}

		if (!HelpMethods.isEntityGrounded(this)) {
			setY(y + TILE_SIZE);
			return;
		}

		float newX = direction == LEFT ? x - TILE_SIZE : x + TILE_SIZE;
		if (HelpMethods.canMoveHere(newX, y, width, height)) {
			setX(newX);
		} else {
			// Try moving in the opposite direction if the first direction fails
			newX = direction == LEFT ? x + TILE_SIZE : x - TILE_SIZE;
			if (HelpMethods.canMoveHere(newX, y, width, height)) {
				direction = direction == LEFT ? RIGHT : LEFT;
				setX(newX);
			} else {
				delete();
			}
		}
	}

	@Override
	public void updateEntity() {
		lifeSpan--;
		if (lifeSpan == 0)
			delete();
		if (y == Level.GAME_HEIGHT)
			setY(-TILE_SIZE);
		updatePosition();
	}

}
