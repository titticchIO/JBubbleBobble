package game.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import game.model.bubbles.BubbleManager;
import game.model.bubbles.ExtendBubble;
import game.model.bubbles.FireBubble;
import game.model.bubbles.PlayerBubble;
import game.model.bubbles.SpecialBubble;
import game.model.bubbles.ThunderBubble;
import game.model.bubbles.WaterBubble;
import game.model.bubbles.special_effects.FireBall;
import game.model.bubbles.special_effects.FireBall.FireState;
import game.model.Fruit;
import game.model.FruitManager;
import game.model.HelpMethods;
import game.model.Model;
import game.model.bubbles.Bubble;
import game.model.enemies.Boss;
import game.model.enemies.Enemy;
import game.model.enemies.EnemyManager;
import game.model.entities.Entity;
import game.model.entities.MovingEntity;
import game.model.entities.Player;
import game.model.powerups.Powerup;
import game.model.powerups.PowerupManager;
import game.model.tiles.Tile;

/**
 * The Level class represents a game level, containing information about the
 * level's layout, enemies, bubbles, power-ups, and player interactions. It also
 * handles collision detection and updating of game entities during the level's
 * progression.
 */
public class Level {
	/**
	 * The number of horizontal tiles in the level.
	 */
	public final static int NUM_HORIZONTAL_TILES = 30;
	/**
	 * The number of vertical tiles in the level.
	 */
	public final static int NUM_VERTICAL_TILES = 24;
	/**
	 * The width of the level.
	 */
	public final static int GAME_WIDTH = NUM_HORIZONTAL_TILES * Tile.TILE_SIZE;
	/**
	 * The height of the level.
	 */
	public final static int GAME_HEIGHT = NUM_VERTICAL_TILES * Tile.TILE_SIZE;
	/**
	 * The number of enemies killed simultaneously during the level.
	 */
	private static int simultaneousKills;

	private Player player;
	private List<Tile> tiles;
	private EnemyManager enemyManager;
	private BubbleManager bubbleManager;
	private PowerupManager powerupManager;
	private FruitManager fruitManager;
	private char[][] lvlData; // the char matrix used to locate the tiles
	private float[] playerSpawnPoint;
	private List<Float> bubblesSpawnPoints; // the valid locations for special bubbles to spawn
	private int levelNumber;

	/**
	 * Constructs a new Level object with the specified level number. Initializes
	 * the level's tiles, enemies, bubbles, power-ups, and fruits.
	 *
	 * @param levelNumber The number representing the current level.
	 */
	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		tiles = new ArrayList<Tile>();
		enemyManager = new EnemyManager();
		lvlData = LevelLoader.loadLevel(this, levelNumber);
		setBubblesSpawnPoints();
		bubbleManager = new BubbleManager(bubblesSpawnPoints);
		powerupManager = new PowerupManager(lvlData);
		fruitManager = new FruitManager();

	}

	/**
	 * Returns the number of the current level.
	 *
	 * @return The level number.
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	/**
	 * Sets the number of the current level.
	 *
	 * @param levelNumber The new level number.
	 */
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	/**
	 * Returns the level data represented as a 2D character array.
	 *
	 * @return The level data.
	 */
	public char[][] getLvlData() {
		return lvlData;
	}

	/**
	 * Returns a list of all entities currently present in the level, including
	 * bubbles, power-ups, and fruit.
	 *
	 * @return A list of entities in the level.
	 */
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		entities.addAll(bubbleManager.getBubbles());
		entities.addAll(bubbleManager.getPlayerBubbles());
		entities.addAll(bubbleManager.getFireBalls());
		entities.addAll(bubbleManager.getBolts());
		entities.addAll(bubbleManager.getWaters());
		entities.addAll(enemyManager.getLasers());
		entities.addAll(powerupManager.getPowerups());
		entities.addAll(fruitManager.getFruits());

		return entities;
	}

	/**
	 * Returns the power-up manager for the level.
	 *
	 * @return The PowerupManager for the level.
	 */
	public PowerupManager getPowerupManager() {
		return powerupManager;
	}

	/**
	 * Returns the fruit manager for the level.
	 *
	 * @return The FruitManager for the level.
	 */
	public FruitManager getFruitManager() {
		return fruitManager;
	}

	/**
	 * Returns the player entity in the level.
	 *
	 * @return The player entity.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player entity for the level.
	 *
	 * @param player The player entity to set.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Adds the player entity to the level.
	 *
	 * @param player The player entity to add.
	 */
	public void addPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Sets the player's spawn point in the level.
	 *
	 * @param x The x-coordinate of the spawn point.
	 * @param y The y-coordinate of the spawn point.
	 */
	public void setPlayerSpawnPoint(float x, float y) {
		playerSpawnPoint = new float[] { x, y };
	}

	/**
	 * Returns the player's spawn point in the level.
	 *
	 * @return An array containing the x and y coordinates of the spawn point.
	 */
	public float[] getPlayerSpawnPoint() {
		return playerSpawnPoint;
	}

	/**
	 * Returns the enemy manager for the level.
	 *
	 * @return The EnemyManager for the level.
	 */
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	/**
	 * Sets the enemy manager for the level.
	 *
	 * @param eManager The EnemyManager to set.
	 */
	public void setEnemyManager(EnemyManager eManager) {
		this.enemyManager = eManager;
	}

	/**
	 * Adds an enemy to the level.
	 *
	 * @param enemy The enemy to add.
	 */
	public void addEnemy(Enemy enemy) {
		enemyManager.addEnemy(enemy);
	}

	/**
	 * Sets the enemy inside a player bubble and removes it from the enemy manager.
	 *
	 * @param b The player bubble to set the enemy in.
	 * @param e The enemy to set inside the bubble.
	 */
	private void setEnemyInBubble(PlayerBubble b, Enemy e) {
		b.setEnemy(e);
		enemyManager.removeEnemy(e);
		b.setHasEnemy(true);
	}

	/**
	 * Returns the number of simultaneous kills in the current level.
	 *
	 * @return the number of simultaneous kills
	 */
	public static int getSimultaneousKills() {
		return simultaneousKills;
	}

	/**
	 * Sets the number of simultaneous kills in the current level.
	 *
	 * @param simultaneousKills the number of simultaneous kills to set
	 */
	public static void setSimultaneousKills(int simultaneousKills) {
		Level.simultaneousKills = simultaneousKills;
	}

	/**
	 * Returns the bubble manager for the level.
	 *
	 * @return The BubbleManager for the level.
	 */
	public BubbleManager getBubbleManager() {
		return bubbleManager;
	}

	/**
	 * Sets the bubble manager for the level.
	 *
	 * @param bManager The BubbleManager to set.
	 */
	public void setBubbleManager(BubbleManager bManager) {
		this.bubbleManager = bManager;
	}

	/**
	 * Returns the bubble spawn points in the level.
	 *
	 * @return the bubble spawn points in the level.
	 */
	public List<Float> getBubblesSpawnPoints() {
		return bubblesSpawnPoints;
	}

	/**
	 * Sets the spawn points for bubbles in the level based on level data.
	 */
	private void setBubblesSpawnPoints() {
		bubblesSpawnPoints = new ArrayList<Float>();
		int y = lvlData.length - 1;
		for (int x = 0; x < lvlData[0].length; x++) {
			if (lvlData[y][x] == ' ')
				bubblesSpawnPoints.add((float) x * Tile.TILE_SIZE);
		}
	}

	/**
	 * Returns the list of tiles in the level.
	 *
	 * @return A list of tiles in the level.
	 */
	public List<Tile> getTiles() {
		return tiles;
	}

	/**
	 * Sets the tiles for the level.
	 *
	 * @param tiles The list of tiles to set.
	 */
	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	/**
	 * Adds a tile to the level.
	 *
	 * @param tile The tile to add.
	 */
	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	/**
	 * Captures enemies inside player bubbles by checking for collisions between
	 * bubbles and enemies.
	 */
	public void captureEnemies() {
		List<PlayerBubble> playerBubbles = bubbleManager.getPlayerBubbles().stream()
				.filter(b -> !b.isPopped() && !b.hasEnemy()).toList();
		List<Enemy> enemies = enemyManager.getEnemies().stream().filter(e -> !(e instanceof Boss) && !e.isDead())
				.toList();
		Optional<Entity[]> enemyCapture = Entity.checkCollisions(playerBubbles, enemies);
		if (enemyCapture.isPresent()) {
			PlayerBubble bubble = (PlayerBubble) enemyCapture.get()[0];
			Enemy enemy = (Enemy) enemyCapture.get()[1];
			if (enemy instanceof Boss)
				bubble.pop();
			else if (!enemy.isDead()) {
				bubble.resetLifeSpan();
				setEnemyInBubble(bubble, enemy);
				if (player.getSpecialBubbleActive())
					bubble.popAndKill();
			}
		}
		;
	}

	/**
	 * Checks if the player is jumping and handles collisions with bubbles for
	 * jumping.
	 */
	private void checkJump() {
		if (player.isJumping()) {
			Optional<PlayerBubble> bounceBubble = Entity.checkBottomCollision(player,
					bubbleManager.getPlayerBubbles().stream().filter(x -> !x.isPopped() && !x.hasEnemy()).toList());
			if (HelpMethods.isEntityGrounded(player))
				player.jump();
			if (bounceBubble.isPresent()) {
				player.jump();
				powerupManager.getPowerupFactory().increaseNumberOfJumpsOnBubbles();
				bounceBubble.get().pop();
			}
		}
	}

	/**
	 * Checks for collisions between the player and bubbles, and handles their
	 * popping.
	 */
	private void checkPlayerBubbleCollisions() {
		Optional<PlayerBubble> playerPopBubble = Entity.checkCollision(player, bubbleManager.getPlayerBubbles());
		if (playerPopBubble.isPresent()) {
			playerPopBubble.get().popAndKill();
			powerupManager.getPowerupFactory().increaseNumberOfBubblesPopped();
		}
		Optional<Bubble> specialPopBubble = Entity.checkCollision(player, bubbleManager.getBubbles());
		if (specialPopBubble.isPresent()) {
			specialPopBubble.get().pop();
			if (specialPopBubble.get() instanceof FireBubble)
				powerupManager.getPowerupFactory().increaseNumberOfFireBubblesPopped();
			else if (specialPopBubble.get() instanceof WaterBubble)
				powerupManager.getPowerupFactory().increaseNumberOfWaterBubblesPopped();
			else if (specialPopBubble.get() instanceof ThunderBubble)
				powerupManager.getPowerupFactory().increaseNumberOfThunderBubblesPopped();
			else if (specialPopBubble.get() instanceof SpecialBubble)
				powerupManager.getPowerupFactory().increaseNumberOfSpecialBubblesPopped();
			else if (specialPopBubble.get() instanceof ExtendBubble) {
				ExtendBubble.incrementCodesIndex();
				powerupManager.getPowerupFactory().increaseNumberOfExtendBubblesPopped();
			}
		}
	}

	/**
	 * Checks if the player has collided with a hazard and if so, reduces the
	 * player's lives. If the player is invulnerable, they will not lose a life. If
	 * the player hits a hazard, they will become invulnerable for a set interval.
	 */
	public void checkLooseLife() {
		if (!player.isInvulnerable()) {
			// Checks if the player is invulnerable; if not, the player can lose a life.
			Optional<MovingEntity> hazardHit = Entity.checkCollision(player, enemyManager.getHazards());
			if (hazardHit.isPresent()) {
				if (hazardHit.get() instanceof Enemy enemy && enemy.isDead())
					return;
				player.looseLife();
				// Activates invulnerability.
				player.setInvulnerable(true);

				// Sets a new invulnerability timer.
				player.setInvulnerabilityTimer(new Timer("Invulnerability"));
				player.getInvulnerabilityTimer().schedule(new TimerTask() {
					@Override
					public void run() {
						// When the timer ends, the player becomes vulnerable again.
						player.setInvulnerable(false);
						player.setInvulnerabilityTimer(null);
					}
				}, Player.INVULNERABILITY_INTERVAL); // Sets the timer for the invulnerability interval.
			}
		}
	}

	/**
	 * Checks for collisions involving special bubbles and the player or enemies.F
	 */
	private void checkSpecialCollisions() {
		// Enemy fire balls collision
		List<FireBall> burningFireBalls = bubbleManager.getFireBalls().stream()
				.filter(f -> f.getFireState() == FireState.BURN).toList();
		Optional<Entity[]> enemyFireHit = Entity.checkCollisions(burningFireBalls,
				enemyManager.getEnemies().stream().filter(e -> !e.isDead()).toList());
		if (enemyFireHit.isPresent()) {
			if (enemyFireHit.get()[1] instanceof Boss boss) {
				boss.looseLife();
			} else {
				Enemy enemy = (Enemy) enemyFireHit.get()[1];
				enemy.kill();
				enemy.updateEntity();
			}
		}

		// Player fire balls collision
		Optional<FireBall> playerHit = Entity.checkCollision(player, burningFireBalls);
		if (playerHit.isPresent()) {
			player.stun(5);
		}

		// Enemy bolt collision
		Optional<Entity[]> enemyBoltHit = Entity.checkCollisions(bubbleManager.getBolts(),
				enemyManager.getEnemies().stream().filter(e -> !e.isDead()).toList());

		if (enemyBoltHit.isPresent()) {
			if (enemyBoltHit.get()[1] instanceof Boss boss) {
				boss.looseLife();
			} else {
				Enemy enemy = (Enemy) enemyBoltHit.get()[1];
				enemy.kill();
			}
		}

		// Player water collision
		bubbleManager.getWaters().stream().filter(w -> w.getCapturedEntity() == null && HelpMethods.isEntityGrounded(w))
				.forEach(w -> {
					Optional<Player> playerCapture = Entity.checkCollision(w, player);
					if (playerCapture.isPresent()) {
						player.setInvulnerable(true);
						player.stun(30);
						w.setCapturedEntity(player);
					}
				});

		// Enemy water collision
		bubbleManager.getWaters().stream().filter(w -> w.getCapturedEntity() == null).forEach(w -> {
			Optional<Enemy> enemyCapture = Entity.checkCollision(w,
					enemyManager.getEnemies().stream().filter(e -> !(e.isDead() || e instanceof Boss)).toList());
			if (enemyCapture.isPresent()) {
				enemyCapture.get().setStopped(true);
				w.setCapturedEntity(enemyCapture.get());
			}
		});
	}
	/**
	 * Checks for collisions involving powerups and the player.
	 */
	private void checkPowerupCollisions() {
		Optional<Powerup> powerupHit=Entity.checkCollision(player, powerupManager.getPowerups());
		if (powerupHit.isPresent()) {
			Powerup powerup=powerupHit.get();
			switch (powerup.getClass().getSimpleName()) {
			case "PinkCandy" -> Model.getInstance().getCurrentLevel().getPowerupManager().getPowerupFactory().increaseNumberOfPinkCandies();
			case "BlueCandy" -> Model.getInstance().getCurrentLevel().getPowerupManager().getPowerupFactory().increaseNumberOfBlueCandies();
			case "YellowCandy" ->
				Model.getInstance().getCurrentLevel().getPowerupManager().getPowerupFactory().increaseNumberOfYellowCandies();
			}
			powerup.effect();
			
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					powerup.resetToNormal();
				}
			}, powerup.getDuration());
			
		}
	}
	
	/**
	 * Checks for collisions between the player and fruits.
	 */
	private void checkFruitCollisions() {
		Optional<Fruit> fruitHit = Entity.checkCollision(player, fruitManager.getFruits());
		if (fruitHit.isPresent()) {
			fruitManager.removeFruit(fruitHit.get());
			Model.getInstance().getCurrentUser().addPoints(fruitHit.get().getPoints());
		}
	}

	/**
	 * Checks for and resolves all types of collisions in the level. Specifically it
	 * checks for jumps, hazards, bubble interactions, special bubble interactions,
	 * and fruit collection.
	 */
	private void checkAllCollisions() {
		checkJump();
		checkLooseLife();
		checkPlayerBubbleCollisions();
		captureEnemies();
		checkSpecialCollisions();
		checkPowerupCollisions();
		checkFruitCollisions();
	}

	/**
	 * Updates the state of the entire level, including the player, enemies,
	 * bubbles, powerups, and collisions.
	 */
	public void updateLevel() {
		player.updateEntity();
		enemyManager.updateEnemies();
		bubbleManager.updateBubbles();
		checkAllCollisions();

	}

}
