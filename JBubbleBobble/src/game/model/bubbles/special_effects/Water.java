package game.model.bubbles.special_effects;

import static game.model.tiles.Tile.TILE_SIZE;

import java.util.Timer;
import java.util.TimerTask;

import game.model.HelpMethods;
import game.model.Model;
import game.model.level.Level;
import game.model.entities.MovingEntity;
import static game.model.tiles.Tile.TILE_SIZE;
import static game.model.entities.MovingEntity.Direction.*;

public class Water extends MovingEntity {

	private Timer moveTimer;

	public Water(float x, float y) {
		super(x, y, "_");
		setDirection(RIGHT);
	}

	private void delete() {
		Model.getInstance().getCurrentLevel().getBubbleManager().removeWater(this);
	}

	private void updatePosition() {
		if (!HelpMethods.isEntityGrounded(this))
			setY(y + TILE_SIZE);
		else {
			switch (direction) {
			case LEFT -> {
				if (HelpMethods.canMoveHere(x - TILE_SIZE, y, width, height))
					setX(x - TILE_SIZE);
				else if (HelpMethods.canMoveHere(x + TILE_SIZE, y, width, height)) {
					direction = RIGHT;
					setX(x + TILE_SIZE);
				} else
					delete();
			}
			case RIGHT -> {
				if (HelpMethods.canMoveHere(x + TILE_SIZE, y, width, height))
					setX(x + TILE_SIZE);
				else if (HelpMethods.canMoveHere(x - TILE_SIZE, y, width, height)) {
					direction = LEFT;
					setX(x - TILE_SIZE);
				} else
					delete();
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + direction);
			}
		}
	}

	@Override
	public void updateEntity() {
		System.out.println(direction.name());
		if (moveTimer == null) {
			moveTimer = new Timer();
			moveTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					updatePosition();
					moveTimer = null;

				}
			}, 300);
		}

	}

}
