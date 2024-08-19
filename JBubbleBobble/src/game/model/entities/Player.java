package game.model.entities;

import game.model.bubbles.Bubble;
import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.entities.MovingEntity.Direction;
import game.model.tiles.Tile;

import java.util.List;
import java.util.Optional;

import game.model.HelpMethods;
import game.model.Model;
import game.model.Settings;

public class Player extends MovingEntity {

	public enum State {
		WALK, JUMP, SHOOT
	}

	public static final int NUMBER_OF_LIVES = 3;
	public static final long INVULNERABILITY_INTERVAL = 5000;

	private final String type = "P";
	private Direction bubbleDirection;
	private State state;
	private int lives;

	private boolean isJumping;

	private boolean isColliding;

	private long lastCollision;

	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private static Player instance;

	public static Player getInstance() {
		if (instance == null)
			instance = new Player(30, 30, 16, 16);

		return instance;
	}

	public static Player getInstance(float x, float y, float width, float height) {
		if (instance == null)
			instance = new Player(x, y, width, height);
		return instance;
	}

	private Player(float x, float y, float width, float height) {
		super(x, y, width, height, "P");
		state = State.WALK;
		currentBubble = new PlayerBubble(x, y, width, height);
		bubbleDirection = Direction.RIGHT;
		lives = NUMBER_OF_LIVES;
		lastCollision = System.currentTimeMillis();
	}

	/**
	 * Getters and Setters
	 */
	public PlayerBubble getCurrentBubble() {
		return currentBubble;
	}

	public void setCurrentBubble(PlayerBubble currentBubble) {
		this.currentBubble = currentBubble;
	}

	@Override
	public void setDirection(Direction direction) {
		super.setDirection(direction);
		if (direction == Direction.RIGHT || direction == direction.LEFT)
			bubbleDirection = direction;
	}

	public void shootBubble() {
		if (bubbleDirection == Direction.RIGHT
				&& !HelpMethods.isEntityInsideWall(x + Tile.TILE_SIZE, y, width, height)) {
			Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x + Tile.TILE_SIZE, y, 2);
		} else if (bubbleDirection == Direction.LEFT
				&& !HelpMethods.isEntityInsideWall(x - Tile.TILE_SIZE, y, width, height)) {
			Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x - Tile.TILE_SIZE, y, -2);
		}
	}

	@Override
	public void jump() {
		inAir = true;
		airSpeed = jumpSpeed;
//		}
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public void looseLife() {
		long now = System.currentTimeMillis();
		
		if (now - lastCollision > INVULNERABILITY_INTERVAL
				&& Entity.checkCollision(this, Model.getInstance().getCurrentLevel().getEnemyManager().getEnemies())
						.isPresent()) {
			lives--;
			lastCollision = now;
		}

	}

	@Override
	public void updateEntity() {
		Optional<PlayerBubble> pb = Entity.checkBottomCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
		if (isJumping() && ((pb.isPresent() && pb.get().getEnemy() == null) || HelpMethods.isEntityGrounded(this))) {
			jump();
		}
		updateXPos();
		updateYPos();
		gravity();
		looseLife();

		System.out.println(lives);
	}
}
