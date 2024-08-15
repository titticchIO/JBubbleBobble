package game.model.bubbles;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

import game.model.Settings;
import game.model.tiles.Tile;

public class BubbleManager{
    private List<Bubble> bubbles;
    private static BubbleManager instance;

    public static BubbleManager getInstance() {
        if (instance == null)
            instance = new BubbleManager();
        return instance;
    }

    private BubbleManager() {
        // Sostituisci ArrayList con CopyOnWriteArrayList
        bubbles = new CopyOnWriteArrayList<>();
    }

    public void createBubble(float x, float y, float xSpeed) {
        Bubble newBubble = new PlayerBubble.Builder(x, y, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1, "B1").xSpeed(xSpeed).build();
        bubbles.add(newBubble);
    }

    public void removeBubble(Bubble bubble) {
        bubbles.remove(bubble);
    }

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void updateBubbles() {
        for (Bubble bubble : bubbles) {
            if (bubble.isPopped()) {
                bubbles.remove(bubble); // Sicuro, poiché CopyOnWriteArrayList gestisce la concorrenza
            } else {
                bubble.updateEntity();
            }
        }
    }
}
