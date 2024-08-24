package game.model.bubbles.special_effects;

import java.util.Timer;
import java.util.TimerTask;
import game.model.HelpMethods;
import game.model.Model;
import game.model.entities.MovingEntity;
import static game.model.tiles.Tile.TILE_SIZE;;

public class FireBall extends MovingEntity {

	public enum FireState {
		FALL, BURN
	}

	public FireState fireState;

	public FireBall(float x, float y, FireState fireState) {
		super(x, y, "#");
		airSpeed = 2.0f;
		this.fireState = fireState;
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("removed");
				Model.getInstance().getCurrentLevel().getBubbleManager().removeFireBall(FireBall.this);
			}
		}, 5000);
	}

	public FireBall(float x, float y) {
		super(x, y, "#");
		airSpeed = 2.0f;
		fireState = FireState.FALL;
	}

	@Override
	protected void updateYPos() {
		setY(y + airSpeed);
	}

	private void spawnFireBalls() {
		String[][] lvlData = Model.getInstance().getCurrentLevel().getLvlData();
		int xPos = (int) (x / TILE_SIZE);
		int yPos = (int) (y / TILE_SIZE);
		for (int i = 1; i < 3; i++) {
			if (lvlData[yPos + 1][xPos + i].matches("[0-9]"))
				Model.getInstance().getCurrentLevel().getBubbleManager()
						.addFireBall(new FireBall((xPos + i) * TILE_SIZE, yPos * TILE_SIZE, FireState.BURN));
			if (lvlData[yPos + 1][xPos - i].matches("[0-9]"))
				Model.getInstance().getCurrentLevel().getBubbleManager()
						.addFireBall(new FireBall((xPos - i) * TILE_SIZE, yPos * TILE_SIZE, FireState.BURN));

		}

	}

	public void expand() {
		fireState = FireState.BURN;
		spawnFireBalls();

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("removed");
				Model.getInstance().getCurrentLevel().getBubbleManager().removeFireBall(FireBall.this);
			}
		}, 5000);
	}

	@Override
	public void updateEntity() {
		if (fireState == FireState.FALL) {
			updateYPos();
			if (HelpMethods.isEntityGrounded(this))
				expand();
		}
	}

}
