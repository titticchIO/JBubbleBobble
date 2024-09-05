package game.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
import game.model.powerups.PowerupManager;
import game.model.tiles.Tile;

public class Level {
	public final static int NUM_HORIZONTAL_TILES = 30;
	public final static int NUM_VERTICAL_TILES = 24;
	public final static int GAME_WIDTH = NUM_HORIZONTAL_TILES * Tile.TILE_SIZE;
	public final static int GAME_HEIGHT = NUM_VERTICAL_TILES * Tile.TILE_SIZE;
	private static int simultaneousKills;

	private Player player;
	private List<Tile> tiles;
	private EnemyManager enemyManager;
	private BubbleManager bubbleManager;
	private PowerupManager powerupManager;
	private FruitManager fruitManager;
	private char[][] lvlData;
	private float[] playerSpawnPoint;
	private List<Float> bubblesSpawnPoints;
	private int levelNumber;

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

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public char[][] getLvlData() {
		return lvlData;
	}

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

	public PowerupManager getPowerupManager() {
		return powerupManager;
	}

	public FruitManager getFruitManager() {
		return fruitManager;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public List<Float> getBubblesSpawnPoints() {
		return bubblesSpawnPoints;
	}

	public void setEnemyManager(EnemyManager eManager) {
		this.enemyManager = eManager;
	}

	public BubbleManager getBubbleManager() {
		return bubbleManager;
	}

	public void setBubbleManager(BubbleManager bManager) {
		this.bubbleManager = bManager;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public void addPlayer(Player player) {
		this.player = player;
	}

	public void setPlayerSpawnPoint(float x, float y) {
		playerSpawnPoint = new float[] { x, y };
	}

	public float[] getPlayerSpawnPoint() {
		return playerSpawnPoint;
	}

	public void addEnemy(Enemy enemy) {
		enemyManager.addEnemy(enemy);
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	private void setEnemyInBubble(PlayerBubble b, Enemy e) {
		b.setEnemy(e);
		enemyManager.removeEnemy(e);
		b.setHasEnemy(true);
	}

	private void setBubblesSpawnPoints() {
		bubblesSpawnPoints = new ArrayList<Float>();
		int y = lvlData.length - 1;
		for (int x = 0; x < lvlData[0].length; x++) {
			if (lvlData[y][x] == ' ')
				bubblesSpawnPoints.add((float) x * Tile.TILE_SIZE);

		}
	}

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

	private void checkJump() {
		if (player.isJumping()) {
			Optional<PlayerBubble> bounceBubble = Entity.checkBottomCollision(player, bubbleManager.getPlayerBubbles().stream().filter(x->!x.isPopped()&&!x.hasEnemy()).toList());
			if (HelpMethods.isEntityGrounded(player))
				player.jump();
			if (bounceBubble.isPresent()) {
				player.jump();
				powerupManager.getPowerupFactory().increaseNumberOfJumpsOnBubbles();
				bounceBubble.get().pop();
			}
		}
	}

	private void checkPlayerBubbleCollisions() {
		Optional<PlayerBubble> playerPopBubble = Entity.checkCollision(player, bubbleManager.getPlayerBubbles());
		if (playerPopBubble.isPresent() && !HelpMethods.isEntityInsideWall(playerPopBubble.get())) {
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

	private void checkSpecialCollisions() {
		Optional<Enemy> enemyHit;

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

	private void checkFruitCollisions() {
		Optional<Fruit> fruitHit = Entity.checkCollision(player, fruitManager.getFruits());
		if (fruitHit.isPresent()) {
			fruitManager.removeFruit(fruitHit.get());
			Model.getInstance().getCurrentUser().addPoints(fruitHit.get().getPoints());
		}
	}

	private void checkAllCollisions() {
		checkJump();
		checkLooseLife();
		checkPlayerBubbleCollisions();
		captureEnemies();
		checkSpecialCollisions();
		checkFruitCollisions();
	}

	public void updateLevel() {
		player.updateEntity();
		enemyManager.updateEnemies();
		bubbleManager.updateBubbles();
		powerupManager.updatePowerups();
		checkAllCollisions();

	}

	public static int getSimultaneousKills() {
		return simultaneousKills;
	}

	public static void setSimultaneousKills(int simultaneousKills) {
		Level.simultaneousKills = simultaneousKills;
	}

}
