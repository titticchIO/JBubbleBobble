package game.model.entities;

import game.model.bubbles.Bubble;
import game.model.bubbles.BubbleManager;
import game.model.bubbles.PlayerBubble;
import game.model.entities.MovingEntity.Direction;
import game.model.tiles.Tile;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import game.model.HelpMethods;
import game.model.Model;
import game.model.Settings;

public class Player extends MovingEntity {

	public enum State {
		WALK, JUMP, SHOOT
	}

	public static final int NUMBER_OF_LIVES = 1;
	public static final long INVULNERABILITY_INTERVAL = 5000;
	public static final long ATTACK_INTERVAL = 100;

	private Direction bubbleDirection;
	private State state;
	private int lives;

	private boolean isJumping;

	private boolean isInvulnerable;
	private Timer invulnerabilityTimer;

	private boolean canShoot;
	private long attackSpeed; // Esempio: velocità di attacco in millisecondi (1 secondo)
	private Timer attackTimer;

	// bolla attuale
	private PlayerBubble currentBubble;
	// singleton
	private static Player instance;

	public static Player getInstance() {
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
		canShoot = true;
		attackSpeed = 1;
		attackTimer=new Timer();
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

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	@Override
	public void setDirection(Direction direction) {
		super.setDirection(direction);
		if (direction == Direction.RIGHT || direction == direction.LEFT)
			bubbleDirection = direction;
	}

	public void shootBubble() {
		// Controlla se il player può sparare
		if (canShoot) {
			if (bubbleDirection == Direction.RIGHT
					&& !HelpMethods.isEntityInsideWall(x + Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x + Tile.TILE_SIZE, y, 2);
			} else if (bubbleDirection == Direction.LEFT
					&& !HelpMethods.isEntityInsideWall(x - Tile.TILE_SIZE, y, width, height)) {
				Model.getInstance().getCurrentLevel().getBubbleManager().createPlayerBubble(x - Tile.TILE_SIZE, y, -2);
			}

			// Disabilita il fuoco fino alla fine del tempo di attesa
			canShoot = false;

			// Se esiste già un timer, lo cancella
			if (attackTimer != null) {
				attackTimer.cancel();
			}

//			 Crea un nuovo Timer per l'attacco
			attackTimer = new Timer();
			attackTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// Dopo attackSpeed millisecondi, il player può sparare di nuovo
					canShoot = true;
					attackTimer.cancel(); // Ferma il timer una volta completato
				}
			}, ATTACK_INTERVAL*attackSpeed); // Imposta il timer in base alla velocità di attacco
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
		// Controlla se il player è invulnerabile; se lo è, non può perdere una vita
		if (!isInvulnerable
				&& Entity.checkCollision(this, Model.getInstance().getCurrentLevel().getEnemyManager().getHazards())
						.isPresent()) {
			lives--;
			// Attiva lo stato di invulnerabilità
			isInvulnerable = true;

			// Se esiste già un timer, lo cancella prima di crearne uno nuovo
			if (invulnerabilityTimer != null) {
				invulnerabilityTimer.cancel();
			}

			// Imposta un nuovo Timer per l'invulnerabilità
			invulnerabilityTimer = new Timer();
			invulnerabilityTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// Quando il Timer scade, il player torna vulnerabile
					isInvulnerable = false;
					invulnerabilityTimer.cancel(); // Ferma il timer una volta completato
				}
			}, INVULNERABILITY_INTERVAL); // Imposta il timer per l'intervallo di invulnerabilità
		}
	}

	@Override
	public void updateEntity() {
		Optional<PlayerBubble> bounceBobble = Entity.checkBottomCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
		if (isJumping() && ((bounceBobble.isPresent() && bounceBobble.get().getEnemy() == null)
				|| HelpMethods.isEntityGrounded(this))) {
			jump();
		}
		Optional<PlayerBubble> popBobble = Entity.checkTopCollision(this,
				Model.getInstance().getCurrentLevel().getBubbleManager().getPlayerBubbles());
		if (popBobble.isPresent())
			popBobble.get().popAndKill();
		updateXPos();
		updateYPos();
		gravity();
		looseLife();
		System.out.println(lives);
	}
	
}
