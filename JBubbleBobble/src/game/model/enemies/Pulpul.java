package game.model.enemies;

import java.util.Random;

import game.model.HelpMethods;

import static game.model.HelpMethods.isSolid;

public class Pulpul extends Enemy {

    // Direzioni di movimento possibili
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction currentDirection;
    private Random random;
    private long lastChangeTime;
    private static final long CHANGE_INTERVAL = 2000; // Intervallo di cambio direzione in millisecondi

    public Pulpul(float x, float y, float width, float height, String imageCode) {
        super(x, y, width, height, imageCode);
        setxSpeed(1);
        setAirSpeed(1);
        currentDirection = Direction.RIGHT; // Direzione iniziale
        random = new Random();
        lastChangeTime = System.currentTimeMillis();
    }

    private void randomizeDirection() {
        int randomInt = random.nextInt(4);
        switch (randomInt) {
            case 0:
                currentDirection = Direction.UP;
                setAirSpeed(-1);
                setxSpeed(0);
                break;
            case 1:
                currentDirection = Direction.DOWN;
                setAirSpeed(1);
                setxSpeed(0);
                break;
            case 2:
                currentDirection = Direction.LEFT;
                setxSpeed(-1);
                setAirSpeed(0);
                break;
            case 3:
                currentDirection = Direction.RIGHT;
                setxSpeed(1);
                setAirSpeed(0);
                break;
        }
    }

    private void checkAndChangeDirection() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastChangeTime > CHANGE_INTERVAL) {
            randomizeDirection();
            lastChangeTime = currentTime;
        }
    }

    @Override
    public void updateEntity() {
        checkAndChangeDirection(); // Verifica se deve cambiare direzione
        // Aggiornamento della posizione basato sulla direzione corrente
        switch (currentDirection) {
            case LEFT:
            case RIGHT:
                updateXPos();
                break;
            case UP:
            case DOWN:
                updateYPos();
                break;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void updateXPos() {
        if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
            if (HelpMethods.canMoveHere(x + xSpeed, y, width, height)) {
                setX(x + xSpeed);
            } else {
                // Cambia direzione se incontra un ostacolo
                randomizeDirection();
            }
        }
    }

    @Override
    public void updateYPos() {
        if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
            if (HelpMethods.canMoveHere(x, y + airSpeed, width, height)) {
                setY(y + airSpeed);
            } else {
                // Cambia direzione se incontra un ostacolo
                randomizeDirection();
            }
        }
    }
}
