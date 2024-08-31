package game.model.enemies;

import java.util.Random;

import game.model.Fruit;
import game.model.Fruit.FruitType;
import game.model.HelpMethods;
import game.model.Model;
import game.model.entities.MovingEntity;

public abstract class Enemy extends MovingEntity {

	/**
	 * Enum representing possible colors for the entity.
	 */
	public enum ColorState {
		NORMAL, RED
	}

	// Current color of the entity
	protected ColorState colorState;
	protected float redXSpeed;
	protected float movementSpeed;
	protected boolean dead;

	public static final int RED_TIME = 10000; // Tempo in millisecondi prima di diventare rosso

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float speed) {
		this.movementSpeed = speed;
	}

	protected boolean isPopped;
	protected boolean isStopped;

	public Enemy(float x, float y, char code) {
		super(x, y, code);
		redXSpeed = 2.0f;
		colorState = ColorState.NORMAL;
		movementSpeed = 1.0f;
		// initializeColorChangeTimer();
	}

	public Enemy(float x, float y, float width, float height, char code) {
		super(x, y, width, height, code);
		redXSpeed = 2.0f;
		colorState = ColorState.NORMAL;
		movementSpeed = 1.0f;
		// initializeColorChangeTimer();
	}

	public boolean isPopped() {
		return isPopped;
	}

	public void pop() {
		isPopped = true;
	}

	public boolean randomBoolean(int chances) {
		return new Random().nextInt(0, chances) == 0;
	}

	public boolean isDead() {
		return dead;
	}

	public boolean isStopped() {
		return isStopped;
	}

	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	/**
	 * Sets the color of the entity.
	 * 
	 * @param color The new color of the entity.
	 */
	public void setColorState(ColorState colorState) {
		this.colorState = colorState;
		if (colorState == ColorState.RED) {
			movementSpeed = redXSpeed;
		}
	}

	public ColorState getColorState() {
		return colorState;
	}

	public void kill() {
		dead = true;
		setxSpeed(0);
		setAirSpeed(-1);
	}

	@Override
	public void updateEntity() {
		if (isDead() && HelpMethods.isEntityGrounded(this)) {
			Fruit fruit = new Fruit(x, y, switch (new Random().nextInt(5)) {
			case 0 -> FruitType.BANANA;
			case 1 -> FruitType.ORANGE;
			case 2 -> FruitType.PEACH;
			case 3 -> FruitType.PEAR;
			default -> FruitType.WATERMELON;
			});
			Model.getInstance().getCurrentLevel().getFruitManager().addFruit(fruit);
			Model.getInstance().getCurrentLevel().getEnemyManager().removeEnemy(this);
			System.out.println("Estas muerto");
		}
	}

	/**
	 * Inizializza il timer che cambierà il colore del nemico a intervalli
	 * specificati.
	 */
	/*
	 * public void initializeColorChangeTimer() { Timer timer = new
	 * Timer("EnemyColorChangeTimer", true);
	 * 
	 * // Cambia il colore a rosso dopo RED_TIME millisecondi timer.schedule(new
	 * TimerTask() {
	 * 
	 * @Override public void run() { setColorState(ColorState.RED); } }, RED_TIME);
	 * }
	 */

}
