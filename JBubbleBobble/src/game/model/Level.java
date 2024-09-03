package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.model.FireBall.FireState;

public class Level {
	public final static int NUM_HORIZONTAL_TILES = 30;
	public final static int NUM_VERTICAL_TILES = 24;
	public final static int GAME_WIDTH = NUM_HORIZONTAL_TILES * Tile.TILE_SIZE;
	public final static int GAME_HEIGHT = NUM_VERTICAL_TILES * Tile.TILE_SIZE;

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
	private int simultaneousKills;

	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		tiles = new ArrayList<Tile>();
		enemyManager = new EnemyManager();
		bubbleManager = new BubbleManager();
		powerupManager = new PowerupManager();
		fruitManager = new FruitManager();
		lvlData = LevelLoader.loadLevel(this, levelNumber);
		setBubblesSpawnPoints();
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

	private void setBubblesSpawnPoints() {
		bubblesSpawnPoints = new ArrayList<Float>();
		int y = lvlData.length - 1;

		for (int x = 0; x < lvlData[0].length; x++) {
			if (lvlData[y][x] == ' ')
				bubblesSpawnPoints.add((float) x * Tile.TILE_SIZE);

		}
	}

	private boolean checkEnemiesBubblesCollision() {
		return Entity.checkCollisions(bubbleManager.getPlayerBubbles(), enemyManager.getEnemies()).isPresent();
	}

	public void captureEnemies() {
		if (checkEnemiesBubblesCollision())
			bubbleManager.getPlayerBubbles().stream()
					.forEach(b -> enemyManager.getEnemies().stream().filter(b::hasHitEnemy).forEach(e -> {
						if (e instanceof Boss) {
							b.pop();
						} else if (!b.hasEnemy() && !e.isDead()) {
							b.setEnemy(e);
							enemyManager.removeEnemy(e);
							if (player.isShooting())
								b.popAndKill();
						}
					}));
	}

	public void killEnemies() {
		Optional<PlayerBubble> bubbleWithEnemy = Entity.checkCollision(player, bubbleManager.getPlayerBubbles());
		if (bubbleWithEnemy.isPresent() && bubbleWithEnemy.get().hasEnemy())
			bubbleManager.getPlayerBubbles().stream().filter(PlayerBubble::hasEnemy).forEach(b -> {
				if (b.hit(player)) {
					if (b.getEnemy() != null)
						setSimultaneousKills(getSimultaneousKills() + 1);
					b.popAndKill();
				}
			});
	}

	public void spawnPowerup(Powerup powerup) {
		Random random = new Random();
		int x = random.nextInt(1, NUM_HORIZONTAL_TILES - 1);
		int y = random.nextInt(1, NUM_VERTICAL_TILES - 1);
		final int MAX_ATTEMPTS = (NUM_HORIZONTAL_TILES - 2) * (NUM_VERTICAL_TILES - 2);

		for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
			// Verifica se la posizione è valida per generare il powerup
			if (lvlData[y][x] == ' ' && Character.isDigit(lvlData[y + 1][x])
					&& !powerupManager.isTherePowerup(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE)) {
				powerup.setPosition(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				powerupManager.addPowerup(powerup);
				return;
			}

			// Passa al prossimo tile orizzontalmente
			x++;
			if (x >= NUM_HORIZONTAL_TILES - 1) {
				x = 1;
				y++;

				// Se y ha superato il limite, ricomincia dall'alto
				if (y >= NUM_VERTICAL_TILES - 1) {
					y = 1;
				}
			}
		}

	}

	public void spawnBubble(Bubble bubble) {
		if (bubblesSpawnPoints.size() != 0) {
			float y = (lvlData.length) * Tile.TILE_SIZE;
			float x = (bubblesSpawnPoints.get(new Random().nextInt(bubblesSpawnPoints.size())));
			bubble.setPosition(x, y);
			bubbleManager.addBubble(bubble);
		}
	}

	private void checkJump() {
		if (player.isJumping()) {
			Optional<PlayerBubble> bounceBubble = Entity.checkBottomCollision(player, bubbleManager.getPlayerBubbles());
			if (HelpMethods.isEntityGrounded(player))
				player.jump();
			if (bounceBubble.isPresent() && bounceBubble.get().getEnemy() == null) {
				player.jump();
				powerupManager.increaseNumberOfJumpsOnBubbles();
			}
		}
	}

	private void checkPlayerBubbleCollisions() {
		Optional<PlayerBubble> playerPopBubble = Entity.checkTopCollision(player, bubbleManager.getPlayerBubbles());
		if (playerPopBubble.isPresent() && HelpMethods.isEntityInsideWall(playerPopBubble.get())) {
			playerPopBubble.get().popAndKill();
			powerupManager.increaseNumberOfBubblesPopped();
		}
		Optional<Bubble> specialPopBubble = Entity.checkCollision(player, bubbleManager.getBubbles());
		if (specialPopBubble.isPresent()) {
			specialPopBubble.get().pop();
			if (specialPopBubble.get() instanceof FireBubble)
				powerupManager.increaseNumberOfFireBubblesPopped();
			else if (specialPopBubble.get() instanceof WaterBubble)
				powerupManager.increaseNumberOfWaterBubblesPopped();
			else if (specialPopBubble.get() instanceof ThunderBubble)
				powerupManager.increaseNumberOfThunderBubblesPopped();
			else if (specialPopBubble.get() instanceof SpecialBubble)
				powerupManager.increaseNumberOfSpecialBubblesPopped();
			else if (specialPopBubble.get() instanceof ExtendBubble) {
				ExtendBubble.incrementCodesIndex();
				powerupManager.increaseNumberOfExtendBubblesPopped();
			}
		}
	}

	public void checkLooseLife() {
		if (!player.isInvulnerable() && player.getInvincibilityTimer() == null) {
			// Checks if the player is invulnerable; if not, the player can lose a life.
			Optional<MovingEntity> hazardHit = Entity.checkCollision(player, enemyManager.getHazards());
			if (hazardHit.isPresent()) {
				if (hazardHit.get() instanceof Enemy enemy && enemy.isDead())
					return;
				player.looseLife();
				// Activates invulnerability.
				player.setInvulnerable(true);

				// Sets a new invulnerability timer.
				player.setInvincibilityTimer(new Timer("Invulnerability"));
				player.getInvincibilityTimer().schedule(new TimerTask() {
					@Override
					public void run() {
						// When the timer ends, the player becomes vulnerable again.
						player.setInvulnerable(false);
						player.setInvincibilityTimer(null);
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
		enemyHit = Entity.checkCollisions(burningFireBalls, enemyManager.getEnemies());
		if (enemyHit.isPresent()) {
			if (enemyHit.get() instanceof Boss boss) {
				boss.looseLives();
				boss.updateEntity();
			} else {
				enemyHit.get().kill();
				enemyHit.get().updateEntity();
				
			}
		}
		// Player fire balls collisions
		Optional<FireBall> playerHit = Entity.checkCollision(player, burningFireBalls);
		if (playerHit.isPresent()) {
			player.stun(5);
		}
		// Player water collision
		bubbleManager.getWaters().stream().filter(w -> w.getCapturedEntity() == null && HelpMethods.isEntityGrounded(w))
				.forEach(w -> {
					Optional<Player> playerCapture = Entity.checkCollision(w, player);
					if (playerCapture.isPresent()) {
						player.stun(30);
						w.setCapturedEntity(player);
					}
				});

		// Enemy water collision
		bubbleManager.getWaters().stream().filter(w -> w.getCapturedEntity() == null).forEach(w -> {
			Optional<Enemy> enemyCapture = Entity.checkCollision(w,
					enemyManager.getEnemies().stream().filter(e -> !(e instanceof Boss)).toList());
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
		killEnemies();
		checkSpecialCollisions();
		checkFruitCollisions();
	}

	public void updateLevel() {
//		System.out.println("Player lives: " + player.getLives());
		player.updateEntity();
		enemyManager.updateEnemies();
		bubbleManager.updateBubbles();
		powerupManager.updatePowerups();
		checkAllCollisions();

	}

	public int getSimultaneousKills() {
		return simultaneousKills;
	}

	public void setSimultaneousKills(int simultaneousKills) {
		this.simultaneousKills = simultaneousKills;
	}

}
