package game.model.powerups;

import java.util.Timer;
import java.util.TimerTask;

import game.model.Model;
import game.model.entities.Entity;

public abstract class Powerup extends Entity {
	protected final int points;
	protected long duration;
	protected boolean toRemove;

	public Powerup(float x, float y, char code, int points, long duration) {
		super(x, y, code);
		this.points = points;
		this.duration = duration;
	}

	public long getPoints() {
		return points;
	}

	public float getDuration() {
		return duration;
	}

	public boolean isToRemove() {
		return toRemove;
	}

	private boolean checkPlayerCollision() {
		return Entity.checkCollision(Model.getInstance().getCurrentLevel().getPlayer(), this).isPresent();
	}

	public void effect() {
		setX(-2000);
		Model.getInstance().getCurrentUser().addPoints(points);
	}

	public void resetToNormal() {
		toRemove = true;
	}

	public void updatePowerup() {
		if (checkPlayerCollision()) {
			// timer creation
			Model.getInstance().getCurrentUser().addPoints(points);
			// effect starts

			effect();
			// scheduling end of the effect
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					resetToNormal();
				}
			}, duration);
		}
	}
}
