package game.model.enemies;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.model.entities.MovingEntity;


public abstract class Enemy extends MovingEntity {
	
	/**
	 * Enum representing possible colors for the entity.
	 */
	public enum ColorState {
		NORMAL, RED
	}
	
	// Current color of the entity
	protected ColorState colorState = ColorState.NORMAL;
	
	
	protected float redXSpeed;
	protected float movementSpeed = 1.0f;

    private static final int RED_TIME = 10000; // Tempo in millisecondi prima di diventare rosso
    
    public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float speed) {
		this.movementSpeed = speed;
	}

	protected boolean isPopped;
    protected boolean isStopped;

    public Enemy(float x, float y, String code) {
        super(x, y, code);
		redXSpeed = 2.0f;
        //initializeColorChangeTimer();
    }

    public Enemy(float x, float y, float width, float height, String code) {
        super(x, y, width, height, code);
		redXSpeed = 4.0f;
        //initializeColorChangeTimer();
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

	
    /**
     * Inizializza il timer che cambierà il colore del nemico a intervalli specificati.
     */
	/*
    public void initializeColorChangeTimer() {
        Timer timer = new Timer("EnemyColorChangeTimer", true);

        // Cambia il colore a rosso dopo RED_TIME millisecondi
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setColorState(ColorState.RED);
            }
        }, RED_TIME);
    }
    */
    
}
